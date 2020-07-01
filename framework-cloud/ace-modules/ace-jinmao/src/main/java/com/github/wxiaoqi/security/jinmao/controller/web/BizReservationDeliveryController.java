package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.jinmao.biz.BizReservationDeliveryBiz;
import com.github.wxiaoqi.security.jinmao.vo.productdelivery.ProductDeliveryData;
import com.github.wxiaoqi.security.jinmao.vo.productdelivery.in.SaveProductDeliveryIn;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

import java.util.List;

/**
 * 预约服务配送范围
 *
 * @author guohao
 * @Date 2020-06-11 12:19:38
 */
@RestController
@RequestMapping("bizReservationDelivery")
@CheckClientToken
@CheckUserToken
public class BizReservationDeliveryController {
    @Autowired
    private BizReservationDeliveryBiz bizReservationDeliveryBiz;

    @PostMapping("/saveReservationDelivery")
    @ApiOperation(value = "编辑预约服务配送范围", notes = "编辑预约服务配送范围", httpMethod = "POST")
    public ObjectRestResponse saveReservationDelivery(@RequestBody SaveProductDeliveryIn saveProductDeliveryIn) {
        return bizReservationDeliveryBiz.editDelivery(saveProductDeliveryIn);
    }

    @GetMapping("/findReservationDeliveryCityCodeList")
    @ApiOperation(value = "获取商品配送范围城市编码", notes = "获取商品配送范围城市编码", httpMethod = "GET")
    public ObjectRestResponse findReservationDeliveryCityCodeList(String productId) {
        List<String> cityCodeList = bizReservationDeliveryBiz.findDeliveryCityCodeList(productId);
        return ObjectRestResponse.ok(cityCodeList);
    }

    @GetMapping("/findReservationDeliveryList")
    @ApiOperation(value = "获取预约配送范围", notes = "获取预约配送范围", httpMethod = "GET")
    public ObjectRestResponse findReservationDeliveryList(String productId) {
        List<ProductDeliveryData> deliveryList = bizReservationDeliveryBiz.findDeliveryList(productId);
        return ObjectRestResponse.ok(deliveryList);
    }

    @DeleteMapping("/deleteByIds")
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE")
    public ObjectRestResponse deleteByIds(List<String> idList) {
        bizReservationDeliveryBiz.deleteByIds(idList);
        return ObjectRestResponse.ok();
    }
}