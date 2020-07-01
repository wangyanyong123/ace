package com.github.wxiaoqi.security.jinmao.biz;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.constant.DocPathConstant;
import com.github.wxiaoqi.security.api.vo.order.in.ExcelInfoVo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.Bean2MapUtil;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.BizIntegralProduct;
import com.github.wxiaoqi.security.jinmao.entity.BizProductLabel;
import com.github.wxiaoqi.security.jinmao.entity.BizProductProject;
import com.github.wxiaoqi.security.jinmao.feign.CodeFeign;
import com.github.wxiaoqi.security.jinmao.feign.OssExcelFeign;
import com.github.wxiaoqi.security.jinmao.feign.ToolFegin;
import com.github.wxiaoqi.security.jinmao.mapper.BizCashProductMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizIntegralProductMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizProductLabelMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizProductProjectMapper;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ResultProjectVo;
import com.github.wxiaoqi.security.jinmao.vo.integralproduct.in.SaveIntegralProductParam;
import com.github.wxiaoqi.security.jinmao.vo.integralproduct.in.SaveProductInfo;
import com.github.wxiaoqi.security.jinmao.vo.integralproduct.in.SaveSpecInfo;
import com.github.wxiaoqi.security.jinmao.vo.integralproduct.in.UpdateStatusParam;
import com.github.wxiaoqi.security.jinmao.vo.integralproduct.out.CashProductVo;
import com.github.wxiaoqi.security.jinmao.vo.integralproduct.out.IntegralProductInfo;
import com.github.wxiaoqi.security.jinmao.vo.integralproduct.out.IntegralProductVo;
import com.github.wxiaoqi.security.jinmao.vo.integralproduct.out.ProductInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 积分商品表
 *
 * @author huangxl
 * @Date 2019-08-28 10:04:24
 */
@Service
public class BizIntegralProductBiz extends BusinessBiz<BizIntegralProductMapper,BizIntegralProduct> {

    private Logger logger = LoggerFactory.getLogger(BizIntegralProductBiz.class);
    @Autowired
    private BizIntegralProductMapper bizIntegralProductMapper;
    @Autowired
    private BizProductProjectMapper bizProductProjectMapper;
    @Autowired
    private BizProductLabelMapper bizProductLabelMapper;
    @Autowired
    private BizCashProductMapper bizCashProductMapper;
    @Autowired
    private CodeFeign codeFeign;
    @Autowired
    private ToolFegin toolFeign;
    @Autowired
    private OssExcelFeign ossExcelFeign;


    /**
     * 查询积分商品列表
     * @param projectId
     * @param isRecommend
     * @param busStatus
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    public List<IntegralProductVo> getIntegralProductList(String projectId,String isRecommend,String busStatus, String searchVal,
                                                          Integer page, Integer limit){
        if (page == null || "".equals(page)) {
            page = 1;
        }
        if (limit == null || "".equals(limit)) {
            limit = 10;
        }
        if(page == 0) {
            page = 1;
        }
        //分页
        Integer startIndex = (page - 1) * limit;
        List<IntegralProductVo> productVoList = bizIntegralProductMapper.selectIntegralProductList(projectId, isRecommend,
                busStatus, searchVal, startIndex, limit);
        if(productVoList != null && productVoList.size() > 0){
            for (IntegralProductVo productVo : productVoList){
                List<String> projectNames = bizProductProjectMapper.selectIntegralProjectIdById(productVo.getId());
                if(projectNames != null && projectNames.size()>0){
                    String projectName = "";
                    StringBuilder resultEva = new StringBuilder();
                    for (String project:projectNames){
                        resultEva.append(project + ",");
                    }
                    if(resultEva.toString() != null && resultEva.length() > 0){
                        projectName = resultEva.substring(0, resultEva.length() - 1);
                    }
                    productVo.setProjectName(projectName);
                }
            }
        }
        return productVoList;
    }

    /**
     * 查询积分商品列表数量
     * @param projectId
     * @param isRecommend
     * @param busStatus
     * @param searchVal
     * @return
     */
    public int selectIntegralProduct(String projectId,String isRecommend,String busStatus, String searchVal){
        return bizIntegralProductMapper.selectIntegralProductCount(projectId, isRecommend, busStatus, searchVal);
    }


    /**
     * 保存积分商品
     * @param param
     * @return
     */
    public ObjectRestResponse  saveIntegralProduct(SaveIntegralProductParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        SaveProductInfo productInfo = param.getProductInfo();
        String productImagetextInfo = param.getProductImagetextInfo();
        SaveSpecInfo specInfo = param.getSpecInfo();
//        if ("2".equals(productInfo.getProductClassify())) {
//            int result =  bizIntegralProductMapper.getResignCard(productInfo.getProjectVo());
//            if (result > 0) {
//                msg.setStatus(1001);
//                msg.setMessage("补签卡已经存在，不能再次添加");
//                return msg;
//            }
//        }
        ObjectRestResponse objectRestResponse = codeFeign.getCode("IntegralProduct","SP1","5","0");
        logger.info("生成积分商品编码处理结果："+objectRestResponse.getData());
        if(param == null){
            msg.setStatus(1001);
            msg.setMessage("参数不能为空");
            return msg;
        }
        if(productInfo == null || specInfo == null){
            msg.setStatus(1002);
            msg.setMessage("商品基本信息不能为空");
            return msg;
        }
        if(StringUtils.isAnyEmpty(productInfo.getProductName(),productInfo.getProductName(),specInfo.getSpecName(),
                specInfo.getSpecIntegral())){
            msg.setStatus(1003);
            msg.setMessage("商品名称/总数量/规格名称/规格积分不能为空");
            return msg;
        }
        if(productInfo.getProductImageList() == null || productInfo.getProductImageList().size() == 0){
            msg.setStatus(201);
            msg.setMessage("商品封面不能为空!");
            return msg;
        }
        if(productInfo.getSelectionImageList() == null || productInfo.getSelectionImageList().size() == 0){
            msg.setStatus(201);
            msg.setMessage("商品精选图片不能为空!");
            return msg;
        }
        if(productInfo.getProjectVo() == null || productInfo.getProjectVo().size() == 0){
            msg.setStatus(201);
            msg.setMessage("项目范围不能为空!");
            return msg;
        }
        StringBuilder sb = new StringBuilder();
        BizIntegralProduct product = new BizIntegralProduct();
        String id = UUIDUtils.generateUuid();
        product.setId(id);
        if(objectRestResponse.getStatus()==200){
            product.setProductCode(objectRestResponse.getData()==null?"":(String)objectRestResponse.getData());
        }
        product.setProductName(productInfo.getProductName());
        product.setBusStatus("1");
        product.setProductClassify(productInfo.getProductClassify());
        for(ImgInfo temp: productInfo.getProductImageList()){
            if(StringUtils.isNotEmpty(temp.getUrl())){
                ObjectRestResponse response = toolFeign.moveAppUploadUrlPaths(temp.getUrl(), DocPathConstant.SHOP);
                if(response.getStatus()==200){
                    product.setProductImage(response.getData()==null ? "" : (String)response.getData());
                }
            }
            //product.setProductImage(temp.getUrl());
        }

        String selectImages = "";
        for(ImgInfo temp: productInfo.getSelectionImageList()){
            sb.append(temp.getUrl()+",");
        }
        if(sb.toString() != null && sb.length() > 0){
            selectImages = sb.substring(0,sb.length()-1);
        }
        if(StringUtils.isNotEmpty(selectImages)){
            ObjectRestResponse response = toolFeign.moveAppUploadUrlPaths(selectImages, DocPathConstant.SHOP);
            if(response.getStatus()==200){
                product.setSelectionImage(response.getData()==null ? "" : (String)response.getData());
            }
        }
        //product.setSelectionImage(selectImages);
        product.setProductImagetextInfo(productImagetextInfo);
        product.setProductNum(Integer.parseInt(productInfo.getProductNum()));
        product.setSpecName(specInfo.getSpecName());
        product.setSpecIntegral(Integer.parseInt(specInfo.getSpecIntegral()));
        product.setSpecUnit(specInfo.getUnit());
        product.setCreateBy(BaseContextHandler.getUserID());
        product.setTimeStamp(new Date());
        product.setCreateTime(new Date());
        if(bizIntegralProductMapper.insertSelective(product) > 0){
            //关联服务范围
            BizProductProject project = new BizProductProject();
            for (ResultProjectVo temp : productInfo.getProjectVo()){
                project.setId(UUIDUtils.generateUuid());
                project.setProductId(id);
                project.setProjectId(temp.getId());
                project.setCreateBy(BaseContextHandler.getUserID());
                project.setCreateTime(new Date());
                project.setTimeStamp(new Date());
                if(bizProductProjectMapper.insertSelective(project) < 0){
                    msg.setStatus(101);
                    msg.setMessage("关联服务范围失败!");
                    return msg;
                }
            }
            //关联商品标签
            BizProductLabel label = new BizProductLabel();
            for (String lab : productInfo.getLabel()){
                label.setId(UUIDUtils.generateUuid());
                label.setProductId(id);
                label.setLabel(lab);
                label.setCreateBy(BaseContextHandler.getUserID());
                label.setCreateTime(new Date());
                label.setTimeStamp(new Date());
                if(bizProductLabelMapper.insertSelective(label) < 0){
                    msg.setStatus(104);
                    msg.setMessage("关联商品标签失败!");
                    return msg;
                }
            }
        }else{
            msg.setStatus(105);
            msg.setMessage("保存积分商品失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    /**
     * 查询积分商品详情
     * @param id
     * @return
     */
    public List<IntegralProductInfo> getIntegralProductInfo(String id){
        List<IntegralProductInfo> result = new ArrayList<>();
        IntegralProductInfo integralProductInfo = new IntegralProductInfo();
        ProductInfo productInfo =  bizIntegralProductMapper.selectIntegralProductInfo(id);
        if(productInfo != null){
            //项目范围
            List<ResultProjectVo> projectVoList = bizProductProjectMapper.selectIntegralProjectList(id);
            productInfo.setProjectVo(projectVoList);
            //商品标签
            List<String> labelList = bizProductLabelMapper.selectLabelList(id);
            productInfo.setLabel(labelList);
            //组装商品规格
            SaveSpecInfo specInfo = new SaveSpecInfo();
            specInfo.setSpecName(productInfo.getSpecName());
            specInfo.setSpecIntegral(productInfo.getSpecIntegral());
            specInfo.setUnit(productInfo.getUnit());

            integralProductInfo.setSpecInfo(specInfo);
            integralProductInfo.setProductInfo(productInfo);
            integralProductInfo.setProductImagetextInfo(productInfo.getProductImagetextInfo());
        }
        result.add(integralProductInfo);
        return result;
    }


    /**
     * 编辑积分商品
     * @param param
     * @return
     */
    public ObjectRestResponse  updateIntegralProduct(SaveIntegralProductParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(param == null){
            msg.setStatus(1001);
            msg.setMessage("参数不能为空");
            return msg;
        }
        SaveProductInfo productParam = param.getProductInfo();
        String productImagetextInfo = param.getProductImagetextInfo();
        SaveSpecInfo specInfo = param.getSpecInfo();
        if(productParam == null || specInfo == null){
            msg.setStatus(1002);
            msg.setMessage("商品基本信息不能为空");
            return msg;
        }
        if(StringUtils.isAnyEmpty(productParam.getProductName(),productParam.getProductName(),specInfo.getSpecName(),
                specInfo.getSpecIntegral())){
            msg.setStatus(1003);
            msg.setMessage("商品名称/总数量/规格名称/规格积分不能为空");
            return msg;
        }
        if(productParam.getProductImageList() == null || productParam.getProductImageList().size() == 0){
            msg.setStatus(201);
            msg.setMessage("商品封面不能为空!");
            return msg;
        }
        if(productParam.getSelectionImageList() == null || productParam.getSelectionImageList().size() == 0){
            msg.setStatus(201);
            msg.setMessage("商品精选图片不能为空!");
            return msg;
        }
        if(productParam.getProjectVo() == null || productParam.getProjectVo().size() == 0){
            msg.setStatus(201);
            msg.setMessage("项目范围不能为空!");
            return msg;
        }
        StringBuilder sb = new StringBuilder();
        BizIntegralProduct product = new BizIntegralProduct();
        ProductInfo productInfo =  bizIntegralProductMapper.selectIntegralProductInfo(productParam.getId());
//        if (productParam.getId().equals(productInfo.getId())) {
//            int result =  bizIntegralProductMapper.getResignCard(productParam.getProjectVo());
//            if (!productParam.getProductClassify().equals(productInfo.getProductClassify()) && result > 0) {
//                msg.setStatus(201);
//                msg.setMessage("补签卡已经存在，不能再次添加!");
//                return msg;
//            }else if ("2".equals(productParam.getProductClassify())){
//                msg.setStatus(201);
//                msg.setMessage("补签卡已经存在，不能再次添加!");
//                return msg;
//            }
//        }
        if(productInfo != null){
            String cash = "2";
            if(cash.equals(productInfo.getBusStatus())){
                msg.setStatus(1101);
                msg.setMessage("该商品已上架,请先下架商品,再进行修改!");
                return msg;
            }
            BeanUtils.copyProperties(productParam,product);
            product.setProductName(productParam.getProductName());
            product.setProductClassify(productParam.getProductClassify());
            for(ImgInfo temp: productParam.getProductImageList()){
                if(StringUtils.isNotEmpty(temp.getUrl())){
                    ObjectRestResponse response = toolFeign.moveAppUploadUrlPaths(temp.getUrl(), DocPathConstant.SHOP);
                    if(response.getStatus()==200){
                        product.setProductImage(response.getData()==null ? "" : (String)response.getData());
                    }
                }
                //product.setProductImage(temp.getUrl());
            }
            String selectImages = "";
            for(ImgInfo temp: productParam.getSelectionImageList()){
                sb.append(temp.getUrl()+",");
            }
            if(sb.toString() != null && sb.length() > 0){
                selectImages = sb.substring(0,sb.length()-1);
            }
            if(StringUtils.isNotEmpty(selectImages)){
                ObjectRestResponse response = toolFeign.moveAppUploadUrlPaths(selectImages, DocPathConstant.SHOP);
                if(response.getStatus()==200){
                    product.setSelectionImage(response.getData()==null ? "" : (String)response.getData());
                }
            }
            //product.setSelectionImage(selectImages);
            product.setProductImagetextInfo(productImagetextInfo);
            product.setProductNum(Integer.parseInt(productParam.getProductNum()));
            product.setSpecName(specInfo.getSpecName());
            product.setSpecIntegral(Integer.parseInt(specInfo.getSpecIntegral()));
            product.setSpecUnit(specInfo.getUnit());
            product.setModifyBy(BaseContextHandler.getUserID());
            product.setModifyTime(new Date());
            if(bizIntegralProductMapper.updateByPrimaryKeySelective(product) > 0){
                //编辑项目范围
                bizProductProjectMapper.delProjectInfo(productParam.getId());
                BizProductProject project = new BizProductProject();
                for (ResultProjectVo temp : productParam.getProjectVo()){
                    project.setId(UUIDUtils.generateUuid());
                    project.setProductId(productParam.getId());
                    project.setProjectId(temp.getId());
                    project.setCreateBy(BaseContextHandler.getUserID());
                    project.setCreateTime(new Date());
                    project.setTimeStamp(new Date());
                    if(bizProductProjectMapper.insertSelective(project) < 0){
                        msg.setStatus(101);
                        msg.setMessage("关联服务范围失败!");
                        return msg;
                    }
                }
                //编辑商品标签
                bizProductLabelMapper.delLabelfo(productParam.getId());
                BizProductLabel label = new BizProductLabel();
                for (String lab : productParam.getLabel()){
                    label.setId(UUIDUtils.generateUuid());
                    label.setProductId(productParam.getId());
                    label.setLabel(lab);
                    label.setCreateBy(BaseContextHandler.getUserID());
                    label.setCreateTime(new Date());
                    label.setTimeStamp(new Date());
                    if(bizProductLabelMapper.insertSelective(label) < 0){
                        msg.setStatus(104);
                        msg.setMessage("关联商品标签失败!");
                        return msg;
                    }
                }

            }else{
                msg.setStatus(103);
                msg.setMessage("编辑积分商品失败!");
                return msg;
            }
        }else{
            msg.setStatus(104);
            msg.setMessage("查无此详情!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 商品上架与下架操作
     * @param param
     * @return
     */
    public ObjectRestResponse updateProductStatus(UpdateStatusParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        //通过apollo获取推荐限制数
        Config config = ConfigService.getConfig("ace-jinmao");
        String num = config.getProperty("recommend.limitNum", "");
        if(param == null){
            msg.setStatus(1001);
            msg.setMessage("参数不能为空");
            return msg;
        }
        String op = "1";
        if(op.equals(param.getType())){
            //上架
            String top = "2";
            if(top.equals(param.getStatus())){
                //项目范围
                List<ResultProjectVo> projectVoList = bizProductProjectMapper.selectIntegralProjectList(param.getId());
                if (param.getProjectId().size() > 0) {
                    int result =  bizIntegralProductMapper.getResignCardTop(param.getProjectId());
                    if (result > 0) {
                        msg.setStatus(101);
                        msg.setMessage("补签卡已上架，不能重复上架");
                        return msg;
                    }
                }
                if(bizIntegralProductMapper.updateAuditStatus(param.getId(),BaseContextHandler.getUserID()) <=0){
                    msg.setStatus(101);
                    msg.setMessage("上架失败!");
                    return msg;
                }
            }else{
                if(bizIntegralProductMapper.updateSoldStatus(param.getId(),BaseContextHandler.getUserID()) <=0){
                    msg.setStatus(102);
                    msg.setMessage("下架失败!");
                    return msg;
                }
            }
        }else{
            if(op.equals(param.getIsRecommend())){
                if(num == null || StringUtils.isEmpty(num)){
                    num = "6";
                }
                if(Integer.parseInt(num) <= bizIntegralProductMapper.selectRecommendCount()){
                    msg.setStatus(103);
                    msg.setMessage("积分商品最多可推荐6个!");
                    return msg;
                }
            }
           if(bizIntegralProductMapper.updateRecommendStatus(param.getIsRecommend(),param.getId(),BaseContextHandler.getUserID()) <= 0){
               msg.setStatus(103);
               msg.setMessage("操作失败!");
               return msg;
           }
        }
        return msg;
    }


    /**
     * 查询商品兑换列表
     * @param projectId
     * @param startTime
     * @param endTime
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    public List<CashProductVo> getCashProductList(String projectId, String startTime, String endTime,String searchVal,
                                                  Integer page, Integer limit){
        if (page == null || "".equals(page)) {
            page = 1;
        }
        if (limit == null || "".equals(limit)) {
            limit = 10;
        }
        if(page == 0) {
            page = 1;
        }
        //分页
        Integer startIndex = (page - 1) * limit;
        List<CashProductVo> cashProductVoList = bizCashProductMapper.selectCashProductList(projectId, startTime, endTime, searchVal, startIndex, limit);
        return cashProductVoList;
    }

    /**
     * 查询商品兑换列表数量
     * @param projectId
     * @param startTime
     * @param endTime
     * @param searchVal
     * @return
     */
    public int selectCashProductCount(String projectId, String startTime, String endTime,String searchVal){
        return bizCashProductMapper.selectCashProductCount(projectId, startTime, endTime, searchVal);
    }


    /**
     * 查询商品兑换详情
     * @param id
     * @return
     */
    public List<CashProductVo> getCashProductInfo(String id){
        List<CashProductVo> result = new ArrayList<>();
        CashProductVo productInfo =  bizCashProductMapper.selectCashProductInfo(id);
        result.add(productInfo);
        return result;
    }


    /**
     * 导出兑换列表
     * @param projectId
     * @param startTime
     * @param endTime
     * @param searchVal
     * @return
     */
    public ObjectRestResponse exportCashListExcel(String projectId, String startTime, String endTime,String searchVal){
        ObjectRestResponse msg = new ObjectRestResponse();
        List<Map<String, Object>> dataList = new ArrayList<>();
        List<CashProductVo> cashProductVoList = bizCashProductMapper.selectCashProductList(projectId, startTime, endTime, searchVal, null, null);
        if(cashProductVoList == null || cashProductVoList.size() == 0){
            msg.setStatus(102);
            msg.setMessage("没有数据，导出失败！");
            return msg;
        }
        ObjectMapper mapper = new ObjectMapper();
        if (cashProductVoList != null && cashProductVoList.size() > 0) {
            for (int i = 0; i < cashProductVoList.size(); i++) {
                CashProductVo temp = mapper.convertValue(cashProductVoList.get(i), CashProductVo.class);
                Map<String, Object> dataMap = Bean2MapUtil.transBean2Map(temp);
                dataList.add(dataMap);
            }
        }
        String[] titles = {"兑换编号","商品名称","所属项目","客户名称","联系方式","积分","地址","兑换时间"};
        String[] keys = {"cashCode","productName","projectName","userName","mobilePhone","cashIntegral","addr","cashTime"};
        String fileName = "积分商品兑换记录.xlsx";
        ExcelInfoVo excelInfoVo = new ExcelInfoVo();
        excelInfoVo.setTitles(titles);
        excelInfoVo.setKeys(keys);
        excelInfoVo.setDataList(dataList);
        excelInfoVo.setFileName(fileName);
        msg = ossExcelFeign.uploadExcel(excelInfoVo);
        return msg;
    }


    public List<CashProductVo> getResignCardList(String startTime, String endTime, String searchVal, Integer page, Integer limit) {
        if (page == null || "".equals(page)) {
            page = 1;
        }
        if (limit == null || "".equals(limit)) {
            limit = 10;
        }
        if(page == 0) {
            page = 1;
        }
        //分页
        Integer startIndex = (page - 1) * limit;
        List<CashProductVo> resignCardList = bizCashProductMapper.getResignCardList(startTime, endTime, searchVal, startIndex, limit);
        return resignCardList;
    }

    public int getResignCardCount(String startTime, String endTime, String searchVal) {
        return bizCashProductMapper.getResignCardCount(startTime, endTime, searchVal);
    }
}