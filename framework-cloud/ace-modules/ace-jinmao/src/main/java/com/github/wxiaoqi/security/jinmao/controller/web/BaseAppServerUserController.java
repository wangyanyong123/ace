package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.jinmao.biz.BaseAppServerUserBiz;
import com.github.wxiaoqi.security.jinmao.vo.Customer.InputParam.SaveCusParam;
import com.github.wxiaoqi.security.jinmao.vo.Customer.outParam.ResultCusInfoVo;
import com.github.wxiaoqi.security.jinmao.vo.Customer.outParam.ResultCustomerVo;
import com.github.wxiaoqi.security.jinmao.vo.household.HouseholdVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("web/baseAppCusUser")
@CheckClientToken
@CheckUserToken
@Api(tags="客服人员管理")
public class BaseAppServerUserController{

    @Autowired
    private BaseAppServerUserBiz baseAppServerUserBiz;
    /**
     * 查询客服人员管理列表
     * @return
     */
    @RequestMapping(value = "/getCustomerListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询客服人员管理列表---PC端", notes = "查询客服人员管理列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="enableStatus",value="状态查询(0:无效,1:有效,3:全部)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据姓名,手机号码模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<ResultCustomerVo> getCustomerListPc(String enableStatus, String searchVal, Integer page, Integer limit){
        List<ResultCustomerVo> customerList = baseAppServerUserBiz.getCustomerList(enableStatus, searchVal, page, limit);
        int total = baseAppServerUserBiz.selectCustomerCount(enableStatus, searchVal);
        return new TableResultResponse<ResultCustomerVo>(total, customerList);
    }


    /**
     * 保存客服人员
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveCustomerPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存客服人员---PC端", notes = "保存客服人员---PC端",httpMethod = "POST")
    public ObjectRestResponse saveCustomerPc(@RequestBody @ApiParam SaveCusParam params){
        return baseAppServerUserBiz.saveCustomer(params);
    }


    /**
     * 查询客服人员详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getCusInfoPc/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询客服人员详情---PC端", notes = "查询客服人员详情---PC端",httpMethod = "GET")
    public TableResultResponse<ResultCusInfoVo> getCusInfoPc(@PathVariable String id){
        List<ResultCusInfoVo> info = baseAppServerUserBiz.getCusInfo(id);
        return new TableResultResponse<ResultCusInfoVo>(info.size(),info);
    }


    /**
     * 删除客服人员
     * @param id
     * @return
     */
    @RequestMapping(value = "/delCusInfoPc/{id}",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "删除客服人员---PC端", notes = "删除客服人员---PC端",httpMethod = "GET")
    public ObjectRestResponse delCusInfoPc(@PathVariable String id){
        return baseAppServerUserBiz.delCusInfo(id);
    }


    /**
     * 编辑客服人员
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateCusPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "编辑客服人员---PC端", notes = "编辑客服人员---PC端",httpMethod = "POST")
    public ObjectRestResponse updateCusPc(@RequestBody @ApiParam SaveCusParam params){
        return baseAppServerUserBiz.updateCus(params);
    }

    /**
     * 查询客服人员管理列表
     * @return
     */
    @RequestMapping(value = "/getHouseholdList",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询住户列表---PC端", notes = "查询住户列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="floorId",value="楼层ID",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<HouseholdVo> getHouseholdList(String houseId,String floorId, String searchVal, Integer page, Integer limit){
        TableResultResponse<HouseholdVo> response = new TableResultResponse<>();
        if(StringUtils.isEmpty(floorId)&&StringUtils.isEmpty(houseId)){
            response.setStatus(500);
            response.setMessage("houseId和floorId不能同时为空！");
            return response;
        }
        List<HouseholdVo> customerList = baseAppServerUserBiz.getHouseholdList(houseId ,floorId, searchVal, page, limit);
        int total = baseAppServerUserBiz.selectHouseholdCount(houseId,floorId, searchVal);
        return new TableResultResponse<HouseholdVo>(total, customerList);
    }

    @RequestMapping(value = "/relieve",method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation(value = "解除用户和房屋的关系---PC端", notes = "解除用户和房屋的关系---PC端",httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="id",dataType="String",required = true,paramType = "query",example="4")
    })
    public ObjectRestResponse relieve(String id){
		ObjectRestResponse response = new ObjectRestResponse();
    	if(StringUtils.isEmpty(id)){
    		response.setStatus(501);
    		response.setMessage("id不能为空");
    		return response;
		}
		response = baseAppServerUserBiz.relieve(id);
        return response;
    }
}