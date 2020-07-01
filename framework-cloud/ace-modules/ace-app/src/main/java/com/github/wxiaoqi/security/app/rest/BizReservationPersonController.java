package com.github.wxiaoqi.security.app.rest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.buffer.OperateConstants;
import com.github.wxiaoqi.security.api.vo.order.in.BuyProductInfo;
import com.github.wxiaoqi.security.api.vo.order.in.CompanyProduct;
import com.github.wxiaoqi.security.api.vo.order.in.SearchSubOrderIn;
import com.github.wxiaoqi.security.api.vo.order.in.SubProduct;
import com.github.wxiaoqi.security.api.vo.order.out.BuyProductOutVo;
import com.github.wxiaoqi.security.api.vo.order.out.SubDetailOutVo;
import com.github.wxiaoqi.security.app.biz.BizReservationPersonBiz;
import com.github.wxiaoqi.security.app.biz.BizSubBiz;
import com.github.wxiaoqi.security.app.mapper.BizProductMapper;
import com.github.wxiaoqi.security.app.mapper.BizReservationPersonMapper;
import com.github.wxiaoqi.security.app.mapper.BizSubProductMapper;
import com.github.wxiaoqi.security.app.vo.product.out.ClassifyVo;
import com.github.wxiaoqi.security.app.vo.product.out.ProductSpecInfo;
import com.github.wxiaoqi.security.app.vo.reservation.in.AppendSubReservationParam;
import com.github.wxiaoqi.security.app.vo.reservation.in.ReservationParam;
import com.github.wxiaoqi.security.app.vo.reservation.out.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.constant.BusinessConstant;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateUtils;
import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 预约服务人员表
 *
 * @author huangxl
 * @Date 2019-03-12 09:26:32
 */
@RestController
@RequestMapping("bizReservationPerson")
@CheckClientToken
@CheckUserToken
@Api(tags="APP预约服务")
public class BizReservationPersonController {

    @Autowired
    private BizReservationPersonBiz bizReservationPersonBiz;
    @Autowired
    private BizSubBiz bizSubBiz;
    @Autowired
    private BizReservationPersonMapper bizReservationPersonMapper;
    @Autowired
    private BizProductMapper bizProductMapper;
    @Autowired
    private BizSubProductMapper bizSubProductMapper;



    /**
     * 查询预约服务的服务分类列表
     * @return
     */
    @RequestMapping(value = "/getServiceClassifyList" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询预约服务的服务分类列表---APP端", notes = "查询预约服务的服务分类列表---APP端",httpMethod = "GET")
    public ObjectRestResponse<List<ClassifyVo>> getServiceClassifyList(){
        return  bizReservationPersonBiz.getServiceClassifyList();
    }




    /**
     * 根据分类id查询预约服务列表
     * @param classifyId
     * @return
     */
    @RequestMapping(value = "/getReservationList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据分类id查询预约服务列表(公用，不影响以前接口)---APP端", notes = "根据分类id查询预约服务列表---APP端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="classifyId",value="分类id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<List<ReservationVo>> getReservationList(String projectId, String classifyId, Integer page, Integer limit){
        return bizReservationPersonBiz.getReservationList(projectId,classifyId,page,limit,null);
    }


    /**
     * 查询预约服务详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getReservationInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询预约服务详情(公用，不影响以前接口)---APP端", notes = "查询预约服务详情---APP端",httpMethod = "GET")
    @ApiImplicitParam(name="id",value="服务id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse<ReservationInfo> getReservationInfo(String id){
        return bizReservationPersonBiz.getReservationInfo(id);
    }


    /**
     * 查询家政超市商品的规格类型
     * @return
     */
    @RequestMapping(value = "/getSpecTypeList" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询家政超市商品的规格类型---APP端", notes = "查询家政超市商品的规格类型---APP端",httpMethod = "GET")
    @ApiImplicitParam(name="id",value="服务id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse<List<ProductSpecInfo>> getSpecTypeList(String id){
        return  bizReservationPersonBiz.getSpecTypeList(id);
    }


    /**
     * 查询预约服务详情(新接口)
     * @param id
     * @return
     */
    @RequestMapping(value = "/getNewReservationInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询预约服务详情(新接口)---APP端", notes = "查询预约服务详情---APP端",httpMethod = "GET")
    /*@ApiImplicitParams({
            @ApiImplicitParam(name="id",value="服务id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="resCode",value="计费类型编码",dataType="String",paramType = "query",example="4")
    })*/
    @ApiImplicitParam(name="id",value="服务id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse<ReservationInfo> getNewReservationInfo(String id){
        return bizReservationPersonBiz.getNewReservationInfo(id, false);
    }

    /**
     * 查询预约服务详情(新接口) by 分享
     * @param id
     * @return
     */
    @RequestMapping(value = "/getShareNewReservationInfo", method = RequestMethod.GET)
    @ResponseBody
    @IgnoreUserToken
    @ApiOperation(value = "查询预约服务详情(新接口)---APP端", notes = "查询预约服务详情---APP端分享",httpMethod = "GET")
    /*@ApiImplicitParams({
            @ApiImplicitParam(name="id",value="服务id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="resCode",value="计费类型编码",dataType="String",paramType = "query",example="4")
    })*/
    @ApiImplicitParam(name="id",value="服务id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse<ReservationInfo> getShareNewReservationInfo(String id){
        return bizReservationPersonBiz.getNewReservationInfo(id, true);
    }


    /**
     * 预约服务产品下单
     * @return
     */
    @RequestMapping(value = "/buyReservationProduct" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "预约服务产品下单(新接口)---APP端", notes = "预约服务产品下单(新接口)---APP端",httpMethod = "POST")
    public ObjectRestResponse<BuyProductOutVo> buyReservationProduct(@RequestBody @ApiParam BuyProductInfo buyProductInfo) throws Exception{
        ObjectRestResponse result = new ObjectRestResponse();
        if(buyProductInfo==null) {
            result.setStatus(101);
            result.setMessage("参数为空");
            return result;
        }
        if(StringUtils.isAnyoneEmpty(buyProductInfo.getContactName(),buyProductInfo.getContactTel(),buyProductInfo.getAddr(),
                buyProductInfo.getProjectId(),buyProductInfo.getSource(),buyProductInfo.getProcCode(),buyProductInfo.getReservationTime())) {
            result.setStatus(101);
            result.setMessage("参数为空");
            return result;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = sdf.parse(buyProductInfo.getReservationTime());
        //判断是否小于当前时间+24小时
        if(date.getTime()< DateUtils.addHours(new Date(),24).getTime()){
            result.setStatus(101);
            result.setMessage("只能预约24小时以后的时间");
            return result;
        }
        //春节打烊
        Date chunjieStartDate = sdf.parse("2020-01-20 00:00");
        Date chunjieEndDate = sdf.parse("2020-02-05 23:59");
        if(date.getTime() > chunjieStartDate.getTime() && date.getTime() <= chunjieEndDate.getTime()){
            result.setStatus(101);
            result.setMessage("春节休息，服务打烊~\n"+"2月6日开门营业，欢迎预约！");
            return result;
        }

        int total = 0;
        List<CompanyProduct> companyProductList = buyProductInfo.getCompanyProductList();
        if(companyProductList==null || companyProductList.size()==0){
            result.setStatus(101);
            result.setMessage("参数为空");
            return result;
        }else{
            for (CompanyProduct companyProduct : companyProductList){
                List<SubProduct> subProductList = companyProduct.getSubProductList();
                if(subProductList != null && subProductList.size() > 0){
                    for(SubProduct subProduct : subProductList){
                        total = subProduct.getSubNum()+total;
                        ReservationInfo reservationInfos = bizReservationPersonMapper.selectNewReservationInfoById(subProduct.getProductId(),BaseContextHandler.getUserID());
                        if(reservationInfos != null){
                            //判断上午下午库存
                            Date reservationDate = DateUtils.parseDateTime(buyProductInfo.getReservationTime(), "yyyy-MM-dd HH:mm");
                            String reservationDay = DateUtils.formatDateTime(reservationDate, "yyyy-MM-dd");
                            String reservationAmpm = DateUtils.dateToString(reservationDate, "a").toUpperCase();
                            if (reservationAmpm.equals("上午")) {
                                reservationAmpm = "AM";
                            } else if (reservationAmpm.equals("下午")) {
                                reservationAmpm = "PM";
                            }
                            List<Map<String, Object>> list = bizSubProductMapper.getSubProductCountForDay(subProduct.getProductId(), reservationDay);
                            int amNum = 0, pmNum = 0;
                            for (Map tmpMap : list) {
                                String createTime = ((String)tmpMap.get("createTime")).toUpperCase();
                                if (createTime.equals("AM")) {
                                    amNum = Integer.parseInt(((BigDecimal)tmpMap.get("num")).toString());
                                } else if (createTime.equals("PM")) {
                                    pmNum = Integer.parseInt(((BigDecimal)tmpMap.get("num")).toString());
                                }
                            }
                            boolean amCan = (reservationInfos.getProductNumForenoon() < 0 || reservationInfos.getProductNumForenoon() > amNum) ? true : false;
                            boolean pmCan = (reservationInfos.getProductNumAfternoon() < 0 || reservationInfos.getProductNumAfternoon() > pmNum) ? true : false;
                            if ((reservationAmpm.equals("AM") && !amCan) || (reservationAmpm.equals("PM") && !pmCan)) {
                                result.setStatus(104);
                                result.setMessage("商品已售罄");
                                return result;
                            }

                            if(reservationInfos.getLimitNum() != -1){
                                int num = bizReservationPersonMapper.getUserBuyNumById(subProduct.getProductId(),BaseContextHandler.getUserID());
                                if (num > 0){
                                    total = total + num;
                                }
                                if(reservationInfos.getLimitNum() < total){
                                    result.setStatus(104);
                                    result.setMessage("你购买的数量,已达到购买上限!");
                                    return result;
                                }
                            }
                            if(reservationInfos.getProductNum() != -1){
                                if(reservationInfos.getProductNum() < subProduct.getSubNum()){
                                    result.setStatus(104);
                                    result.setMessage("你购买的数量，已超过当前库存量");
                                    return result;
                                }
                                //直接判断库存是否够并减库存
                                int stockResult = bizProductMapper.updateResStockById(subProduct.getProductId(), subProduct.getSubNum());
                                if (stockResult<=0) {
                                    result.setStatus(104);
                                    result.setMessage("商品已售罄");
                                    return result;
                                }
                            }else{
                                int stockResult = bizProductMapper.updateResStockNoLimitById(subProduct.getProductId(), subProduct.getSubNum());
                            }
                        }
                    }
                }
            }
        }
        String busId = BusinessConstant.getReservationBusId();
        return bizSubBiz.buyProduct(buyProductInfo,busId);
    }


    /**
     * 获取我的预约列表
     * @param searchSubOrderIn
     * @return
     */
    @RequestMapping(value = "/getNewReservationList" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取我的预约列表(新接口)---APP端", notes = "获取我的预约列表(新接口)---APP端",httpMethod = "POST")
    public ObjectRestResponse<List<SubReservationListVo>> getNewReservationList(@RequestBody @ApiParam SearchSubOrderIn searchSubOrderIn) {
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        String userId = BaseContextHandler.getUserID();
        if(StringUtils.isEmpty(userId)){
            objectRestResponse.setStatus(101);
            objectRestResponse.setMessage("用户未登陆，请登陆系统");
            return objectRestResponse;
        }

        int page = searchSubOrderIn.getPage();
        int limit = searchSubOrderIn.getLimit();
        if (page<1) {
            page = 1;
        }
        if (limit<1) {
            limit = 10;
        }
        //分页
        int startIndex = (page - 1) * limit;
        List<SubReservationListVo> subList = null;
        Map<String,Object> paramMap = new HashMap<>();
        if("99".equals(searchSubOrderIn.getSubscribeStatus())){
            searchSubOrderIn.setSubscribeStatus(null);
        }
        if("4".equals(searchSubOrderIn.getSubscribeStatus())){
            //待收货
            searchSubOrderIn.setSubscribeStatus("5");
        }
        if("3".equals(searchSubOrderIn.getSubscribeStatus())){
            //待评价
            searchSubOrderIn.setSubscribeStatus("4");
            paramMap.put("commentStatus","1");
        }
        paramMap.put("subscribeStatus",searchSubOrderIn.getSubscribeStatus());
        paramMap.put("userId",userId);
        paramMap.put("page",startIndex);
        paramMap.put("limit",limit);
        subList = bizReservationPersonMapper.getReservationListForApp(paramMap);
        if(subList==null || subList.size()==0){
            subList = new ArrayList<>();
        }
        objectRestResponse.setData(subList);
        return objectRestResponse;
    }



    /**
     * 获取用户预约详情
     * @param id 参数
     * @return
     */
    @RequestMapping(value = "/getNewReservationDetail" ,method = RequestMethod.GET)
    @ApiOperation(value = "获取用户预约详情(新接口)---APP端", notes = "获取用户预约详情(新接口)---APP端",httpMethod = "GET")
    @ApiImplicitParam(name="id",value="工单ID",dataType="String",required = true ,paramType = "query",example="")
    public ObjectRestResponse<SubDetailOutVo> getNewReservationDetail(String id) throws Exception {
        return bizSubBiz.getSubDetail(id, OperateConstants.ClientType.CLIENT_APP.toString());
    }

    /**
     * 获取上下午库存状态
     * @param id 服务id
     * @param day 日期
     * @return
     */
    @RequestMapping(value = "/getReservationStatusForDay" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取服务上下午的库存状态", notes = "获取服务上下午的库存状态---APP端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="服务id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="day",value="日期",dataType="String",paramType = "query",example="2019-07-01")
    })
    public ObjectRestResponse<JSONArray> getReservationStatusForDay(String id, String day) throws Exception {
        return bizSubBiz.getReservationStatusForDay(id, day);
    }

    /**
     * 修改服务订单
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateSubReservation" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "修改服务订单", notes = "修改服务订单---APP端",httpMethod = "POST")
    public ObjectRestResponse<Object> updateSubReservation(@RequestBody @ApiParam JSONObject param) throws Exception {
        return bizSubBiz.updateSubReservation(param.getString("subId"), param.getString("reservationId"), param.getString("expectedServiceTime"));
    }

    /**
     * 修改服务订单
     * @param subId 预约订单id
     * @param reservationId 服务商品id
     * @return
     */
    @RequestMapping(value = "/getSubReservationSpec" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取服务订单商品规格", notes = "获取服务订单商品规格---APP端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="subId",value="预约订单id",dataType="String",example="4"),
            @ApiImplicitParam(name="reservationId",value="服务商品id",dataType="String",example="5")
    })
    public ObjectRestResponse<Object> getSubReservationSpec(String subId, String reservationId) throws Exception {
        return bizSubBiz.getSubReservationSpec(subId, reservationId);
    }

    /**
     * 续单
     * @param param
     * @return
     */
    @RequestMapping(value = "/appendSubReservation" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "续单", notes = "续单---APP端",httpMethod = "POST")
    public ObjectRestResponse<Object> appendSubReservation(@RequestBody @ApiParam AppendSubReservationParam param) throws Exception {
        return bizSubBiz.appendSubReservation(param);
    }


    /**
     * 提交预约服务
     * @param param
     * @return
     */
    @RequestMapping(value = "/saveReservation" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "提交预约服务---APP端", notes = "提交预约服务---APP端",httpMethod = "POST")
    public ObjectRestResponse saveReservation(@RequestBody @ApiParam ReservationParam param){
        return  bizReservationPersonBiz.saveReservation(param);
    }


    /**
     * 查询用户预约列表
     * @param dealStatus
     * @return
     */
    @RequestMapping(value = "/getUserReservationList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询用户预约列表---APP端", notes = "查询用户预约列表---APP端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="dealStatus",value="工单状态(00-生成工单、01-调度程序已受理,02-已接受,03-处理中,04-暂停,05-已完成,06-用户已取消,07-服务人员已取消)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<List<MyReservationVo>> getUserReservationList(String dealStatus, Integer page, Integer limit){
        return bizReservationPersonBiz.getUserReservationList(dealStatus, page, limit);
    }



    /**
     * 查看用户预约详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getMyReservationInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查看用户预约详情---APP端", notes = "查看用户预约详情---APP端",httpMethod = "GET")
    @ApiImplicitParam(name="id",value="工单id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse<WoDetail> getMyReservationInfo(String id){
        return bizReservationPersonBiz.getMyReservationInfo(id);
    }


    /**
     * 查看预约工单详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getReservationWoInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查看预约工单详情---APP端", notes = "查看预约工单详情---APP端",httpMethod = "GET")
    @ApiImplicitParam(name="id",value="工单id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse<ReservatWoDetail> getReservationWoInfo(String id){
        return bizReservationPersonBiz.getReservationWoInfo(id);
    }


}
