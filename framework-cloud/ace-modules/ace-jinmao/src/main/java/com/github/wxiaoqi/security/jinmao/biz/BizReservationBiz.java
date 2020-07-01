package com.github.wxiaoqi.security.jinmao.biz;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.constant.DocPathConstant;
import com.github.wxiaoqi.security.api.vo.order.in.SearchSubInWeb;
import com.github.wxiaoqi.security.api.vo.order.out.SubListForWebVo;
import com.github.wxiaoqi.security.api.vo.store.CacheStore;
import com.github.wxiaoqi.security.api.vo.store.CacheStoreQuery;
import com.github.wxiaoqi.security.api.vo.store.pc.PCStore;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.BusinessConstant;
import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.*;
import com.github.wxiaoqi.security.jinmao.feign.*;
import com.github.wxiaoqi.security.jinmao.mapper.*;
import com.github.wxiaoqi.security.jinmao.vo.Product.InputParam.UpdateSpecVo;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ResultClassifyVo;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ResultProjectVo;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ResultSpecVo;
import com.github.wxiaoqi.security.jinmao.vo.reservat.in.ReservationParam;
import com.github.wxiaoqi.security.jinmao.vo.reservat.in.ReservationParamInfo;
import com.github.wxiaoqi.security.jinmao.vo.reservat.in.UpdateReservatParam;
import com.github.wxiaoqi.security.jinmao.vo.reservat.in.UpdateStatusParam;
import com.github.wxiaoqi.security.jinmao.vo.reservat.out.*;
import com.github.wxiaoqi.security.jinmao.vo.statement.out.TenameInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 预约服务表
 *
 * @author huangxl
 * @Date 2019-03-12 09:26:32
 */
@Slf4j
@Service
public class BizReservationBiz extends BusinessBiz<BizReservationMapper,BizReservation> {
    private Logger logger = LoggerFactory.getLogger(BizReservationBiz.class);
    @Autowired
    private BizReservationMapper bizReservationMapper;
    @Autowired
    private BizProductMapper bizProductMapper;
    @Autowired
    private BizSubProductMapper bizSubProductMapper;
    @Autowired
    private BizProductProjectMapper bizProductProjectMapper;
    @Autowired
    private BizProductClassifyMapper bizProductClassifyMapper;
    @Autowired
    private CodeFeign codeFeign;
    @Autowired
    private OrderEngineFeign orderEngineFeign;
    @Autowired
    private ToolFegin toolFeign;
    @Autowired
    private BizProductLabelMapper bizProductLabelMapper;
    @Autowired
    private BizProductSpecMapper bizProductSpecMapper;
    @Autowired
    private AdminFeign adminFeign;
    @Autowired
    private StoreFegin storeFegin;



    /**
     * 查询服务列表
     * @param reservaStatus
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    public List<ReservationList> getReservationList(String projectId,String classifyId, String reservaStatus,
                                                    String searchVal, Integer page, Integer limit) {
        if (page == null || page.equals("")) {
            page = 1;
        }
        if (limit == null || limit.equals("")) {
            limit = 10;
        }
        if (page == 0) {
            page = 1;
        }
        //分页
        Integer startIndex = (page - 1) * limit;
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        List<ReservationList> reservationList = bizReservationMapper.selectReservationList(type,BaseContextHandler.getTenantID(),
                reservaStatus,classifyId,projectId,searchVal,startIndex,limit);
        return reservationList;
    }

//    public List<ReservationList> getReservationForAD(String projectId, String reservaStatus,
//                                                    String searchVal, Integer page, Integer limit) {
//        Integer startIndex = null;
//        if (page != null && limit!=null) {
//            startIndex = (page - 1) * limit;
//        }
//
//        List<String> projectIdList = Arrays.asList(projectId.split(","));
//        List<String> projectNameList = bizFamilyAdvertisingProjectMapper.selectProjectNameList(projectIdList);
//        List<ReservationList> reservationList = bizReservationMapper.getReservationForAD(reservaStatus,projectIdList,searchVal,startIndex,limit);
//        for (ReservationList reservation : reservationList){
//            List<String> projectNames = bizReservationMapper.selectProjectIdById(reservation.getId());
//            if(projectNames != null && projectNames.size()>0){
//                String projectName = "";
//                StringBuilder resultEva = new StringBuilder();
//                for (String project:projectNames){
//                    resultEva.append(project + ",");
//                }
//                projectName = resultEva.substring(0, resultEva.length() - 1);
//                reservation.setProjectName(projectName);
//            }
//            List<String> classifyNames = bizReservationMapper.selectClassifyNameById(reservation.getId());
//            if(classifyNames != null && classifyNames.size()>0){
//                String classifyName = "";
//                StringBuilder resultEva = new StringBuilder();
//                for (String classify:classifyNames){
//                    resultEva.append(classify + ",");
//                }
//                classifyName = resultEva.substring(0, resultEva.length() - 1);
//                reservation.setClassifyName(classifyName);
//            }
//        }
//        List<ReservationList> newList = new ArrayList<>();
//        for (ReservationList reservation : reservationList) {
//            int temp = 1;
//            for(String str : projectNameList){
//                if(reservation.getProjectName().contains(str)){
//                    temp = 1;
//                }else{
//                    temp = 0;
//                    break;
//                }
//            }
//            if(temp ==1){
//                newList.add(reservation);
//            }
//        }
//        return newList;
//    }

    public int selectReservationCount(String projectId,String classifyId, String reservaStatus, String searchVal){
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        int total = bizReservationMapper.selectReservationCount(type,BaseContextHandler.getTenantID(),
                reservaStatus,classifyId,projectId,searchVal);
        return total;
    }



    /**
     * 保存服务
     * @param info
     * @return
     */
    @Transactional
    public ObjectRestResponse saveReservation(ReservationParamInfo info){
        // 库存集合
        List<PCStore> pcStores = new ArrayList<>();
        ObjectRestResponse msg = new ObjectRestResponse();
        ObjectRestResponse objectRestResponse = codeFeign.getCode("Reservat","R","6","0");
        logger.info("生成商品编码处理结果："+objectRestResponse.getData());
        //获取计费类型
        Map<String, String> map =adminFeign.getDictValue("bill");
        ReservationParam param = info.getProductInfo();
        List<UpdateSpecVo> specInfo = info.getSpecInfo();
        String productImagetextInfo = info.getProductImagetextInfo();
        if(StringUtils.isEmpty(param.getName())){
            msg.setStatus(201);
            msg.setMessage("服务名称不能为空!");
            return msg;
        }
        if(param.getProductImageList() == null || param.getProductImageList().size() == 0){
            msg.setStatus(201);
            msg.setMessage("封面logo不能为空!");
            return msg;
        }
        if(param.getSelectionImageList() == null || param.getSelectionImageList().size() == 0){
            msg.setStatus(201);
            msg.setMessage("精选图片不能为空!");
            return msg;
        }
        if(param.getClassifyVo() == null || param.getClassifyVo().size() == 0){
            msg.setStatus(201);
            msg.setMessage("服务所属分类不能为空!");
            return msg;
        }
        if(param.getProjectVo() == null || param.getProjectVo().size() == 0){
            msg.setStatus(201);
            msg.setMessage("项目范围不能为空!");
            return msg;
        }
        //2019.11.01新增字段  -1 代表无限制
        if(StringUtils.isEmpty(param.getDataScopeVal())){
            msg.setStatus(201);
            msg.setMessage("服务日期范围不能为空!");
            return msg;
        }
        String type = "0";
        if(!StringUtils.isEmpty(param.getForenoonStartTime()) || !StringUtils.isEmpty(param.getForenoonEndTime())){
            type = "1";//上午
        }
        if(!StringUtils.isEmpty(param.getAfternoonStartTime()) || !StringUtils.isEmpty(param.getAfternoonEndTime())){
            type = "2";//下午
        }
        if("1".equals(type) &&(StringUtils.isEmpty(param.getForenoonStartTime()) || StringUtils.isEmpty(param.getForenoonEndTime()))){
            msg.setStatus(201);
            msg.setMessage("上午时间范围不能为空!");
            return msg;
        }
        if("2".equals(type) &&(StringUtils.isEmpty(param.getAfternoonStartTime()) || StringUtils.isEmpty(param.getAfternoonEndTime()))){
            msg.setStatus(201);
            msg.setMessage("下午时间范围不能为空!");
            return msg;
        }
        int limitNum = -1;
        int productNum = -1;
        if(!StringUtils.isEmpty(param.getLimitNum()) && Integer.parseInt(param.getLimitNum()) != -1){
            limitNum = Integer.parseInt(param.getLimitNum());
        }
        if(!StringUtils.isEmpty(param.getProductNum()) && Integer.parseInt(param.getProductNum()) != -1){
            productNum = Integer.parseInt(param.getProductNum());
        }

        StringBuilder sb = new StringBuilder();
        BizReservation reservation = new BizReservation();
        String id = UUIDUtils.generateUuid();
        reservation.setId(id);
        reservation.setCompanyId(BaseContextHandler.getTenantID());
        if(objectRestResponse.getStatus()==200){
            reservation.setReservationCode(objectRestResponse.getData()==null?"":(String)objectRestResponse.getData());
        }
        reservation.setName(param.getName());
        for (ImgInfo temp: param.getProductImageList()){
            if(StringUtils.isNotEmpty(temp.getUrl())){
                ObjectRestResponse restResponse = toolFeign.moveAppUploadUrlPaths(temp.getUrl(), DocPathConstant.SHOP);
                if(restResponse.getStatus()==200){
                    reservation.setReservationLogo(restResponse.getData()==null ? "" : (String)restResponse.getData());
                }
            }
        }
        String selectImages = "";
        for(ImgInfo temp: param.getSelectionImageList()){
            sb.append(temp.getUrl()+",");
        }
        if(sb.toString() != null && sb.length() > 0){
            selectImages = sb.substring(0,sb.length()-1);
        }
        if(StringUtils.isNotEmpty(selectImages)){
            ObjectRestResponse restResponse = toolFeign.moveAppUploadUrlPaths(selectImages, DocPathConstant.SHOP);
            if(restResponse.getStatus()==200){
                reservation.setSelectionImage(restResponse.getData()==null ? "" : (String)restResponse.getData());
            }
        }
        reservation.setReservationImagetextInfo(productImagetextInfo);
        reservation.setReservaStatus("1");
        //新加字段
        reservation.setLimitNum(limitNum);
        reservation.setProductNum(productNum);
        reservation.setReservationDesc(param.getReservationDesc());
        reservation.setReservationTel(param.getReservationTel());
        if(!StringUtils.isEmpty(param.getForenoonStartTime()) && !StringUtils.isEmpty(param.getForenoonEndTime())){
            reservation.setForenoonStartTime(param.getForenoonStartTime());
            reservation.setForenoonEndTime(param.getForenoonEndTime());
        }
        if(!StringUtils.isEmpty(param.getAfternoonStartTime()) && !StringUtils.isEmpty(param.getAfternoonEndTime())){
            reservation.setAfternoonStartTime(param.getAfternoonStartTime());
            reservation.setAfternoonEndTime(param.getAfternoonEndTime());
        }
        //新增上下午库存
        if(!StringUtils.isEmpty(param.getProductNumForenoon())) {
            reservation.setProductNumForenoon(Integer.parseInt(param.getProductNumForenoon()));
        }
        if(!StringUtils.isEmpty(param.getProductNumAfternoon())) {
            reservation.setProductNumAfternoon(Integer.parseInt(param.getProductNumAfternoon()));
        }
        reservation.setDataScopeVal(param.getDataScopeVal());
        reservation.setTenantId(BaseContextHandler.getTenantID());
        reservation.setTimeStamp(new Date());
        reservation.setCreateBy(BaseContextHandler.getUserID());
        reservation.setCreateTime(new Date());
        reservation.setSupplier(param.getSupplier());
        reservation.setSalesWay(param.getSalesWay());
        if(bizReservationMapper.insertSelective(reservation) > 0){
            //关联服务范围
            BizProductProject project = new BizProductProject();
            for (ResultProjectVo temp : param.getProjectVo()){
                project.setId(UUIDUtils.generateUuid());
                project.setProductId(id);
                project.setProjectId(temp.getProjectId());
                project.setCreateBy(BaseContextHandler.getUserID());
                project.setCreateTime(new Date());
                project.setTimeStamp(new Date());
                if(bizProductProjectMapper.insertSelective(project) < 0){
                    msg.setStatus(101);
                    msg.setMessage("关联服务范围失败!");
                    return msg;
                }
            }
            //关联业务下所属商品分类
            BizProductClassify classify = new BizProductClassify();
            for(ResultClassifyVo temp : param.getClassifyVo()){
                classify.setId(UUIDUtils.generateUuid());
                classify.setProductId(id);
                classify.setBusId(BusinessConstant.getReservationBusId());
                classify.setClassifyId(temp.getClassifyId());
                classify.setCreateBy(BaseContextHandler.getUserID());
                classify.setCreateTime(new Date());
                classify.setTimeStamp(new Date());
                if(bizProductClassifyMapper.insertSelective(classify) < 0){
                    msg.setStatus(102);
                    msg.setMessage("关联业务下所属服务分类失败!");
                    return msg;
                }
            }
            //关联商品标签
            BizProductLabel label = new BizProductLabel();
            for (int i = 0;i<param.getLabel().size();i++){
                label.setId(UUIDUtils.generateUuid());
                label.setProductId(id);
                label.setLabel(param.getLabel().get(i));
                label.setSort(i);
                label.setCreateBy(BaseContextHandler.getUserID());
                label.setCreateTime(new Date());
                label.setTimeStamp(new Date());
                if(bizProductLabelMapper.insertSelective(label) < 0){
                    msg.setStatus(104);
                    msg.setMessage("关联商品标签失败!");
                    return msg;
                }
            }
            //添加商品规格
            BizProductSpec spec = new BizProductSpec();
            if(specInfo.size()>0 && specInfo != null){
                for (UpdateSpecVo temp : specInfo){
                    if(StringUtils.isAnyEmpty(temp.getSpecName(),temp.getPrice(),temp.getSpecTypeCode(),temp.getUnit())){
                        msg.setStatus(201);
                        msg.setMessage("请完善必填基本信息(商品计费类型,规格名称,价格,单位)!");
                        return msg;
                    }
                    String sort  = bizProductSpecMapper.selectLastSortById(id);
                    spec.setId(UUIDUtils.generateUuid());
                    spec.setProductId(id);
                    spec.setSpecName(temp.getSpecName());
                    if(temp.getOriginalPrice() !=null && temp.getOriginalPrice() != ""){
                        spec.setOriginalPrice(new BigDecimal(temp.getOriginalPrice()));
                    }
                    if(temp.getLowestNum() != null && temp.getLowestNum() != ""){
                        spec.setLowestNum(new BigDecimal(temp.getLowestNum()));
                    }
                    spec.setPrice(new BigDecimal(temp.getPrice()));
                    if(temp.getSpecImageList() != null && temp.getSpecImageList().size() > 0){
                        for(ImgInfo temp1: temp.getSpecImageList()){
                            if(StringUtils.isNotEmpty(temp1.getUrl())){
                                ObjectRestResponse restResponse = toolFeign.moveAppUploadUrlPaths(temp1.getUrl(), DocPathConstant.SHOP);
                                if(restResponse.getStatus()==200){
                                    spec.setSpecImage(restResponse.getData()==null ? "" : (String)restResponse.getData());
                                }
                            }
                            //spec.setSpecImage(temp1.getUrl());
                        }
                    }
                    spec.setUnit(temp.getUnit());
                    if(sort != null){
                        spec.setSort(Integer.parseInt(sort)+1);
                    }else{
                        spec.setSort(1);
                    }
                    spec.setSpecTypeCode(temp.getSpecTypeCode());
                    spec.setSpecTypeVal(map.get(temp.getSpecTypeCode()));
                    spec.setCreateBy(BaseContextHandler.getUserID());
                    spec.setCreateTime(new Date());
                    spec.setTimeStamp(new Date());
                    if(bizProductSpecMapper.insertSelective(spec) < 0){
                        msg.setStatus(105);
                        msg.setMessage("添加商品规格失败!");
                        return msg;
                    }

                    // 添加库存
                    getPcStore(reservation,spec,pcStores,temp);
                }
            }else{
                msg.setStatus(107);
                msg.setMessage("保存信息不全,请继续填写完整!");
                return msg;
            }
        }else{
            msg.setStatus(102);
            msg.setMessage("保存服务失败!");
            return msg;
        }
        objectRestResponse = storeFegin.addStore(pcStores);
        if(!objectRestResponse.success()){
            throw new BusinessException("商品库存保存失败!");
        }
        msg.setMessage("Operation succeed!");
        msg.setData(reservation.getId());
        return msg;
    }

    private void getPcStore(BizReservation reservation,BizProductSpec spec,List<PCStore> pcStores,UpdateSpecVo temp){
        PCStore pcStore = getPcStore(reservation,spec,2,temp.getStoreNumAfternoon());
        pcStores.add(pcStore);
        pcStore = getPcStore(reservation,spec,1,temp.getStoreNumForenoon());
        pcStores.add(pcStore);
    }

    private PCStore getPcStore(BizReservation reservation,BizProductSpec spec,Integer timeSlot,Integer storeNum){
        PCStore pcStore = new PCStore();
        pcStore.setCreateBy(BaseContextHandler.getUserID());
        boolean flag = !ObjectUtils.isEmpty(storeNum);
        pcStore.setIsLimit(flag);
        pcStore.setProductCode(reservation.getReservationCode());
        pcStore.setProductId(reservation.getId());
        pcStore.setProductName(reservation.getName());
        pcStore.setProductType(2);
        pcStore.setSpecId(spec.getId());
        pcStore.setStoreNum(flag?storeNum:0);
        pcStore.setTenantId(reservation.getTenantId());
        pcStore.setTimeSlot(timeSlot);
        return pcStore;
    }



    /**
     * 查询服务详情
     * @param id
     * @return
     */
    public List<ResultReservationInfo> getReservationInfo(String id){
        List<ResultReservationInfo> result = new ArrayList<>();
        ResultReservationInfo info = new ResultReservationInfo();

        DecimalFormat df =new  DecimalFormat("0.00");
        ReservationInfo reservationInfo = bizReservationMapper.selectReservationInfo(id);

        if(reservationInfo != null){
            //项目范围
            List<ResultProjectVo> projectVoList = bizReservationMapper.selectProjectList(id);
            reservationInfo.setProjectVo(projectVoList);
            //商品分类
            List<ResultClassifyVo> classifyVoList = bizReservationMapper.selectClassifyListById(id);
            reservationInfo.setClassifyVo(classifyVoList);
            //商品标签
            List<String> labelList = bizProductLabelMapper.selectLabelList(id);
            reservationInfo.setLabel(labelList);
            info.setProductInfo(reservationInfo);
            info.setProductImagetextInfo(reservationInfo.getReservationImagetextInfo());
        }else{
            reservationInfo = new ReservationInfo();
        }
        List<ResultSpecVo> specVoList = bizProductSpecMapper.selectSpecInfo(id);
        if(specVoList != null && specVoList.size() > 0){
            for (ResultSpecVo specVo: specVoList){
                List<ImgInfo> list = new ArrayList<>();
                ImgInfo imgInfo = new ImgInfo();
                imgInfo.setUrl(specVo.getSpecImage());
                list.add(imgInfo);
                if(list != null && list.size() >0){
                    specVo.setSpecImageList(list);
                }else{
                    List<ImgInfo> list1 = new ArrayList<>();
                    specVo.setSpecImageList(list1);
                }
                specVo.setPrice(df.format(Double.parseDouble(specVo.getPrice())));
                if(specVo.getOriginalPrice() != null){
                    specVo.setOriginalPrice(df.format(Double.parseDouble(specVo.getOriginalPrice())));
                }
                getStoreNum(specVo);
            }
            info.setSpecInfo(specVoList);
        }else {
            List<ResultSpecVo> specVos = new ArrayList<>();
        }
        result.add(info);
        return result;
    }

    private void getStoreNum(ResultSpecVo specVo){

        try {
            List<CacheStoreQuery> cacheStoreQueryList = storeFegin.getReservationCacheStore(specVo.getId());

            if(CollectionUtils.isEmpty(cacheStoreQueryList)){
                log.error("设置商品库存失败，specId:{},message:{}", specVo.getId(),"未查到该商品库存信息");
                return;
            }
            for (CacheStoreQuery cacheStoreQuery: cacheStoreQueryList) {
                if(cacheStoreQuery.getTimeSlot() == 1){
                    CacheStore cacheStore = cacheStoreQuery.getCacheStore();
                    if(cacheStore.getIsLimit()){
                        specVo.setStoreNumForenoon(cacheStore.getStoreNum());
                    }
                }

                if(cacheStoreQuery.getTimeSlot() == 2){
                    CacheStore cacheStore = cacheStoreQuery.getCacheStore();
                    if(cacheStore.getIsLimit()){
                        specVo.setStoreNumAfternoon(cacheStore.getStoreNum());
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            log.error("设置商品库存失败，specId:{},message:{}",specVo.getId(),e.getMessage());
        }
    }



    /**
     * 编辑服务
     * @param  info
     * @return
     */
    @Transactional
    public ObjectRestResponse updateReservation(ReservationParamInfo info) {
        // 库存集合
        List<PCStore> pcStores = new ArrayList<>();
        ObjectRestResponse msg = new ObjectRestResponse();
        //获取计费类型
        Map<String, String> map =adminFeign.getDictValue("bill");
        ReservationParam param = info.getProductInfo();
        List<UpdateSpecVo> specInfo = info.getSpecInfo();
        String productImagetextInfo = info.getProductImagetextInfo();
        if(StringUtils.isEmpty(param.getName())){
            msg.setStatus(201);
            msg.setMessage("服务名称不能为空!");
            return msg;
        }
        if(param.getProductImageList() == null || param.getProductImageList().size() == 0){
            msg.setStatus(201);
            msg.setMessage("封面logo不能为空!");
            return msg;
        }
        if(param.getSelectionImageList() == null || param.getSelectionImageList().size() == 0){
            msg.setStatus(201);
            msg.setMessage("精选图片不能为空!");
            return msg;
        }
        if(param.getClassifyVo() == null || param.getClassifyVo().size() == 0){
            msg.setStatus(201);
            msg.setMessage("服务所属分类不能为空!");
            return msg;
        }
        if(param.getProjectVo() == null || param.getProjectVo().size() == 0){
            msg.setStatus(201);
            msg.setMessage("项目范围不能为空!");
            return msg;
        }
        //2019.11.01新增字段  -1 代表无限制
        if(StringUtils.isEmpty(param.getDataScopeVal())){
            msg.setStatus(201);
            msg.setMessage("服务日期范围不能为空!");
            return msg;
        }
        String type = "0";
        if(!StringUtils.isEmpty(param.getForenoonStartTime()) || !StringUtils.isEmpty(param.getForenoonEndTime())){
            type = "1";//上午
        }
        if(!StringUtils.isEmpty(param.getAfternoonStartTime()) || !StringUtils.isEmpty(param.getAfternoonEndTime())){
            type = "2";//下午
        }
        if("1".equals(type) &&(StringUtils.isEmpty(param.getForenoonStartTime()) || StringUtils.isEmpty(param.getForenoonEndTime()))){
            msg.setStatus(201);
            msg.setMessage("上午时间范围不能为空!");
            return msg;
        }
        if("2".equals(type) &&(StringUtils.isEmpty(param.getAfternoonStartTime()) || StringUtils.isEmpty(param.getAfternoonEndTime()))){
            msg.setStatus(201);
            msg.setMessage("下午时间范围不能为空!");
            return msg;
        }
        int limitNum = -1;
        int productNum = -1;
        if(!StringUtils.isEmpty(param.getLimitNum()) && Integer.parseInt(param.getLimitNum()) != -1){
            limitNum = Integer.parseInt(param.getLimitNum());
        }
        if(!StringUtils.isEmpty(param.getProductNum()) && Integer.parseInt(param.getProductNum()) != -1){
            productNum = Integer.parseInt(param.getProductNum());
        }
        StringBuilder sb = new StringBuilder();
        ReservationInfo reservationInfo = bizReservationMapper.selectReservationInfo(param.getId());
        BizReservation reservation = bizReservationMapper.selectByPrimaryKey(param.getId());
        if(reservationInfo != null){
            reservation.setId(param.getId());
            reservation.setName(param.getName());
            for (ImgInfo temp: param.getProductImageList()){
                if(StringUtils.isNotEmpty(temp.getUrl())){
                    ObjectRestResponse restResponse = toolFeign.moveAppUploadUrlPaths(temp.getUrl(), DocPathConstant.SHOP);
                    if(restResponse.getStatus()==200){
                        reservation.setReservationLogo(restResponse.getData()==null ? "" : (String)restResponse.getData());
                    }
                }
            }
            String selectImages = "";
            for(ImgInfo temp: param.getSelectionImageList()){
                sb.append(temp.getUrl()+",");
            }
            if(sb.toString() != null && sb.length() > 0){
                selectImages = sb.substring(0,sb.length()-1);
            }
            if(StringUtils.isNotEmpty(selectImages)){
                ObjectRestResponse restResponse = toolFeign.moveAppUploadUrlPaths(selectImages, DocPathConstant.SHOP);
                if(restResponse.getStatus()==200){
                    reservation.setSelectionImage(restResponse.getData()==null ? "" : (String)restResponse.getData());
                }
            }
            reservation.setReservationImagetextInfo(productImagetextInfo);
            //新加字段
            reservation.setLimitNum(limitNum);
            reservation.setProductNum(productNum);
            reservation.setReservationDesc(param.getReservationDesc());
            reservation.setReservationTel(param.getReservationTel());
            if(!StringUtils.isEmpty(param.getForenoonStartTime()) && !StringUtils.isEmpty(param.getForenoonEndTime())){
                reservation.setForenoonStartTime(param.getForenoonStartTime());
                reservation.setForenoonEndTime(param.getForenoonEndTime());
            }else {
                reservation.setForenoonStartTime(null);
                reservation.setForenoonEndTime(null);
            }
            if(!StringUtils.isEmpty(param.getAfternoonStartTime()) && !StringUtils.isEmpty(param.getAfternoonEndTime())){
                reservation.setAfternoonStartTime(param.getAfternoonStartTime());
                reservation.setAfternoonEndTime(param.getAfternoonEndTime());
            }else {
                reservation.setAfternoonStartTime(null);
                reservation.setAfternoonEndTime(null);
            }
            if (!StringUtils.isEmpty(param.getProductNumForenoon())) {
                reservation.setProductNumForenoon(Integer.parseInt(param.getProductNumForenoon()));
            } else {
                reservation.setProductNumForenoon(-1);
            }
            if (!StringUtils.isEmpty(param.getProductNumAfternoon())) {
                reservation.setProductNumAfternoon(Integer.parseInt(param.getProductNumAfternoon()));
            } else {
                reservation.setProductNumAfternoon(-1);
            }
            reservation.setDataScopeVal(param.getDataScopeVal());
            reservation.setModifyBy(BaseContextHandler.getUserID());
            reservation.setModifyTime(new Date());
            reservation.setTimeStamp(new Date());
            reservation.setSupplier(param.getSupplier());
            reservation.setSalesWay(param.getSalesWay());
            if(bizReservationMapper.updateByPrimaryKey(reservation) > 0){
                //编辑商品分类
                if(bizProductClassifyMapper.delClassifyInfo(param.getId()) > 0) {
                    BizProductClassify classify = new BizProductClassify(); for(ResultClassifyVo temp : param.getClassifyVo()){
                        classify.setId(UUIDUtils.generateUuid());
                        classify.setProductId(param.getId());
                        classify.setBusId(BusinessConstant.getReservationBusId());
                        classify.setClassifyId(temp.getClassifyId());
                        classify.setCreateBy(BaseContextHandler.getUserID());
                        classify.setCreateTime(new Date());
                        classify.setTimeStamp(new Date());
                        if(bizProductClassifyMapper.insertSelective(classify) < 0){
                            Assert.isTrue(Boolean.FALSE,"关联业务下所属服务分类失败!");
                            msg.setStatus(102);
                            msg.setMessage("关联业务下所属服务分类失败!");
                            return msg;
                        }
                    }
                }
                //编辑项目范围
                if(bizProductProjectMapper.delProjectInfo(param.getId()) > 0){
                    BizProductProject project = new BizProductProject();
                    for (ResultProjectVo temp : param.getProjectVo()){
                        project.setId(UUIDUtils.generateUuid());
                        project.setProductId(param.getId());
                        project.setProjectId(temp.getProjectId());
                        project.setCreateBy(BaseContextHandler.getUserID());
                        project.setCreateTime(new Date());
                        project.setTimeStamp(new Date());
                        if(bizProductProjectMapper.insertSelective(project) < 0){
                            Assert.isTrue(Boolean.FALSE,"关联服务范围失败!");
                            msg.setStatus(101);
                            msg.setMessage("关联服务范围失败!");
                            return msg;
                        }
                    }
                }

            }else{
                msg.setStatus(101);
                msg.setMessage("编辑服务失败!");
                return msg;
            }
            //编辑商品标签
            if(param.getLabel() != null && param.getLabel().size() >0){
                if(bizProductLabelMapper.delLabelfo(param.getId()) >= 0){
                    BizProductLabel label = new BizProductLabel();
                    for (int i = 0;i<param.getLabel().size();i++){
                        label.setId(UUIDUtils.generateUuid());
                        label.setProductId(param.getId());
                        label.setLabel(param.getLabel().get(i));
                        label.setSort(i);
                        label.setCreateBy(BaseContextHandler.getUserID());
                        label.setCreateTime(new Date());
                        label.setTimeStamp(new Date());
                        if(bizProductLabelMapper.insertSelective(label) < 0){
                            Assert.isTrue(Boolean.FALSE,"关联商品标签失败!");
                            msg.setStatus(104);
                            msg.setMessage("关联商品标签失败!");
                            return msg;
                        }
                    }
                }else{
                    Assert.isTrue(Boolean.FALSE,"编辑商品失败!");
                    msg.setStatus(103);
                    msg.setMessage("编辑商品失败!");
                    return msg;
                }
            }
            //编辑商品规格
//            if(bizProductSpecMapper.delSpecInfo(param.getId()) > 0){
                BizProductSpec spec = new BizProductSpec();
                if(specInfo.size()>0 && specInfo != null){
                    List<String> specIds = bizProductSpecMapper.selectIdList(param.getId());
                    for (UpdateSpecVo temp : specInfo){
                        if(StringUtils.isAnyEmpty(temp.getSpecName(),temp.getPrice(),temp.getUnit())){
                            Assert.isTrue(Boolean.FALSE,"请完善必填基本信息(商品规格名称,价格,单位)!");
                            msg.setStatus(201);
                            msg.setMessage("请完善必填基本信息(商品规格名称,价格,单位)!");
                            return msg;
                        }
                        if(temp.getLowestNum() == "0"){
                            Assert.isTrue(Boolean.FALSE,"最小购买数量不能为零!");
                            msg.setStatus(201);
                            msg.setMessage("最小购买数量不能为零!");
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //事务回滚
                            return msg;
                        }
                        if(specIds.contains(temp.getId())){
                            // 规格存在更新
                            spec = new BizProductSpec();
                            spec.setId(temp.getId());
                            spec.setSpecName(temp.getSpecName());
                            if(temp.getOriginalPrice() !=null && temp.getOriginalPrice() != ""){
                                spec.setOriginalPrice(new BigDecimal(temp.getOriginalPrice()));
                            }
                            if(temp.getLowestNum() != null && temp.getLowestNum() != ""){
                                spec.setLowestNum(new BigDecimal(temp.getLowestNum()));
                            }
                            spec.setPrice(new BigDecimal(temp.getPrice()));
                            if(temp.getSpecImageList() != null && temp.getSpecImageList().size() > 0){
                                for(ImgInfo temp1: temp.getSpecImageList()){
                                    if(StringUtils.isNotEmpty(temp1.getUrl())){
                                        ObjectRestResponse restResponse = toolFeign.moveAppUploadUrlPaths(temp1.getUrl(), DocPathConstant.SHOP);
                                        if(restResponse.getStatus()==200){
                                            spec.setSpecImage(restResponse.getData()==null ? "" : (String)restResponse.getData());
                                        }
                                    }
                                    //spec.setSpecImage(temp1.getUrl());
                                }
                            }
                            spec.setUnit(temp.getUnit());
                            spec.setSpecTypeCode(temp.getSpecTypeCode());
                            spec.setSpecTypeVal(map.get(temp.getSpecTypeCode()));
                            spec.setModifyBy(BaseContextHandler.getUserID());
                            spec.setTimeStamp(new Date());
                            if(bizProductSpecMapper.updateByPrimaryKeySelective(spec) < 0){
                                Assert.isTrue(Boolean.FALSE,"更新商品规格失败!");
                                msg.setStatus(105);
                                msg.setMessage("更新商品规格失败!");
                                return msg;
                            }
                            // 移除已经更新的规格ID
                            specIds.remove(temp.getId());
                        }else{
                            // 规格不存在添加
                            String sort  = bizProductSpecMapper.selectLastSortById(param.getId());
                            spec = new BizProductSpec();
                            spec.setId(UUIDUtils.generateUuid());
                            spec.setProductId(param.getId());
                            spec.setSpecName(temp.getSpecName());
                            if(temp.getOriginalPrice() !=null && temp.getOriginalPrice() != ""){
                                spec.setOriginalPrice(new BigDecimal(temp.getOriginalPrice()));
                            }
                            if(temp.getLowestNum() != null && temp.getLowestNum() != ""){
                                spec.setLowestNum(new BigDecimal(temp.getLowestNum()));
                            }
                            spec.setPrice(new BigDecimal(temp.getPrice()));
                            if(temp.getSpecImageList() != null && temp.getSpecImageList().size() > 0){
                                for(ImgInfo temp1: temp.getSpecImageList()){
                                    if(StringUtils.isNotEmpty(temp1.getUrl())){
                                        ObjectRestResponse restResponse = toolFeign.moveAppUploadUrlPaths(temp1.getUrl(), DocPathConstant.SHOP);
                                        if(restResponse.getStatus()==200){
                                            spec.setSpecImage(restResponse.getData()==null ? "" : (String)restResponse.getData());
                                        }
                                    }
                                    //spec.setSpecImage(temp1.getUrl());
                                }
                            }
                            spec.setUnit(temp.getUnit());
                            if(sort != null){
                                spec.setSort(Integer.parseInt(sort)+1);
                            }else{
                                spec.setSort(1);
                            }
                            spec.setSpecTypeCode(temp.getSpecTypeCode());
                            spec.setSpecTypeVal(map.get(temp.getSpecTypeCode()));
                            spec.setCreateBy(BaseContextHandler.getUserID());
                            spec.setCreateTime(new Date());
                            spec.setTimeStamp(new Date());
                            if(bizProductSpecMapper.insertSelective(spec) < 0){
                                Assert.isTrue(Boolean.FALSE,"添加商品规格失败!!");
                                msg.setStatus(105);
                                msg.setMessage("添加商品规格失败!");
                                return msg;
                            }
                        }
                        // 添加库存
                        getPcStore(reservation,spec,pcStores,temp);
                    }
                    if(CollectionUtils.isNotEmpty(specIds)){
                        // 删除集合未处理的规格（余下的表示用户已删除）
                        bizProductSpecMapper.deleteSpecIds(specIds);
                    }
                }else{
                    Assert.isTrue(Boolean.FALSE,"编辑信息不全,请继续填写完整!");
                    msg.setStatus(107);
                    msg.setMessage("编辑信息不全,请继续填写完整!");
                    return msg;
                }
//            }
        }else{
            Assert.isTrue(Boolean.FALSE,"编辑服务失败!");
            msg.setStatus(101);
            msg.setMessage("编辑服务失败!");
            return msg;
        }

        ObjectRestResponse objectRestResponse = storeFegin.updateStore(pcStores);
        if(!objectRestResponse.success()){
            Assert.isTrue(Boolean.FALSE,"商品库存保存失败!");
            msg.setStatus(105);
            msg.setMessage("商品库存保存失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 服务操作
     * @param param
     * @return
     */
    public ObjectRestResponse updateReservationStatus(UpdateStatusParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        String reservaStatus = bizReservationMapper.selectReservaStatusById(param.getId());
        if("1".equals(param.getStatus())){//申请上架
            if("1".equals(reservaStatus) || "4".equals(reservaStatus) || "5".equals(reservaStatus)) {//待发布
                if (bizReservationMapper.updateAuditStatus(param.getId(), BaseContextHandler.getUserID()) < 0) {
                    msg.setStatus(101);
                    msg.setMessage("申请发布失败!");
                    return msg;
                }
            }else{
                msg.setStatus(102);
                msg.setMessage("预约服务的服务状态,不能进行该操作!");
                return msg;
            }
        }else if("3".equals(param.getStatus())){//撤回
            if("3".equals(reservaStatus)){//已发布
                if(bizReservationMapper.updateSoldStatus(param.getId(), BaseContextHandler.getUserID()) < 0) {
                    msg.setStatus(103);
                    msg.setMessage("撤回失败!");
                    return msg;
                }
            }else{
                msg.setStatus(102);
                msg.setMessage("预约服务的服务状态,不能进行该操作!");
                return msg;

            }
        }else if("2".equals(param.getStatus())){//发布
            if("2".equals(reservaStatus)){//待审核
                if(bizReservationMapper.updatePutawayStatus(param.getId(), BaseContextHandler.getUserID()) < 0){
                    msg.setStatus(104);
                    msg.setMessage("发布失败!");
                    return msg;
                }
            }else{
                msg.setStatus(102);
                msg.setMessage("商品的业务状态,不能进行该操作!");
                return msg;
            }
        }else if("4".equals(param.getStatus())){//驳回
            if("2".equals(reservaStatus)) {//待审核
                if (bizReservationMapper.updateRejectStatus(param.getId(), BaseContextHandler.getUserID()) < 0){
                    msg.setStatus(105);
                    msg.setMessage("驳回失败!");
                    return msg;
                }
            }else{
                msg.setStatus(102);
                msg.setMessage("商品的业务状态,不能进行该操作!");
                return msg;
            }
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    /**
     * 查询服务分类列表
     * @return
     */
    public List<ResultClassifyVo> getReservationClassifyList(){
        List<ResultClassifyVo> classifyList =  bizProductMapper.selectProductClassifyList(BusinessConstant.getReservationBusId());;
        return classifyList;
    }

    /**
     * 查询商户下的项目列表
     * @return
     */
    public List<ResultProjectVo> getReservationProjectList(){
        List<ResultProjectVo> projectVoList = new ArrayList<>();
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        if("3".equals(type)){
            projectVoList =  bizReservationMapper.selectProjectName();
        }else{
            projectVoList = bizProductMapper.selectTenantProjectList(type,BaseContextHandler.getTenantID());
        }

        return projectVoList;
    }


    /**
     * 查询服务审核列表
     * @param reservaStatus
     * @param searchVal
     * @param classifyId
     * @param page
     * @param limit
     * @return
     */
    public List<ResrevationAuditVo> getResrevationAuditList(String projectId, String reservaStatus, String searchVal, String classifyId,
                                                          Integer page, Integer limit) {
        if (page == null || page.equals("")) {
            page = 1;
        }
        if (limit == null || limit.equals("")) {
            limit = 10;
        }
        if (page == 0) {
            page = 1;
        }
        //分页
        Integer startIndex = (page - 1) * limit;
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        List<ResrevationAuditVo> resrevationAuditVoList = bizReservationMapper.selectResrevationAuditList(type,BaseContextHandler.getTenantID(),
                reservaStatus,classifyId,projectId,searchVal,startIndex,limit);
        for(ResrevationAuditVo auditVo: resrevationAuditVoList){
            List<String> projectNames = bizReservationMapper.selectProjectIdById(auditVo.getId());
            if(projectNames != null && projectNames.size()>0){
                String projectName = "";
                StringBuilder resultEva = new StringBuilder();
                for (String project:projectNames){
                    resultEva.append(project + ",");
                }
                projectName = resultEva.substring(0, resultEva.length() - 1);
                auditVo.setProjectName(projectName);
            }
            List<String> classifyNames = bizReservationMapper.selectClassifyNameById(auditVo.getId());
            if(classifyNames != null && classifyNames.size()>0){
                String classifyName = "";
                StringBuilder resultEva = new StringBuilder();
                for (String classify:classifyNames){
                    resultEva.append(classify + ",");
                }
                classifyName = resultEva.substring(0, resultEva.length() - 1);
                auditVo.setClassifyName(classifyName);
            }
        }
        return resrevationAuditVoList;
    }


    /**
     * 查询服务审核列表数量
     * @param reservaStatus
     * @param searchVal
     * @param classifyId
     * @return
     */
    public int selectResrevationAuditCount(String projectId, String reservaStatus,String searchVal,String classifyId){
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        return bizReservationMapper.selectResrevationAuditCount(type,BaseContextHandler.getTenantID(),
                reservaStatus,classifyId,projectId,searchVal);
    }


    /**
     * 查询预约服务人员列表
     * @param tenantId
     * @param projectId
     * @param startTime
     * @param endTime
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    public List<ReservatPersonVo> getReservationPersonList(String tenantId, String projectId, String startTime, String endTime,
                                                     String searchVal,String dealStatus, Integer page, Integer limit) {
        if (page == null || page.equals("")) {
            page = 1;
        }
        if (limit == null || limit.equals("")) {
            limit = 10;
        }
        if (page == 0) {
            page = 1;
        }
        //分页
        Integer startIndex = (page - 1) * limit;
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        if(!"3".equals(type)){
            tenantId = BaseContextHandler.getTenantID();
        }
        List<ReservatPersonVo> reservationPersonList = bizReservationMapper.selectReservationPersonList(tenantId, startTime, endTime,
                projectId, searchVal,dealStatus, startIndex, limit);
        if(reservationPersonList == null || reservationPersonList.size() == 0){
            reservationPersonList = new ArrayList<>();
        }
        return reservationPersonList;
    }


    public int selectReservationPersonCount(String tenantId, String projectId, String startTime, String endTime, String searchVal ,String dealStatus){
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        if(!"3".equals(type)){
            tenantId = BaseContextHandler.getTenantID();
        }
        return bizReservationMapper.selectReservationPersonCount(tenantId, startTime, endTime, projectId, searchVal, dealStatus);
    }


    /**
     * 服务工单操作
     * @param param
     * @return
     */
    public ObjectRestResponse updateDealStatus(UpdateReservatParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        TenameInfo tenameInfo = bizReservationMapper.seelctTenameInfoById(BaseContextHandler.getTenantID());
        if(bizReservationMapper.updateReservatStatus(param.getDealStatus(),BaseContextHandler.getUserID(),param.getId()) < 0){
            msg.setStatus(102);
            msg.setMessage("操作失败!");
            return msg;
        }else{
            BizTransactionLog transactionLog = new BizTransactionLog();
            transactionLog.setId(UUIDUtils.generateUuid());
            transactionLog.setWoId(param.getId());
            if("4".equals(param.getDealStatus())){//
                transactionLog.setCurrStep("处理中");
                if(tenameInfo != null){
                    transactionLog.setDescription("已接单，处理中\r\n处理人："+tenameInfo.getContactorName());
                    transactionLog.setConName(tenameInfo.getContactorName());
                    transactionLog.setConTel(tenameInfo.getContactTel());
                }else{
                    transactionLog.setDescription("已接单，处理中");
                }
            }else if("2".equals(param.getDealStatus())){//已取消
                transactionLog.setCurrStep("工单关闭");
                transactionLog.setDescription("");
                transactionLog.setConName("");
                transactionLog.setConTel("");
            }else{
                transactionLog.setCurrStep("已完成");
                transactionLog.setDescription("预约已联系");
                transactionLog.setConName("");
                transactionLog.setConTel("");
            }
            transactionLog.setCreateBy(BaseContextHandler.getUserID());
            bizReservationMapper.insertReservatLog(transactionLog);
        }
        return msg;
    }

    /**
     * 查看预约工单详情
     * @param woId
     * @return
     */
    public ObjectRestResponse<ReservatWoDetail> getReservatWoDetail(String woId){
        return orderEngineFeign.getReservationWoInfo(woId);
    }

    /**
     * 修改服务工单预约时间
     * @param params 参数
     * @return
     */
    public ObjectRestResponse<Object> updateSubReservation(JSONObject params){
        return orderEngineFeign.updateSubReservation(params);
    }


    /**
     * Web后台查询预约服务订单列表
     * @param busIdList
     * @param searchSubInWeb
     * @return
     */
    public ObjectRestResponse<List<SubListForWebVo>> querySubListByWeb(List<String> busIdList, SearchSubInWeb searchSubInWeb){
        ObjectRestResponse restResponse = new ObjectRestResponse();
        int page = searchSubInWeb.getPage();
        int limit = searchSubInWeb.getLimit();
        if (page<0) {
            page = 1;
        }
        if (limit<0) {
            limit = 10;
        }
        //分页
        int startIndex = (page - 1) * limit;
        Map<String,Object> paramMap = new HashMap<>();
        if (page != 0 && limit != 0) {
            paramMap.put("page",startIndex);
            paramMap.put("limit",limit);
        }
        paramMap.put("busIdList",busIdList);
        paramMap.put("searchVal",searchSubInWeb.getSearchVal());
        paramMap.put("startDate",searchSubInWeb.getStartDate());
        paramMap.put("endDate",searchSubInWeb.getEndDate());
        paramMap.put("projectId",searchSubInWeb.getProjectId());
        paramMap.put("companyId",searchSubInWeb.getCompanyId());
        paramMap.put("subStatus",searchSubInWeb.getSubStatus());
        List<SubListForWebVo> woList = bizReservationMapper.querySubListByWeb(paramMap);
        int total = 0;
        if(woList==null || woList.size()==0){
            woList = new ArrayList<>();
        }else{
            total = bizReservationMapper.querySubListByWebTotal(paramMap);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("list",woList);

        restResponse.setData(map);
        return restResponse;
    }

    /**
     * Web后台查询预约服务订单列表
     * @param busIdList
     * @param searchSubInWeb
     * @return
     */
    public ObjectRestResponse<List<SubListForWebVo>> querySubListWithSupplierByWeb(List<String> busIdList, SearchSubInWeb searchSubInWeb){
        ObjectRestResponse restResponse = new ObjectRestResponse();
        int page = searchSubInWeb.getPage();
        int limit = searchSubInWeb.getLimit();
        if (page<0) {
            page = 1;
        }
        if (limit<0) {
            limit = 10;
        }
        //分页
        int startIndex = (page - 1) * limit;
        Map<String,Object> paramMap = new HashMap<>();
        if (page != 0 && limit != 0) {
            paramMap.put("page",startIndex);
            paramMap.put("limit",limit);
        }
        paramMap.put("busIdList",busIdList);
        paramMap.put("searchVal",searchSubInWeb.getSearchVal());
        paramMap.put("startDate",searchSubInWeb.getStartDate());
        paramMap.put("endDate",searchSubInWeb.getEndDate());
        paramMap.put("projectId",searchSubInWeb.getProjectId());
        paramMap.put("companyId",searchSubInWeb.getCompanyId());
        paramMap.put("subStatus",searchSubInWeb.getSubStatus());
        paramMap.put("startExpectedServiceTime",searchSubInWeb.getStartExpectedServiceTime());
        if(searchSubInWeb.getEndExpectedServiceTime() != null){
            searchSubInWeb.setEndExpectedServiceTime(DateUtils.addDays(searchSubInWeb.getEndExpectedServiceTime(),1));
        }
        paramMap.put("endExpectedServiceTime",searchSubInWeb.getEndExpectedServiceTime() );
        List<SubListForWebVo> woList = bizReservationMapper.querySubListByWeb(paramMap);
        Map<String,Object> map = new HashMap<>();
        int total = 0;
        if(woList==null || woList.size()==0){
            woList = new ArrayList<>();
            map.put("list",woList);
        }else{
            total = bizReservationMapper.querySubListByWebTotal(paramMap);
            int size = woList.size();
            JSONArray jsonArray = new JSONArray(size);
            for (int i = 0; i < size; i++) {
                SubListForWebVo tmpSubListForWebVo = woList.get(i);
                JSONObject jsonObject = (JSONObject)JSONObject.toJSON(tmpSubListForWebVo);
                jsonArray.add(jsonObject);
                List<Map> tmpList = bizSubProductMapper.selectReservationInfoBySubId(woList.get(i).getId());
                if (tmpList != null && tmpList.size() > 0) {
                    Map tmpMap = tmpList.get(0);
                    String supplier = (String)tmpMap.get("supplier");
                    jsonObject.put("supplier", supplier);
                } else {
                    jsonObject.put("supplier", "");
                }
            }
            map.put("list", jsonArray);
        }
        map.put("total",total);
        restResponse.setData(map);
        return restResponse;
    }


}
