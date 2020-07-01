package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.BizAccountStatementBiz;
import com.github.wxiaoqi.security.jinmao.vo.statement.in.AccountExcel;
import com.github.wxiaoqi.security.jinmao.vo.statement.in.AddParam;
import com.github.wxiaoqi.security.jinmao.vo.statement.in.UpdateParam;
import com.github.wxiaoqi.security.jinmao.vo.statement.out.AccountInfo;
import com.github.wxiaoqi.security.jinmao.vo.statement.out.BillDetailList;
import com.github.wxiaoqi.security.jinmao.vo.statement.out.BillInfo;
import com.github.wxiaoqi.security.jinmao.vo.statement.out.StatementVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 
 *
 * @author huangxl
 * @Date 2019-03-19 15:54:18
 */
@RestController
@RequestMapping("web/bizAccountStatement")
@CheckClientToken
@CheckUserToken
@Api(tags="商户清算管理")
public class BizAccountStatementController {


    @Autowired
    private BizAccountStatementBiz bizAccountStatementBiz;


    /**
     *查询账单列表
     * @return
     */
    @RequestMapping(value = "/getBillListPc", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询账单列表--PC端",notes = "查询账单列表--PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="tenantId",value="商户id",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="startTime",value="开始时间",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="endTime",value="结束时间",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="balanceStatus",value="状态查询(0：未结算，1：结算中，2：已结算,3-全部)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<StatementVo> getBillListPc(String tenantId,String startTime,String endTime,String balanceStatus,
                                                              String projectId,Integer page,Integer limit) {
        List<StatementVo> billList = bizAccountStatementBiz.getBillList(tenantId, startTime, endTime, balanceStatus, projectId, page, limit);
        int total = bizAccountStatementBiz.selectBillCount(tenantId, startTime, endTime, balanceStatus, projectId);
        return new TableResultResponse<StatementVo>(total, billList);
    }




    /**
     * 导出账单excel
     * @return
     */
    @RequestMapping(value = "/exportExcel",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "导出账单excel---PC端", notes = "导出账单excel---PC端",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="tenantId",value="商户id",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="startTime",value="开始时间",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="endTime",value="结束时间",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="balanceStatus",value="状态查询(0：未结算，1：结算中，2：已结算,3-全部)",dataType="String",paramType = "query",example="4")
    })
    public ObjectRestResponse exportExcel(@RequestBody @ApiParam AccountExcel excel){
        return bizAccountStatementBiz.exportExcel(excel.getTenantId(), excel.getStartTime(), excel.getEndTime(), excel.getBalanceStatus(), excel.getProjectId());
    }


    /**
     * 查询账单详情
     * @return
     */
    @RequestMapping(value = "/getBillDetailPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询账单详情---PC端", notes = "查询账单详情---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="tenantId",value="商户id",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="id",value="账单id",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<BillInfo> getBillDetailPc(String id, String tenantId){
        return bizAccountStatementBiz.getBillDetail(id,tenantId);
    }




    /**
     *查询账单明细列表
     * @return
     */
    @RequestMapping(value = "/getBillDetailListPc", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询账单明细列表(已完成)--PC端",notes = "查询账单明细列表--PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="tenantId",value="商户id",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="startTime",value="开始时间",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="endTime",value="结束时间",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<BillDetailList> getBillDetailListPc(String tenantId, String startTime, String endTime, Integer page, Integer limit) {
        List<BillDetailList> billDetailList = bizAccountStatementBiz.getBillDetailList(tenantId, startTime, endTime, page, limit);
        int total = bizAccountStatementBiz.selectBillDetailCount(tenantId, startTime, endTime);
        return new TableResultResponse<BillDetailList>(total, billDetailList);
    }


    /**
     *查询账单明细列表
     * @return
     */
    @RequestMapping(value = "/getBillDetailAllListPc", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询账单明细列表(所有支付状态)--PC端",notes = "查询账单明细列表--PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="tenantId",value="商户id",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="startTime",value="开始时间",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="endTime",value="结束时间",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<BillDetailList> getBillDetailAllListPc(String tenantId, String startTime, String endTime, Integer page, Integer limit) {
        List<BillDetailList> billDetailList = bizAccountStatementBiz.getBillDetailAllList(tenantId, startTime, endTime, page, limit);
        int total = bizAccountStatementBiz.selectBillDetailAllCount(tenantId, startTime, endTime);
        return new TableResultResponse<BillDetailList>(total, billDetailList);
    }



    /**
     * 导出账单明细excel
     * @return
     */
    @RequestMapping(value = "/exportBillDetailExcel",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "导出账单明细excel---PC端", notes = "导出账单明细excel---PC端",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="tenantId",value="商户id",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="startTime",value="开始时间",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="endTime",value="结束时间",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="status",value="1-导出结算状态,2-导出未结算状态",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse exportBillDetailExcel(@RequestBody @ApiParam AccountExcel excel){
        return bizAccountStatementBiz.exportBillDetailExcel(excel.getTenantId(), excel.getStartTime(), excel.getEndTime(),excel.getStatus());
    }



    /**
     * 账单操作
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateBillStatusPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "账单操作---PC端", notes = "账单操作---PC端",httpMethod = "POST")
    public ObjectRestResponse updateBillStatusPc(@RequestBody @ApiParam UpdateParam params){
        return bizAccountStatementBiz.updateBillStatus(params);
    }


    /**
     * 插入付款凭证
     * @return
     */
    @RequestMapping(value = "/addCredentialsPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "插入付款凭证---PC端", notes = "插入付款凭证---PC端",httpMethod = "POST")
    public ObjectRestResponse addCredentialsPc(@RequestBody @ApiParam AddParam param){
        return bizAccountStatementBiz.addCredentials(param);
    }

    /**
     * 查询凭证信息
     * @return
     */
    @RequestMapping(value = "/getAccountInfoPc",method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value = "查询凭证信息---PC端", notes = "查询凭证信息---PC端",httpMethod = "PUT")
    @ApiImplicitParam(name="id",value="账单id",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd")
    public ObjectRestResponse<AccountInfo> getAccountInfoPc(String id){
        return bizAccountStatementBiz.getAccountInfo(id);
    }


}