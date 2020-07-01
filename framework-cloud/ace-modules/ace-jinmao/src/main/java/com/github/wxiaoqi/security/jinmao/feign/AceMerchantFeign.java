package com.github.wxiaoqi.security.jinmao.feign;

import com.github.wxiaoqi.security.api.vo.logistics.OrderLogisticsVO;
import com.github.wxiaoqi.security.api.vo.waiter.OrderWaiterVO;
import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@FeignClient(value = "ace-merchant", configuration = FeignApplyConfiguration.class)
public interface AceMerchantFeign {

    /**
     * 商品发货
     * @param orderLogisticsVO
     * @return
     */
    @PostMapping("/bizProductOrder/sendProduct")
    @ApiOperation(value = "商品发货", notes = "商品发货",httpMethod = "POST")
    ObjectRestResponse sendProduct(@RequestBody OrderLogisticsVO orderLogisticsVO);

    @GetMapping("/bizExpressCompany/queryExpressCompanyList")
    @ApiOperation(value = "我的商品订单", notes = "我的商品订单",httpMethod = "GET")
    ObjectRestResponse queryExpressList();

    /**
     * 更新物流信息
     */
    @PostMapping("/bizProductOrder/updateOrderLogistics")
    @ApiOperation(value = "更新物流信息", notes = "更新物流信息",httpMethod = "POST")
    ObjectRestResponse saveOrUpdateOrderLogistics(@RequestBody @Valid OrderLogisticsVO orderLogisticsVO);


    /**
     * 分配服务人员
     * @param orderWaiterVO
     * @return
     */
    @PostMapping("/bizReservationOrder/assignWaiter")
    @ApiOperation(value = "分配服务人员", notes = "分配服务人员",httpMethod = "POST")
    ObjectRestResponse assignWaiter(@RequestBody @Valid OrderWaiterVO orderWaiterVO);

    /**
     * 完成
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/bizReservationOrder/finish" ,method = RequestMethod.GET)
    @ApiOperation(value = "取消", notes = "取消",httpMethod = "GET")
    ObjectRestResponse doFinish(@RequestParam("orderId") String orderId);

    /**
     * 更新分配人员
     * @param orderWaiterVO
     * @return
     */
    @PostMapping("/bizReservationOrder/updateWaiter")
    @ApiOperation(value = "更新分配人员", notes = "更新分配人员",httpMethod = "POST")
    ObjectRestResponse saveOrUpdateWaiter(@RequestBody @Valid OrderWaiterVO orderWaiterVO);

}
