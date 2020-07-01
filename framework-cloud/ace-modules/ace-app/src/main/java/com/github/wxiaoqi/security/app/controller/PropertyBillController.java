package com.github.wxiaoqi.security.app.controller;

import com.aliyun.oss.common.utils.DateUtil;
import com.github.wxiaoqi.security.app.biz.BizPropertyBillBiz;
import com.github.wxiaoqi.security.app.fegin.BillFegin;
import com.github.wxiaoqi.security.app.vo.bill.ShouldDate;
import com.github.wxiaoqi.security.app.vo.propertybill.ShouldDateList;
import com.github.wxiaoqi.security.app.vo.propertybill.in.InvoiceParam;
import com.github.wxiaoqi.security.app.vo.propertybill.in.UserBillOrder;
import com.github.wxiaoqi.security.app.vo.propertybill.out.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.constant.BusinessConstant;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateTimeUtil;
import com.github.wxiaoqi.security.common.util.DateUtils;
import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("bill")
@CheckUserToken
@CheckClientToken
@Api(tags="物业缴费")
@Slf4j
public class PropertyBillController {

    @Autowired
    private BizPropertyBillBiz bizPropertyBillBiz;
    @Autowired
    private BillFegin billFegin;

    @GetMapping("getNoPayBillList")
    @ApiOperation(value = "未缴账单查询", notes = "获取用户账单列表", httpMethod = "GET")
    public ObjectRestResponse<UserBillList> getNoPayBillList() {
        ObjectRestResponse result = this.bizPropertyBillBiz.getBillList();
        ObjectRestResponse response = new ObjectRestResponse();
        if (result != null && result.getStatus() == 200) {
            NoPayBill noPayBill = (NoPayBill) result.getData();
            List<UserBillList> billList = noPayBill.getBillList();
            if (billList != null && billList.size() != 0) {
                List<BillListOut> bill = new ArrayList<>();
                List<ShouldBillOut> shouldBillOut= new ArrayList<>();
                for (UserBillList userBillList : billList) {
                    shouldBillOut.addAll(userBillList.getShouldBillOut());
                }
                Map<String,List<ShouldBillOut>> billMap = shouldBillOut.stream().collect(Collectors.groupingBy(ShouldBillOut::getShouldDate));
                List<UserAllBillList> userAllBillList= new ArrayList<>();
                billMap.keySet().forEach(key -> {
                            UserAllBillList userAllBill = new UserAllBillList();
                            userAllBill.setShouldDate(key);
                            userAllBill.setShouldDateList(billMap.get(key));
                            userAllBillList.add(userAllBill);
                        }
                );
                Collections.sort(userAllBillList, Comparator.comparing(UserAllBillList::getShouldDate).reversed());
                for (UserAllBillList allBillList : userAllBillList) {
                    double totalAmount = 0;
                    for (ShouldBillOut shouldBill : allBillList.getShouldDateList()) {
                        totalAmount += Double.parseDouble(shouldBill.getShouldAmount());
                        shouldBill.setYear(shouldBill.getShouldDate().substring(0,4));
                        shouldBill.setMouth(shouldBill.getShouldDate().substring(4));
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
                    bill.add(billListOut);
                });
                Collections.sort(bill,Comparator.comparing(BillListOut::getYear).reversed());
                response.setData(bill);
            }
        }
        return response;
    }

    @PostMapping("userBillOrder")
    @ApiOperation(value = "用户缴费下单", notes = "用户缴费下单", httpMethod = "POST")
    public ObjectRestResponse<UserBillOutVo> userBillOrder(@RequestBody @ApiParam ShouldDate date) {
        ObjectRestResponse response = this.bizPropertyBillBiz.getBillList();
        if(response!=null && response.getStatus()==200){
            NoPayBill noPayBill = (NoPayBill)response.getData();
            String busId = BusinessConstant.getPropertyBusId();
            UserBillOrder billVo = new UserBillOrder();
            List<ShouldDateList> shouldBillOut = new ArrayList<>();
            List<UserBillList> userBillList = noPayBill.getBillList();
            Collections.sort(userBillList,Comparator.comparing(UserBillList::getShouldDate));
            OK:
            for (UserBillList userBill: userBillList) {
                List<ShouldBillOut> shouldBillOutList= userBill.getShouldBillOut();
                for (ShouldBillOut shouldBillOutTemp: shouldBillOutList) {
                    boolean flag = false;
                    try {
                        flag = DateUtils.stringToDate(shouldBillOutTemp.getShouldDate(),"yyyyMM") .after(DateUtils.stringToDate(date.getDate(),"yyyyMM"));
                    }catch (Exception e){

                    }
                    if (StringUtils.isNotEmpty(date.getDate()) &&(flag)) {
                        break OK;
                    }
                    ShouldDateList shouldDate = new ShouldDateList();
                    shouldDate.setItem(shouldBillOutTemp.getItem());
                    shouldDate.setItemStr(shouldBillOutTemp.getItemStr());
                    shouldDate.setYear(shouldBillOutTemp.getYear());
                    shouldDate.setMouth(shouldBillOutTemp.getMouth());
                    shouldDate.setShouldAmount(shouldBillOutTemp.getShouldAmount());
                    shouldDate.setShouldDate(shouldBillOutTemp.getShouldDate());
                    shouldDate.setShouldId(shouldBillOutTemp.getShouldId());
                    shouldBillOut.add(shouldDate);
                }
            }
            billVo.setShouldBillOut(shouldBillOut);
            BigDecimal totalAmount = new BigDecimal(0);
            for (ShouldDateList shouldDateList : shouldBillOut) {
                BigDecimal shouldAmount = new BigDecimal(shouldDateList.getShouldAmount());
                totalAmount = totalAmount.add(shouldAmount);
            }
            billVo.setTotalAmount(String.valueOf(totalAmount.doubleValue()));
            return this.bizPropertyBillBiz.createPropertyBill(billVo,busId);
        }else{
            response.setStatus(101);
            response.setMessage("没有待缴账单");
            return response;
        }

    }

    @GetMapping("getUserBillList")
    @ApiOperation(value = "账单查询", notes = "获取用户账单列表", httpMethod = "GET")
    public ObjectRestResponse<NoPayBill> getUserBillList() {
        return this.bizPropertyBillBiz.getBillList();
    }

//    @GetMapping("getNewUserBillList")
//    @ApiOperation(value = "账单查询(新接口)", notes = "获取用户账单列表(新接口)", httpMethod = "GET")
//    public ObjectRestResponse getNewUserBillList() {
//        return this.bizPropertyBillBiz.getNewUserBillList();
//    }

    @GetMapping("getAllUserBill")
    @ApiOperation(value = "全部账单", notes = "获取全部账单列表", httpMethod = "GET")
    public ObjectRestResponse getAllUserBill() {
        return this.bizPropertyBillBiz.getAllPropertyBill();
    }


    /**
     * 查询未开发票账单列表
     * @return
     */
    @RequestMapping(value = "/getInvoiceBillList" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询未开发票账单列表---APP端", notes = "查询未开发票账单列表---APP端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgs1")
    })
    public ObjectRestResponse<List<InvoiceBillVo>> getInvoiceBillList(String projectId, Integer page, Integer limit){
        return  bizPropertyBillBiz.getInvoiceBillList(projectId, page, limit);
    }

    /**
     * 查询已缴费账单列表
     * @return
     */
    @RequestMapping(value = "/getPayBillList" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询已缴费账单列表---APP端", notes = "查询已缴费账单列表---APP端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<List<InvoiceBillVo>> getPayBillList(String projectId, Integer page, Integer limit){
        return  bizPropertyBillBiz.getPayBillList(projectId, page, limit);
    }


    /**
     * 获取物业账单详情
     * @return
     */
    @RequestMapping(value = "/getBillInfo" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取物业账单详情---APP端", notes = "获取物业账单详情---APP端",httpMethod = "GET")
    @ApiImplicitParam(name="id",value="账单id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse getModuleList(String id) {
        ObjectRestResponse result = new ObjectRestResponse();
        if(StringUtils.isEmpty(id)) {
            return result.data("账单id为空");
        }
        result = billFegin.getBillInfo(id);
        return result;
    }


    /**
     * 开发票
     * @param params
     * @return
     */
    @RequestMapping(value = "/addInvoice",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "开发票---PC端", notes = "开发票---PC端",httpMethod = "POST")
    public ObjectRestResponse addInvoice(@RequestBody @ApiParam InvoiceParam params){
        return bizPropertyBillBiz.addInvoice(params);
    }

}
