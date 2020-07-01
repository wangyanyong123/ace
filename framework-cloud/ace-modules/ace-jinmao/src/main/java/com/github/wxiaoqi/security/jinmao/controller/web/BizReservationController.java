package com.github.wxiaoqi.security.jinmao.controller.web;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.order.in.ExcelInfoVo;
import com.github.wxiaoqi.security.api.vo.order.in.SearchSubInWeb;
import com.github.wxiaoqi.security.api.vo.order.out.SubListForWebVo;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.constant.BusinessConstant;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.Bean2MapUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.jinmao.biz.BizReservationBiz;
import com.github.wxiaoqi.security.jinmao.biz.BizSubscribeBiz;
import com.github.wxiaoqi.security.jinmao.feign.OssExcelFeign;
import com.github.wxiaoqi.security.jinmao.mapper.BaseTenantMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizReservationMapper;
import com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.OutParam.UserInfo;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ResultClassifyVo;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ResultProjectVo;
import com.github.wxiaoqi.security.jinmao.vo.reservat.in.ReservationParamInfo;
import com.github.wxiaoqi.security.jinmao.vo.reservat.in.UpdateReservatParam;
import com.github.wxiaoqi.security.jinmao.vo.reservat.in.UpdateStatusParam;
import com.github.wxiaoqi.security.jinmao.vo.reservat.out.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预约服务表
 *
 * @author huangxl
 * @Date 2019-03-12 09:26:32
 */
@RestController
@RequestMapping("web/bizReservation")
@CheckClientToken
@CheckUserToken
@Api(tags = "预约服务管理")
public class BizReservationController {

    @Autowired
    private BizReservationBiz bizReservationBiz;
    @Autowired
    private BaseTenantMapper baseTenantMapper;
    @Autowired
    private OssExcelFeign ossExcelFeign;
    @Autowired
    private BizReservationMapper bizReservationMapper;

    @Autowired
    private BizSubscribeBiz bizSubscribeBiz;

    /**
     * 查询服务列表
     * @return
     */
    @RequestMapping(value = "/getReservationListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询服务列表---PC端", notes = "查询服务列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="classifyId",value="分类id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="reservaStatus",value="服务状态(0-全部,1-待发布，2-待审核，3-已发布，4已驳回,5-已撤回）",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据编码、名称模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<ReservationList>getReservationListPc(String projectId, String classifyId, String reservaStatus,
                                                                 String searchVal, Integer page, Integer limit){
        List<ReservationList> reservationList = bizReservationBiz.getReservationList(projectId, classifyId, reservaStatus, searchVal, page, limit);
        int total =bizReservationBiz.selectReservationCount(projectId, classifyId, reservaStatus, searchVal);
        return new TableResultResponse<ReservationList>(total, reservationList);
    }



    /**
     * 保存服务
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveReservationPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存服务---PC端", notes = "保存服务---PC端",httpMethod = "POST")
    public ObjectRestResponse saveReservationPc(@RequestBody @ApiParam ReservationParamInfo params){
        return bizReservationBiz.saveReservation(params);
    }


    /**
     * 查询服务详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getReservationInfoPc/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询服务详情---PC端", notes = "查询服务详情---PC端",httpMethod = "GET")
    public TableResultResponse<ResultReservationInfo> getReservationInfoPc(@PathVariable String id){
        List<ResultReservationInfo> infoList = bizReservationBiz.getReservationInfo(id);
        return new TableResultResponse<ResultReservationInfo>(infoList.size(), infoList);
    }


    /**
     * 编辑服务
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateReservationPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "编辑服务---PC端", notes = "编辑服务---PC端",httpMethod = "POST")
    public ObjectRestResponse updateReservationPc(@RequestBody @ApiParam ReservationParamInfo params){
        return bizReservationBiz.updateReservation(params);
    }



    /**
     * 服务操作
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateReservationStatusPc", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "服务操作---PC端", notes = "服务操作---PC端",httpMethod = "POST")
    public ObjectRestResponse updateReservationStatusPc(@RequestBody @ApiParam UpdateStatusParam param){
        return bizReservationBiz.updateReservationStatus(param);
    }


    /**
     * 查询服务分类列表
     * @return
     */
    @RequestMapping(value = "/getReservationClassifyListPc", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询服务分类列表---PC端", notes = "查询服务分类列表---PC端",httpMethod = "GET")
    public TableResultResponse<ResultClassifyVo> getReservationClassifyListPc(){
        List<ResultClassifyVo> classifyVoList = bizReservationBiz.getReservationClassifyList();
        return new TableResultResponse<ResultClassifyVo>(classifyVoList.size(),classifyVoList);
    }

    /**
     * 查询商户下的项目列表
     * @param
     * @return
     */
    @RequestMapping(value = "/getReservationProjectListPc", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询商户下的项目列表---PC端", notes = "查询商户下的项目列表---PC端",httpMethod = "GET")
    public TableResultResponse<ResultProjectVo> getReservationProjectListPc(){
        List<ResultProjectVo> projectList = bizReservationBiz.getReservationProjectList();
        return new TableResultResponse<ResultProjectVo>(projectList.size(),projectList);
    }


    /**
     * 查询服务审核列表
     * @return
     */
    @RequestMapping(value = "/getResrevationAuditListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询服务审核列表---PC端", notes = "查询服务审核列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="reservaStatus",value="服务状态(0-全部,1-待发布，2-待审核，3-已发布，4已驳回,5-已撤回）",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据服务编码、服务名称、商户名称码模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="classifyId",value="根据服务分类查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<ResrevationAuditVo> getResrevationAuditListPc(String projectId, String reservaStatus, String searchVal, String classifyId, Integer page, Integer limit){
        List<ResrevationAuditVo> auditList =  bizReservationBiz.getResrevationAuditList(projectId, reservaStatus, searchVal, classifyId, page, limit);
        int total =bizReservationBiz.selectResrevationAuditCount(projectId, reservaStatus, searchVal, classifyId);
        return new TableResultResponse<ResrevationAuditVo>(total, auditList);
    }



    /**
     * 查询预约服务人员列表
     * @return
     */
    @RequestMapping(value = "/getReservationPersonListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询预约服务人员列表---PC端", notes = "查询预约服务人员列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="dealStatus",value="工单状态(00-生成工单、01-调度程序已受理,02-已接受,03-处理中,04-暂停,05-已完成,06-用户已取消,07-服务人员已取消)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="tenantId",value="商户id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据单号、服务名称、联系人,电话,分类名称码模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="startTime",value="开始时间",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="endTime",value="结束时间",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<ReservatPersonVo> getReservationPersonListPc(String tenantId, String projectId, String startTime, String endTime,
                                                                           String searchVal,String dealStatus, Integer page, Integer limit){
        if("0".equals(dealStatus)){
            dealStatus = "";
        }
        List<ReservatPersonVo> reservationPersonList =  bizReservationBiz.getReservationPersonList(tenantId, projectId, startTime, endTime, searchVal,dealStatus, page, limit);
        int total =bizReservationBiz.selectReservationPersonCount(tenantId, projectId, startTime, endTime, searchVal, dealStatus);
        return new TableResultResponse<ReservatPersonVo>(total, reservationPersonList);
    }



    /**
     * 服务工单操作
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateDealStatusPc", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "服务工单操作---PC端", notes = "服务工单操作---PC端",httpMethod = "POST")
    public ObjectRestResponse updateDealStatusPc(@RequestBody @ApiParam UpdateReservatParam param){
        return bizReservationBiz.updateDealStatus(param);
    }



    /**
     * 查看预约工单详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getReservatWoDetail", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查看预约工单详情---APP端", notes = "查看预约工单详情---APP端",httpMethod = "GET")
    @ApiImplicitParam(name="id",value="工单id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse<ReservatWoDetail> getReservatWoDetail(String id){
        return bizReservationBiz.getReservatWoDetail(id);
    }

    /**
     * 修改服务工单预约时间
     * @param params 参数
     * @return
     */
    @RequestMapping(value = "/updateSubReservation", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "修改服务工单预约时间---Web端", notes = "修改服务工单预约时间---Web端",httpMethod = "POST")
    public ObjectRestResponse<Object> updateSubReservation(@RequestBody @ApiParam JSONObject params){
        return bizReservationBiz.updateSubReservation(params);
    }


    /**
     * Web后台查询预约服务订单列表
     * @param searchSubInWeb 参数
     * @return
     */
    @RequestMapping(value = "/querySubListByWeb" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "Web后台查询预约服务订单列表", notes = "订单工单模块---Web后台查询预约服务订单列表",httpMethod = "POST")
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
        List<String> busIdList = new ArrayList<>();
        busIdList.add(BusinessConstant.getReservationBusId());
        return bizReservationBiz.querySubListWithSupplierByWeb(busIdList,searchSubInWeb);
    }

    @RequestMapping(value = "/getReservatSubExcel" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "Web后台导出预约服务订单Excel", notes = "Web后台导出预约服务订单Excel",httpMethod = "POST")
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

        List result = bizSubscribeBiz.querySubList(searchSubInWeb, true);
        if (result == null || result.size() == 0) {
            msg.setStatus(102);
            msg.setMessage("没有数据，导出失败！");
            return msg;
        }
        String[] titles = {"序号","订单编号","商户订单号","订单标题","下单时间","服务时间","客户姓名","联系方式","项目","联系地址","订单实际支付金额","支付方式","订单状态","满意度","退款金额","退款时间","服务名称","服务规格","服务数量","服务金额","销售方式","供方","订单来源","备注"};
        String[] keys = {"num","code","actualId","title","createTime","expectedServiceTime","contactName","contactTel","projectName","deliveryAddr","actualCostStr","payWay","subStatusStr","appraisalValStr","refundCostStr","refundTime","productName","specName","subNum","price","salesWay","supplier","appType","remark"};
        String fileName = "订单.xlsx";
        ExcelInfoVo excelInfoVo = new ExcelInfoVo();
        excelInfoVo.setTitles(titles);
        excelInfoVo.setKeys(keys);
        excelInfoVo.setDataList(result);
        excelInfoVo.setFileName(fileName);
        msg = ossExcelFeign.uploadExcel(excelInfoVo);

        return msg;
    }



}