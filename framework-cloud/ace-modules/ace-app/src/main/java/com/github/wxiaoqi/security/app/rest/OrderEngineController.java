package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.api.vo.buffer.OperateConstants;
import com.github.wxiaoqi.security.api.vo.constant.DocPathConstant;
import com.github.wxiaoqi.security.api.vo.order.in.*;
import com.github.wxiaoqi.security.api.vo.order.out.*;
import com.github.wxiaoqi.security.app.biz.BizSubBiz;
import com.github.wxiaoqi.security.app.biz.BizSubscribeWoBiz;
import com.github.wxiaoqi.security.app.biz.BizWoBiz;
import com.github.wxiaoqi.security.app.biz.CrmServiceBiz;
import com.github.wxiaoqi.security.app.fegin.ToolFegin;
import com.github.wxiaoqi.security.app.mapper.BaseAppServerUserMapper;
import com.github.wxiaoqi.security.app.vo.in.DoOperateBean;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.constant.BusinessConstant;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单工单引擎模块
 *
 * @author huangxl
 * @Date 2018-12-03 10:00:01
 */
@RestController
@RequestMapping("orderEngine")
@CheckClientToken
@CheckUserToken
@Api(tags="订单工单引擎模块")
public class OrderEngineController {

	@Autowired
	private BizWoBiz bizWoBiz;
	@Autowired
	private BizSubBiz bizSubBiz;
	@Autowired
	private BizSubscribeWoBiz bizSubscribeWoBiz;
	@Autowired
	private BaseAppServerUserMapper baseAppServerUserMapper;
	@Autowired
	private CrmServiceBiz crmServiceBiz;
	@Autowired
	private ToolFegin toolFegin;
	/**
	 * 订单工单操作
	 * @return
	 */
	@RequestMapping(value = "/doOperate" ,method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "订单工单操作", notes = "订单工单引擎模块---订单工单操作",httpMethod = "POST")
	public ObjectRestResponse doOperate(@RequestBody @ApiParam DoOperateVo doOperateVo)  throws Exception{
		ObjectRestResponse objectRestResponse= new ObjectRestResponse();
		String id = doOperateVo.getId();
		String operateId = doOperateVo.getOperateId();
		if(StringUtils.isEmpty(id) || StringUtils.isEmpty(operateId)){
			objectRestResponse.setStatus(102);
			objectRestResponse.setMessage("参数操作ID为空");
			return objectRestResponse;
		}
		if(!StringUtils.isEmpty(doOperateVo.getExpressCompany()) && doOperateVo.getExpressCompany() != null){
			if(StringUtils.isEmpty(doOperateVo.getExpressNum()) || doOperateVo.getExpressNum() == null){
				objectRestResponse.setStatus(102);
				objectRestResponse.setMessage("请填写快递单号");
				return objectRestResponse;
			}
		}
		TransactionLogBean transactionLogBean = null;
		if(StringUtils.isNotEmpty(doOperateVo.getDescription())){
			transactionLogBean = new TransactionLogBean();
			transactionLogBean.setDesc(doOperateVo.getDescription());
			if(StringUtils.isNotEmpty(doOperateVo.getImgIds())){
				ObjectRestResponse restResponse = toolFegin.moveAppUploadUrlPaths(doOperateVo.getImgIds(), DocPathConstant.ORDERWO);
				if(restResponse.getStatus()==200){
					transactionLogBean.setImgIds(restResponse.getData()==null ? "" : (String)restResponse.getData());
				}
			}
			//transactionLogBean.setImgIds(doOperateVo.getImgIds());
			transactionLogBean.setAppraisalVal(doOperateVo.getAppraisalVal());
            transactionLogBean.setIsArriveOntime(doOperateVo.getIsArriveOntime());
		}
		DoOperateBean doOperateBean = new DoOperateBean();
		doOperateBean.setId(id);
		doOperateBean.setOperateId(operateId);
		doOperateBean.setTransactionLogBean(transactionLogBean);
		doOperateBean.setExpressCompany(doOperateVo.getExpressCompany());
		doOperateBean.setExpressNum(doOperateVo.getExpressNum());
		return bizSubscribeWoBiz.doOperate(doOperateBean);
	}

	/**
	 * 根据指定操作类型操作订单/工单
	 * @param doOperateByTypeVo 参数
	 * @return
	 */
	@RequestMapping(value = "/doOperateByType" ,method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "根据指定操作类型操作订单/工单", notes = "订单工单引擎模块---根据指定操作类型操作订单/工单",httpMethod = "POST")
	@IgnoreUserToken
	public ObjectRestResponse doOperateByType(@RequestBody @ApiParam DoOperateByTypeVo doOperateByTypeVo){
		return bizSubscribeWoBiz.doOperateByType(doOperateByTypeVo);
	}

	/**
	 * 客户端APP获取工单详情
	 * @param id 参数
	 * @return
	 */
	@RequestMapping(value = "/getWoDetailByClient" ,method = RequestMethod.GET)
	@ApiOperation(value = "客户端APP获取工单详情", notes = "订单工单引擎模块---客户端APP获取工单详情",httpMethod = "GET")
	@ApiImplicitParam(name="id",value="工单ID",dataType="String",required = true ,paramType = "query",example="")
	public ObjectRestResponse<WoDetailOutVo> getWoDetailByClient(String id) throws Exception {
		return bizWoBiz.getWoDetail(id, OperateConstants.ClientType.CLIENT_APP.toString(),null);
	}

	/**
	 * 服务端APP获取工单详情
	 * @param id 参数
	 * @return
	 */
	@RequestMapping(value = "/getWoDetailByService" ,method = RequestMethod.GET)
	@ApiOperation(value = "服务端APP获取工单详情", notes = "订单工单引擎模块---服务端APP获取工单详情",httpMethod = "GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="工单ID",dataType="String",required = true ,paramType = "query",example=""),
		@ApiImplicitParam(name="isPlanWO",value="是否是计划工单:0、不是；1、是，默认不填",dataType="String",required = false ,paramType = "query",example="")
	})
	public ObjectRestResponse<WoDetailOutVo> getWoDetailByService(String id,String isPlanWO) throws Exception {
		return bizWoBiz.getWoDetail(id, OperateConstants.ClientType.SERVER_APP.toString(),isPlanWO);
	}

	/**
	 * 客户端APP获取订单详情
	 * @param id 参数
	 * @return
	 */
	@RequestMapping(value = "/getSubDetailByClient" ,method = RequestMethod.GET)
	@ApiOperation(value = "客户端APP获取订单详情", notes = "订单工单引擎模块---客户端APP获取订单详情",httpMethod = "GET")
	@ApiImplicitParam(name="id",value="ID",dataType="String",required = true ,paramType = "query",example="")
	public ObjectRestResponse<SubDetailOutVo> getSubDetailByClient(String id) throws Exception {
		return bizSubBiz.getSubDetail(id, OperateConstants.ClientType.CLIENT_APP.toString());
	}

	/**
	 * 服务端APP获取订单详情
	 * @param id 参数
	 * @return
	 */
	@RequestMapping(value = "/getSubDetailByService" ,method = RequestMethod.GET)
	@ApiOperation(value = "服务端APP获取订单详情", notes = "订单工单引擎模块---服务端APP获取订单详情",httpMethod = "GET")
	@ApiImplicitParam(name="id",value="ID",dataType="String",required = true ,paramType = "query",example="")
	public ObjectRestResponse<SubDetailOutVo> getSubDetailByService(String id) throws Exception {
		return bizSubBiz.getSubDetail(id, OperateConstants.ClientType.SERVER_APP.toString());
	}

	/**
	 * Web后台获取工单详情
	 * @param id 参数
	 * @return
	 */
	@RequestMapping(value = "/getSubDetailByWeb" ,method = RequestMethod.GET)
	@ApiOperation(value = "Web后台获取订单详情", notes = "订单工单引擎模块---服务端APP获取订单详情",httpMethod = "GET")
	@ApiImplicitParam(name="id",value="订单ID",dataType="String",required = true ,paramType = "query",example="")
	public ObjectRestResponse<SubDetailOutForWebVo> getSubDetailByWeb(String id){
		return bizSubBiz.getSubDetailByWeb(id);
	}

	/**
	 * Web后台查询订单列表
	 * @param searchSubInWeb 参数
	 * @return
	 */
	@RequestMapping(value = "/querySubListByWeb" ,method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Web后台查询订单列表", notes = "订单工单模块---Web后台查询订单列表",httpMethod = "POST")
	public ObjectRestResponse<List<SubListForWebVo>> querySubListByWeb(@RequestBody @ApiParam SearchSubInWeb searchSubInWeb) {
		ObjectRestResponse result = new ObjectRestResponse();
		List<String> busIdList = new ArrayList<>();
		busIdList.add(BusinessConstant.getShoppingBusId());
		busIdList.add(BusinessConstant.getGroupBuyingBusId());
		busIdList.add(BusinessConstant.getSeckillBusId());
		busIdList.add(BusinessConstant.getTravelBusId());
		return bizSubBiz.querySubListByWeb(busIdList,searchSubInWeb);
	}

	/**
	 * Web后台获取工单详情
	 * @param id 参数
	 * @return
	 */
	@RequestMapping(value = "/getWoDetailByWeb" ,method = RequestMethod.GET)
	@ApiOperation(value = "Web后台获取工单详情", notes = "订单工单引擎模块---服务端APP获取工单详情",httpMethod = "GET")
	@ApiImplicitParam(name="id",value="工单ID",dataType="String",required = true ,paramType = "query",example="")
	public ObjectRestResponse<WoDetailOutForWebVo> getWoDetailByWeb(String id){
		return bizWoBiz.getWoDetailByWeb(id);
	}

	/**
	 * Web后台查询投诉、报修工单列表
	 * @param searchSubIncidentInWeb 参数
	 * @return
	 */
	@RequestMapping(value = "/queryIncidentList" ,method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Web后台查询投诉、报修工单列表", notes = "订单工单模块---Web后台查询投诉、报修工单列表",httpMethod = "POST")
	public ObjectRestResponse queryIncidentList(@RequestBody @ApiParam SearchSubIncidentInWeb searchSubIncidentInWeb) {
		ObjectRestResponse result = new ObjectRestResponse();
		List<String> busIdList = new ArrayList<>();
		if(searchSubIncidentInWeb==null || searchSubIncidentInWeb.getIncidentType()==null){
            busIdList.add(BusinessConstant.getCmplainBusId());
            busIdList.add(BusinessConstant.getRepairBusId());
        }else {
            if (searchSubIncidentInWeb.getIncidentType().toLowerCase().equals("cmplain")) {
                busIdList.add(BusinessConstant.getCmplainBusId());
            } else if (searchSubIncidentInWeb.getIncidentType().toLowerCase().equals("repair")) {
                busIdList.add(BusinessConstant.getRepairBusId());
            } else if (searchSubIncidentInWeb.getIncidentType().toLowerCase().equals("plan")){
				busIdList.add(BusinessConstant.getPlanWo());
			} else {
                busIdList.add(BusinessConstant.getCmplainBusId());
                busIdList.add(BusinessConstant.getRepairBusId());
            }
        }
		return bizWoBiz.queryIncidentList(busIdList,searchSubIncidentInWeb);
	}

	@RequestMapping(value = "/getPlanWoExcel" ,method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "导出计划工单execl数据", notes = "导出计划工单execl数据",httpMethod = "POST")
	public ObjectRestResponse getPlanWoExcel(@RequestBody @ApiParam SearchSubIncidentInWeb searchSubIncidentInWeb) {
		ObjectRestResponse result = new ObjectRestResponse();
		List<String> busIdList = new ArrayList<>();
		if(searchSubIncidentInWeb==null || searchSubIncidentInWeb.getIncidentType()==null){
			return result;
		}else {
			if (searchSubIncidentInWeb.getIncidentType().toLowerCase().equals("plan")){
				busIdList.add(BusinessConstant.getPlanWo());
			} else {
				return result;
			}
		}
		return bizWoBiz.getPlanWoExcel(busIdList,searchSubIncidentInWeb);
	}

	/**
	 * 同步CRM工单
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/syncWoToCRM" ,method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "同步CRM工单", notes = "同步CRM工单",httpMethod = "GET")
	public ObjectRestResponse syncWoToCRM(String id) {
		return bizSubscribeWoBiz.syncWoToCRM(id);
	}

	/**
	 * 同步CRM工单
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/syncWoToCRMNoUserLogin" ,method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "同步CRM工单", notes = "同步CRM工单",httpMethod = "GET")
	@IgnoreUserToken
	public ObjectRestResponse syncWoToCRMNoUserLogin(String id) {
		return bizSubscribeWoBiz.syncWoToCRM(id);
	}

//	@RequestMapping(value = "/selectRepairWoOrder" ,method = RequestMethod.GET)
//	@ResponseBody
//	@ApiOperation(value = "查询工单", notes = "查询工单",httpMethod = "GET")
//	public ObjectRestResponse selectRepairWoOrder(String HouseCode,String RepairStatus,String PhoneNumber,Integer PageSize,Integer PageCount) {
//		SelectRepairOrderIn selectRepairOrderIn = new SelectRepairOrderIn();
//		selectRepairOrderIn.setHouseCode(HouseCode);
//		selectRepairOrderIn.setPageCount(PageCount);
//		selectRepairOrderIn.setPageSize(PageSize);
//		selectRepairOrderIn.setPhoneNumber(PhoneNumber);
//		selectRepairOrderIn.setRepairStatus(RepairStatus);
//		return crmServiceBiz.selectRepairWoOrder(selectRepairOrderIn);
//	}
//
//
//	@RequestMapping(value = "/selectComplaintWoOrder" ,method = RequestMethod.GET)
//	@ResponseBody
//	@ApiOperation(value = "查询工单", notes = "查询工单",httpMethod = "GET")
//	public ObjectRestResponse selectComplaintWoOrder(Integer Type,Integer PageSize,Integer PageCount) {
//		SelectComplaintOrderIn selectComplaintOrderIn = new SelectComplaintOrderIn();
//		selectComplaintOrderIn.setType(Type);
//		selectComplaintOrderIn.setPageCount(PageCount);
//		selectComplaintOrderIn.setPageSize(PageSize);
//		return crmServiceBiz.selectComplaintWoOrder(selectComplaintOrderIn);
//	}
//
//
//	@RequestMapping(value = "/selectConsultWoOrder" ,method = RequestMethod.GET)
//	@ResponseBody
//	@ApiOperation(value = "查询工单", notes = "查询工单",httpMethod = "GET")
//	public ObjectRestResponse selectConsultWoOrder(String HouseCode,String ProjectCode,String IsRecordHouseCode,Integer PageSize,Integer PageCount) {
//		SelectConsultOrderIn selectConsultOrderIn = new SelectConsultOrderIn();
//		selectConsultOrderIn.setHouseCode(HouseCode);
//		selectConsultOrderIn.setProjectCode(ProjectCode);
//		selectConsultOrderIn.setPageCount(PageCount);
//		selectConsultOrderIn.setPageSize(PageSize);
//		selectConsultOrderIn.setIsRecordHouseCode(IsRecordHouseCode);
//		return crmServiceBiz.selectConsultWoOrder(selectConsultOrderIn);
//	}
}