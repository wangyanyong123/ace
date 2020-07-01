package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.jinmao.biz.BizProductDeliveryBiz;
import com.github.wxiaoqi.security.jinmao.entity.BizProductDelivery;
import com.github.wxiaoqi.security.jinmao.vo.productdelivery.ProductDeliveryData;
import com.github.wxiaoqi.security.jinmao.vo.productdelivery.in.SaveProductDeliveryIn;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品配送范围
 *
 * @author guohao
 * @Date 2020-04-25 13:50:35
 */
@RestController
@RequestMapping("bizProductDelivery")
@CheckClientToken
@CheckUserToken
public class BizProductDeliveryController extends BaseController<BizProductDeliveryBiz,BizProductDelivery,String> {


    @Autowired
    private BizProductDeliveryBiz bizProductDeliveryBiz;

    @PostMapping("/saveProductDelivery")
    @ApiOperation(value = "编辑商品配送范围", notes = "编辑商品配送范围", httpMethod = "POST")
    public ObjectRestResponse saveProductDelivery(@RequestBody SaveProductDeliveryIn saveProductDeliveryIn) {
        return bizProductDeliveryBiz.editProductDelivery(saveProductDeliveryIn);
    }

    @GetMapping("/findProductDeliveryCityCodeList")
    @ApiOperation(value = "获取商品配送范围城市编码", notes = "获取商品配送范围城市编码", httpMethod = "GET")
    public ObjectRestResponse findProductDeliveryCityCodeList(String productId) {
        List<String> productDeliveryList = bizProductDeliveryBiz.findProductDeliveryCityCodeList(productId);
        return ObjectRestResponse.ok(productDeliveryList);
    }

    @GetMapping("/findProductDeliveryList")
    @ApiOperation(value = "获取商品配送范围", notes = "编辑商品配送范围", httpMethod = "GET")
    public ObjectRestResponse findProductDeliveryList(String productId) {
        List<ProductDeliveryData> productDeliveryList = bizProductDeliveryBiz.findProductDeliveryList(productId);
        return ObjectRestResponse.ok(productDeliveryList);
    }

    @DeleteMapping("/deleteByIds")
    @ApiOperation(value = "删除", notes = "编辑商品配送范围", httpMethod = "DELETE")
    public ObjectRestResponse deleteByIds(List<String> idList) {
         bizProductDeliveryBiz.deleteByIds(idList);
        return ObjectRestResponse.ok();
    }

}