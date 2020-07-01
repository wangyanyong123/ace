package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.BizRegionBiz;
import com.github.wxiaoqi.security.jinmao.vo.nodelivery.in.SaveNoDelivery;
import com.github.wxiaoqi.security.jinmao.vo.nodelivery.out.ResultNoDelivery;
import com.github.wxiaoqi.security.jinmao.vo.nodelivery.out.ResultRegionList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 省市区区域表
 *
 * @author zxl
 * @Date 2018-12-18 15:19:48
 */
@RestController
@RequestMapping("web/bizRegion")
@CheckClientToken
@CheckUserToken
@Api(tags = "设置不配送区域")
public class BizRegionController {

    @Autowired
    private BizRegionBiz bizRegionBiz;
    /**
     * 获取所有区域
     * @return
     */
    @RequestMapping(value = "/getAllRegionListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询区域列表---PC端", notes = "查询区域列表---PC端",httpMethod = "GET")
    public TableResultResponse<ResultRegionList> getAllRegionListPc() {
        List<ResultRegionList> resultRegionList =  bizRegionBiz.getAllRegionList();
        return new TableResultResponse<ResultRegionList>(resultRegionList.size(), resultRegionList);
    }

    /**
     * 获取不配送区域列表
     * @return
     */
    @RequestMapping(value = "/getNoDeliveryListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询不区域列表---PC端", notes = "查询不区域列表---PC端",httpMethod = "GET")
    public TableResultResponse<ResultNoDelivery> getNoDeliveryListPc() {
        List<ResultNoDelivery> noDeliveryList = bizRegionBiz.getNoDeliveryList();
        return new TableResultResponse<ResultNoDelivery>(noDeliveryList.size(), noDeliveryList);
    }

    /**
     * 保存不配送区域
     * @param param
     * @return
     */
    @RequestMapping(value = "/saveNoDeliveryPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存不配送区域---PC端", notes = "保存不配送区域---PC端",httpMethod = "POST")
    public ObjectRestResponse saveNoDeliveryPc(@RequestBody @ApiParam SaveNoDelivery param) {
        return bizRegionBiz.saveNoDeliveryRegion(param);
    }

    /**
     * 删除不配送区域
     * @param regionCode
     * @return
     */
    @RequestMapping(value = "/deleteNoDeliveryPc/{id}",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "删除不区域信息---PC端", notes = "删除不区域信息---PC端",httpMethod = "GET")
    public ObjectRestResponse deleteNoDeliveryPc(@PathVariable String id) {
        return bizRegionBiz.deleteNoDeliveryById(id);
    }
}