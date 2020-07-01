package com.github.wxiaoqi.security.app.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import com.github.wxiaoqi.security.api.vo.order.in.CreateWoInVo;
import com.github.wxiaoqi.security.api.vo.order.out.OperateButton;
import com.github.wxiaoqi.security.api.vo.order.out.TransactionLogVo;
import com.github.wxiaoqi.security.app.buffer.ConfigureBuffer;
import com.github.wxiaoqi.security.app.entity.*;
import com.github.wxiaoqi.security.app.fegin.CodeFeign;
import com.github.wxiaoqi.security.app.mapper.*;
import com.github.wxiaoqi.security.app.rpc.WorkOrderSyncBiz;
import com.github.wxiaoqi.security.app.vo.in.WoInVo;
import com.github.wxiaoqi.security.app.vo.product.out.*;
import com.github.wxiaoqi.security.app.vo.reservation.in.ReservationParam;
import com.github.wxiaoqi.security.app.vo.reservation.out.*;
import com.github.wxiaoqi.security.app.vo.shopping.out.CompanyInfo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.BusinessConstant;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateTimeUtil;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 预约服务业务
 *
 * @Author guohao
 * @Date 2020/4/16 20:35
 */
@Service
public class BizReservationBiz extends BusinessBiz<BizReservationPersonMapper, BizReservationPerson> {
    private Logger log = LoggerFactory.getLogger(BizReservationBiz.class);
    @Resource
    private BizReservationPersonMapper bizReservationPersonMapper;
    @Resource
    private BizBusinessClassifyMapper bizBusinessClassifyMapper;
    @Autowired
    private BizWoBiz bizWoBiz;
    @Autowired
    private BizTransactionLogBiz bizTransactionLogBiz;
    @Autowired
    private CodeFeign codeFeign;
    @Resource
    private BizReservationMapper bizReservationMapper;
    @Resource
    private BizCrmProjectMapper bizCrmProjectMapper;
    @Resource
    private BizSubscribeMapper bizSubscribeMapper;
    @Resource
    private BizSubReservationMapper bizSubReservationMapper;
    @Resource
    private WorkOrderSyncBiz workOrderSyncBiz;
    @Resource
    private BizProductLabelMapper bizProductLabelMapper;
    @Resource
    private BizProductSpecMapper bizProductSpecMapper;
    @Resource
    private BizProductMapper bizProductMapper;

    /**
     * 查询预约服务的服务分类列表
     *
     * @return
     */
    public ObjectRestResponse<List<ClassifyVo>> getServiceClassifyList() {
        List<ClassifyVo> classifyVoList = bizBusinessClassifyMapper.selectClassifyList(BusinessConstant.getReservationBusId());
        if (classifyVoList == null || classifyVoList.size() == 0) {
            classifyVoList = new ArrayList<>();
        }
        return ObjectRestResponse.ok(classifyVoList);
    }


    /**
     * 根据分类id查询预约服务列表
     * 此接口已修改,对老版本无影响
     *
     * @param classifyId
     * @return
     */
    public ObjectRestResponse<List<ReservationVo>> getReservationList(String projectId, String classifyId, Integer page, Integer limit) {
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
        DecimalFormat df = new DecimalFormat("0.00");
        List<ReservationVo> reservationVoList = bizReservationPersonMapper.selectReservationByClassifyId(projectId, classifyId, startIndex, limit,null);
        if (reservationVoList == null || reservationVoList.size() == 0) {
            reservationVoList = new ArrayList<>();
        } else {
            //更新过期优惠券状态
            if (reservationVoList.size() != 0) {
                List<String> productList = new ArrayList<>();
                for (ReservationVo reservationVo : reservationVoList) {
                    productList.add(reservationVo.getId());
                }
                List<String> couponIds = bizProductMapper.selectCouponIds(productList);
                if (couponIds.size() != 0) {
                    bizProductMapper.updateCouponStatusByProduct(couponIds);
                }
            }
            for (ReservationVo vo : reservationVoList) {
                //商品标签
                List<String> labelList = bizProductLabelMapper.selectLabelList(vo.getId());
                vo.setLabel(labelList);
                //商品规格单价
                SpecVo specInfo = bizProductSpecMapper.selectSpecInfo(vo.getId());
                if (specInfo != null) {
                    vo.setPrice(df.format(Double.parseDouble(specInfo.getPrice())));
                    if (specInfo.getUnit() != null || !"".equals(specInfo.getUnit())) {
                        vo.setUnit(specInfo.getUnit());
                    } else {
                        vo.setUnit("");
                    }
                }
                //商品优惠券
                CouponInfoVo coupon = bizProductMapper.getProductCoupon(vo.getId());
                if (coupon != null) {
                    vo.setCoupon(coupon.getDiscountNum() == null ? "领券满" + coupon.getMinValue() + "减" + coupon.getValue() : "领取" + coupon.getDiscountNum() + "折券");
                }
            }
        }
        return ObjectRestResponse.ok(reservationVoList);
    }


    /**
     * 查询预约服务详情
     * 此接口已修改,对老版本无影响
     *
     * @param id
     * @return
     */
    public ObjectRestResponse<ReservationInfo> getReservationInfo(String id) {
        DecimalFormat df = new DecimalFormat("0.00");
        ReservationInfo reservationInfos = bizReservationPersonMapper.selectReservationInfoById(id);
        if (reservationInfos == null) {
            reservationInfos = new ReservationInfo();
        } else {
            //商品信息
            if (reservationInfos.getReservationImagetextInfo() != null) {
                if (reservationInfos.getReservationImagetextInfo().equals("<p><br data-mce-bogus=\"1\"></p>")) {
                    reservationInfos.setReservationImagetextInfo("");
                }
            }
            //商品标签
            List<String> labelList = bizProductLabelMapper.selectLabelList(reservationInfos.getId());
            reservationInfos.setLabel(labelList);
            //商品规格单价
            SpecVo specInfo = bizProductSpecMapper.selectSpecInfo(reservationInfos.getId());
            if (specInfo != null) {
                reservationInfos.setPrice(df.format(Double.parseDouble(specInfo.getPrice())));
                if (specInfo.getOriginalPrice() != null) {
                    reservationInfos.setOriginalPrice(df.format(Double.parseDouble(specInfo.getOriginalPrice())));
                } else {
                    reservationInfos.setOriginalPrice("0");
                }
                if (specInfo.getUnit() != null || !"".equals(specInfo.getUnit())) {
                    reservationInfos.setUnit(specInfo.getUnit());
                } else {
                    reservationInfos.setUnit("");
                }
            }
            reservationInfos.setSelectedSpec("");
            reservationInfos.setBuyNum("0");
            reservationInfos.setAddress("");

            //商品所属规格
            List<ProductSpecInfo> productSpecInfoList = bizProductSpecMapper.selectSpecListByProductId(id);
            if (productSpecInfoList != null && productSpecInfoList.size() > 0) {
                for (ProductSpecInfo info : productSpecInfoList) {
                    List<ImgInfo> imglist = new ArrayList<>();
                    ImgInfo specImgInfo = new ImgInfo();
                    if (info.getSpecImage() != null && info.getSpecImage() != "") {
                        specImgInfo.setUrl(info.getSpecImage());
                    } else {
                        specImgInfo.setUrl(reservationInfos.getProductImage());
                    }
                    if (info.getLowestNum() == null || info.getLowestNum() == "") {
                        info.setLowestNum("");
                    }
                    if (specInfo.getUnit() == null || "".equals(specInfo.getUnit())) {
                        info.setUnit("");
                    }
                    imglist.add(specImgInfo);
                    info.setSpecImageList(imglist);
                    info.setPrice(df.format(Double.parseDouble(info.getPrice())));
                }
                reservationInfos.setProductSpecInfo(productSpecInfoList);
            }
            //商品所属公司
            CompanyInfo companyInfo = bizReservationPersonMapper.selectCompanyInfoByRId(id);
            if (companyInfo != null) {
                reservationInfos.setCompanyId(companyInfo.getCompanyId());
                reservationInfos.setCompanyName(companyInfo.getCompanyName());
                reservationInfos.setLogoImg(companyInfo.getLogoImg());
                reservationInfos.setIsClose(companyInfo.getIsClose());
                reservationInfos.setIsInvoice(companyInfo.getIsInvoice());
            }
            //查询商品的用户评价
            List<UserCommentVo> userCommentList = bizProductMapper.selectProductCommentDetail(id, 0, 2);
            if (userCommentList == null || userCommentList.size() == 0) {
                userCommentList = new ArrayList<>();
            }
            reservationInfos.setUserCommentList(userCommentList);
        }
        return ObjectRestResponse.ok(reservationInfos);
    }


    /**
     * 查询家政超市的规格类型
     *
     * @param id
     * @return
     */
    public ObjectRestResponse<List<ProductSpecInfo>> getSpecTypeList(String id) {
        List<ProductSpecInfo> specInfoList = bizProductSpecMapper.selectAllSpecTypeList(id);
        return ObjectRestResponse.ok(specInfoList);
    }


    /**
     * 查询预约服务详情(新接口)
     *
     * @param id
     * @return
     */
    public ObjectRestResponse<ReservationInfo> getNewReservationInfo(String id, boolean share) {
        DecimalFormat df = new DecimalFormat("0.00");
        ReservationInfo reservationInfos = null;
        if (!share) {
            reservationInfos = bizReservationPersonMapper.selectNewReservationInfoById(id, BaseContextHandler.getUserID());
        } else {
            reservationInfos = bizReservationPersonMapper.selectShareNewReservationInfoById(id);
        }
        if (reservationInfos == null) {
            reservationInfos = new ReservationInfo();
        } else {
            //商品信息
            if (reservationInfos.getReservationImagetextInfo() != null) {
                if (reservationInfos.getReservationImagetextInfo().equals("<p><br data-mce-bogus=\"1\"></p>")) {
                    reservationInfos.setReservationImagetextInfo("");
                }
            }
            //商品标签
            List<String> labelList = bizProductLabelMapper.selectLabelList(reservationInfos.getId());
            reservationInfos.setLabel(labelList);
            //商品规格单价
            SpecVo specInfo = bizProductSpecMapper.selectSpecInfo(reservationInfos.getId());
            if (specInfo != null) {
                reservationInfos.setPrice(df.format(Double.parseDouble(specInfo.getPrice())));
                if (specInfo.getOriginalPrice() != null) {
                    reservationInfos.setOriginalPrice(df.format(Double.parseDouble(specInfo.getOriginalPrice())));
                } else {
                    reservationInfos.setOriginalPrice("0");
                }
                if (specInfo.getUnit() != null || !"".equals(specInfo.getUnit())) {
                    reservationInfos.setUnit(specInfo.getUnit());
                } else {
                    reservationInfos.setUnit("");
                }
            }
            reservationInfos.setSelectedSpec("");
            reservationInfos.setBuyNum("0");
            reservationInfos.setAddress("");

            //商品所属规格
            List<ProductSpecInfo> productSpecInfoList = bizProductSpecMapper.selectSpecListByProductId(id);
            if (productSpecInfoList != null && productSpecInfoList.size() > 0) {
                for (ProductSpecInfo info : productSpecInfoList) {
                    List<ImgInfo> imglist = new ArrayList<>();
                    ImgInfo specImgInfo = new ImgInfo();
                    if (info.getSpecImage() != null && info.getSpecImage() != "") {
                        specImgInfo.setUrl(info.getSpecImage());
                    } else {
                        specImgInfo.setUrl(reservationInfos.getProductImage());
                    }
                    if (info.getLowestNum() == null || info.getLowestNum() == "") {
                        info.setLowestNum("");
                    }
                    if (info.getUnit() == null || "".equals(info.getUnit())) {
                        info.setUnit("");
                    }
                    if (info.getOriginalPrice() == null || "".equals(info.getOriginalPrice())) {
                        info.setOriginalPrice("0");
                    } else {
                        info.setOriginalPrice(df.format(Double.parseDouble(info.getOriginalPrice())));
                    }
                    imglist.add(specImgInfo);
                    info.setSpecImageList(imglist);
                    info.setPrice(df.format(Double.parseDouble(info.getPrice())));
                }
                reservationInfos.setProductSpecInfo(productSpecInfoList);
            }
            //商品所属公司
            CompanyInfo companyInfo = bizReservationPersonMapper.selectCompanyInfoByRId(id);
            if (companyInfo != null) {
                reservationInfos.setCompanyId(companyInfo.getCompanyId());
                reservationInfos.setCompanyName(companyInfo.getCompanyName());
                reservationInfos.setLogoImg(companyInfo.getLogoImg());
                reservationInfos.setIsClose(companyInfo.getIsClose());
                reservationInfos.setIsInvoice(companyInfo.getIsInvoice());
            }
            //查询商品的用户评价
            List<UserCommentVo> userCommentList = bizProductMapper.selectProductCommentDetail(id, 0, 2);
            if (userCommentList == null || userCommentList.size() == 0) {
                userCommentList = new ArrayList<>();
            }
            reservationInfos.setUserCommentList(userCommentList);
            //优惠券信息
            List<CouponInfoVo> couponList = bizProductMapper.getProductCouponList(id);
            List<String> couponInfo = new ArrayList<>();
            if (couponList.size() != 0) {
                for (CouponInfoVo coupon : couponList) {
                    couponInfo.add(coupon.getDiscountNum() == null ? "领券" + coupon.getMinValue() + "减" + coupon.getValue() : "领取" + coupon.getDiscountNum() + "折券");
                }
            }
            reservationInfos.setCouponList(couponInfo);
        }
        return ObjectRestResponse.ok(reservationInfos);
    }


    /**
     * 提交预约服务
     *
     * @param param
     * @return
     */
    public ObjectRestResponse saveReservation(ReservationParam param) {
        ObjectRestResponse msg = new ObjectRestResponse();
        Map<String, String> woMap = new HashMap<>();
        if (param != null || param == null) {
            msg.setStatus(1001);
            msg.setMessage("发现新版本，请前去更新!");
            return msg;
        }
        if (StringUtils.isEmpty(param.getContactorName())) {
            msg.setStatus(201);
            msg.setMessage("联系人不能为空!");
            return msg;
        }
        if (StringUtils.isEmpty(param.getContactTel())) {
            msg.setStatus(201);
            msg.setMessage("联系方式不能为空!");
            return msg;
        }
        if (StringUtils.isEmpty(param.getAddress())) {
            msg.setStatus(201);
            msg.setMessage("联系地址不能为空!");
            return msg;
        }
        if (StringUtils.isEmpty(param.getReservationTime())) {
            msg.setStatus(201);
            msg.setMessage("预约时间不能为空!");
            return msg;
        }
        String logo = bizReservationPersonMapper.selectLogoById(param.getReservationId());
        String companyId = bizReservationPersonMapper.selectTenantIdById(param.getReservationId());
        String classifyName = bizReservationPersonMapper.seelctClassifyNameById(param.getClassifyId());
        String woCode = "";
        List<String> projectIds = new ArrayList<>();
        projectIds.add(param.getProjectId());
        List<BizCrmProject> bizCrmProjectBizList = bizCrmProjectMapper.getByIds(projectIds);
        String projectCode = "";
        String wcode = "";
        if (bizCrmProjectBizList != null && bizCrmProjectBizList.size() > 0) {
            projectCode = bizCrmProjectBizList.get(0).getProjectCode();
        }
        String[] projectCodeArray = projectCode.split("-");
        if (projectCodeArray != null && projectCodeArray.length >= 2) {
            wcode = projectCodeArray[1];
        }
        String companyCode = bizSubscribeMapper.getByCompanyId(companyId);
        if (companyCode == null) {
            companyCode = "";
        }

        String bstype = "-S-";
        String subCode = wcode + "-" + companyCode + bstype + DateTimeUtil.shortDateString() + "-";
        ObjectRestResponse response = codeFeign.getCode("Subscribe", subCode, "6", "0");
        log.info("生成工单编码处理结果：" + response.toString());
        if (response.getStatus() == 200) {
            woCode = (String) response.getData();
        }
        String woId = "";
        try {
            CreateWoInVo createWoInVo = new CreateWoInVo();
            createWoInVo.setContactUserId(BaseContextHandler.getUserID());
            createWoInVo.setContactName(param.getContactorName());
            createWoInVo.setContactTel(param.getContactTel());
            createWoInVo.setAddr(param.getAddress());
            createWoInVo.setDescription(param.getRemark());
            createWoInVo.setProjectId(param.getProjectId());
            createWoInVo.setRoomId(param.getRoomId());
            createWoInVo.setComeFrom("1");
            createWoInVo.setExpectedServiceTimeStr(param.getReservationTime());
            createWoInVo.setBusId(BusinessConstant.getReservationBusId());
            WoInVo woInVo = new WoInVo();
            BeanUtils.copyProperties(createWoInVo, woInVo);
            woInVo.setCompanyId(companyId);
            woInVo.setWoCode(woCode);
            woInVo.setThreeCategoryName(classifyName);
            woInVo.setImgId(logo);
            ObjectRestResponse objectRestResponse = bizWoBiz.createWoOrder(woInVo);
            if (objectRestResponse.getStatus() != 200) {
                msg.setStatus(201);
                msg.setMessage("生成工单失败!");
                return msg;
            } else {
                Map<String, String> temp = objectRestResponse.getData() == null ? null : (Map<String, String>) objectRestResponse.getData();
                woId = temp.get("woId") == null ? "" : temp.get("woId");
                if (woMap != null) {
                    workOrderSyncBiz.createReservationWorkOrderThread(woId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //生成预约单号
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String code = "Y" + sdf.format(new Date());
        ObjectRestResponse objectRestResponse = codeFeign.getCode("Reservation", code, "6", "0");
        log.info("生成预约单号处理结果：" + objectRestResponse.toString());
        BizReservationPerson person = new BizReservationPerson();
        String id = UUIDUtils.generateUuid();
        person.setId(id);
        if (objectRestResponse.getStatus() == 200) {
            person.setReservationNum(objectRestResponse.getData() == null ? "" : (String) objectRestResponse.getData());
        }
        person.setReservationId(param.getReservationId());
        person.setClassifyId(param.getClassifyId());
        person.setUserId(BaseContextHandler.getUserID());
        person.setContactorName(param.getContactorName());
        person.setContactTel(param.getContactTel());
        person.setAddress(param.getAddress());
        person.setRemark(param.getRemark());
        person.setReservationTime(param.getReservationTime());
        person.setDealStatus("1");
        person.setCreateBy(BaseContextHandler.getUserID());
        person.setCreateTime(new Date());
        person.setTimeStamp(new Date());
        if (bizReservationPersonMapper.insertSelective(person) < 0) {
            msg.setStatus(201);
            msg.setMessage("提交预约失败!");
            return msg;
        } else {
            int sales = bizReservationPersonMapper.selectSalesById(param.getReservationId());
            BizReservation reservation = new BizReservation();
            reservation.setId(param.getReservationId());
            reservation.setSales(sales + 1);
            reservation.setModifyBy(BaseContextHandler.getUserID());
            reservation.setModifyTime(new Date());
            reservation.setTimeStamp(new Date());
            bizReservationMapper.updateByPrimaryKeySelective(reservation);

            //服务工单
            BizSubReservation subReservation = new BizSubReservation();
            subReservation.setId(UUIDUtils.generateUuid());
            subReservation.setReservationId(id);
            subReservation.setWoId(woId);
            subReservation.setCreateBy(BaseContextHandler.getUserID());
            subReservation.setCreateTime(new Date());
            subReservation.setTimeStamp(new Date());
            bizSubReservationMapper.insertSelective(subReservation);
        }
        woMap.put("woId", woId);
        msg.setMessage("Operation succeed!");
        msg.setData(woMap);
        return msg;
    }


    /**
     * 查询用户预约列表
     *
     * @param dealStatus
     * @param page
     * @param limit
     * @return
     */
    public ObjectRestResponse<List<MyReservationVo>> getUserReservationList(String dealStatus, Integer page, Integer limit) {
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        String userId = BaseContextHandler.getUserID();
        if (com.github.wxiaoqi.security.common.util.StringUtils.isEmpty(userId)) {
            objectRestResponse.setStatus(101);
            objectRestResponse.setMessage("用户未登陆，请登陆系统");
            return objectRestResponse;
        }
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
        if ("0".equals(dealStatus)) {
            dealStatus = "";
        }
        List<MyReservationVo> myReservationVoList = bizReservationPersonMapper.selectUserReservation(BaseContextHandler.getUserID(), dealStatus, startIndex, limit);
        if (myReservationVoList == null || myReservationVoList.size() == 0) {
            myReservationVoList = new ArrayList<>();
        }
        return ObjectRestResponse.ok(myReservationVoList);
    }


    /**
     * 查看预约详情
     *
     * @param id
     * @return
     */
    public ObjectRestResponse<WoDetail> getMyReservationInfo(String id) {
        WoDetail woDetails = new WoDetail();
        MyReservationInfo myReservationInfo = bizReservationPersonMapper.selectUserReservationInfo(id);
        if (myReservationInfo == null) {
            myReservationInfo = new MyReservationInfo();
        }
        woDetails.setMyReservationInfo(myReservationInfo);

        //2.获取工单操作按钮
        List<OperateButton> operateButtonList = null;
        operateButtonList = ConfigureBuffer.getInstance().getClientButtonByProcessId(myReservationInfo.getProcessId());
        if (operateButtonList == null || operateButtonList.size() == 0) {
            operateButtonList = new ArrayList<>();
        }
        woDetails.setOperateButtonList(operateButtonList);

        //3.获取操作流水日志
        TenameInfo tenameInfo = bizReservationPersonMapper.seelctTenameInfoById(myReservationInfo.getCompanyId());
        List<TransactionLogVo> transactionLogList = bizTransactionLogBiz.selectTransactionLogListById(id);
        if (transactionLogList == null && transactionLogList.size() == 0) {
            transactionLogList = new ArrayList<>();
        } else {
            for (TransactionLogVo logVo : transactionLogList) {
                if ("处理中".equals(logVo.getCurrStep())) {
                    if ("".equals(logVo.getConName()) || logVo.getConName() == null) {
                        logVo.setDescription("已接单，处理中\r\n处理人: " + tenameInfo.getContactorName());
                    }
                }
            }
        }
        woDetails.setTransactionLogList(transactionLogList);
        return ObjectRestResponse.ok(woDetails);
    }


    /**
     * 查看预约工单详情
     *
     * @param woId
     * @return
     */
    public ObjectRestResponse<ReservatWoDetail> getReservationWoInfo(String woId) {
        ReservatWoDetail woDetails = new ReservatWoDetail();
        TenameInfo tenameInfo = bizReservationPersonMapper.seelctTenameInfoById(BaseContextHandler.getTenantID());
        ReservatPersonInfo reservationInfo = bizReservationPersonMapper.selectReservationPersonInfo(woId);
        String type = bizReservationPersonMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        if (reservationInfo == null) {
            reservationInfo = new ReservatPersonInfo();
        } else {
            if (("3".equals(type) || "2".equals(type)) && ("01".equals(reservationInfo.getDealStatus()) || "06".equals(reservationInfo.getDealStatus()))) {
                reservationInfo.setHandleName("");
            }
            //项目范围
            List<String> projectNames = bizReservationPersonMapper.selectProjectIdById(reservationInfo.getId());
            if (projectNames != null && projectNames.size() > 0) {
                String projectName = "";
                StringBuilder resultEva = new StringBuilder();
                for (String project : projectNames) {
                    resultEva.append(project + ",");
                }
                projectName = resultEva.substring(0, resultEva.length() - 1);
                reservationInfo.setProjectName(projectName);
            }
            //2.获取工单操作按钮
            List<OperateButton> operateButtonList = null;
            operateButtonList = ConfigureBuffer.getInstance().getServiceButtonByProcessId(reservationInfo.getProcessId());
            //判断是否可以转单条件(1.当前处理人和接单人为当前用户，2.工单状态为已接受处理中)
            String woStatusStr = reservationInfo.getDealStatus() == null ? "" : (String) reservationInfo.getDealStatus();
            String userId = BaseContextHandler.getUserID();
            if (com.github.wxiaoqi.security.common.util.StringUtils.isNotEmpty(woStatusStr) && "03".equals(woStatusStr) && userId.equals(reservationInfo.getHandleId())) {
                reservationInfo.setIsTurn("1");
            } else {
                reservationInfo.setIsTurn("0");
            }
            if (operateButtonList == null || operateButtonList.size() == 0) {
                operateButtonList = new ArrayList<>();
            }
            woDetails.setOperateButtonList(operateButtonList);
        }
        woDetails.setReservationInfo(reservationInfo);
        //3.获取操作流水日志
        List<TransactionLogVo> transactionLogList = bizTransactionLogBiz.selectTransactionLogListById(woId);
        if (transactionLogList == null && transactionLogList.size() == 0) {
            transactionLogList = new ArrayList<>();
        } else {
            for (TransactionLogVo logVo : transactionLogList) {
                if ("处理中".equals(logVo.getCurrStep()) && ("3".equals(type) || "2".equals(type))) {
                    if ("".equals(logVo.getConName()) || logVo.getConName() == null) {
                        if (tenameInfo.getContactorName() != null) {
                            logVo.setDescription("已接单，处理中\r\n处理人: " + tenameInfo.getContactorName());
                            BizTransactionLog log = new BizTransactionLog();
                            log.setId(logVo.getId());
                            log.setDescription("已接单，处理中\r\n处理人: " + tenameInfo.getContactorName());
                            bizTransactionLogBiz.updateSelectiveById(log);
                        }
                    }
                }
            }
        }
        woDetails.setTransactionLogList(transactionLogList);
        return ObjectRestResponse.ok(woDetails);
    }

    public ObjectRestResponse getReservationIndex(String projectId) {
        ObjectRestResponse msg = new ObjectRestResponse();
        if (StringUtils.isEmpty(projectId)) {
            msg.setStatus(101);
            msg.setMessage("项目ID不能为空");
            return msg;
        }
        List<ReservationVo> recommendVoList = bizReservationPersonMapper.getReservationIndex(projectId);
        for (ReservationVo vo : recommendVoList) {
            List<ImgInfo> imglist = new ArrayList<>();
            ImgInfo imgInfo = new ImgInfo();
            imgInfo.setUrl(vo.getReservationLogo());
            imglist.add(imgInfo);
        }
        if (recommendVoList == null || recommendVoList.size() == 0) {
            recommendVoList = new ArrayList<>();
        }
        msg.setData(recommendVoList);
        return msg;
    }

    public ObjectRestResponse<List<ReservationVo>> getReservationMore(String projectId, Integer page, Integer limit) {
        ObjectRestResponse msg = new ObjectRestResponse();
        if (StringUtils.isEmpty(projectId)) {
            msg.setStatus(101);
            msg.setMessage("项目ID不能为空");
            return msg;
        }
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
        DecimalFormat df = new DecimalFormat("0.00");
        List<ReservationVo> reservationList = bizReservationPersonMapper.getReservationMore(projectId, startIndex, limit);
        if (reservationList == null || reservationList.size() == 0) {
            reservationList = new ArrayList<>();
        } else {
            //更新过期优惠券状态
            if (reservationList.size() != 0) {
                List<String> productList = new ArrayList<>();
                for (ReservationVo reservationVo : reservationList) {
                    productList.add(reservationVo.getId());
                }
                List<String> couponIds = bizProductMapper.selectCouponIds(productList);
                if (couponIds.size() != 0) {
                    bizProductMapper.updateCouponStatusByProduct(couponIds);
                }
            }
            for (ReservationVo vo : reservationList) {
                //商品标签
                List<String> labelList = bizProductLabelMapper.selectLabelList(vo.getId());
                vo.setLabel(labelList);
                //商品规格单价
                SpecVo specInfo = bizProductSpecMapper.selectSpecInfo(vo.getId());
                if (specInfo != null) {
                    vo.setPrice(df.format(Double.parseDouble(specInfo.getPrice())));
                    if (specInfo.getUnit() != null || !"".equals(specInfo.getUnit())) {
                        vo.setUnit(specInfo.getUnit());
                    } else {
                        vo.setUnit("");
                    }
                }
                //商品优惠券
                CouponInfoVo coupon = bizProductMapper.getProductCoupon(vo.getId());
                if (coupon != null) {
                    vo.setCoupon(coupon.getDiscountNum() == null ? "领券满" + coupon.getMinValue() + "减" + coupon.getValue() : "领取" + coupon.getDiscountNum() + "折券");
                }
            }
        }
        msg.setData(reservationList);
        return msg;
    }
}
