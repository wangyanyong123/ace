package com.github.wxiaoqi.security.jinmao.biz;

import com.alibaba.fastjson.JSON;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.constant.DocPathConstant;
import com.github.wxiaoqi.security.api.vo.store.CacheStore;
import com.github.wxiaoqi.security.api.vo.store.CacheStoreProduct;
import com.github.wxiaoqi.security.api.vo.store.pc.PCStore;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.constant.BusinessConstant;
import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.*;
import com.github.wxiaoqi.security.jinmao.feign.CodeFeign;
import com.github.wxiaoqi.security.jinmao.feign.StoreFegin;
import com.github.wxiaoqi.security.jinmao.feign.ToolFegin;
import com.github.wxiaoqi.security.jinmao.mapper.*;
import com.github.wxiaoqi.security.jinmao.vo.Product.InputParam.UpdateProductInfo;
import com.github.wxiaoqi.security.jinmao.vo.Product.InputParam.UpdateProductInfoVo;
import com.github.wxiaoqi.security.jinmao.vo.Product.InputParam.UpdateSpecVo;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.*;
import com.github.wxiaoqi.security.jinmao.vo.ProductRecommend.OutputParam.ResultRecommendInfo;
import com.github.wxiaoqi.security.jinmao.vo.ResultProjectVo.ProjectListVo;
import com.github.wxiaoqi.security.jinmao.vo.productgoodvisit.outputparam.ProductListVo;
import com.github.wxiaoqi.security.jinmao.vo.reservat.out.ReservationList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品表
 *
 * @author zxl
 * @Date 2018-12-05 09:58:43
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Slf4j
@Service
public class BizProductBiz extends BusinessBiz<BizProductMapper,BizProduct> {
    private Logger logger = LoggerFactory.getLogger(BizProductBiz.class);
    @Autowired
    private BizProductMapper bizProductMapper;
    @Autowired
    private BizProductProjectMapper bizProductProjectMapper;
    @Autowired
    private BizProductClassifyMapper bizProductClassifyMapper;
    @Autowired
    private BizProductLabelMapper bizProductLabelMapper;
    @Autowired
    private BizProductSpecMapper bizProductSpecMapper;
    @Autowired
    private CodeFeign codeFeign;
    @Autowired
    private BizProductGoodtoVisitMapper bizProductGoodtoVisitMapper;
    @Autowired
    private ToolFegin toolFeign;
    @Autowired
    private BizFamilyAdvertisingProjectMapper bizFamilyAdvertisingProjectMapper;
    @Autowired
    private BizReservationMapper bizReservationMapper;
    @Autowired
    private BizProductOrderBiz bizProductOrderBiz;
    @Autowired
    private StoreFegin storeFegin;
    @Autowired
    private BizProductOrderMapper bizProductOrderMapper;

    /**
     * 查询商品列表
     * @param busStatus
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    public List<ResultProductVo> getProductList(String busStatus,String searchVal,Integer page, Integer limit){
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
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        List<ResultProductVo> productVoList =  bizProductMapper.selectProductList(type,BaseContextHandler.getTenantID(),busStatus,
                searchVal, startIndex, limit);
        for (ResultProductVo product : productVoList){
            List<String> projectNames = bizProductProjectMapper.selectProjectIdById(product.getId());
            if(projectNames != null && projectNames.size()>0){
                String projectName = "";
                StringBuilder resultEva = new StringBuilder();
                for (String project:projectNames){
                    resultEva.append(project + ",");
                }
                projectName = resultEva.substring(0, resultEva.length() - 1);
                product.setProjectName(projectName);
            }
            List<String> classifyNames = bizProductClassifyMapper.selectClassifyNameById(product.getId());
            if(classifyNames != null && classifyNames.size()>0){
                String classifyName = "";
                StringBuilder resultEva = new StringBuilder();
                for (String classify:classifyNames){
                    resultEva.append(classify + ",");
                }
                classifyName = resultEva.substring(0, resultEva.length() - 1);
                product.setClassifyName(classifyName);
            }

        }
        return  productVoList;
    }

    /**
     * 查询商品数量
     * @param busStatus
     * @param searchVal
     * @return
     */
    public int selectProductCount(String busStatus,String searchVal){
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        int total = bizProductMapper.selectProductCount(type,BaseContextHandler.getTenantID(),busStatus, searchVal);
        return total;
    }

    /**
     * 保存商品
     * @param info
     * @return
     */
    @Transactional
    public ObjectRestResponse saveProduct(UpdateProductInfo info){
        info.check();
        // 库存集合
        List<PCStore> pcStores = new ArrayList<>();
        ObjectRestResponse msg = new ObjectRestResponse();
        ObjectRestResponse objectRestResponse = codeFeign.getCode("Product","P","6","0");
        logger.info("生成商品编码处理结果："+objectRestResponse.getData());
        UpdateProductInfoVo param = info.getProductInfo();
        List<UpdateSpecVo> specInfo = info.getSpecInfo();
        String productImagetextInfo = info.getProductImagetextInfo();
        //判断该业务是否是团购
        String type = bizProductClassifyMapper.selectTypeByBusId(param.getBusId());
        if(!"4".equals(type)){
            if(StringUtils.isAnyEmpty(param.getProductName(),param.getBusId())){
                msg.setStatus(201);
                msg.setMessage("请完善必填基本信息(商品名称,所属业务,商品logo,所属项目)!");
                return msg;
            }
            if(param.getSelectionImageList() == null || param.getSelectionImageList().size() == 0){
                msg.setStatus(201);
                msg.setMessage("商品精选图片不能为空!");
                return msg;
            }
            if(!"2".equals(type)){
                if(param.getClassifyVo() == null || param.getClassifyVo().size() == 0){
                    msg.setStatus(201);
                    msg.setMessage("商品所属分类不能为空!");
                    return msg;
                }
            }
        }
        if(param.getProductImageList() == null || param.getProductImageList().size() == 0){
            msg.setStatus(201);
            msg.setMessage("商品图片不能为空!");
            return msg;
        }

//        if("4".equals(type)){
//            if(param.getSpikeArr() == null || param.getSpikeArr().size() == 0){
//                msg.setStatus(201);
//                msg.setMessage("秒杀商品不能为空!");
//                return msg;
//            }
//        }
        int limitNum = -1;
        int productNum = -1;
        if("1".equals(type)){
            if(!StringUtils.isEmpty(param.getLimitNum()) && Integer.parseInt(param.getLimitNum()) != -1){
                limitNum = Integer.parseInt(param.getLimitNum());
            }
            if(!StringUtils.isEmpty(param.getProductNums()) && Integer.parseInt(param.getProductNums()) != -1){
                productNum = Integer.parseInt(param.getProductNums());
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        BizProduct product = new BizProduct();
        //String code = "P"+ StringUtils.generateRandomNumber(5);
        String id = UUIDUtils.generateUuid();
        product.setId(id);
        if(objectRestResponse.getStatus()==200){
            product.setProductCode(objectRestResponse.getData()==null?"":(String)objectRestResponse.getData());
        }
        product.setProductName(param.getProductName());
        product.setCompanyId(BaseContextHandler.getTenantID());
        product.setBusStatus("1");
        product.setUnit(specInfo.get(0).getUnit());
        for(ImgInfo temp: param.getProductImageList()){
            if(StringUtils.isNotEmpty(temp.getUrl())){
                ObjectRestResponse restResponse = toolFeign.moveAppUploadUrlPaths(temp.getUrl(), DocPathConstant.SHOP);
                if(restResponse.getStatus()==200){
                    product.setProductImage(restResponse.getData()==null ? "" : (String)restResponse.getData());
                }
            }
            //product.setProductImage(temp.getUrl());
        }

        String selectImages = "";
        for(ImgInfo temp: param.getSelectionImageList()){
            sb.append(temp.getUrl()+",");
        }
        selectImages = sb.substring(0,sb.length()-1);
        if(StringUtils.isNotEmpty(selectImages)){
            ObjectRestResponse restResponse = toolFeign.moveAppUploadUrlPaths(selectImages, DocPathConstant.SHOP);
            if(restResponse.getStatus()==200){
                product.setSelectionImage(restResponse.getData()==null ? "" : (String)restResponse.getData());
            }
        }
        //product.setSelectionImage(selectImages);
        product.setTenantId(BaseContextHandler.getTenantID());
        product.setProductSummary(param.getProductSummary());
        product.setProductAfterSale(param.getProductAfterSale());
        product.setProductImagetextInfo(productImagetextInfo);
        if("2".equals(type)){
            //添加团购
            try {
                product.setBegTime(sdf.parse(param.getBegTime()));
                product.setEndTime(sdf.parse(param.getEndTime()));
                //product.setProductNum(Integer.parseInt(param.getProductNum()));
                product.setGroupbuyNum(Integer.parseInt(param.getGroupbuyNum()));
            } catch (ParseException e) {
                logger.error("处理时间异常",e);
            }
        }
        if("4".equals(type)){
            //添加疯抢商品
            try {
                product.setBegTime(sdf.parse(param.getBegTime()));
                product.setEndTime(sdf.parse(param.getEndTime()));
//                product.setProductNum(Integer.parseInt(param.getProductNum()));
//                for(ResultSpike temp : param.getSpikeArr()){
//                    product.setProductId(temp.getId());
//                }
            }catch (ParseException e){
                logger.error("处理时间异常",e);
            }

        }
        if("1".equals(type)){
            product.setProductNum(productNum);
            product.setLimitNum(limitNum);
        }
        product.setCreateBy(BaseContextHandler.getUserID());
        product.setTimeStamp(new Date());
        product.setCreateTime(new Date());
        product.setSupplier(param.getSupplier());
        product.setSalesWay(param.getSalesWay());
        if(bizProductMapper.insertSelective(product) > 0){
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
            if("2".equals(type)){
                classify.setId(UUIDUtils.generateUuid());
                classify.setProductId(id);
                classify.setBusId(param.getBusId());
                classify.setCreateBy(BaseContextHandler.getUserID());
                classify.setCreateTime(new Date());
                classify.setTimeStamp(new Date());
                if(bizProductClassifyMapper.insertSelective(classify) < 0){
                    msg.setStatus(102);
                    msg.setMessage("关联业务下所属商品分类失败!");
                    return msg;
                }
            }else if("4".equals(type)){
                classify.setId(UUIDUtils.generateUuid());
                classify.setProductId(id);
                classify.setBusId(param.getBusId());
                classify.setCreateBy(BaseContextHandler.getUserID());
                classify.setCreateTime(new Date());
                classify.setTimeStamp(new Date());
                if(bizProductClassifyMapper.insertSelective(classify) < 0){
                    msg.setStatus(102);
                    msg.setMessage("关联业务下所属商品分类失败!");
                    return msg;
                }
            }
            else{
                for(ResultClassifyVo temp : param.getClassifyVo()){
                    classify.setId(UUIDUtils.generateUuid());
                    classify.setProductId(id);
                    classify.setBusId(param.getBusId());
                    classify.setClassifyId(temp.getClassifyId());
                    classify.setCreateBy(BaseContextHandler.getUserID());
                    classify.setCreateTime(new Date());
                    classify.setTimeStamp(new Date());
                    if(bizProductClassifyMapper.insertSelective(classify) < 0){
                        msg.setStatus(102);
                        msg.setMessage("关联业务下所属商品分类失败!");
                        return msg;
                    }
                }
            }
            //关联商品标签
            BizProductLabel label = new BizProductLabel();
            for (int i=0;i< param.getLabel().size();i++){
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
            int productCount = 0; // 剩余份数
            String productTotal = null; // 规格库存存在无上限
            BizProductSpec spec = new BizProductSpec();
            if(specInfo.size()>0 && specInfo != null){

                for (UpdateSpecVo temp : specInfo){
                    if(StringUtils.isAnyEmpty(temp.getSpecName(),temp.getPrice())){
                        msg.setStatus(201);
                        msg.setMessage("请完善必填基本信息(商品规格名称,价格)!");
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
                    spec.setCreateBy(BaseContextHandler.getUserID());
                    spec.setCreateTime(new Date());
                    spec.setTimeStamp(new Date());

                    if(bizProductSpecMapper.insertSelective(spec) < 0){
                        msg.setStatus(105);
                        msg.setMessage("添加商品规格失败!");
                        return msg;
                    }
                    // 添加库存
                    PCStore pcStore = addPcStore(product,spec,temp.getStoreNum());
                    pcStores.add(pcStore);
                    // 记录疯抢商品总份数（总份数等于规格库存总和）
                    if("4".equals(type)){
                        if(ObjectUtils.isEmpty(temp.getStoreNum())){
                            productTotal = "9999";
                        }else {
                            productCount += temp.getStoreNum();
                        }
                    }
                }

                if("4".equals(type)){
                    // productTotal为空说明不存在无上限
                    if(StringUtils.isEmpty(productTotal)){
                        productTotal = productCount+"";
                    }
                    // 更新库存数量
                    product.setProductNum(Integer.parseInt(productTotal));
                    bizProductMapper.updateByPrimaryKeySelective(product);
                }
            }else{
                msg.setStatus(107);
                msg.setMessage("保存信息不全,请继续填写完整!");
                return msg;
            }

        }else{
            msg.setStatus(105);
            msg.setMessage("保存商品失败!");
            return msg;
        }

        // 普通商品的库存处理
        objectRestResponse = storeFegin.addStore(pcStores);
        if(!objectRestResponse.success()){
            throw new BusinessException("商品库存保存失败!");
        }

        msg.setMessage("Operation succeed!");
        msg.setData(product.getId());
        return msg;
    }

    // 添加商品时的库存处理
    private PCStore addPcStore(BizProduct product,BizProductSpec spec,Integer storeNum){
        PCStore pcStore = new PCStore();
        pcStore.setCreateBy(BaseContextHandler.getUserID());
        boolean flag = ObjectUtils.isEmpty(storeNum);
        pcStore.setIsLimit(!flag);
        pcStore.setProductCode(product.getProductCode());
        pcStore.setProductId(product.getId());
        pcStore.setProductName(product.getProductName());
        pcStore.setProductType(1);
        pcStore.setSpecId(spec.getId());
        pcStore.setStoreNum(flag?0:storeNum);
        pcStore.setTenantId(product.getTenantId());
        pcStore.setTimeSlot(0);
        return pcStore;
    }

    // 修改商品时的库存处理
    private PCStore updatePcStore(BizProduct product,BizProductSpec spec,Integer storeNum){
        PCStore pcStore = new PCStore();
        pcStore.setCreateBy(BaseContextHandler.getUserID());
        boolean flag = storeNum.equals(9999);
        pcStore.setIsLimit(!flag);
        pcStore.setProductCode(product.getProductCode());
        pcStore.setProductId(product.getId());
        pcStore.setProductName(product.getProductName());
        pcStore.setProductType(1);
        pcStore.setSpecId(spec.getId());
        pcStore.setStoreNum(flag?0:storeNum);
        pcStore.setTenantId(product.getTenantId());
        pcStore.setTimeSlot(0);
        return pcStore;
    }


    /**
     * 查询商品详情
     * @param id
     * @return
     */
     public List<ResultProductInfo> getProductInfo(String id){
         List<ResultProductInfo> result = new ArrayList<>();
         ResultProductInfo info = new ResultProductInfo();
         DecimalFormat df =new  DecimalFormat("0.00");
         ResultProductInfoVo productInfoVo = bizProductMapper.selectProductInfo(id);
         if(productInfoVo != null){
             //秒杀商品
             List<ResultSpike> spikeVoList=bizProductMapper.selectSpikeByid(id);
             productInfoVo.setSpikeArr(spikeVoList);

             //项目范围
             List<ResultProjectVo> projectVoList = bizProductProjectMapper.selectProjectList(id);
             productInfoVo.setProjectVo(projectVoList);
             //商品分类
             List<ResultClassifyVo> classifyVoList = bizProductClassifyMapper.selectClassifyListById(id);
             productInfoVo.setClassifyVo(classifyVoList);
             //商品标签
             List<String> labelList = bizProductLabelMapper.selectLabelList(id);
             productInfoVo.setLabel(labelList);
             //商品logo
             List<ImgInfo> list = new ArrayList<>();
             ImgInfo imgInfo = new ImgInfo();
             imgInfo.setUrl(productInfoVo.getProductImage());
             list.add(imgInfo);
             productInfoVo.setProductImageList(list);
             //商品精选图片
             List<ImgInfo> selectList = new ArrayList<>();
             ImgInfo selectLImgInfo = new ImgInfo();
             if(productInfoVo.getSelectionImage() != null){
                 String[] selectLImgs = productInfoVo.getSelectionImage().split(",");
                 for(String url : selectLImgs){
                     selectLImgInfo.setUrl(url);
                     selectList.add(imgInfo);
                 }
             }
             productInfoVo.setSelectionImageList(selectList);
             //团购信息
             String type = bizProductClassifyMapper.selectTypeByBusId(productInfoVo.getBusId());
             if(!"2".equals(type) && !"4".equals(type)){
                 productInfoVo.setBegTime("");
                 productInfoVo.setEndTime("");
                 productInfoVo.setProductNum("");
             }
             info.setProductInfo(productInfoVo);
             info.setProductImagetextInfo(productInfoVo.getProductImagetextInfo());
         }else{
             ResultProductInfoVo vo = new ResultProductInfoVo();
             List<ResultProjectVo> projectVos = new ArrayList<>();
             List<ResultClassifyVo> classifyVos = new ArrayList<>();
             vo.setId("");
             vo.setProductName("");
             vo.setProductImagetextInfo("");
             vo.setBegTime("");
             vo.setEndTime("");
             vo.setBusId("");
             vo.setBusName("");
             vo.setProductAfterSale("");
             vo.setProductNum("");
             vo.setProductSummary("");
             vo.setProjectVo(projectVos);
             vo.setClassifyVo(classifyVos);
             info.setProductInfo(vo);
             info.setProductImagetextInfo("");
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
                 specVo.setStoreNumDesc(getStoreNum(specVo.getId()));
             }
             info.setSpecInfo(specVoList);
         }else {
             List<ResultSpecVo> specVos = new ArrayList<>();
             ResultSpecVo vo = new ResultSpecVo();
             vo.setId("");
             vo.setLowestNum("");
             vo.setOriginalPrice("");
             vo.setPrice("");
             vo.setUnit("");
             vo.setSpecName("");
             vo.setStoreNumDesc("");
             specVos.add(vo);
             info.setSpecInfo(specVos);
         }
         result.add(info);
         return result;
     }

     // 获取库存信息
     private String getStoreNum(String specId){
         try {
             CacheStore cacheStore = storeFegin.getProductCacheStore(specId);
             if(ObjectUtils.isEmpty(cacheStore)){
                 log.error("获取商品库存失败，未查到该商品库存信息");
                 return null;
             }
             if(cacheStore.getIsLimit()){
                 return cacheStore.getStoreNum()+"";
             }
             return "无限制";
         }catch (Exception e){
             e.printStackTrace();
             log.error("获取商品库存失败，未查到该商品库存信息");
         }
         return null;
     }

    /**
     * 编辑商品
     * @param info
     * @return
     */
    @Transactional
     public ObjectRestResponse updateProduct(UpdateProductInfo info){
        info.check();
         // 库存集合
         List<PCStore> pcStores = new ArrayList<>();
         ObjectRestResponse msg = new ObjectRestResponse();
         UpdateProductInfoVo param = info.getProductInfo();
         List<UpdateSpecVo> specInfo = info.getSpecInfo();
         String productImagetextInfo = info.getProductImagetextInfo();
         //编辑团购
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         String type = bizProductClassifyMapper.selectTypeByBusId(param.getBusId());
         if(!"4".equals(type)){
             if(StringUtils.isAnyEmpty(param.getProductName(),param.getBusId())){
                 msg.setStatus(201);
                 msg.setMessage("请完善必填基本信息(商品名称,所属业务,商品logo,所属项目)!");
                 return msg;
             }
             if(!"2".equals(type)){
                 if(param.getClassifyVo() == null || param.getClassifyVo().size() == 0){
                     msg.setStatus(201);
                     msg.setMessage("商品所属分类不能为空!");
                     return msg;
                 }
             }
         }

         if(param.getProductImageList() == null || param.getProductImageList().size() == 0){
             msg.setStatus(201);
             msg.setMessage("商品logo不能为空!");
             return msg;
         }
         if(param.getSelectionImageList() == null || param.getSelectionImageList().size() == 0){
             msg.setStatus(201);
             msg.setMessage("商品精选图片不能为空!");
             return msg;
         }
         int limitNum = -1;
         int productNum = -1;
         if("1".equals(type)){
             if(!StringUtils.isEmpty(param.getLimitNum()) && Integer.parseInt(param.getLimitNum()) != -1){
                 limitNum = Integer.parseInt(param.getLimitNum());
             }
             if(!StringUtils.isEmpty(param.getProductNums()) && Integer.parseInt(param.getProductNums()) != -1){
                 productNum = Integer.parseInt(param.getProductNums());
             }
         }
         ResultProductInfoVo productInfoVo = bizProductMapper.selectProductInfo(param.getId());
         StringBuilder sb = new StringBuilder();
         BizProduct product = new BizProduct();
         if(productInfoVo != null){
             try {
                 //BeanUtils.copyProperties(product , productInfoVo);
                 product.setId(param.getId());
                 product.setProductName(param.getProductName());
                 if(param.getProductImageList() != null && param.getProductImageList().size() > 0){
                     for(ImgInfo temp: param.getProductImageList()){
                         if(StringUtils.isNotEmpty(temp.getUrl())){
                             ObjectRestResponse restResponse = toolFeign.moveAppUploadUrlPaths(temp.getUrl(), DocPathConstant.SHOP);
                             if(restResponse.getStatus()==200){
                                 product.setProductImage(restResponse.getData()==null ? "" : (String)restResponse.getData());
                             }
                         }
                         //product.setProductImage(temp.getUrl());
                     }
                 }else{
                     product.setProductImage("");
                 }

                 String selectImages = "";
                 for(ImgInfo temp: param.getSelectionImageList()){
                     sb.append(temp.getUrl()+",");
                 }
                 selectImages = sb.substring(0,sb.length()-1);
                 if(StringUtils.isNotEmpty(selectImages)){
                     ObjectRestResponse restResponse = toolFeign.moveAppUploadUrlPaths(selectImages, DocPathConstant.SHOP);
                     if(restResponse.getStatus()==200){
                         product.setSelectionImage(restResponse.getData()==null ? "" : (String)restResponse.getData());
                     }
                 }
                 product.setProductSummary(param.getProductSummary());
                 product.setProductAfterSale(param.getProductAfterSale());
                 product.setProductImagetextInfo(productImagetextInfo);
                 product.setModifyBy(BaseContextHandler.getUserID());
                 product.setModifyTime(new Date());
                 product.setTimeStamp(new Date());
                 if("2".equals(type)){
                     product.setBegTime(sdf.parse(param.getBegTime()));
                     product.setEndTime(sdf.parse(param.getEndTime()));
                     //product.setProductNum(Integer.parseInt(param.getProductNum()));
                     product.setGroupbuyNum(Integer.parseInt(param.getGroupbuyNum()));
                 }
                 if("4".equals(type)){
                     product.setBegTime(sdf.parse(param.getBegTime()));
                     product.setEndTime(sdf.parse(param.getEndTime()));
//                     product.setProductNum(Integer.parseInt(param.getProductNum()));
//                     for(ResultSpike temp : param.getSpikeArr()){
//                         product.setProductId(temp.getId());
//                     }

                 }
                 if("1".equals(type)){
                     product.setProductNum(productNum);
                     product.setLimitNum(limitNum);
                 }
                 product.setSupplier(param.getSupplier());
                 product.setSalesWay(param.getSalesWay());
                 if(bizProductMapper.updateByPrimaryKeySelective(product) < 0 ){
                     msg.setStatus(108);
                     msg.setMessage("编辑商品失败!");
                     return msg;
                 }else{
                     //编辑商品分类
                     if(bizProductClassifyMapper.delClassifyInfo(param.getId()) > 0){
                         BizProductClassify classify = new BizProductClassify();
                         if("2".equals(type) || "4".equals(type)){
                             classify.setId(UUIDUtils.generateUuid());
                             classify.setProductId(param.getId());
                             classify.setBusId(param.getBusId());
                             classify.setCreateBy(BaseContextHandler.getUserID());
                             classify.setCreateTime(new Date());
                             classify.setTimeStamp(new Date());
                             if(bizProductClassifyMapper.insertSelective(classify) < 0){
                                 msg.setStatus(102);
                                 msg.setMessage("关联业务下所属商品分类失败!");
                                 return msg;
                             }
                         }else{
                             for(ResultClassifyVo temp : param.getClassifyVo()){
                                 classify.setId(UUIDUtils.generateUuid());
                                 classify.setProductId(param.getId());
                                 classify.setBusId(param.getBusId());
                                 classify.setClassifyId(temp.getClassifyId());
                                 classify.setCreateBy(BaseContextHandler.getUserID());
                                 classify.setCreateTime(new Date());
                                 classify.setTimeStamp(new Date());
                                 if(bizProductClassifyMapper.insertSelective(classify) < 0){
                                     msg.setStatus(102);
                                     msg.setMessage("关联业务下所属商品分类失败!");
                                     return msg;
                                 }
                             }
                         }
                     }else{
                         msg.setStatus(103);
                         msg.setMessage("编辑商品失败!");
                         return msg;
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
                                 msg.setStatus(101);
                                 msg.setMessage("关联服务范围失败!");
                                 return msg;
                             }
                         }
                     }else{
                         msg.setStatus(103);
                         msg.setMessage("编辑商品失败!");
                         return msg;
                     }
                     //编辑商品标签
                     if(param.getLabel() != null && param.getLabel().size() >0){
                         if(bizProductLabelMapper.delLabelfo(param.getId()) >= 0){
                             BizProductLabel label = new BizProductLabel();
                             for (int i=0;i< param.getLabel().size();i++){
                                 label.setId(UUIDUtils.generateUuid());
                                 label.setProductId(param.getId());
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
                         }else{
                             msg.setStatus(103);
                             msg.setMessage("编辑商品失败!");
                             return msg;
                         }
                     }
                 }
             } catch (Exception e) {
                 logger.error("编辑商品失败!",e);
             }

             //编辑商品规格
             int productCount = 0; // 剩余份数
             String productTotal = null; // 规格库存存在无上限
//             if(bizProductSpecMapper.delSpecInfo(param.getId()) > 0){
                 BizProductSpec spec = new BizProductSpec();
                 if(specInfo.size()>0 && specInfo != null){
                     List<String> specIds = bizProductSpecMapper.selectIdList(param.getId());
                     for (UpdateSpecVo temp : specInfo){
                         if(StringUtils.isAnyEmpty(temp.getSpecName(),temp.getPrice())){
                             msg.setStatus(201);
                             msg.setMessage("请完善必填基本信息(商品规格名称,价格)!");
                             return msg;
                         }
                         if(temp.getLowestNum() == "0"){
                             msg.setStatus(201);
                             msg.setMessage("最小购买数量不能为零!");
                             return msg;
                         }
                         if(ObjectUtils.isEmpty(temp.getStoreNum())){
                             temp.setStoreNum(0);
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
                             spec.setModifyBy(BaseContextHandler.getUserID());
                             spec.setModifyTime(new Date());
                             if(bizProductSpecMapper.updateByPrimaryKeySelective(spec) < 0){
                                 msg.setStatus(105);
                                 msg.setMessage("更新商品规格失败!");
                                 return msg;
                             }

                             // 对修改的规格记录疯抢商品总份数（规格库存数量之和）
                             if("4".equals(type)){
                                 CacheStore cacheStore = getProductSpecStock(temp.getId());
                                 if(ObjectUtils.isEmpty(cacheStore)){
                                     throw new BusinessException("库存设置失败");
                                 }

                                 // 库存有修改的处理
                                 if(!ObjectUtils.isEmpty(temp.getStoreNum()) && temp.getStoreNum() != 0){
                                     if(temp.getStoreNum().equals(9999)){
                                         if(StringUtils.isEmpty(productTotal)){
                                             productTotal = "9999";
                                         }
                                     }else {
                                         if(cacheStore.getIsLimit()){
                                             // 计算更新后的规格库存
                                             Integer specNum = temp.getStoreNum()+cacheStore.getStoreNum();
                                             if(specNum < 0 ){
                                                 throw new BusinessException(temp.getSpecName()+"规格增减库存数异常");
                                             }
                                             productCount += specNum;
                                         }else {
                                             // 无上限则获取修改的库存
                                             if(temp.getStoreNum() < 0 ){
                                                 throw new BusinessException(temp.getSpecName()+"规格增减库存数异常");
                                             }
                                             productCount += temp.getStoreNum();

                                         }

                                     }

                                 }else{
                                     // 库存无修改处理
                                     if(cacheStore.getIsLimit()){
                                         // 计算更新后的规格库存
                                         productCount += cacheStore.getStoreNum();
                                     }else {
                                         // 无上限则设置为9999
                                         productTotal = "9999";
                                     }
                                 }

                             }
                             // 移除已经更新的规格ID
                             specIds.remove(temp.getId());
                         }else{
                             // 规格不存在添加
                             // 新增的规格库存不能为负数
                             if(temp.getStoreNum() < 0 ){
                                 throw new BusinessException(temp.getSpecName()+"规格增减库存数异常");
                             }
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
                             spec.setCreateBy(BaseContextHandler.getUserID());
                             spec.setCreateTime(new Date());
                             spec.setTimeStamp(new Date());
                             if(bizProductSpecMapper.insertSelective(spec) < 0){
                                 msg.setStatus(105);
                                 msg.setMessage("添加商品规格失败!");
                                 return msg;
                             }

                             // 对新增的规格记录疯抢商品总份数（规格库存数量之和）
                             if("4".equals(type)){
                                 if(temp.getStoreNum().equals(9999)){
                                     productTotal = "9999";
                                 }else {
                                     productCount += temp.getStoreNum();
                                 }
                             }
                         }
                         // 普通商品的库存预处理
                         if(!ObjectUtils.isEmpty(temp.getStoreNum()) && temp.getStoreNum()!=0){
                             PCStore pcStore = updatePcStore(product,spec,temp.getStoreNum());
                             pcStores.add(pcStore);
                         }


                     }
                     if(CollectionUtils.isNotEmpty(specIds)){
                         // 删除集合未处理的规格（余下的表示用户已删除）
                         bizProductSpecMapper.deleteSpecIds(specIds);
                         // 对删除的规格记录疯抢商品总份数，返回的是负值（规格库存数量之和）
//                         if("4".equals(type)){
//                             productCount += getProductSpecStock(specIds);
//                         }
                     }

                     if("4".equals(type)){
                         // productTotal为空说明不存在无上限的规格
                         if(StringUtils.isEmpty(productTotal)){
                             productTotal = productCount + "";
                         }

                         // 更新总份数
                         product.setProductNum(Integer.parseInt(productTotal));
                         bizProductMapper.updateByPrimaryKeySelective(product);
                     }

                 }else{
                     msg.setStatus(107);
                     msg.setMessage("编辑信息不全,请继续填写完整!");
                     return msg;
                 }
//             }
         }else{
             msg.setStatus(103);
             msg.setMessage("编辑商品失败!");
             return msg;
         }
        // 普通商品的库存处理
        ObjectRestResponse objectRestResponse = storeFegin.updateStore(pcStores);
        if(!objectRestResponse.success()){
            throw new BusinessException("商品库存保存失败!");
        }
         msg.setMessage("Operation succeed!");
         msg.setData("1");
         return msg;
     }

    //批量获取商品规格库存
    private Integer getProductSpecStock(List<String> specIds){
        Integer productCount = 0;
        try {
            List<CacheStoreProduct> cacheStoreBatch = storeFegin.getCacheStoreBatch(specIds);

            if(CollectionUtils.isEmpty(cacheStoreBatch)){
                log.error("设置商品库存失败，specIdList:{},message:{}"
                        , JSON.toJSONString(specIds),"未查到该商品库存信息");
            }
            ImmutableMap<String, CacheStoreProduct> storeMap = Maps.uniqueIndex(cacheStoreBatch, CacheStoreProduct::getSpecId);
            for (String specId : specIds) {
                CacheStoreProduct cacheStoreProduct = storeMap.get(specId);
                if(cacheStoreProduct == null){
                    log.error("设置商品库存失败，specId:{},message:{}"
                            , specId,"未查到该商品库存信息");
                }
                CacheStore cacheStore = cacheStoreProduct.getCacheStore();
                if(cacheStore.getIsLimit()){
                    productCount += (cacheStore.getStoreNum() * -1);
                }
            }
        }catch (Exception e){
            log.error("设置商品库存失败，specIdList:{},message:{}"
                    , JSON.toJSONString(specIds),e.getMessage());
        }
        return productCount;
    }


    //获取商品规格库存
    private CacheStore getProductSpecStock(String specId){
        CacheStore cacheStore = null;
        try {
            cacheStore = storeFegin.getProductCacheStore(specId);
        }catch (Exception e){
            log.error("获取商品库存失败，specId:{},message:{}"
                    , specId,e.getMessage());
        }
        return cacheStore;
    }

    /**
     * 查询可选秒杀商品列表
     * @return
     */
    public List<ResultProductVo> getSpikeProductListPc(String searchVal){
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        List<ResultProductVo> spikeNameList = bizProductMapper.selectSpikeProductList(searchVal,type,BaseContextHandler.getTenantID());
        return spikeNameList;
    }
    /**
     * 查询商户列表下的商品分类名称列表
     * @return
     */
    public List<ResultClassifyVo> getClassifyNameList(){
         List<ResultClassifyVo> classifyNameList = bizProductClassifyMapper.selectClassifyNameList();
         return classifyNameList;
     }


    /**
     * 商品申请上架与下架操作
     * @param id
     * @return
     */
     public ObjectRestResponse updateStatus(String id,String status){
         ObjectRestResponse msg = new ObjectRestResponse();
         String busStatus = bizProductMapper.selectBusStatusById(id);
         if("1".equals(status)){//申请上架
             if("1".equals(busStatus) || "4".equals(busStatus) || "5".equals(busStatus)) {//待发布
                 if (bizProductMapper.updateAuditStatus(id, BaseContextHandler.getUserID()) < 0) {
                     msg.setStatus(101);
                     msg.setMessage("申请上架失败!");
                     return msg;
                 }
             }else{
                 msg.setStatus(102);
                 msg.setMessage("商品的业务状态,不能进行该操作!");
                 return msg;
             }
         }else if("3".equals(status)){//下架
             if("3".equals(busStatus)){//已发布
                 if(bizProductMapper.updateSoldStatus(id, BaseContextHandler.getUserID()) < 0) {
                     msg.setStatus(103);
                     msg.setMessage("下架失败!");
                     return msg;
                 }
             }else{
                 msg.setStatus(102);
                 msg.setMessage("商品的业务状态,不能进行该操作!");
                 return msg;

             }
         }else if("2".equals(status)){//上架
             if("2".equals(busStatus)){//待审核
                  if(bizProductMapper.updatePutawayStatus(id, BaseContextHandler.getUserID()) < 0){
                      msg.setStatus(104);
                      msg.setMessage("上架失败!");
                      return msg;
                  }
             }else{
                 msg.setStatus(102);
                 msg.setMessage("商品的业务状态,不能进行该操作!");
                 return msg;
             }
         }else if("4".equals(status)){//驳回
             if("2".equals(busStatus)) {//待审核
                  if (bizProductMapper.updateRejectStatus(id, BaseContextHandler.getUserID()) < 0){
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
     * 查询业务列表
     * @return
     */
     public List<ResultProductBusinessVo> getProductBusinessList(){
         List<ResultProductBusinessVo> productBusinessVoList = bizProductMapper.selectProductBusinessList();
         return productBusinessVoList;
     }

    /**
     * 查询商品分类列表
     * @param busId
     * @return
     */
     public List<ResultClassifyVo> getProductClassifyList(String busId){
         List<ResultClassifyVo> productClassifyList =  bizProductMapper.selectProductClassifyList(busId);
         return productClassifyList;
     }


    /**
     * 根据商户id查询商户下的项目列表
     * @return
     */
    public List<ResultProjectVo> getTenantProjectList(){
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        List<ResultProjectVo> projectVoList = bizProductMapper.selectTenantProjectList(type,BaseContextHandler.getTenantID());
         return projectVoList;
     }

    /**
     * 根据商户id查询商户下的业务列表
     * @return
     */
     public List<ResultProductBusinessVo> getTenantBusiness(){
         String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
         List<ResultProductBusinessVo> businessVoList = bizProductMapper.selectTenantBusinessList(BaseContextHandler.getTenantID(),type);
         return businessVoList;
     }


    /**
     * 查询商品审核列表
     * @param busStatus
     * @param searchVal
     * @param classifyId
     * @param page
     * @param limit
     * @return
     */
     public List<ResultProductAuditVo> getProductAuditList(String busStatus,String searchVal,String classifyId,
                                                           Integer page, Integer limit){
         DecimalFormat df =new  DecimalFormat("0.00");
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
         String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
         List<ResultProductAuditVo> auditVoList = bizProductMapper.selectProductAuditList(type,BaseContextHandler.getTenantID(),
                 busStatus, classifyId, searchVal, startIndex, limit);
         for (ResultProductAuditVo auditVo :auditVoList){
             //查询商品规格详情
             ResultSpecVo specInfo =  bizProductSpecMapper.selectAuditSpecInfo(auditVo.getId());
             if(specInfo != null){
                 auditVo.setPrice(df.format(Double.parseDouble(specInfo.getPrice())));
                 if(specInfo.getOriginalPrice() != null){
                     auditVo.setOriginalPrice(df.format(Double.parseDouble(specInfo.getOriginalPrice())));
                 }
                 auditVo.setUnit(specInfo.getUnit());
             }
             List<String> classifyNames = bizProductClassifyMapper.selectClassifyNameById(auditVo.getId());
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
         return auditVoList;
     }


    /**
     * 查询商品审核列表数量
     * @param busStatus
     * @param searchVal
     * @param classifyId
     * @return
     */
     public int selectProductAuditCount(String busStatus,String searchVal,String classifyId){
         String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
         int total = bizProductMapper.selectProductAuditCount(type,BaseContextHandler.getTenantID(),busStatus, classifyId ,searchVal);
         return total;
     }


    /**
     * 查询团购活动列表
     * @param productStatus
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
     public List<ResultGroupActiveVo> getGroupActiveList(String productStatus, String searchVal, Integer page,Integer limit){
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
         String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
         List<ResultGroupActiveVo> groupActiveVoList = bizProductMapper.selectGroupActiveList(BusinessConstant.getGroupBuyingBusId(),type,BaseContextHandler.getTenantID(),
                 productStatus, searchVal, startIndex, limit);
         for (ResultGroupActiveVo resultGroupActiveVo : groupActiveVoList) {
             int sales = bizProductMapper.selectOrderCount(BusinessConstant.getGroupBuyingBusId(), resultGroupActiveVo.getId());
             int newOrderTotal = bizProductOrderMapper.getGroupProductCount(resultGroupActiveVo.getId());
             resultGroupActiveVo.setSales(String.valueOf(sales+newOrderTotal));
         }
         return groupActiveVoList;
     }

    /**
     * 查询团购活动数量
     * @param productStatus
     * @param searchVal
     * @return
     */
     public int selectGroupActiveCount(String productStatus, String searchVal){
         String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
         int total = bizProductMapper.selectGroupActiveCount(BusinessConstant.getGroupBuyingBusId(),type,BaseContextHandler.getTenantID(),productStatus, searchVal);
         return total;
     }

    /**
     *查询团购活动详情
     * @param id
     * @return
     */
     public List<ResultGroupActiveInfoVo> getGroupActiveInfo(String id){
         List<ResultGroupActiveInfoVo> resultVo = new ArrayList<>();
         DecimalFormat df =new  DecimalFormat("0.00");
         ResultGroupActiveInfoVo groupActiveInfoVo = bizProductMapper.selectGroupAcidtiveInfo(id);
         if(groupActiveInfoVo != null){
             //查询商品规格详情
             ResultSpecVo specInfo =  bizProductSpecMapper.selectAuditSpecInfo(id);
             if(specInfo != null){

                 groupActiveInfoVo.setPrice(df.format(Double.parseDouble(specInfo.getPrice())));
                 if(specInfo.getOriginalPrice() != null){
                     groupActiveInfoVo.setOriginalPrice(df.format(Double.parseDouble(specInfo.getOriginalPrice())));
                 }
             }
             List<ResultOrderList> orderList = bizProductMapper.selectOrderListById(BusinessConstant.getGroupBuyingBusId(),id);
             for (ResultOrderList orderInfo : orderList){
                 orderInfo.setActualPrice(df.format(Double.parseDouble(orderInfo.getActualPrice())));
             }
             if(orderList == null || orderList.size() == 0){
                 orderList = new ArrayList<>();
             }
             groupActiveInfoVo.setOrderList(orderList);
         }
         resultVo.add(groupActiveInfoVo);
         return resultVo;
     }

    public List<ResultRecommendInfo> getProductListByBusId(String searchVal,Integer page,Integer limit) {
        String tenantId = BaseContextHandler.getTenantID();
        String type = bizProductMapper.selectSystemByTenantId(tenantId);
        if (!type.equals("2")) {
            tenantId = "";
        }
        Integer startIndex = 0;
        if (page != null) {
            //分页
            startIndex = (page - 1) * limit;
        }
        List<ResultRecommendInfo> productsInfos = bizProductMapper.getProductListByBusId(searchVal,startIndex,limit);
        if (productsInfos == null || productsInfos.size() == 0) {
            productsInfos = new ArrayList<>();
        }
        if (productsInfos != null && productsInfos.size() > 0) {
            for (ResultRecommendInfo productsInfo : productsInfos) {
                ProductListVo product = new ProductListVo();
                product.setProductId(productsInfo.getProductId());
                product.setProductName(productsInfo.getProductName());
                productsInfo.setProductInfo(product);
                List<ProjectListVo> projectInfo = bizProductGoodtoVisitMapper.selectProjectNames(productsInfo.getProductId());
                String projectName = "";
                StringBuilder projectSb = new StringBuilder();
                for (ProjectListVo projectListVo : projectInfo) {
                    projectSb.append(projectListVo.getProjectName() + ",");
                }
                projectName = projectSb.substring(0, projectSb.length() - 1);
                productsInfo.setProjectName(projectName);
            }
        }
        return productsInfos;
    }

    public int getProductListCount(String searchVal){
        List<Integer> count = bizProductMapper.getProductListCount(searchVal);
        int total = 0;
        for (Integer size : count) {
            total += size;
        }
        return total;
    }

    public List<ResultRecommendInfo> getProductForAD(String projectId, String searchVal, Integer page, Integer limit,String type) {
        Integer startIndex = null;
        if (page != null && limit!=null) {
            startIndex = (page - 1) * limit;
        }

        List<String> projectIdList = Arrays.asList(projectId.split(","));
        List<String> projectNameList = bizFamilyAdvertisingProjectMapper.selectProjectNameList(projectIdList);
        List<ResultRecommendInfo> productsInfos = bizProductMapper.getProductForAD(projectIdList,searchVal,startIndex,limit,type);
        if (productsInfos == null || productsInfos.size() == 0) {
            productsInfos = new ArrayList<>();
        }
        List<ResultRecommendInfo> newList = new ArrayList<>();
        if (productsInfos != null && productsInfos.size() > 0) {
            for (ResultRecommendInfo productsInfo : productsInfos) {
                ProductListVo product = new ProductListVo();
                product.setProductId(productsInfo.getProductId());
                product.setProductName(productsInfo.getProductName());
                productsInfo.setProductInfo(product);
                List<ProjectListVo> projectInfo = bizProductGoodtoVisitMapper.selectProjectNames(productsInfo.getProductId());
                String projectName = "";
                StringBuilder projectSb = new StringBuilder();
                for (ProjectListVo projectListVo : projectInfo) {
                    projectSb.append(projectListVo.getProjectName() + ",");
                }
                projectName = projectSb.substring(0, projectSb.length() - 1);
                productsInfo.setProjectName(projectName);
            }
        }

        for(ResultRecommendInfo product : productsInfos){
            int temp = 1;
            for(String str : projectNameList){
                if(product.getProjectName().contains(str)){
                    temp = 1;
                }else{
                    temp = 0;
                    break;
                }
            }
            if(temp ==1){
                newList.add(product);
            }
        }

        return newList;
    }

    public int getProductForADTotal(String projectId, String searchVal,String type) {
        return this.getProductForAD(projectId,searchVal,null,null,type).size();
    }


    public List<ReservationList> getReservationForAD(String projectId, String reservaStatus,
                                                     String searchVal, Integer page, Integer limit) {
        Integer startIndex = null;
        if (page != null && limit!=null) {
            startIndex = (page - 1) * limit;
        }

        List<String> projectIdList = Arrays.asList(projectId.split(","));
        List<String> projectNameList = bizFamilyAdvertisingProjectMapper.selectProjectNameList(projectIdList);
        List<ReservationList> reservationList = bizFamilyAdvertisingProjectMapper.getReservationForAD(reservaStatus,projectIdList,searchVal,startIndex,limit);
        for (ReservationList reservation : reservationList){
            List<String> projectNames = bizReservationMapper.selectProjectIdById(reservation.getId());
            if(projectNames != null && projectNames.size()>0){
                String projectName = "";
                StringBuilder resultEva = new StringBuilder();
                for (String project:projectNames){
                    resultEva.append(project + ",");
                }
                projectName = resultEva.substring(0, resultEva.length() - 1);
                reservation.setProjectName(projectName);
            }
            List<String> classifyNames = bizReservationMapper.selectClassifyNameById(reservation.getId());
            if(classifyNames != null && classifyNames.size()>0){
                String classifyName = "";
                StringBuilder resultEva = new StringBuilder();
                for (String classify:classifyNames){
                    resultEva.append(classify + ",");
                }
                classifyName = resultEva.substring(0, resultEva.length() - 1);
                reservation.setClassifyName(classifyName);
            }
        }
        List<ReservationList> newList = new ArrayList<>();
        for (ReservationList reservation : reservationList) {
            int temp = 1;
            for(String str : projectNameList){
                if(reservation.getProjectName().contains(str)){
                    temp = 1;
                }else{
                    temp = 0;
                    break;
                }
            }
            if(temp ==1){
                newList.add(reservation);
            }
        }
        return newList;
    }

    public int getReservationForADCount(String projectId, String reserveStatus, String searchVal) {
        return this.getReservationForAD(projectId,reserveStatus,searchVal,null,null).size();
    }

    public List<ResultGroupActiveInfoVo> getGroupActiveInfoByPid(String id) {

        List<ResultGroupActiveInfoVo> resultVo = new ArrayList<>();
        DecimalFormat df =new  DecimalFormat("0.00");
        ResultGroupActiveInfoVo groupActiveInfoVo = bizProductMapper.selectGroupAcidtiveInfo(id);
        if(groupActiveInfoVo != null){
            //查询商品规格详情
            ResultSpecVo specInfo =  bizProductSpecMapper.selectAuditSpecInfo(id);
            if(specInfo != null){
                groupActiveInfoVo.setPrice(df.format(Double.parseDouble(specInfo.getPrice())));
                if(specInfo.getOriginalPrice() != null){
                    groupActiveInfoVo.setOriginalPrice(df.format(Double.parseDouble(specInfo.getOriginalPrice())));
                }
            }

            List<ResultOrderList> orderList = bizProductMapper.selectOrderListById(BusinessConstant.getGroupBuyingBusId(),id);
            for (ResultOrderList orderInfo : orderList){
                orderInfo.setActualPrice(df.format(Double.parseDouble(orderInfo.getActualPrice())));
            }
            if(orderList == null || orderList.size() == 0){
                orderList = new ArrayList<>();
            }
            List<ResultOrderList> newOrderList = bizProductOrderBiz.findOrderListByProductId(AceDictionary.ORDER_TYPE_GROUP, id);
            if(CollectionUtils.isNotEmpty(newOrderList)){
                orderList.addAll(newOrderList);
            }
            groupActiveInfoVo.setSales(orderList.size()+"");
            groupActiveInfoVo.setOrderList(orderList);
        }
        resultVo.add(groupActiveInfoVo);
        return resultVo;
    }
}
