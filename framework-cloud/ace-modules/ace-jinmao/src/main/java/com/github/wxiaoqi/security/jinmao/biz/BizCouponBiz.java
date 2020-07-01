package com.github.wxiaoqi.security.jinmao.biz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.constant.DocPathConstant;
import com.github.wxiaoqi.security.api.vo.household.ProjectInfoVo;
import com.github.wxiaoqi.security.api.vo.order.in.ExcelInfoVo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.Bean2MapUtil;
import com.github.wxiaoqi.security.common.util.DateUtils;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.BizCoupon;
import com.github.wxiaoqi.security.jinmao.entity.BizCouponProduct;
import com.github.wxiaoqi.security.jinmao.entity.BizCouponProject;
import com.github.wxiaoqi.security.jinmao.entity.BizCouponUse;
import com.github.wxiaoqi.security.jinmao.feign.CodeFeign;
import com.github.wxiaoqi.security.jinmao.feign.OssExcelFeign;
import com.github.wxiaoqi.security.jinmao.feign.ToolFegin;
import com.github.wxiaoqi.security.jinmao.mapper.*;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import com.github.wxiaoqi.security.jinmao.vo.coupon.in.CouponParams;
import com.github.wxiaoqi.security.jinmao.vo.coupon.out.CouponListVo;
import com.github.wxiaoqi.security.jinmao.vo.coupon.out.ProductInfoVo;
import com.github.wxiaoqi.security.jinmao.vo.coupon.out.ResultCoupon;
import com.github.wxiaoqi.security.jinmao.vo.coupon.out.UseSituationVo;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 优惠券表
 *
 * @Date 2019-04-16 10:49:40
 */
@Service
public class BizCouponBiz extends BusinessBiz<BizCouponMapper, BizCoupon> {
    private Logger logger = LoggerFactory.getLogger(BizCouponBiz.class);
    @Autowired
    private BizCouponMapper bizCouponMapper;
    @Autowired
    private CodeFeign codeFeign;
    @Autowired
    private BizCouponProjectMapper bizCouponProjectMapper;
    @Autowired
    private BizCouponUseMapper bizCouponUseMapper;
    @Autowired
    private BizCouponProductMapper bizCouponProductMapper;
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    @Autowired
    private OssExcelFeign ossExcelFeign;
    @Autowired
    private BizProductMapper bizProductMapper;
    @Autowired
    private ToolFegin toolFeign;

    public ObjectRestResponse saveCoupon(CouponParams params) {
        ObjectRestResponse response = new ObjectRestResponse();
        BizCoupon bizCoupon = new BizCoupon();
        String id = UUIDUtils.generateUuid();
        if (Integer.valueOf(params.getAmount()) < Integer.valueOf(params.getGetLimit())) {
            response.setStatus(101);
            response.setMessage("券总数必须大于每人领取上限");
            return response;
        }
        if (params.getCouponType().equals("1") && new BigDecimal(params.getMinValue()).compareTo(new BigDecimal(params.getValue())) == -1) {
            response.setStatus(101);
            response.setMessage("最低消费金额必须大于券面值");
            return response;
        }
        bizCoupon.setId(id);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String flowNum = "Y" + bizCouponMapper.getAccountByTenantId(BaseContextHandler.getTenantID())+ sdf.format(new Date());
        ObjectRestResponse code = codeFeign.getCode("CouponFlow"+sdf.format(new Date()), flowNum, "2", "0");
        if (code.getStatus() == 200) {
            bizCoupon.setFlowNum(code.getData()==null?"":(String)code.getData());
        }else {
            bizCoupon.setFlowNum("");
        }
        bizCoupon.setCouponName(params.getCouponName());
        bizCoupon.setCouponType(params.getCouponType());
        bizCoupon.setAmount(Integer.valueOf(params.getAmount()));
        for (ImgInfo imgInfo : params.getCoverPhoto()) {
            if(StringUtils.isNotEmpty(imgInfo.getUrl())){
                ObjectRestResponse objectRestResponse = toolFeign.moveAppUploadUrlPaths(imgInfo.getUrl(), DocPathConstant.SHOP);
                if(objectRestResponse.getStatus()==200){
                    bizCoupon.setCoverPhoto(objectRestResponse.getData()==null ? "" : (String)objectRestResponse.getData());
                }
            }
            //bizCoupon.setCoverPhoto(imgInfo.getUrl());
        }
        bizCoupon.setGetLimit(Integer.valueOf(params.getGetLimit()));
        try {
            Date startUseTime = DateUtils.stringToDate(params.getStartUseTime(), "yyyy-MM-dd HH:mm");
            Date endUseTime = DateUtils.stringToDate(params.getEndUseTime(), "yyyy-MM-dd HH:mm");
            bizCoupon.setStartUseTime(startUseTime);
            bizCoupon.setEndUseTime(endUseTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        bizCoupon.setMinValue(new BigDecimal(params.getMinValue()));
        //代金券
        if ("1".equals(params.getCouponType())) {
            bizCoupon.setValue(new BigDecimal(params.getValue()));
        }else {
            //折扣券
            bizCoupon.setDiscountNum(new BigDecimal(params.getDiscountNum()));
            bizCoupon.setMaxValue(new BigDecimal(params.getMasvalue()));
        }
        if (params.getProductId() != null) {
            bizCoupon.setProductCover("2");
        }else {
            bizCoupon.setProductCover("1");
        }
        bizCoupon.setTenantId(BaseContextHandler.getTenantID());
        bizCoupon.setUseStatus("0");
        bizCoupon.setCreateBy(BaseContextHandler.getUserID());
        bizCoupon.setCreateTime(new Date());
        BizCouponProject bizCouponProject = new BizCouponProject();
        BizCouponProduct bizCouponProduct = new BizCouponProduct();
        if (bizCouponMapper.insertSelective(bizCoupon) > 0) {
            //关联项目
            for (String project: params.getProjectId()) {
                bizCouponProject.setId(UUIDUtils.generateUuid());
                bizCouponProject.setCouponId(id);
                bizCouponProject.setProjectId(project);
                bizCouponProject.setCreateBy(BaseContextHandler.getUserID());
                bizCouponProject.setCreateTime(new Date());
                bizCouponProject.setTimeStamp(new Date());
                if (bizCouponProjectMapper.insertSelective(bizCouponProject) < 0) {
                    logger.error("保存关联数据失败,id{}",id);
                    response.setStatus(102);
                    response.setMessage("保存关联数据失败");
                    return response;
                }
            }
            //关联商品
            if (params.getProductId() != null) {
                for (String product: params.getProductId()) {
                    bizCouponProduct.setId(UUIDUtils.generateUuid());
                    bizCouponProduct.setCouponId(id);
                    bizCouponProduct.setProductId(product);
                    bizCouponProduct.setCreateBy(BaseContextHandler.getUserID());
                    bizCouponProduct.setCreateTime(new Date());
                    bizCouponProduct.setTimeStamp(new Date());
                    if (bizCouponProductMapper.insertSelective(bizCouponProduct) < 0) {
                        logger.error("保存关联数据失败,id{}",id);
                        response.setStatus(103);
                        response.setMessage("保存关联数据失败");
                        return response;
                    }
                }
            }
        }
        response.setData(id);
        return response;
    }

    public ObjectRestResponse updateCoupon(CouponParams params) {
        ObjectRestResponse response = new ObjectRestResponse();
        if (Integer.valueOf(params.getAmount()) < Integer.valueOf(params.getGetLimit())) {
            response.setStatus(101);
            response.setMessage("券总数必须大于每人领取上限");
            return response;
        }
        if (params.getCouponType().equals("1") && new BigDecimal(params.getMinValue()).compareTo(new BigDecimal(params.getValue())) == -1) {
            response.setStatus(101);
            response.setMessage("最低消费金额必须大于券面值");
            return response;
        }
        if (params.getProjectId() == null || params.getProjectId().size() == 0) {
            response.setStatus(101);
            response.setMessage("项目不能为空");
            return response;
        }
        if (params.getProductId() == null || params.getProductId().size() == 0) {
            response.setStatus(101);
            response.setMessage("商品不能为空");
            return response;
        }
        BizCoupon bizCoupon = new BizCoupon();
        BizCoupon coupon = bizCouponMapper.selectByPrimaryKey(params.getId());
        //0-待发布1-已发布2-使用中3-已下架
        if ("2".equals(coupon.getUseStatus())) {
            response.setStatus(101);
            response.setMessage("该优惠券已被用户领取，无法修改");
            return response;
        } else if ("3".equals(coupon.getUseStatus())) {
            int count = bizCouponMapper.getCouponUseCount(params.getId());
            if (count > 0) {
                response.setStatus(101);
                response.setMessage("该优惠券已被用户领取，无法修改");
                return response;
            }
        } else if ("1".equals(coupon.getUseStatus())) {
            response.setStatus(101);
            response.setMessage("已发布的优惠券无法修改，请撤回后修改");
            return response;
        }
        bizCoupon.setId(params.getId());
        bizCoupon.setCouponName(params.getCouponName());
        bizCoupon.setCouponType(params.getCouponType());
        bizCoupon.setAmount(Integer.valueOf(params.getAmount()));
        for (ImgInfo imgInfo : params.getCoverPhoto()) {
            if(StringUtils.isNotEmpty(imgInfo.getUrl())){
                ObjectRestResponse objectRestResponse = toolFeign.moveAppUploadUrlPaths(imgInfo.getUrl(), DocPathConstant.SHOP);
                if(objectRestResponse.getStatus()==200){
                    bizCoupon.setCoverPhoto(objectRestResponse.getData()==null ? "" : (String)objectRestResponse.getData());
                }
            }
            //bizCoupon.setCoverPhoto(imgInfo.getUrl());
        }
        bizCoupon.setGetLimit(Integer.valueOf(params.getGetLimit()));
        try {
            Date startUseTime = DateUtils.stringToDate(params.getStartUseTime(), "yyyy-MM-dd HH:mm");
            Date endUseTime = DateUtils.stringToDate(params.getEndUseTime(), "yyyy-MM-dd HH:mm");
            bizCoupon.setStartUseTime(startUseTime);
            bizCoupon.setEndUseTime(endUseTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        bizCoupon.setMinValue(new BigDecimal(params.getMinValue()));
        if (params.getProductId() != null) {
            bizCoupon.setProductCover("2");
        }else {
            bizCoupon.setProductCover("1");
        }
        //代金券
        if ("1".equals(params.getCouponType())) {
            bizCoupon.setValue(new BigDecimal(params.getValue()));
        }else {
            //折扣券
            bizCoupon.setDiscountNum(new BigDecimal(params.getDiscountNum()));
            bizCoupon.setMaxValue(new BigDecimal(params.getMasvalue()));
        }
        bizCoupon.setModifyBy(BaseContextHandler.getUserID());
        bizCoupon.setModifyTime(new Date());
        BizCouponProject bizCouponProject = new BizCouponProject();
        BizCouponProduct bizCouponProduct = new BizCouponProduct();
        if (bizCouponMapper.updateByPrimaryKeySelective(bizCoupon)>0) {
            int i = bizCouponProjectMapper.deleteProject(params.getId());
            if (i >= 0) {
                for (String project : params.getProjectId()) {
                    bizCouponProject.setId(UUIDUtils.generateUuid());
                    bizCouponProject.setCouponId(params.getId());
                    bizCouponProject.setProjectId(project);
                    bizCouponProject.setCreateBy(BaseContextHandler.getUserID());
                    bizCouponProject.setCreateTime(new Date());
                    bizCouponProject.setTimeStamp(new Date());
                    if (bizCouponProjectMapper.insertSelective(bizCouponProject) < 0) {
                        logger.error("保存关联数据失败,id{}",params.getId());
                        response.setStatus(102);
                        response.setMessage("保存关联数据失败");
                        return response;
                    }
                }
            }
            int j = bizCouponProductMapper.deleteProduct(params.getId());
            if (j >= 0) {
                if (params.getProductId() != null) {
                    for (String product : params.getProductId()) {
                        bizCouponProduct.setId(UUIDUtils.generateUuid());
                        bizCouponProduct.setCouponId(params.getId());
                        bizCouponProduct.setProductId(product);
                        bizCouponProduct.setCreateBy(BaseContextHandler.getUserID());
                        bizCouponProduct.setCreateTime(new Date());
                        bizCouponProduct.setTimeStamp(new Date());
                        if (bizCouponProductMapper.insertSelective(bizCouponProduct) < 0) {
                            logger.error("保存关联数据失败,id{}",params.getId());
                            response.setStatus(103);
                            response.setMessage("保存关联数据失败");
                            return response;
                        }
                    }
                }
            }
        }
        return response;
    }

    public List<CouponListVo> getCouponList(String projectId,String searchVal,String useStatus,Integer page,Integer limit) {
        if (page == null || page.equals("")) {
            page = 1;
        }
        if (limit == null || limit.equals("")) {
            limit = 10;
        }
        if(page == 0) {
            page = 1;
        }
        //分页
        Integer startIndex = (page - 1) * limit;
        List<CouponListVo> couponList = bizCouponMapper.getCouponList(BaseContextHandler.getTenantID(),projectId, searchVal, useStatus, startIndex, limit);
        if (couponList == null || couponList.size() == 0) {
            couponList = new ArrayList<>();
        }else {
            for (CouponListVo couponListVo : couponList) {
                List<String> projectNames = bizCouponMapper.getProjectByCouponId(couponListVo.getId());
                if(projectNames != null && projectNames.size()>0){
                    String projectName = "";
                    StringBuilder sb = new StringBuilder();
                    for (String project:projectNames){
                        sb.append(project + ",");
                    }
                    System.out.println(sb);
                    projectName = sb.substring(0, sb.length()-1);
                    couponListVo.setProjectName(projectName);
                }else {
                    couponListVo.setProjectName("");
                }
            }
        }

        return couponList;
    }


    public int getCouponTotal(String searchVal, String useStatus) {
        int couponTotal = bizCouponMapper.getCouponTotal(BaseContextHandler.getTenantID(), searchVal, useStatus);
        return couponTotal;
    }

    public List<UseSituationVo> getUseSituation(String couponId,String useStatus,Integer page,Integer limit) {
        if (page == null || page.equals("")) {
            page = 1;
        }
        if (limit == null || limit.equals("")) {
            limit = 10;
        }
        if(page == 0) {
            page = 1;
        }
        //分页
        Integer startIndex = (page - 1) * limit;
        List<UseSituationVo> useSituation = bizCouponMapper.getUseSituation(couponId,useStatus,startIndex,limit);
        if (useSituation == null || useSituation.size() == 0) {
            useSituation = new ArrayList<>();
        }
        return useSituation;
    }
    public int getUseSituationTotal(String couponId,String useStatus) {
        return bizCouponMapper.getUseSituationTotal(couponId,useStatus);
    }


    public ObjectRestResponse getCouponDetail(String id) {
        ObjectRestResponse response = new ObjectRestResponse();
        if (StringUtils.isEmpty(id)) {
            response.setStatus(101);
            response.setMessage("ID不能为空");
            return response;
        }
        ResultCoupon resultCoupon = bizCouponMapper.getCouponDetail(id);
        List<ProductInfoVo> couponProduct = bizCouponMapper.getCouponProduct(id);
        List<ProjectInfoVo> couponProject = bizCouponMapper.getCouponProject(id);
        if (couponProduct.size() != 0) {
            resultCoupon.setProductInfo(couponProduct);
        }else {
            resultCoupon.setProductInfo(new ArrayList<>());
        }
        if (couponProject.size() != 0) {
            resultCoupon.setProjectInfo(couponProject);
        }else {
            resultCoupon.setProjectInfo(new ArrayList<>());
        }
        if (resultCoupon.getCoverPhotoImage()!=null) {
            List<ImgInfo> list = new ArrayList<>();
            ImgInfo imge = new ImgInfo();
            imge.setUrl(resultCoupon.getCoverPhotoImage());
            list.add(imge);
            resultCoupon.setCoverPhoto(list);
        }

        if (resultCoupon == null) {
            resultCoupon = new ResultCoupon();
        }
        response.setData(resultCoupon);
        return response;
    }

    public ObjectRestResponse operateCouponStatus(String couponId, String useStatus) {
        ObjectRestResponse response = new ObjectRestResponse();
        String createBy = BaseContextHandler.getUserID();
        //发布
        //生成优惠券
        if ("1".equals(useStatus)) {
            try {
                new Thread(() -> {
                    createCoupon(couponId,createBy);
                }).start();
            } catch (Exception e) {
                logger.info("生成优惠券异常，优惠券ID为{}",couponId);
            }
        }

        //撤回
        //删除优惠券
        if ("0".equals(useStatus)) {
            bizCouponMapper.updateCouponUseStatus(couponId);
        }
        BizCoupon bizCoupon = new BizCoupon();
        bizCoupon.setId(couponId);
        bizCoupon.setUseStatus(useStatus);
        bizCouponMapper.updateByPrimaryKeySelective(bizCoupon);

        return response;
    }

    public void createCoupon(String couponId,String userId) {
        BizCoupon coupon = bizCouponMapper.selectByPrimaryKey(couponId);
        SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {

            for (int i = 0; i < coupon.getAmount(); i++) {
                BizCouponUse bizCouponUse = new BizCouponUse();
                bizCouponUse.setId(UUIDUtils.generateUuid());
                bizCouponUse.setCouponId(couponId);
                ObjectRestResponse<String> couponCode = codeFeign.getCode("CouponUse", coupon.getFlowNum(), "5", "0");
                if (couponCode.getStatus() == 200) {
                    bizCouponUse.setCouponCode(couponCode.getData()==null?"":(String)couponCode.getData());
                }
                bizCouponUse.setCreateBy(userId);
                bizCouponUse.setCreateTime(new Date());
                session.insert("com.github.wxiaoqi.security.jinmao.mapper.BizCouponUseMapper.insertSelective", bizCouponUse);
                if ((i>0 && i % 1000 == 0) || i == coupon.getAmount() - 1) {
                    // 手动每1000个一提交，提交后无法回滚
                    session.commit();
                    // 清理缓存，防止溢出
                    session.clearCache();
                }
                logger.info("生成优惠券，couponId为{}，生成第{}张",couponId,i+1);
            }
        } catch (Exception e) {
        // 没有提交的数据可以回滚
            session.rollback();
            e.printStackTrace();
            logger.info("生成优惠券发生异常");
        } finally {
            session.close();
        }
    }


    public ObjectRestResponse getProductByProject(List<String> projectList) {
        ObjectRestResponse response = new ObjectRestResponse();
        if (projectList == null) {
            response.setStatus(101);
            response.setMessage("项目ID不能为空");
            return response;
        }
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        List<ProductInfoVo> product = bizCouponMapper.getProductByProject(type,projectList,BaseContextHandler.getTenantID());
        if (product == null || product.size() == 0) {
            product = new ArrayList<>();
        }
        response.setData(product);
        return response;
    }

    public ObjectRestResponse getCouponExcel(String couponId, String useStatus) {
        ObjectRestResponse response = new ObjectRestResponse();
        List<UseSituationVo> result = bizCouponMapper.getUseSituation(couponId, useStatus, null, null);
        if (result.size() == 0) {
            response.setMessage("没有数据，导出失败");
            response.setStatus(101);
            return response;
        }
        List<Map<String, Object>> dataList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        if (result != null && result.size() > 0) {
            for (int i = 0; i < result.size(); i++) {
                UseSituationVo temp = mapper.convertValue(result.get(i), UseSituationVo.class);
                Map<String, Object> dataMap = Bean2MapUtil.transBean2Map(temp);
                dataList.add(dataMap);
            }
        }
        String[] titles = {"优惠券编码","用户姓名","用户电话","订单号","使用状态"};
        String[] keys = {"couponCode","userName","phone","subCode","useStatusStr"};
        String fileName = "优惠券.xlsx";

        ExcelInfoVo excelInfoVo = new ExcelInfoVo();
        excelInfoVo.setTitles(titles);
        excelInfoVo.setKeys(keys);
        excelInfoVo.setDataList(dataList);
        excelInfoVo.setFileName(fileName);
        response = ossExcelFeign.uploadExcel(excelInfoVo);
        return response;
    }
}