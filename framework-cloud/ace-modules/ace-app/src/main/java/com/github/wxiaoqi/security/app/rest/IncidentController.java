package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.api.vo.order.in.CreateWoInVo;
import com.github.wxiaoqi.security.api.vo.order.in.SearchSubIncident;
import com.github.wxiaoqi.security.app.biz.BizSubscribeWoBiz;
import com.github.wxiaoqi.security.app.biz.BizWoBiz;
import com.github.wxiaoqi.security.app.fegin.BizDictFeign;
import com.github.wxiaoqi.security.app.fegin.DictFeign;
import com.github.wxiaoqi.security.app.rpc.WorkOrderSyncBiz;
import com.github.wxiaoqi.security.app.vo.in.WoInVo;
import com.github.wxiaoqi.security.app.vo.plan.QrResultVo;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.constant.BusinessConstant;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * app投诉报修接口
 *
 * @author huangxl
 * @Date 2018-12-03 10:00:01
 */
@RestController
@RequestMapping("incident")
@CheckClientToken
@CheckUserToken
@Api(tags="APP投诉报修接口")
public class IncidentController {
	@Autowired
	private DictFeign dictFeign;
	@Autowired
	private BizDictFeign bizDictFeign;
	@Autowired
	private BizWoBiz bizWoBiz;
	@Autowired
	private BizSubscribeWoBiz bizSubscribeWoBiz;
	@Autowired
	private WorkOrderSyncBiz workOrderSyncBiz;

	/**
	 * 生成工单
	 * @param createWoInVo 参数
	 * @return
	 */
	@RequestMapping(value = "/createWo" ,method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "生成工单", notes = "订单工单模块---生成工单",httpMethod = "POST")
	public ObjectRestResponse createWo(@RequestBody @ApiParam CreateWoInVo createWoInVo) throws Exception {
		ObjectRestResponse result = new ObjectRestResponse();
		if(StringUtils.isEmpty(createWoInVo.getIncidentType())){
			result.setStatus(101);
			result.setMessage("生成工单类型不能为空");
			return result;
		}

		if (createWoInVo.getContactTel()!=null &&StringUtils.isPhone(createWoInVo.getContactTel())) {
			result.setStatus(101);
			result.setMessage("请输入正确的联系方式");
			return result;
		}

		if (createWoInVo.getIncidentType().equals("cmplain")) {
			createWoInVo.setBusId(BusinessConstant.getCmplainBusId());
		}else if (createWoInVo.getIncidentType().equals("repair")){
			createWoInVo.setBusId(BusinessConstant.getRepairBusId());
		}else{
			result.setStatus(101);
			result.setMessage("工单类型参数不正确");
			return result;
		}
		WoInVo woInVo = new WoInVo();
		BeanUtils.copyProperties(createWoInVo,woInVo);
		result = bizWoBiz.createWoOrder(woInVo,createWoInVo);
		if(result!=null && result.getStatus()==200){
			Map<String,String> woMap = result.getData()==null ? null : (Map<String,String>)result.getData();
			if(woMap!=null){
				final String woIdTemp = woMap.get("woId")==null ? "" : woMap.get("woId");
//				bizSubscribeWoBiz.syncWoToCRMThread(woIdTemp);
				workOrderSyncBiz.createHomeWorkOrderThread(createWoInVo);
			}
		}

		return result;
	}

	/**
	 * 获取我的投诉、报修工单列表
	 * @param searchSubIncident 参数
	 * @return
	 */
	@RequestMapping(value = "/getIncidentList" ,method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "获取我的投诉、报修工单列表", notes = "订单工单模块---获取我的投诉、报修工单列表",httpMethod = "POST")
	public ObjectRestResponse getIncidentList(@RequestBody @ApiParam SearchSubIncident searchSubIncident) throws Exception {
		ObjectRestResponse result = new ObjectRestResponse();
		String busId = "";
		if (searchSubIncident.getIncidentType().toLowerCase().equals("cmplain")) {
			busId = BusinessConstant.getCmplainBusId();
		}else{
			busId = BusinessConstant.getRepairBusId();
		}
		return bizWoBiz.getWoListByBusId(busId,searchSubIncident.getPage(),searchSubIncident.getLimit());
	}



	/**
	 * 获取投诉报修分类
	 * @param incidentType 参数
	 * @return
	 */
	@RequestMapping(value = "/getIncidentClassify" ,method = RequestMethod.GET)
	@ApiOperation(value = "获取投诉报修分类", notes = "订单工单模块---获取投诉报修分类",httpMethod = "GET")
	@ApiImplicitParam(name="incidentType",value="生成工单类型(报修-repair 投诉-cmplain)",dataType="String",required = true ,paramType = "query",example="")
	public ObjectRestResponse getIncidentClassify(String incidentType) {
		ObjectRestResponse result = new ObjectRestResponse();
		Map<String, String> map = null;
		if (incidentType.toLowerCase().equals("cmplain")) {
			map =  dictFeign.getDictValues("incident_cmplain");
		}else{
			map =  dictFeign.getDictValues("incident_repair");
		}
		List<Map<String,String>> list = new ArrayList<>();
		if(map!=null && map.size()>0){
			Map<String,String> tempMap = null;
			for (String key : map.keySet()) {
				String value = map.get(key);
				tempMap = new HashMap<String, String>();
				tempMap.put("code",key);
				tempMap.put("name",value);
				list.add(tempMap);
			}
		}
		result.setData(list);
		return result;
	}

	/**
	 * 根据业务字典获取投诉报修分类
	 * @return
	 */
	@RequestMapping(value = "/getIncidentClassifyNew" ,method = RequestMethod.GET)
	@ApiOperation(value = "获取投诉报修分类", notes = "订单工单模块---根据业务字典获取投诉报修分类",httpMethod = "GET")
	@ApiImplicitParam(name="incidentType",value="生成工单类型(报修-repair 投诉-cmplain)",dataType="String",required = true ,paramType = "query",example="")
	public ObjectRestResponse getIncidentClassifyNew(String incidentType) {
		ObjectRestResponse result = new ObjectRestResponse();
		if (incidentType.toLowerCase().equals("repair")) {
			result = bizDictFeign.selectDictThreeList("repair");
		}else if (incidentType.toLowerCase().equals("cmplain")) {
			result = bizDictFeign.selectDictThreeList("cmplain");
		}else{
			result.setStatus(101);
			result.setMessage("参数工单类型错误："+incidentType);
		}
		return result;
	}


	/**
	 * 扫码获取设备、空间信息
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/qrForWo" ,method = RequestMethod.GET)
	@ApiOperation(value = "扫码报修工单接口", notes = "扫码报修工单接口",httpMethod = "GET")
	@ApiImplicitParam(name="url",value="二维码返回字段",dataType="String",required = true , paramType = "query",example="")
	@IgnoreUserToken
	public ObjectRestResponse<QrResultVo> qrForWo(String url) {
		ObjectRestResponse response = new ObjectRestResponse();
		if (StringUtils.isEmpty(url)) {
			response.setStatus(101);
			response.setMessage("二维码字段为空！");
			return response;
		}
		if ( !(url.contains("regionId")|| url.contains("qrcodeid") || url.contains("-"))) {
			response.setStatus(101);
			response.setMessage("错误的二维码！");
			return response;
		}
		return bizWoBiz.qrForWo(url);
	}

}