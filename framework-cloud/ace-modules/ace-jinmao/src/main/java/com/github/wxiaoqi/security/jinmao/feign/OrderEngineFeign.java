package com.github.wxiaoqi.security.jinmao.feign;

import com.alibaba.fastjson.JSONObject;
import com.github.wxiaoqi.security.api.vo.order.in.*;
import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 订单工单引擎模块
 * @author huangxl
 * @create 2018/12/11
 */
@FeignClient(value = "ace-app", configuration = FeignApplyConfiguration.class)
public interface OrderEngineFeign {

    /**
     * Web后台获取工单详情
     * @param id 参数
     * @return
     */
    @RequestMapping(value = "/orderEngine/getWoDetailByWeb" ,method = RequestMethod.GET)
    public ObjectRestResponse getWoDetailByWeb(@RequestParam("id") String id);

    /**
     * Web后台查询投诉、报修工单列表
     * @param searchSubIncidentInWeb 参数
     * @return
     */
    @RequestMapping(value = "/orderEngine/queryIncidentList" ,method = RequestMethod.POST)
    ObjectRestResponse queryIncidentList(@RequestBody SearchSubIncidentInWeb searchSubIncidentInWeb) ;

    /**
     * Web后台获取工单详情
     * @param id 参数
     * @return
     */
    @RequestMapping(value = "/orderEngine/getSubDetailByWeb" ,method = RequestMethod.GET)
    public ObjectRestResponse getSubDetailByWeb(@RequestParam("id") String id);

    /**
     * Web后台查询投诉、报修工单列表
     * @param searchSubInWeb 参数
     * @return
     */
    @RequestMapping(value = "/orderEngine/querySubListByWeb" ,method = RequestMethod.POST)
    public ObjectRestResponse querySubListByWeb(@RequestBody SearchSubInWeb searchSubInWeb) ;

    /**
     * Web后台查询投诉报修分类
     * @param incidentType
     * @return
     */
    @RequestMapping(value = "/incident/getIncidentClassifyNew" ,method = RequestMethod.GET)
    ObjectRestResponse getIncidentClassifyNew(@RequestParam("incidentType") String incidentType);

    @RequestMapping(value = "/serverUser/getProjectId" ,method = RequestMethod.GET)
    ObjectRestResponse getProjectId();

    @RequestMapping(value = "/incident/createWo",method = RequestMethod.POST)
    ObjectRestResponse createWo(@RequestBody CreateWoInVo createWoInVo);

    @RequestMapping(value = "/orderEngine/syncWoToCRM",method = RequestMethod.GET)
    ObjectRestResponse syncWoToCRM(@RequestParam("id") String id);

    @RequestMapping(value = "/orderEngine/doOperate" ,method = RequestMethod.POST)
    ObjectRestResponse doOperate(@RequestBody DoOperateVo doOperateVo);

	@RequestMapping(value = "/woService/doAccpet" ,method = RequestMethod.POST)
	ObjectRestResponse doAccpet(@RequestBody TrunWoInVo trunWoInVo);

	@RequestMapping(value = "/woService/doAssign" ,method = RequestMethod.POST)
	ObjectRestResponse doAssign(@RequestBody TrunWoInVo trunWoInVo);
	@RequestMapping(value = "/woService/doTurnWo" ,method = RequestMethod.POST)
	ObjectRestResponse doTurnWo(@RequestBody TrunWoInVo trunWoInVo);
	@RequestMapping(value = "/woService/getAccpetWoUserList" ,method = RequestMethod.GET)
	ObjectRestResponse getAccpetWoUserList(@RequestParam("woId") String woId);



    @RequestMapping(value = "/bizReservationPerson/getReservationWoInfo", method = RequestMethod.GET)
    public ObjectRestResponse getReservationWoInfo(@RequestParam("id") String id);

    @RequestMapping(value = "/bizReservationPerson/updateSubReservation", method = RequestMethod.POST)
    public ObjectRestResponse updateSubReservation(@RequestBody JSONObject params);

	@RequestMapping(value = "/orderEngine/getPlanWoExcel" ,method = RequestMethod.POST)
	ObjectRestResponse getPlanWoExcel(SearchSubIncidentInWeb searchSubIncidentInWeb);


    @RequestMapping(value = "/commodity/finishGroupProduct" ,method = RequestMethod.GET)
    ObjectRestResponse finishGroupProduct(@RequestParam("id")String id);
}
