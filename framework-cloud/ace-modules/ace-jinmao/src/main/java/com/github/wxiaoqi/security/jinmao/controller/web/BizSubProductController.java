package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.DateUtils;
import com.github.wxiaoqi.security.jinmao.biz.BizSubProductBiz;
import com.github.wxiaoqi.security.jinmao.vo.Product.InputParam.ProductExcel;
import com.github.wxiaoqi.security.jinmao.vo.account.*;
import com.github.wxiaoqi.security.jinmao.vo.statement.in.AccountExcel;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 订单产品表
 *
 * @author huangxl
 * @Date 2019-03-25 10:52:39
 */
@RestController
@RequestMapping("web/bizSubProduct")
@CheckClientToken
@CheckUserToken
@Api(tags = "商品销售分析管理")
public class BizSubProductController  {

    @Autowired
    private BizSubProductBiz bizSubProductBiz;



    /**
     * 查询商品销售明细列表
     * @return
     */
    @RequestMapping(value = "/getAccountDetailListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询商品销售明细列表---PC端", notes = "查询商品销售明细列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="startTime",value="开始时间",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="endTime",value="结束时间",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="searchVal",value="根据商品编码、商品名称、商户名称模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<AccountDetailVo> getAccountDetailListPc(String searchVal, String projectId, String startTime, String endTime, Integer page, Integer limit){
        TableResultResponse resultResponse = new TableResultResponse();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if(DateUtils.daysBetween(sdf.parse(startTime),sdf.parse(endTime)) > 60){
                resultResponse.setStatus(501);
                resultResponse.setMessage("查询日期之差已超过最大天数60天限制!");
                return resultResponse;
            }else{

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<AccountDetailVo> accountDetailList =  bizSubProductBiz.getAccountDetailList(searchVal, projectId, startTime, endTime, page, limit);
        int total = 0;
        if(accountDetailList != null && accountDetailList.size() > 0){
            total =bizSubProductBiz.selectAccountDetailCount(searchVal, projectId, startTime, endTime);
        }
        return new TableResultResponse<AccountDetailVo>(total, accountDetailList);
    }



    /**
     * 导出商品销售明细excel
     * @return
     */
    @RequestMapping(value = "/exportAccountDetail",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "导出商品销售明细excel---PC端", notes = "导出商品销售明细excel---PC端",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="startTime",value="开始时间",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="endTime",value="结束时间",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="searchVal",value="根据商品编码、商品名称、商户名称模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse exportAccountDetail(@RequestBody @ApiParam ProductExcel excel){
        return bizSubProductBiz.exportAccountDetail(excel.getSearchVal(), excel.getProjectId(),excel.getStartTime(),excel.getEndTime());
    }



    /**
     * 商品业务和分类销量与订单总金额的占比
     * @return
     */
    @RequestMapping(value = "/getBusInfoPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "商品业务和分类销量与订单总金额的占比---PC端", notes = "商品业务和分类销量与订单总金额的占比---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="startTime",value="开始时间",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="endTime",value="结束时间",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="searchVal",value="根据商品编码、商品名称、商户名称模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<BusAccountInfo> getBusInfoPc(String searchVal, String projectId, String startTime, String endTime){
        return bizSubProductBiz.getBusInfo(searchVal, projectId, startTime, endTime);
    }


    /**
     * 查询项目销量与订单总金额的占比
     * @return
     */
    @RequestMapping(value = "/getProjectInfoPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询项目销量与订单总金额的占比---PC端", notes = "查询项目销量与订单总金额的占比---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="startTime",value="开始时间",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="endTime",value="结束时间",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="searchVal",value="根据商品编码、商品名称、商户名称模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<ProjectAccountInfo> getProjectInfoPc(String searchVal, String projectId, String startTime, String endTime){
        return bizSubProductBiz.getProjectInfo(searchVal, projectId, startTime, endTime);
    }



    /**
     * 查询起止时间销量与订单总金额的占比
     * @return
     */
    @RequestMapping(value = "/getTimeInfoPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询起止时间销量与订单总金额的占比---PC端", notes = "查询起止时间销量与订单总金额的占比---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="startTime",value="开始时间",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="endTime",value="结束时间",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="searchVal",value="根据商品编码、商品名称、商户名称模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<TimeAccountInfo> getTimeInfoPc(String searchVal, String projectId, String startTime, String endTime){
        return bizSubProductBiz.getTimeInfo(searchVal, projectId, startTime, endTime);
    }




    /**
     * 查询商家对账列表
     * @return
     */
    @RequestMapping(value = "/getCheckAccountListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询商家对账列表---PC端", notes = "查询商家对账列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="startTime",value="开始时间",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="endTime",value="结束时间",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="tenantId",value="商户id",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<CheckAccountVo> getCheckAccountListPc(String tenantId,String startTime, String endTime, Integer page, Integer limit){
        List<CheckAccountVo> checkAccountList =  bizSubProductBiz.getCheckAccountList(tenantId,startTime, endTime, page, limit);
        int total =bizSubProductBiz.selectCheckAccountCount(tenantId,startTime, endTime);
        return new TableResultResponse<CheckAccountVo>(total, checkAccountList);
    }




    /**
     * 导出商家对账明细excel
     * @return
     */
    @RequestMapping(value = "/exportCheckAccount",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "导出商家对账明细excel---PC端", notes = "导出商家对账明细excel---PC端",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="tenantId",value="商户id",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="startTime",value="开始时间",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="endTime",value="结束时间",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse exportCheckAccount(@RequestBody @ApiParam AccountExcel excel){
        return bizSubProductBiz.exportCheckAccount(excel.getTenantId(),excel.getStartTime(), excel.getEndTime());
    }


    /**
     * 查询结算状态占比与周期结算金额占比
     * @return
     */
    @RequestMapping(value = "/getBalanceStatusInfoPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询结算状态占比与周期结算金额占比---PC端", notes = "查询结算状态占比与周期结算金额占比---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="startTime",value="开始时间",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="endTime",value="结束时间",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="tenantId",value="商户id",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<BalanceStatusInfo> getBalanceStatusInfoPc(String tenantId,String startTime, String endTime){
        return bizSubProductBiz.getBalanceStatusInfo(tenantId, startTime, endTime);
    }


}