package com.github.wxiaoqi.security.jinmao.controller.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.order.in.*;
import com.github.wxiaoqi.security.api.vo.order.out.SubDetailOutForWebVo;
import com.github.wxiaoqi.security.api.vo.order.out.WoListForWebVo;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.Bean2MapUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.jinmao.biz.BaseAppServerUserBiz;
import com.github.wxiaoqi.security.jinmao.biz.BizCrmHouseBiz;
import com.github.wxiaoqi.security.jinmao.biz.BizSubscribeBiz;
import com.github.wxiaoqi.security.jinmao.biz.BizWoBiz;
import com.github.wxiaoqi.security.jinmao.feign.OrderEngineFeign;
import com.github.wxiaoqi.security.jinmao.feign.OssExcelFeign;
import com.github.wxiaoqi.security.jinmao.mapper.BaseTenantMapper;
import com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.OutParam.UserInfo;
import com.github.wxiaoqi.security.jinmao.vo.house.HouseInfoTree;
import com.github.wxiaoqi.security.jinmao.vo.order.PlanWoListVo;
import com.github.wxiaoqi.security.jinmao.vo.order.SubListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 订单工单引擎模块
 *
 * @author huangxl
 * @Date 2018-12-03 10:00:01
 */
@RestController
@RequestMapping("web/orderEngine")
@CheckClientToken
@CheckUserToken
@Api(tags="订单工单引擎模块")
public class OrderEngineController {

	@Autowired
	private OrderEngineFeign orderEngineFeign;
	@Autowired
	private OssExcelFeign ossExcelFeign;
	@Autowired
	private BizWoBiz bizWoBiz;
	@Autowired
	private HttpServletResponse response;
    @Autowired
    private BaseTenantMapper baseTenantMapper;
    @Autowired
	private BaseAppServerUserBiz appServerUserBiz;
    @Autowired
	private BizCrmHouseBiz bizCrmHouseBiz;

    @Autowired
    private BizSubscribeBiz bizSubscribeBiz;



	/**
	 * Web后台查询投诉、报修工单列表
	 * @param searchSubIncidentInWeb 参数
	 * @return
	 */
	@RequestMapping(value = "/queryIncidentList" ,method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Web后台查询投诉、报修工单列表", notes = "订单工单模块---Web后台查询投诉、报修工单列表",httpMethod = "POST")
	public ObjectRestResponse queryIncidentList(@RequestBody @ApiParam SearchSubIncidentInWeb searchSubIncidentInWeb) {
        UserInfo userInfo = baseTenantMapper.selectRoleTypeByUser(BaseContextHandler.getUserID());
        if(userInfo == null){
			userInfo = baseTenantMapper.selectRoleTypeByUserId(BaseContextHandler.getUserID());
		}
        if(userInfo!=null){
            if("1".equals(userInfo.getTenantType()) || "2".equals(userInfo.getTenantType())) {
                //非平台用户需要传入公司Id
                searchSubIncidentInWeb.setCompanyId(BaseContextHandler.getTenantID());
            }
        }else{
            ObjectRestResponse restResponse = new ObjectRestResponse();
            restResponse.setStatus(101);
            restResponse.setMessage("非法用户");
            return restResponse;
        }

		return orderEngineFeign.queryIncidentList(searchSubIncidentInWeb);
	}

	/**
	 * Web后台获取工单详情
	 * @param id 参数
	 * @return
	 */
	@RequestMapping(value = "/getWoDetailByWeb" ,method = RequestMethod.GET)
	@ApiOperation(value = "Web后台获取工单详情", notes = "订单工单引擎模块---服务端APP获取工单详情",httpMethod = "GET")
	@ApiImplicitParam(name="id",value="工单ID",dataType="String",required = true ,paramType = "query",example="")
	public ObjectRestResponse getWoDetailByWeb(String id){
		return orderEngineFeign.getWoDetailByWeb(id);
	}

	/**
	 * Web后台查询订单列表
	 * @param searchSubInWeb 参数
	 * @return
	 */
	@RequestMapping(value = "/querySubListByWeb" ,method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Web后台查询订单列表", notes = "订单工单模块---Web后台查询订单列表",httpMethod = "POST")
	public ObjectRestResponse querySubListByWeb(@RequestBody @ApiParam SearchSubInWeb searchSubInWeb) {
	    if(searchSubInWeb!=null && StringUtils.isEmpty(searchSubInWeb.getCompanyId())){
            UserInfo userInfo = baseTenantMapper.selectRoleTypeByUser(BaseContextHandler.getUserID());
			if(userInfo == null){
				userInfo = baseTenantMapper.selectRoleTypeByUserId(BaseContextHandler.getUserID());
			}
            if(userInfo!=null){
                if("1".equals(userInfo.getTenantType()) || "2".equals(userInfo.getTenantType())) {
                    //非平台用户需要传入公司Id
                    searchSubInWeb.setCompanyId(BaseContextHandler.getTenantID());
                }
            }else{
                ObjectRestResponse restResponse = new ObjectRestResponse();
                restResponse.setStatus(101);
                restResponse.setMessage("非法用户");
                return restResponse;
            }
        }
		return orderEngineFeign.querySubListByWeb(searchSubInWeb);
	}

	/**
	 * Web后台获取订单详情
	 * @param id 参数
	 * @return
	 */
	@RequestMapping(value = "/getSubDetailByWeb" ,method = RequestMethod.GET)
	@ApiOperation(value = "Web后台获取订单详情", notes = "订单工单引擎模块---Web后台获取订单详情",httpMethod = "GET")
	@ApiImplicitParam(name="id",value="工单ID",dataType="String",required = true ,paramType = "query",example="")
	public ObjectRestResponse<SubDetailOutForWebVo> getSubDetailByWeb(String id){
		return orderEngineFeign.getSubDetailByWeb(id);
	}

	@RequestMapping(value = "/getPlanWoExcel" ,method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Web后台导出计划工单Excel", notes = "订单工单模块---Web后台导出计划工单Excel",httpMethod = "POST")
	public ObjectRestResponse getPlanWoExcel(@RequestBody @ApiParam SearchSubIncidentInWeb searchSubIncidentInWeb) throws Exception {
		ObjectRestResponse msg = new ObjectRestResponse();
		UserInfo userInfo = baseTenantMapper.selectRoleTypeByUser(BaseContextHandler.getUserID());
		if(userInfo == null){
			userInfo = baseTenantMapper.selectRoleTypeByUserId(BaseContextHandler.getUserID());
		}
		if(userInfo!=null){
			if("1".equals(userInfo.getTenantType()) || "2".equals(userInfo.getTenantType())) {
				//非平台用户需要传入公司Id
				searchSubIncidentInWeb.setCompanyId(BaseContextHandler.getTenantID());
			}
		}
		//获取工单列表
		Map<String,Object> data = (Map<String, Object>) orderEngineFeign.getPlanWoExcel(searchSubIncidentInWeb).getData();
		List result = (List) data.get("list");
		if (data == null || result.size() == 0) {
			msg.setStatus(102);
			msg.setMessage("没有数据，导出失败！");
			return msg;
		}
		List<Map<String, Object>> dataList = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		if (result != null && result.size() > 0) {
			for (int i = 0; i < result.size(); i++) {
				PlanWoListVo temp = mapper.convertValue(result.get(i), PlanWoListVo.class);
				Map<String, Object> dataMap = Bean2MapUtil.transBean2Map(temp);
				dataList.add(dataMap);
			}
		}
		String[] titles = {"工单编码","标题","工单来源","工单分类","描述","工单下单时间","工单接单时间","工单处理时间","工单完成时间","工单处理人","工单状态","房屋信息"};
		String[] keys = {"woCode","title","comeFromStr","categoryCode","description","createTime","receiveWoTime","startProcessTime","finishWoTime","handleBy","woStatusStr","houseNames"};
		String fileName = "计划工单.xlsx";

		ExcelInfoVo excelInfoVo = new ExcelInfoVo();
		excelInfoVo.setTitles(titles);
		excelInfoVo.setKeys(keys);
		excelInfoVo.setDataList(dataList);
		excelInfoVo.setFileName(fileName);
		msg = ossExcelFeign.uploadExcel(excelInfoVo);
		return msg;
	}

	@RequestMapping(value = "/getWoExcel" ,method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Web后台导出工单Excel", notes = "订单工单模块---Web后台导出工单Excel",httpMethod = "POST")
	public ObjectRestResponse getWoExcel(@RequestBody @ApiParam SearchSubIncidentInWeb searchSubIncidentInWeb) throws Exception {
		ObjectRestResponse msg = new ObjectRestResponse();
		UserInfo userInfo = baseTenantMapper.selectRoleTypeByUser(BaseContextHandler.getUserID());
		if(userInfo == null){
			userInfo = baseTenantMapper.selectRoleTypeByUserId(BaseContextHandler.getUserID());
		}
		if(userInfo!=null){
			if("1".equals(userInfo.getTenantType()) || "2".equals(userInfo.getTenantType())) {
				//非平台用户需要传入公司Id
				searchSubIncidentInWeb.setCompanyId(BaseContextHandler.getTenantID());
			}
		}
        //获取工单列表
		Map<String,Object> data = (Map<String, Object>) orderEngineFeign.queryIncidentList(searchSubIncidentInWeb).getData();
		List result = (List) data.get("list");
		if (data == null || result.size() == 0) {
			msg.setStatus(102);
			msg.setMessage("没有数据，导出失败！");
			return msg;
		}
		List<Map<String, Object>> dataList = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
        if (result != null && result.size() > 0) {
            for (int i = 0; i < result.size(); i++) {
				WoListForWebVo temp = mapper.convertValue(result.get(i), WoListForWebVo.class);
                if ("0".equals(temp.getValet())){
                	temp.setPublishName("");
                	temp.setPublishTel("");
				}
                Map<String, Object> dataMap = Bean2MapUtil.transBean2Map(temp);
                dataList.add(dataMap);
            }
        }
		String[] titles = {"工单编码","CRM工单编码","标题","客户名称","客户电话","地址","工单来源","工单是否代客","代客人","代客电话","描述","工单下单时间","工单接单时间","工单处理时间","工单完成时间","工单处理人","工单状态","同步CRM状态"};
		String[] keys = {"woCode","crmWoCode","title","contactName","contactTel","addr","comeFromStr","valetStr","publishName","publishTel","description","createTime","receiveWoTime","startProcessTime","finishWoTime","handleBy","woStatusStr","crmSyncFlagStr"};
		String fileName = "工单.xlsx";

        ExcelInfoVo excelInfoVo = new ExcelInfoVo();
        excelInfoVo.setTitles(titles);
        excelInfoVo.setKeys(keys);
        excelInfoVo.setDataList(dataList);
        excelInfoVo.setFileName(fileName);
		msg = ossExcelFeign.uploadExcel(excelInfoVo);
		return msg;
	}

	@RequestMapping(value = "/getSubExcel" ,method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Web后台导出订单Excel", notes = "订单工单模块---Web后台导出工单Excel",httpMethod = "POST")
	public ObjectRestResponse getSubExcel(@RequestBody @ApiParam SearchSubInWeb searchSubInWeb) throws Exception {
		ObjectRestResponse msg = new ObjectRestResponse();
		if(searchSubInWeb!=null && StringUtils.isEmpty(searchSubInWeb.getCompanyId())){
			UserInfo userInfo = baseTenantMapper.selectRoleTypeByUser(BaseContextHandler.getUserID());
			if(userInfo == null){
				userInfo = baseTenantMapper.selectRoleTypeByUserId(BaseContextHandler.getUserID());
			}
			if(userInfo!=null){
				if("1".equals(userInfo.getTenantType()) || "2".equals(userInfo.getTenantType())) {
					//非平台用户需要传入公司Id
					searchSubInWeb.setCompanyId(BaseContextHandler.getTenantID());
				}
			}else{
				msg.setStatus(101);
				msg.setMessage("非法用户");
				return msg;
			}
		}
//        Map<String,Object> data = (Map<String, Object>) orderEngineFeign.querySubListByWeb(searchSubInWeb).getData();
        List result = bizSubscribeBiz.querySubList(searchSubInWeb, false);
		if (result == null || result.size() == 0) {
			msg.setStatus(102);
			msg.setMessage("没有数据，导出失败！");
			return msg;
		}
        String[] titles = {"序号","订单编号","商户订单号","订单标题","下单时间","客户姓名","联系方式","项目","联系地址","商品总数","订单实际支付","支付方式","订单状态","满意度","退款金额","退款时间","商品名称","商品规格","商品数量","商品金额","供方","销售方式","订单来源","备注"};
        String[] keys = {"num","code","actualId","title","createTime","contactName","contactTel","projectName","deliveryAddr","totalNum","actualCostStr","payWay","subStatusStr","appraisalValStr","refundCostStr","refundTime","productName","specName","subNum","price","supplier","salesWay","appType","remark"};
        String fileName = "订单.xlsx";
        ExcelInfoVo excelInfoVo = new ExcelInfoVo();
        excelInfoVo.setTitles(titles);
        excelInfoVo.setKeys(keys);
        excelInfoVo.setDataList(result);
        excelInfoVo.setFileName(fileName);
        msg = ossExcelFeign.uploadExcel(excelInfoVo);

        return msg;
	}

	/**
	 * Web后台获取投诉报修分类
	 * @return
	 */
	@RequestMapping(value = "/getIncidentClassifyNew" ,method = RequestMethod.GET)
	@ApiOperation(value = "Web后台获取投诉报修分类", notes = "订单工单引擎模块---Web后台获取投诉报修分类",httpMethod = "GET")
	@ApiImplicitParam(name="id",value="工单类型",dataType="String",required = true ,paramType = "query",example="")
	public ObjectRestResponse getIncidentClassifyNew(String incidentType){
		return orderEngineFeign.getIncidentClassifyNew(incidentType);
	}

	@GetMapping("getAccpetWoUserList")
	@ResponseBody
	@ApiOperation(value = "获取可指派/转单人员列表", notes = "服务端工单模块---获取可指派/转单人员列表",httpMethod = "GET")
	@ApiImplicitParam(name="woId",value="工单ID",dataType="String",required = true ,paramType = "query",example="")
	public ObjectRestResponse getAccpetWoUserList(String woId) {
		return orderEngineFeign.getAccpetWoUserList(woId);
	}

	@PostMapping("doTurnWo")
	@ResponseBody
	@ApiOperation(value = "转单", notes = "服务端工单模块---转单",httpMethod = "POST")
	public ObjectRestResponse doTurnWo(@RequestBody @ApiParam TrunWoInVo trunWoInVo) {
		return orderEngineFeign.doTurnWo(trunWoInVo);
	}
	@PostMapping("doAssign")
	@ResponseBody
	@ApiOperation(value = "指派工单", notes = "服务端工单模块---指派工单",httpMethod = "POST")
	public ObjectRestResponse doAssign(@RequestBody @ApiParam TrunWoInVo trunWoInVo) throws Exception {
		return orderEngineFeign.doAssign(trunWoInVo);
	}

	@PostMapping("doAccpet")
	@ResponseBody
	@ApiOperation(value = "接受工单", notes = "服务端工单模块---接受工单",httpMethod = "POST")
	public ObjectRestResponse doAccpet(@RequestBody @ApiParam TrunWoInVo trunWoInVo){
		return orderEngineFeign.doAccpet(trunWoInVo);
	}

	@RequestMapping(value = "/doOperate" ,method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "订单工单操作", notes = "订单工单引擎模块---订单工单操作",httpMethod = "POST")
	public ObjectRestResponse doOperate(@RequestBody @ApiParam DoOperateVo doOperateVo){
		return orderEngineFeign.doOperate(doOperateVo);
	}

	@GetMapping(value = "getProjectId")
	@ResponseBody
	@ApiOperation(value = "获取客服、物业人员所属项目", notes = "获取客服、物业人员所属项目",httpMethod = "GET")
	public ObjectRestResponse getProjectId(){
		String projectId = appServerUserBiz.getProjectId(BaseContextHandler.getUserID());
		return ObjectRestResponse.ok(projectId);
	}

	@GetMapping(value = "getPublicAreaTree")
	@ResponseBody
	@ApiOperation(value = "获取公告区域", notes = "获取公告区域",httpMethod = "GET")
	public TableResultResponse<HouseInfoTree> getPublicAreaTree(String projectId){
		int type = 2;
		return bizCrmHouseBiz.getHouseInfoTree(projectId,type);
	}

	@GetMapping(value = "getHouseInfoTree")
	@ResponseBody
	@ApiOperation(value = "获取房屋", notes = "获取房屋",httpMethod = "GET")
	public TableResultResponse<HouseInfoTree> getHouseInfoTree(String projectId){
		int type = 1;
		return bizCrmHouseBiz.getHouseInfoTree(projectId,type);
	}

	@PostMapping(value = "createWo")
	@ResponseBody
	@ApiOperation(value = "生成工单", notes = "生成工单",httpMethod = "POST")
	public ObjectRestResponse createWo(@RequestBody @ApiParam CreateWoInVo createWoInVo){
		return orderEngineFeign.createWo(createWoInVo);
	}

    @GetMapping(value = "syncWoToCRM")
    @ResponseBody
    @ApiOperation(value = "通知CRM", notes = "通知CRM", httpMethod = "GET")
    public ObjectRestResponse syncWoToCRM(String id) {
	    return orderEngineFeign.syncWoToCRM(id);
    }
}