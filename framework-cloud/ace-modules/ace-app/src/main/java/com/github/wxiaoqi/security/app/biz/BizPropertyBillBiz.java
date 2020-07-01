package com.github.wxiaoqi.security.app.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.config.CrmConfig;
import com.github.wxiaoqi.security.app.entity.*;
import com.github.wxiaoqi.security.app.mapper.*;
import com.github.wxiaoqi.security.app.vo.house.HouseAllInfoVo;
import com.github.wxiaoqi.security.app.vo.in.SubInVo;
import com.github.wxiaoqi.security.app.vo.propertybill.ShouldDateList;
import com.github.wxiaoqi.security.app.vo.propertybill.in.InvoiceParam;
import com.github.wxiaoqi.security.app.vo.propertybill.in.UserBillOrder;
import com.github.wxiaoqi.security.app.vo.propertybill.out.*;
import com.github.wxiaoqi.security.app.vo.userhouse.out.HouseInfoVo;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateTimeUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class BizPropertyBillBiz {

    @Autowired
    private BizSubBiz bizSubBiz;
    @Autowired
    private BizSubPropertyFeeMapper bizSubPropertyFeeMapper;
    @Autowired
    private BizAccountBookMapper bizAccountBookMapper;
    @Autowired
    private BizCrmHouseMapper bizCrmHouseMapper;
    @Autowired
    private BizUserHouseBiz bizUserHouseBiz;
    @Autowired
    private BizSubscribeMapper bizSubscribeMapper;
    @Autowired
    private BaseAppClientUserBiz baseAppClientUserBiz;
    @Autowired
    private BizSubscribeWoMapper bizSubscribeWoMapper;
    @Autowired
    private BizPropertyPayLogMapper bizPropertyPayLogMapper;
    @Autowired
    private CrmConfig crmConfig;
    @Autowired
    private CrmServiceBiz crmServiceBiz;
    @Autowired
    private ZhongTaiServiceBiz zhongTaiServiceBiz;

//    public ObjectRestResponse getBillHistoryList(String StartDate,String EndDate) {
//        ObjectRestResponse response = new ObjectRestResponse();
//        ObjectRestResponse<HouseInfoVo> currentHouse = bizUserHouseBiz.getCurrentHouse();
//        if (currentHouse.getStatus() == 200) {
//            HouseInfoVo house = currentHouse.getData();
//            HouseAllInfoVo houseInfo = bizCrmHouseMapper.getHouseAllInfoVoByHouseId(house.getHouseId());
//            ObjectRestResponse billHistory = crmServiceBiz.getBillHistory(houseInfo.getCrmHouseCode(), StartDate, EndDate);
//            if (billHistory.getStatus() == 200) {
//                List<HistoryBill> data = (List<HistoryBill>) billHistory.getData();
//                if (data.size() != 0) {
//                    //组装数据返回前端
//                }
//            }
//        }
//
//        return response;
//    }

    /**
     * 获取用户账单列表
     * @return
     */
    public ObjectRestResponse<NoPayBill> getBillList() {
        ObjectRestResponse response = new ObjectRestResponse();
        if (bizUserHouseBiz.getCurrentHouse().getStatus() == 200) {
            HouseInfoVo currentHouse = bizUserHouseBiz.getCurrentHouse().getData();
            BaseAppClientUser user = baseAppClientUserBiz.getUserById(BaseContextHandler.getUserID());
            HouseAllInfoVo houseInfo = bizCrmHouseMapper.getHouseAllInfoVoByHouseId(currentHouse.getHouseId());
            if (user.getMemberId() == null) {
                response.setMessage("未查询到账单");
                response.setStatus(511);
                return response;
            }
            //查询当前用户已缴费账单是否存在支付成功但同步CRM系统失败的订单
            NoPayBill propertyBill = (NoPayBill) getPropertyBill(houseInfo.getProjectId(),houseInfo.getHouseId()).getData();
            if(propertyBill!=null && propertyBill.getBillList()!=null && propertyBill.getBillList().size()>0){
                UserBillList userBillTemp = propertyBill.getBillList().get(0);
                List<ShouldBillOut> shouldBillOutList = userBillTemp.getShouldBillOut();
                if(shouldBillOutList!=null && shouldBillOutList.size()>0){
                    ShouldBillOut shouldBillOutTemp = shouldBillOutList.get(0);
                    //TODO
                    //NoticeStatus 是否同步到CRM系统(0-未同步,1-已同步,2-同步失败)
                    if(StringUtils.isNotEmpty(shouldBillOutTemp.getNoticeStatus()) && !"1".equals(shouldBillOutTemp.getNoticeStatus())){
                        response.setData(propertyBill);
                        return response;
                    }
                }
            }
            List<ShouldBillOut> data = new ArrayList<>();
            NoPayBill noPayBill = new NoPayBill();
            //获取账单
            ObjectRestResponse billFromCRM = crmServiceBiz.getNoBillFromCrm(user, houseInfo,"100000000");
            ObjectRestResponse carBillFromCrm = crmServiceBiz.getNoBillFromCrm(user, houseInfo,"100000003");
            if (billFromCRM.getStatus() == 200) {
                data = (List<ShouldBillOut>) billFromCRM.getData();
                List<ShouldBillOut> carData = new ArrayList<>();
                if (carBillFromCrm.getStatus() == 200) {
                    carData = (List<ShouldBillOut>) carBillFromCrm.getData();
                    data.addAll(carData);
                }
                List<BillListOut> billList = new ArrayList<>();
                if (data == null || data.size() == 0) {
                    //查询CRM无未缴纳账单，查询数据库账单
                    response.setData(propertyBill);
                    return response;
                }
                if (data.size() != 0){

                    //组装返回数据列表
                    List<UserBillList> bill = new ArrayList<>();
                    Map<String,List<ShouldBillOut>> billMap = data.stream().collect(Collectors.groupingBy(ShouldBillOut::getShouldDate));
                    billMap.keySet().forEach(key -> {
                                UserBillList userBillList = new UserBillList();
                                userBillList.setShouldDate(key);
                                userBillList.setShouldBillOut(billMap.get(key));
                                bill.add(userBillList);
                            }
                    );
                    Collections.sort(bill,Comparator.comparing(UserBillList::getShouldDate).reversed());
                    for (UserBillList userBillList : bill) {
                        BigDecimal totalAmount = new BigDecimal(0);
                        for (ShouldBillOut shouldBillOut : userBillList.getShouldBillOut()) {
                            totalAmount = totalAmount.add(new BigDecimal(shouldBillOut.getShouldAmount()));
                        }
                        userBillList.setTotalAmount(String.valueOf(totalAmount));
                    }
                    BigDecimal total = new BigDecimal(0);
                    for (UserBillList userBillList : bill) {
                        total = total.add(new BigDecimal(userBillList.getTotalAmount()));
                    }
                    noPayBill.setPayStatus("2");
                    noPayBill.setTotal(String.format("%.2f", total));
                    noPayBill.setBillList(bill);

                    response.setData(noPayBill);
                }else {
                    response.setStatus(101);
                    response.setMessage("未查询到账单");
                    return response;
                }

            }else {
                //当前用户这个房间最近缴费详情
                List<ShouldBillOut> userPayBill = bizSubPropertyFeeMapper.getPropertyBill(BaseContextHandler.getUserID(),houseInfo.getProjectId(),houseInfo.getHouseId());
                if (userPayBill != null && userPayBill.size() != 0) {
                    NoPayBill payBill = new NoPayBill();
                    List<UserBillList> bill = new ArrayList<>();
                    Map<String,List<ShouldBillOut>> billMap = userPayBill.stream().collect(Collectors.groupingBy(ShouldBillOut::getShouldDate));
                    billMap.keySet().forEach(key -> {
                                UserBillList userBillList = new UserBillList();
                                userBillList.setShouldDate(key);
                                userBillList.setShouldBillOut(billMap.get(key));
                                bill.add(userBillList);
                            }
                    );
                    Collections.sort(bill,Comparator.comparing(UserBillList::getShouldDate));
                    for (UserBillList userBillList : bill) {
                        double totalAmount = 0;
                        for (ShouldBillOut shouldBillOut : userBillList.getShouldBillOut()) {
                            totalAmount += Double.parseDouble(shouldBillOut.getShouldAmount());
                        }
                        userBillList.setTotalAmount(String.valueOf(totalAmount));
                    }
                    double total = 0;
                    for (UserBillList userBillList : bill) {
                        total += Double.parseDouble(userBillList.getTotalAmount());
                    }
                    payBill.setPayStatus("1");
                    payBill.setTotal(String.format("%.2f", total));
                    payBill.setBillList(bill);
                    response.setData(payBill);
                }else {
                    //若无，返回字段“未查询到账单”
                    response.setStatus(101);
                    response.setMessage("未查询到账单");
                }

                return response;
            }
        }else {
            response.setMessage(bizUserHouseBiz.getCurrentHouse().getMessage());
            response.setStatus(501);
            return response;
        }
        return response;

    }

    /**
     * 从数据库获取已缴信息
     * @param projectId
     * @return
     */
    public ObjectRestResponse getPropertyBill(String projectId,String houseId) {
        ObjectRestResponse response = new ObjectRestResponse();
        List<BillListOut> billList = new ArrayList<>();
        NoPayBill noPayBill = new NoPayBill();
        List<ShouldBillOut> propertyBill = bizSubPropertyFeeMapper.getPropertyBill(BaseContextHandler.getUserID(),projectId,houseId);
        if (propertyBill != null && propertyBill.size() != 0) {
            Map<String,List<ShouldBillOut>> billMap = propertyBill.stream().collect(Collectors.groupingBy(ShouldBillOut::getShouldDate));
            List<UserBillList> billLists= new ArrayList<>();
            billMap.keySet().forEach(key -> {
                        UserBillList userBillList = new UserBillList();
                        userBillList.setShouldDate(key);
                        userBillList.setShouldBillOut(billMap.get(key));
                        billLists.add(userBillList);
                    }
            );
            Collections.sort(billLists,Comparator.comparing(UserBillList::getShouldDate).reversed());
            for (UserBillList userBillList : billLists) {
                double totalAmount = 0;
                for (ShouldBillOut shouldBillOut : userBillList.getShouldBillOut()) {
                    totalAmount += Double.parseDouble(shouldBillOut.getShouldAmount());
                }
                userBillList.setTotalAmount(String.valueOf(totalAmount));
            }
            double total = 0;
            for (UserBillList userBillList : billLists) {
                total += Double.parseDouble(userBillList.getTotalAmount());
            }

            noPayBill.setBillList(billLists);
            noPayBill.setPayStatus("1");
            noPayBill.setTotal(String.format("%.2f",total));

        }
        response.setData(noPayBill);
        return response;
    }



    /**
     * 创建物业缴费订单
     * @param billOrder
     * @param busId
     * @return
     */
    public ObjectRestResponse createPropertyBill(UserBillOrder billOrder, String busId) {
        ObjectRestResponse response = new ObjectRestResponse();

        List<ShouldDateList> shouldDateList = billOrder.getShouldBillOut();
        //获取当前房屋和项目ID
        HouseInfoVo currentHouse = bizUserHouseBiz.getCurrentHouse().getData();
        HouseAllInfoVo houseInfo = bizCrmHouseMapper.getHouseAllInfoVoByHouseId(currentHouse.getHouseId());
        BaseAppClientUser user = baseAppClientUserBiz.getUserById(BaseContextHandler.getUserID());
        //下单前判断是否已支付
        for (ShouldDateList dateList : shouldDateList) {
            List<PropertySubVo> propertySub = bizSubPropertyFeeMapper.getPropertySub(dateList.getShouldId());
            //订单状态(0-已下单、1-处理中、2-待支付、3-已取消、4-已完成、5-待确认、6-退款中、7-退款完成）
            if (propertySub!=null ) {
                for (PropertySubVo propertySubVo : propertySub) {
                    if (propertySubVo.getSubStatus().equals("2")) {
                        BizSubscribeWo bizSubscribeWo = bizSubscribeWoMapper.selectByPrimaryKey(propertySubVo.getSubId());
                        bizSubscribeWo.setSubscribeStatus("3");
                        bizSubscribeWoMapper.updateByPrimaryKey(bizSubscribeWo);
                        log.info("取消未支付订单成功,订单id为:{}",propertySubVo.getSubId());
                    }
                }
            }
        }
        List<ShouldDateList> shouldBillOut = billOrder.getShouldBillOut();
        String shouldDate = "";
        for (ShouldDateList dateList : billOrder.getShouldBillOut()) {
            shouldDate += dateList.getShouldDate()+"/";
        }
        shouldDate = shouldDate.substring(0, shouldDate.length() - 1);
        //创建物业缴费订单
        SubInVo subInVo = new SubInVo();
        subInVo.setProjectId(houseInfo.getProjectId());
        subInVo.setActualCost(new BigDecimal(billOrder.getTotalAmount()));
        subInVo.setBusId(busId);
        subInVo.setUserId(BaseContextHandler.getUserID());
        subInVo.setContactName(user.getNickname());
        subInVo.setContactTel(user.getMobilePhone());
        subInVo.setDescription(shouldDate);
        subInVo.setCompanyId(houseInfo.getProjectId());
        BizSubscribe subscribe = bizSubBiz.createSubscribe(subInVo).getData();
        subscribe.setRoomId(houseInfo.getHouseId());
        subscribe.setCrmProjectCode(houseInfo.getCrmProjectCode());
        subscribe.setCrmRoomCode(houseInfo.getCrmHouseCode());
        subscribe.setCrmUserId(user.getCrmUserId());
        subscribe.setNoticeStatus("0");
        bizSubscribeMapper.updateByPrimaryKey(subscribe);
        if (subscribe != null) {
            for (ShouldDateList billList : shouldDateList) {
                //维护物业缴费明细
                String id = UUIDUtils.generateUuid();
                BizSubPropertyFee bizSubPropertyFee = new BizSubPropertyFee();
                bizSubPropertyFee.setId(id);
                bizSubPropertyFee.setItem(billList.getItem());
                bizSubPropertyFee.setSubId(subscribe.getId());
                bizSubPropertyFee.setShouldId(billList.getShouldId());
                bizSubPropertyFee.setShouldDate(billList.getShouldDate());
                bizSubPropertyFee.setShouldAmount(new BigDecimal(billList.getShouldAmount()));
                bizSubPropertyFee.setTimeStamp(DateTimeUtil.getLocalTime());
                bizSubPropertyFee.setCreateTime(DateTimeUtil.getLocalTime());
                bizSubPropertyFee.setCreateBy(BaseContextHandler.getUserID());
                bizSubPropertyFeeMapper.insertSelective(bizSubPropertyFee);
            }

            //维护订单账单表
            BizAccountBook bizAccountBook = new BizAccountBook();
            bizAccountBook.setId(UUIDUtils.generateUuid());
            bizAccountBook.setPayStatus("1");
            bizAccountBook.setSubId(subscribe.getId());
            String actualId = UUIDUtils.generateUuid();
            bizAccountBook.setActualId(actualId);
            bizAccountBook.setPayId(BaseContextHandler.getUserID());
            bizAccountBook.setActualCost(new BigDecimal(billOrder.getTotalAmount()));
            bizAccountBook.setTimeStamp(DateTimeUtil.getLocalTime());
            bizAccountBook.setCreateBy(BaseContextHandler.getUserID());
            bizAccountBook.setCreateTime(DateTimeUtil.getLocalTime());
            bizAccountBookMapper.insertSelective(bizAccountBook);

            //返回结果参数
            UserBillOutVo billOutVo = new UserBillOutVo();
            billOutVo.setActualId(actualId);
            billOutVo.setSubId(subscribe.getId());
            billOutVo.setActualPrice(billOrder.getTotalAmount());
            billOutVo.setTitle(subscribe.getTitle());
            response.setData(billOutVo);
            log.info("支付信息：{}",billOutVo.toString());
        }

        return response;
    }


    public ObjectRestResponse getAllPropertyBill() {
        ObjectRestResponse response = new ObjectRestResponse();
        List<BillListOut> billList = new ArrayList<>();
//        HouseInfoVo currentHouse = bizUserHouseBiz.getCurrentHouse().getData();
        ObjectRestResponse<HouseInfoVo> currentHouse = bizUserHouseBiz.getCurrentHouse();
        if (currentHouse.getStatus() != 200) {
            response.setData(billList);
            return response;
        }
        HouseAllInfoVo houseInfo = bizCrmHouseMapper.getHouseAllInfoVoByHouseId(currentHouse.getData().getHouseId());
        List<ShouldBillOut> propertyBill = bizSubPropertyFeeMapper.getPropertyBill(BaseContextHandler.getUserID(),houseInfo.getProjectId(),houseInfo.getHouseId());
        if (propertyBill != null ) {
            Map<String,List<ShouldBillOut>> billMap = propertyBill.stream().collect(Collectors.groupingBy(ShouldBillOut::getShouldDate));
            List<UserAllBillList> userAllBillList= new ArrayList<>();
            billMap.keySet().forEach(key -> {
                        UserAllBillList userAllBill = new UserAllBillList();
                        userAllBill.setShouldDate(key);
                        userAllBill.setShouldDateList(billMap.get(key));
                        userAllBillList.add(userAllBill);
                    }
            );
            Collections.sort(userAllBillList,Comparator.comparing(UserAllBillList::getShouldDate).reversed());
            for (UserAllBillList allBillList : userAllBillList) {
                double totalAmount = 0;
                for (ShouldBillOut shouldBillOut : allBillList.getShouldDateList()) {
                    totalAmount += Double.parseDouble(shouldBillOut.getShouldAmount());
                    shouldBillOut.setYear(shouldBillOut.getShouldDate().substring(0,4));
                    shouldBillOut.setMouth(shouldBillOut.getShouldDate().substring(4));
                }
                allBillList.setYear(allBillList.getShouldDate().substring(0,4));
                allBillList.setTotalAmount(String.valueOf(totalAmount));
                allBillList.setMouth(allBillList.getShouldDate().substring(4));
            }

            //
            Map<String, List<UserAllBillList>> map = userAllBillList.stream().collect(Collectors.groupingBy(UserAllBillList::getYear));
            map.keySet().forEach(key -> {
                BillListOut billListOut = new BillListOut();
                billListOut.setYear(key);
                billListOut.setBillList(map.get(key));
                billList.add(billListOut);
            });
            Collections.sort(billList,Comparator.comparing(BillListOut::getYear).reversed());
        }
        response.setData(billList);


        return response;
    }


    /**
     * 查询未开发票账单列表
     * @param projectId
     * @param page
     * @param limit
     * @return
     */
    public ObjectRestResponse<List<InvoiceBillVo>> getInvoiceBillList(String projectId, Integer page, Integer limit){
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
        String userId = BaseContextHandler.getUserID();
        List<InvoiceBillVo> invoiceBillVoList = bizSubPropertyFeeMapper.selectInvoiceBillList(userId,projectId, startIndex, limit);
        if(invoiceBillVoList == null || invoiceBillVoList.size() == 0){
            invoiceBillVoList = new ArrayList<>();
        }
        return ObjectRestResponse.ok(invoiceBillVoList);
    }

    /**
     * 查询已缴费账单列表
     * @param projectId
     * @param page
     * @param limit
     * @return
     */
    public ObjectRestResponse<List<InvoiceBillVo>> getPayBillList(String projectId, Integer page, Integer limit){
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
        String userId = BaseContextHandler.getUserID();
        List<InvoiceBillVo> invoiceBillVoList = bizSubPropertyFeeMapper.selectPayBillList(userId,projectId, startIndex, limit);
        if(invoiceBillVoList == null || invoiceBillVoList.size() == 0){
            invoiceBillVoList = new ArrayList<>();
        }
        return ObjectRestResponse.ok(invoiceBillVoList);
    }

    /**
     * 开发票
     * @param param
     * @return
     */
    public ObjectRestResponse addInvoice(InvoiceParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if("2".equals(param.getInvoiceName())){
            //公司
            if(StringUtils.isEmpty(param.getInvoiceName())){
                 msg.setStatus(201);
                 msg.setMessage("发票抬头不能为空");
                 return msg;
            }
        }
        if(StringUtils.isEmpty(param.getIds())){
            msg.setStatus(201);
            msg.setMessage("请前去选择可开发票账单");
            return msg;
        }
        List<String> idList = Arrays.asList(param.getIds().split(","));
        for (String id : idList){
            BizSubscribe subscribe = new BizSubscribe();
            subscribe.setId(id);
            subscribe.setInvoiceType(param.getInvoiceType());
            subscribe.setInvoiceName(param.getInvoiceName());
            subscribe.setBankName(param.getBankName());
            subscribe.setBankNum(param.getBankNum());
            subscribe.setDutyNum(param.getDutyNum());
            subscribe.setUnitAddr(param.getUnitAddr());
            subscribe.setTelphone(param.getTelphone());
            subscribe.setTimeStamp(new Date());
            subscribe.setModifyBy(BaseContextHandler.getUserID());
            subscribe.setModifyTime(new Date());
            if(bizSubscribeMapper.updateByPrimaryKeySelective(subscribe) <0 ){
                msg.setStatus(501);
                msg.setMessage("开票失败");
                return msg;
            }
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }
}
