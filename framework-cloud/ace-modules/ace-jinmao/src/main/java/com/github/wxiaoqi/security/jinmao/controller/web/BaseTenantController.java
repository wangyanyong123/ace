package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.BaseTenantBiz;
import com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.InputParam.AilSaveParam;
import com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.InputParam.ManageSaveParam;
import com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.InputParam.UpdateEnableParam;
import com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.InputParam.WechatSaveParam;
import com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.OutParam.ResultManageVo;
import com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.OutParam.ResultTenantManageInfo;
import com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.OutParam.UserInfo;
import com.github.wxiaoqi.security.jinmao.vo.ResultProjectVo.ProjectListVo;
import com.github.wxiaoqi.security.jinmao.vo.postage.in.SaveParams;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("web/baseTenant")
@CheckClientToken
@CheckUserToken
@Api(tags="公司管理")
public class BaseTenantController{

    @Autowired
    private BaseTenantBiz baseTenantBiz;

    /**
     * 查询公司管理列表
     * @return
     */
    @RequestMapping(value = "/getManageListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询公司管理列表---PC端", notes = "查询公司管理列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="enableStatus",value="状态查询(0:禁用,1:启用,3:全部)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据账号、公司名称、负责人手机号码模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<ResultManageVo> getManageListPc(String enableStatus,String searchVal,Integer page,Integer limit){
        int total = baseTenantBiz.selectCompanyManageCount(enableStatus, searchVal);
        List<ResultManageVo> manageList =  baseTenantBiz.getManageList(enableStatus, searchVal, page, limit);
        return new TableResultResponse<ResultManageVo>(total, manageList);
    }



    /**
     * 保存公司管理
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveManagePc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存公司管理---PC端", notes = "保存公司管理---PC端",httpMethod = "POST")
    public ObjectRestResponse saveManagePc(@RequestBody @ApiParam ManageSaveParam params){
        return baseTenantBiz.saveManageInfo(params);
    }

    @RequestMapping(value = "/saveCenterManagePc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存中心城市管理---PC端", notes = "保存中心城市管理---PC端",httpMethod = "POST")
    public ObjectRestResponse saveCenterManagePc(@RequestBody @ApiParam ManageSaveParam params){
        return baseTenantBiz.saveCenterManageInfo(params);
    }


    /**
     * 保存支付宝设置
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveAilPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存支付宝设置---PC端", notes = "保存支付宝设置---PC端",httpMethod = "POST")
    public ObjectRestResponse saveAilPc(@RequestBody @ApiParam AilSaveParam params){
        return baseTenantBiz.saveAilInfo(params);
    }


    /**
     * 保存微信设置
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveWechatPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存微信设置---PC端", notes = "保存微信设置---PC端",httpMethod = "POST")
    public ObjectRestResponse saveWechatPc(@RequestBody @ApiParam WechatSaveParam params){
        return baseTenantBiz.saveWechatInfo(params);
    }


    /**
     * 修改禁用与启用状态
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateEnableStatusPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "修改禁用与启用状态---PC端", notes = "修改禁用与启用状态---PC端",httpMethod = "POST")
    public ObjectRestResponse updateEnableStatusPc(@RequestBody @ApiParam UpdateEnableParam params){
        return baseTenantBiz.updateEnableStatus(params);
    }


    /**
     * 查询租户详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getTenantManageInfoPc/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询租户详情---PC端", notes = "查询租户详情---PC端",httpMethod = "GET")
    public TableResultResponse<ResultTenantManageInfo> getTenantManageInfoPc(@PathVariable String id){
        List<ResultTenantManageInfo> info = baseTenantBiz.getTenantManageInfo(id);
        return new TableResultResponse<ResultTenantManageInfo>(info.size(),info);
    }


    /**
     * 更新支付宝设置
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateAilInfoPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "更新支付宝设置---PC端", notes = "更新支付宝设置---PC端",httpMethod = "POST")
    public ObjectRestResponse updateAilInfoPc(@RequestBody @ApiParam AilSaveParam params){
        return baseTenantBiz.updateAilInfo(params);
    }

    /**
     * 更新微信设置
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateWechatInfoPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "更新微信设置---PC端", notes = "更新微信设置---PC端",httpMethod = "POST")
    public ObjectRestResponse updateWechatInfoPc(@RequestBody @ApiParam WechatSaveParam params){
        return baseTenantBiz.updateWechatInfo(params);
    }


    /**
     * 更新租户信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateTenamtInfoPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "更新租户信息---PC端", notes = "更新租户信息---PC端",httpMethod = "POST")
    public ObjectRestResponse updateTenamtInfoPc(@RequestBody @ApiParam ManageSaveParam params){
        return this.baseTenantBiz.updateTenamtInfo(params);
    }    /**
     * 更新租户信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateCenterTenantInfo",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "更新中心城市管理信息---PC端", notes = "更新中心城市管理信息---PC端",httpMethod = "POST")
    public ObjectRestResponse updateCenterTenantInfo(@RequestBody @ApiParam ManageSaveParam params){
        return this.baseTenantBiz.updateCenterTenantInfo(params);
    }


    /**
     * 查询当前用户所属角色
     * @return
     */
    @RequestMapping(value = "/getUserInfo",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询当前用户所属角色---PC端", notes = "查询当前用户所属角色---PC端",httpMethod = "GET")
    public ObjectRestResponse<UserInfo> getUserInfo(){
        return baseTenantBiz.getUserInfo();
    }


    /**
     * 查询商城支付账号信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/getPayMallInfoPc/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询租户详情---PC端", notes = "查询租户详情---PC端",httpMethod = "GET")
    public TableResultResponse<ResultTenantManageInfo> getPayMallInfoPc(@PathVariable String id){
        List<ResultTenantManageInfo> info = baseTenantBiz.getPayMallInfo(id);
        return new TableResultResponse<ResultTenantManageInfo>(info.size(),info);
    }

    @RequestMapping(value = "/savePostageInfo",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存邮费规则---PC端", notes = "更新租户信息---PC端",httpMethod = "POST")
    public ObjectRestResponse savePostageInfo(@RequestBody @ApiParam SaveParams saveParams) {
        return baseTenantBiz.savePostageInfo(saveParams);
    }

    @RequestMapping(value = "/delPostageInfo",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "删除邮费规则---PC端", notes = "更新租户信息---PC端",httpMethod = "GET")
    public ObjectRestResponse savePostageInfo(String id) {
        return baseTenantBiz.delPostageInfo(id);
    }

    @RequestMapping(value = "/getCenterCitys",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取中心城市---PC端", notes = "获取中心城市---PC端",httpMethod = "GET")
    public ObjectRestResponse getCenterCitys() {
        return baseTenantBiz.getCenterCity();
    }

    @RequestMapping(value = "/getUnSelectedProjectListForPC",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取未关联得项目列表---PC端", notes = "获取未关联得项目列表---PC端",httpMethod = "GET")
    public ObjectRestResponse getUnSelectedProjectListForPC(String tenantType) {
        List<ProjectListVo> unSelectedProjectList = baseTenantBiz.getUnSelectedProjectList(tenantType);
        return ObjectRestResponse.ok(unSelectedProjectList);
    }
}
