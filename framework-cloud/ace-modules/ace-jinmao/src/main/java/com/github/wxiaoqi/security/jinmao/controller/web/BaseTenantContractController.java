package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.jinmao.biz.BaseTenantContractBiz;
import com.github.wxiaoqi.security.jinmao.vo.Business.OutParam.ResultBusinessVo;
import com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.InputParam.UpdateEnableParam;
import com.github.wxiaoqi.security.jinmao.vo.MerchantManagement.InputParam.ContractSaveParam;
import com.github.wxiaoqi.security.jinmao.vo.MerchantManagement.InputParam.MerchantSaveParam;
import com.github.wxiaoqi.security.jinmao.vo.MerchantManagement.OutParam.ResultMerchantManageInfo;
import com.github.wxiaoqi.security.jinmao.vo.MerchantManagement.OutParam.ResultMerchantManageVo;
import com.github.wxiaoqi.security.jinmao.vo.MerchantManagement.InputParam.CloseInvoice;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("web/baseTenantContract")
@CheckClientToken
@CheckUserToken
@Api(tags = "商户管理")
public class BaseTenantContractController{

    @Autowired
    private BaseTenantContractBiz baseTenantContractBiz;

    /**
     **查询商户管理列表
     * @return
     */
    @RequestMapping(value = "/getMerchantListPc", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询商户管理列表--PC端",notes = "查询商户管理列表--PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="enableStatus",value="状态查询(0:禁用,1:启用,3:全部)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据账号、商户名称、负责人手机号码模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })

    /**
     * 查询商户总数
     */
    public TableResultResponse<ResultMerchantManageVo> getMerchantListPc(String enableStatus, String searchVal,Integer page, Integer limit) {
        List<ResultMerchantManageVo> merchantMangeList = baseTenantContractBiz.getMerchantMangeList(enableStatus, searchVal, page, limit);
        int total = baseTenantContractBiz.selectMerchantManageCount(enableStatus, searchVal);
        return new TableResultResponse<ResultMerchantManageVo>(total, merchantMangeList);
    }

    /**
     * 保存商户管理
     * @param param
     * @return
     */
    @RequestMapping(value = "/saveMerchantPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存商户管理---PC端", notes = "保存商户管理---PC端",httpMethod = "POST")
    public ObjectRestResponse saveMerchantPc(@RequestBody @ApiParam MerchantSaveParam param) {
        return baseTenantContractBiz.saveMerchantManageInfo(param);
    }

    /**
     * 保存协议设置
     * @param param
     * @return
     */
    @RequestMapping(value = "/saveContractPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存协议设置---PC端", notes = "保存协议设置---PC端",httpMethod = "POST")
    public ObjectRestResponse saveContractPc(@RequestBody @ApiParam ContractSaveParam param) {
        return baseTenantContractBiz.saveContractInfo(param);
    }

    /**
     * 修改禁用与启用状态
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateMerEnableStatusPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "修改禁用与启用状态---PC端", notes = "修改禁用与启用状态---PC端",httpMethod = "POST")
    public ObjectRestResponse updateEnableStatusPc(@RequestBody @ApiParam UpdateEnableParam params){
        return baseTenantContractBiz.updateEnableStatus(params);
    }

    /**
     * 查询商户详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getMerchantManageInfoPc/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询商户详情---PC端", notes = "查询商户详情---PC端",httpMethod = "GET")
    public TableResultResponse<ResultMerchantManageInfo> getMerchantInfoPc(@PathVariable String id) {

        if(StringUtils.isEmpty(id) || "123".equals(id) ){
            id= BaseContextHandler.getTenantID();
        }
        List<ResultMerchantManageInfo> info = baseTenantContractBiz.getMerchantManageInfo(id);
        return new TableResultResponse<ResultMerchantManageInfo>(info.size(), info);
    }

    /**
     * 更新是否可打烊，是否可开票
     */
    @RequestMapping(value = "/updateIsInvoice",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "更新打烊---PC端", notes = "更新打烊---PC端",httpMethod = "POST")
    public  ObjectRestResponse updateIsInvoice(@RequestBody @ApiParam CloseInvoice params){
        return baseTenantContractBiz.updateIsInvoice(params);
    }

    /**
     * 更新协议配置
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateContractInfoPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "更新协议设置---PC端", notes = "更新协议设置---PC端",httpMethod = "POST")
    public ObjectRestResponse updateContractInfoPc(@RequestBody @ApiParam ContractSaveParam params) {
        if (params.getId().equals("")) {
            return baseTenantContractBiz.saveContractInfo(params);
        }
        return baseTenantContractBiz.updateContractInfo(params);
    }

    /**
     * 更新用户信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateMerchantInfoPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "更新商户信息---PC端", notes = "更新商户信息---PC端",httpMethod = "POST")
    public ObjectRestResponse updateMerchantInfoPc(@RequestBody @ApiParam MerchantSaveParam params) {
        return this.baseTenantContractBiz.updateMerchantInfo(params);
    }

    /**
     * 查询商户所关联业务
     * @return
     */
    @RequestMapping(value = "/getBusiness",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询商户所关联业务---PC端", notes = "查询商户所关联业务---PC端",httpMethod = "GET")
    public TableResultResponse<ResultBusinessVo> getBusiness(){
        List<ResultBusinessVo> businessVoList = baseTenantContractBiz.getBusiness();
        return new TableResultResponse<ResultBusinessVo>(businessVoList.size(), businessVoList);
    }



}