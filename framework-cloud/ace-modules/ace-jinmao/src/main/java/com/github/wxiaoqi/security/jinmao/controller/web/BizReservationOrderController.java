package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.waiter.OrderWaiterVO;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.jinmao.biz.BizReservationOrderBiz;
import com.github.wxiaoqi.security.jinmao.entity.BizReservationOrder;
import com.github.wxiaoqi.security.jinmao.feign.AceAppFeign;
import com.github.wxiaoqi.security.jinmao.feign.AceMerchantFeign;
import com.github.wxiaoqi.security.jinmao.mapper.BaseTenantMapper;
import com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.OutParam.UserInfo;
import com.github.wxiaoqi.security.jinmao.vo.reservation.order.in.ReservationOrderQuery;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 预约服务订单表
 *
 * @author wangyanyong
 * @Date 2020-04-24 13:13:42
 */
@RestController
@RequestMapping("bizReservationOrder")
@CheckClientToken
@CheckUserToken
public class BizReservationOrderController extends BaseController<BizReservationOrderBiz,BizReservationOrder,String> {

    @Resource
    private AceAppFeign aceAppFeign;

    @Resource
    private AceMerchantFeign aceMerchantFeign;

    @Resource
    private BaseTenantMapper baseTenantMapper;

    /**
     * 服务订单查询
     * @param reservationOrderQuery
     * @return
     */
    @GetMapping("queryReservationOrderPage")
    @ApiOperation(value = "服务订单查询", notes = "服务订单查询",httpMethod = "GET")
    public TableResultResponse queryOrderListPage(@ApiParam ReservationOrderQuery reservationOrderQuery){
        return this.baseBiz.queryReservationOrderPage(reservationOrderQuery);
    }


    /**
     * 服务订单详情
     * @param orderId
     * @return
     */
    @GetMapping("queryReservationOrderInfo")
    @ApiOperation(value = "服务订单详情", notes = "服务订单详情",httpMethod = "GET")
    public ObjectRestResponse queryReservationOrderInfo(String orderId){
        return this.baseBiz.queryReservationOrderInfo(orderId);
    }


    @RequestMapping(value = "/exportReservationOrderExcel" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "Web后台导出预约服务订单Excel", notes = "Web后台导出预约服务订单Excel",httpMethod = "POST")
    public ObjectRestResponse exportReservationOrderExcel(@RequestBody @ApiParam ReservationOrderQuery reservationOrderQuery) throws Exception {
        return this.baseBiz.exportReservationOrderExcel(reservationOrderQuery);
    }


    /**
     * 取消
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/concel" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "取消", notes = "取消",httpMethod = "GET")
    public ObjectRestResponse cancel(String orderId){
        return aceAppFeign.cancel(orderId);
    }

    /**
     * 分配服务人员
     * @param orderWaiterVO
     * @return
     */
    @PostMapping("/assignWaiter")
    @ApiOperation(value = "分配服务人员", notes = "分配服务人员",httpMethod = "POST")
    public ObjectRestResponse assignWaiter(@RequestBody @Valid OrderWaiterVO orderWaiterVO){
        return aceMerchantFeign.assignWaiter(orderWaiterVO);
    }

    /**
     * 完成
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/finish" ,method = RequestMethod.GET)
    @ApiOperation(value = "取消", notes = "取消",httpMethod = "GET")
    public ObjectRestResponse doFinish(String orderId){
        return aceMerchantFeign.doFinish(orderId);
    }

    /**
     * 更新分配人员
     * @param orderWaiterVO
     * @return
     */
    @PostMapping("updateWaiter")
    @ApiOperation(value = "更新分配人员", notes = "更新分配人员",httpMethod = "POST")
    public ObjectRestResponse saveOrUpdateWaiter(@RequestBody @Valid OrderWaiterVO orderWaiterVO){
        return aceMerchantFeign.saveOrUpdateWaiter(orderWaiterVO);
    }


    /**
     * 完成后申请退款
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/returnAudit" ,method = RequestMethod.GET)
    @ApiOperation(value = "完成后申请退款", notes = "完成后申请退款",httpMethod = "GET")
    public ObjectRestResponse returnAudit(String orderId){
        UserInfo userInfo = baseTenantMapper.selectRoleTypeByUser(BaseContextHandler.getUserID());
        if(userInfo!=null){
            if("3".equals(userInfo.getTenantType())) {
                return aceAppFeign.reservationOrderReturnAudit(orderId);
            }
        }
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        objectRestResponse.setStatus(101);
        objectRestResponse.setMessage("非法用户");
        return objectRestResponse;
    }

}