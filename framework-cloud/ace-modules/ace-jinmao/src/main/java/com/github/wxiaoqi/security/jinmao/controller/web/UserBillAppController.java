package com.github.wxiaoqi.security.jinmao.controller.web;


import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.jinmao.biz.BizSubscribeBiz;
import com.github.wxiaoqi.security.jinmao.entity.BizSubscribe;
import com.github.wxiaoqi.security.jinmao.feign.NoticeFeign;
import com.github.wxiaoqi.security.jinmao.mapper.BizSubscribeMapper;
import com.github.wxiaoqi.security.jinmao.vo.order.SubExcel;
import com.github.wxiaoqi.security.jinmao.vo.propertybill.UserBillVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("web/propertyBill")
@CheckClientToken
@CheckUserToken
@Api(tags="物业缴费")
public class UserBillAppController {

    @Autowired
    private BizSubscribeBiz bizSubscribeBiz;
    @Autowired
    private NoticeFeign noticeFeign;
    @Autowired
    private BizSubscribeMapper bizSubscribeMapper;

    @RequestMapping(value = "/getPropertyBillList" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取物业账单列表---web端", notes = "获取物业账单列表---web端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目ID",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="subStatus",value="缴费状态(0-已下单、1-处理中、2-待支付、3-已取消、4-已完成、5-待确认、6-退款中、7-退款完成)",dataType="String",paramType = "query",example="1"),
            @ApiImplicitParam(name="searchVal",value="根据编码、账单名称，客户名称，联系方式模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse getPropertyBillList(String projectId,String subStatus,String syncStatus,String searchVal, Integer page, Integer limit) {
        List<UserBillVo> propertyBillList = bizSubscribeBiz.getPropertyBillList(projectId, subStatus, syncStatus,searchVal, page, limit);
        int count = bizSubscribeBiz.getPropertyBillCount(projectId, subStatus, searchVal);
        return new TableResultResponse(count,propertyBillList);
    }

    @RequestMapping(value = "/getPropertyBillDetail",method = RequestMethod.GET)
    @ApiOperation(value = "获取物业账单详情---web端", notes = "获取物业账单详情---web端",httpMethod = "GET")
    @ApiImplicitParam(name="id",value="账单ID",dataType="String",required = true ,paramType = "query",example="")
    public ObjectRestResponse getPropertyBillList(String id) {
        ObjectRestResponse response = new ObjectRestResponse();
        if (StringUtils.isEmpty(id)) {
            response.setStatus(101);
            response.setMessage("id不能为空！");
            return response;
        }
        return bizSubscribeBiz.getPropertyDetailById(id);
    }

    @RequestMapping(value = "/getProSubExcel" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "导出物业订单Excel---web端", notes = "导出物业订单Excel---web端",httpMethod = "POST")
    public ObjectRestResponse getProSubExcel(@RequestBody @ApiParam SubExcel subExcel) throws Exception {
        return bizSubscribeBiz.getSubExcel(subExcel.getProjectId(), subExcel.getSubStatus(), subExcel.getSyncStatus(),subExcel.getSearchVal(),null, null);
    }

    @RequestMapping(value = "/sycPropertyToCrm", method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse sycPropertyToCrm(@RequestBody @ApiParam BizSubscribe bizSubscribe) {
        return noticeFeign.syncPropertyToCrm(bizSubscribe);
    }
}
