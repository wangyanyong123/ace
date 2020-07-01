package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.BaseAppServerUserBiz;
import com.github.wxiaoqi.security.jinmao.vo.busService.in.SaveBusServiceParam;
import com.github.wxiaoqi.security.jinmao.vo.busService.out.BusServiceInfo;
import com.github.wxiaoqi.security.jinmao.vo.busService.out.BusServiceVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("web/baseBusService")
@CheckClientToken
@CheckUserToken
@Api(tags="商业服务人员管理")
public class BaseBusServiceController {

    @Autowired
    private BaseAppServerUserBiz baseAppServerUserBiz;


    /**
     * 查询商业服务人员列表
     * @return
     */
    @RequestMapping(value = "/getBusServiceListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询商业服务人员列表---PC端", notes = "查询商业服务人员列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="enableStatus",value="状态查询(0:无效,1:有效,3:全部)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据姓名,手机号码模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<BusServiceVo> getBusServiceListPc(String enableStatus, String searchVal, Integer page, Integer limit){
        List<BusServiceVo> busServiceList = baseAppServerUserBiz.getBusServiceList(enableStatus, searchVal, page, limit);
        int total = baseAppServerUserBiz.selectBusServiceCount(enableStatus, searchVal);
        return new TableResultResponse<BusServiceVo>(total, busServiceList);
    }


    /**
     * 保存商业服务人员
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveBusServicePc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存商业服务人员---PC端", notes = "保存商业服务人员---PC端",httpMethod = "POST")
    public ObjectRestResponse saveBusServicePc(@RequestBody @ApiParam SaveBusServiceParam params){
        return baseAppServerUserBiz.saveBusService(params);
    }



    /**
     * 查询商业服务人员信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/getBusServiceInfoPc/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询商业服务人员信息---PC端", notes = "查询商业服务人员信息---PC端",httpMethod = "GET")
    public TableResultResponse<BusServiceInfo> getBusServiceInfoPc(@PathVariable String id){
        List<BusServiceInfo> info = baseAppServerUserBiz.getBusServiceInfo(id);
        return new TableResultResponse<BusServiceInfo>(info.size(),info);
    }


    /**
     * 删除商业服务人员
     * @param id
     * @return
     */
    @RequestMapping(value = "/delBusServiceInfoPc/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation(value = "删除商业服务人员---PC端", notes = "删除商业服务人员---PC端",httpMethod = "DELETE")
    public ObjectRestResponse delBusServiceInfoPc(@PathVariable String id){
        return baseAppServerUserBiz.delBusServiceInfo(id);
    }


    /**
     * 编辑商业服务人员
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateBusServicePc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "编辑商业服务人员---PC端", notes = "编辑商业服务人员---PC端",httpMethod = "POST")
    public ObjectRestResponse updateBusServicePc(@RequestBody @ApiParam SaveBusServiceParam params){
        return baseAppServerUserBiz.updateBusService(params);
    }



}
