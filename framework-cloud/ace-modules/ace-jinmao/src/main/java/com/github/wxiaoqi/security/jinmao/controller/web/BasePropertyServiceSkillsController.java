package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.BasePropertyServiceSkillsBiz;
import com.github.wxiaoqi.security.jinmao.vo.Service.InputParam.SaveServiceParam;
import com.github.wxiaoqi.security.jinmao.vo.Service.InputParam.SaveSrviceGroupParam;
import com.github.wxiaoqi.security.jinmao.vo.Service.OutParam.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("web/baseAppServerUser")
@CheckClientToken
@CheckUserToken
@Api(tags="物业人员管理")
public class BasePropertyServiceSkillsController {

    @Autowired
    private BasePropertyServiceSkillsBiz basePropertyServiceSkillsBiz;

    /**
     * 查询技能列表
     * @return
     */
    @RequestMapping(value = "/getSkillListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询技能列表---PC端", notes = "查询技能列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="ids",value="勾选技能id，多个用,隔开",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据编码和名称模糊查询指定技能",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
    })
    public TableResultResponse<ResultSkillVo> getSkillListPc(String searchVal,String ids){
        List<ResultSkillVo> skillList = basePropertyServiceSkillsBiz.getSkillList(searchVal, ids);
        return new TableResultResponse<ResultSkillVo>(skillList.size(), skillList);
    }


    /**
     * 查询物业人员技能服务范围
     * @return
     */
    @RequestMapping(value = "/getServiceAreaListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询物业人员技能服务范围---PC端", notes = "查询物业人员技能服务范围---PC端",httpMethod = "GET")
    public TableResultResponse<ResultServiceAreaInfoVo> getServiceAreaListPc(){
        List<ResultServiceAreaInfoVo> areaList = basePropertyServiceSkillsBiz.getServiceAreaList();
        return new TableResultResponse<ResultServiceAreaInfoVo>(areaList.size(), areaList);
    }


    /**
     * 查询物业分类树
     * @return
     */
    @RequestMapping(value = "/getServiceTreeListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询物业分类树---PC端", notes = "查询物业分类树---PC端",httpMethod = "GET")
    public TableResultResponse<ResultServiceVo> getServiceTreeListPc(){
        List<ResultServiceVo> serviceList = basePropertyServiceSkillsBiz.getServiceTreeList();
        return new TableResultResponse<ResultServiceVo>(serviceList.size(),serviceList);
    }


    /**
     * 添加物业人员分类
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveSrviceGroupPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加物业人员分类---PC端", notes = "添加物业人员分类---PC端",httpMethod = "POST")
    public ObjectRestResponse saveSrviceGroupPc(@RequestBody @ApiParam SaveSrviceGroupParam params){
        return basePropertyServiceSkillsBiz.saveSrviceGroup(params);
    }


    /**
     * 查询物业分类详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getServiceGroupInfoPc/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询物业分类详情---PC端", notes = "查询物业分类详情---PC端",httpMethod = "GET")
    public TableResultResponse<ResultServiceInfoVo> getServiceGroupInfoPc(@PathVariable String id){
        List<ResultServiceInfoVo> serviceInfoList = basePropertyServiceSkillsBiz.getServiceGroupInfo(id);
        return new TableResultResponse<ResultServiceInfoVo>(serviceInfoList.size(),serviceInfoList);
    }


    /**
     * 编辑物业人员分类
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateServiceGroupPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "编辑物业人员分类---PC端", notes = "编辑物业人员分类---PC端",httpMethod = "POST")
    public ObjectRestResponse updateServiceGroupPc(@RequestBody @ApiParam SaveSrviceGroupParam params){
        return basePropertyServiceSkillsBiz.updateServiceGroup(params);
    }


    /**
     * 删除物业分类
     * @param id
     * @return
     */
    @RequestMapping(value = "/delServiceGroupInfoPc/{id}",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "删除物业分类---PC端", notes = "删除物业分类---PC端",httpMethod = "GET")
    public ObjectRestResponse delServiceGroupInfoPc(@PathVariable String id){
        return basePropertyServiceSkillsBiz.delServiceGroupInfo(id);
    }




    /**
     * 查询物业管理人员列表
     * @return
     */
    @RequestMapping(value = "/getServiceListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询物业管理人员列表---PC端", notes = "查询物业管理人员列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="sid",value="物业分类id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="enableStatus",value="状态查询(0:无效,1:有效,3:全部)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据姓名,手机号码模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<ResultServiceListVo> getServiceListPc(String enableStatus, String searchVal, Integer page, Integer limit){
        List<ResultServiceListVo> skillList = basePropertyServiceSkillsBiz.getServiceList(enableStatus, searchVal, page, limit);
        int total = basePropertyServiceSkillsBiz.selectServiceCount(enableStatus, searchVal);
        return new TableResultResponse<ResultServiceListVo>(total, skillList);
    }



    /**
     * 保存物业人员
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveServicePc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存物业人员---PC端", notes = "保存物业人员---PC端",httpMethod = "POST")
    public ObjectRestResponse saveServicePc(@RequestBody @ApiParam SaveServiceParam params){
        return basePropertyServiceSkillsBiz.saveService(params);
    }

    /**
     * 删除物业人员
     * @param id
     * @return
     */
    @RequestMapping(value = "/delServiceInfoPc/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation(value = "删除物业人员---PC端", notes = "删除物业人员---PC端",httpMethod = "DELETE")
    public ObjectRestResponse delServiceInfoPc(@PathVariable String id){
        return basePropertyServiceSkillsBiz.deleteServiceInfo(id);
    }

    /**
     * 查询物业人员详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getServiceInfoPc/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询物业人员详情---PC端", notes = "查询物业人员详情---PC端",httpMethod = "GET")
    public TableResultResponse<ResultServiceInfo> getServiceInfoPc(@PathVariable String id){
        List<ResultServiceInfo> serviceInfo = basePropertyServiceSkillsBiz.getServiceInfo(id);
        return new TableResultResponse<ResultServiceInfo>(serviceInfo.size(),serviceInfo);
    }


    /**
     * 编辑物业人员
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateServicePc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "编辑物业人员---PC端", notes = "编辑物业人员---PC端",httpMethod = "POST")
    public ObjectRestResponse updateServicePc(@RequestBody @ApiParam SaveServiceParam params){
        return basePropertyServiceSkillsBiz.updateService(params);
    }

    /**
     * 根据手机查询是否已经添加过该用户
     * @param id
     * @return
     */
    @RequestMapping(value = "/selectUserByPhone/{id}",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询是否已经添加过该用户---PC端", notes = "查询是否已经添加过该用户---PC端",httpMethod = "GET")
    public ObjectRestResponse selectUserByPhone(@PathVariable String id){
        return basePropertyServiceSkillsBiz.selectUserByPhone(id);
    }



}