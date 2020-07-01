package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.BizCouponBiz;
import com.github.wxiaoqi.security.jinmao.biz.BizProductBiz;
import com.github.wxiaoqi.security.jinmao.vo.coupon.in.CouponParams;
import com.github.wxiaoqi.security.jinmao.vo.coupon.in.ProjectInfo;
import com.github.wxiaoqi.security.jinmao.vo.coupon.in.StatusParams;
import com.github.wxiaoqi.security.jinmao.vo.coupon.out.CouponListVo;
import com.github.wxiaoqi.security.jinmao.vo.coupon.out.UseSituationVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 优惠券表
 *
 * @Date 2019-04-16 10:49:40
 */
@RestController
@RequestMapping("web/bizCoupon")
@CheckClientToken
@CheckUserToken
@Api(tags = "优惠券管理")
public class BizCouponController  {

    @Autowired
    private BizCouponBiz bizCouponBiz;
    @Autowired
    private BizProductBiz bizProductBiz;


    @RequestMapping("/saveCoupon")
    @ResponseBody
    @ApiOperation(value = "保存优惠券---PC端", notes = "保存优惠券---PC端",httpMethod = "POST")
    public ObjectRestResponse saveCoupon(@RequestBody @ApiParam CouponParams couponParams) {
        return bizCouponBiz.saveCoupon(couponParams);
    }

    @RequestMapping("/updateCoupon")
    @ResponseBody
    @ApiOperation(value = "编辑优惠券---PC端", notes = "编辑优惠券---PC端",httpMethod = "POST")
    public ObjectRestResponse updateCoupon(@RequestBody @ApiParam CouponParams couponParams) {
        return bizCouponBiz.updateCoupon(couponParams);
    }

    @RequestMapping(value = "/getCouponList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询优惠券列表---PC端", notes = "查询优惠券列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目ID",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="searchVal",value="根据名称模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="useStatus",value="状态",dataType="String",paramType = "query",example="1"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="10")
    })
    public TableResultResponse<CouponListVo> getCouponList(String projectId,String searchVal,String useStatus,Integer page,Integer limit) {
        List<CouponListVo> couponList = bizCouponBiz.getCouponList(projectId, searchVal, useStatus, page, limit);
        int couponTotal = bizCouponBiz.getCouponTotal(searchVal, useStatus);
        return new TableResultResponse<>(couponTotal,couponList);
    }

    @RequestMapping(value = "/getCouponDetail", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询优惠券详情---PC端", notes = "查询优惠券详情---PC端",httpMethod = "GET")
    public ObjectRestResponse getCouponDetail(String id) {
        return bizCouponBiz.getCouponDetail(id);
    }

    @RequestMapping(value = "/getUseSituation", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询优惠券使用情况---PC端", notes = "查询优惠券使用情况---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="优惠券ID",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="useStatus",value="状态(1-未使用2-已使用3-已退款4-全部)",dataType="String",paramType = "query",example="1"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="10")
    })
    public TableResultResponse<UseSituationVo> getUseSituation(String id,String useStatus,Integer page,Integer limit) {
        List<UseSituationVo> useSituation = bizCouponBiz.getUseSituation(id,useStatus,page,limit);
        int total = bizCouponBiz.getUseSituationTotal(id,useStatus);
        return new TableResultResponse<>(total,useSituation);
    }

    @RequestMapping(value = "/getProjectInfoByTenant", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询商家关联项目---PC端", notes = "查询商家关联项目---PC端",httpMethod = "GET")
    public ObjectRestResponse getProjectInfoByTenant() {
        ObjectRestResponse response = new ObjectRestResponse();
        response.setData(bizProductBiz.getTenantProjectList());
        return response;
    }

    @RequestMapping(value = "/getProductByProject", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "查询项目下商品---PC端", notes = "查询项目下商品---PC端",httpMethod = "POST")
    public ObjectRestResponse getProductByProject(@RequestBody @ApiParam ProjectInfo projectInfo) {
        ObjectRestResponse response = new ObjectRestResponse();
        if (projectInfo.getProjectId() == null || projectInfo.getProjectId().size() == 0) {
            response.setStatus(101);
            response.setMessage("projectId不能为空！");
            return response;
        }
        return bizCouponBiz.getProductByProject(projectInfo.getProjectId());
    }

    @RequestMapping(value = "/operateCouponStatus", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "操作优惠券---PC端", notes = "操作优惠券---PC端",httpMethod = "POST")
    public ObjectRestResponse operateCouponStatus(@RequestBody @ApiParam StatusParams params) {
        return bizCouponBiz.operateCouponStatus(params.getId(), params.getUseStatus());
    }

    @RequestMapping(value = "/getCouponExcel", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "导出Excel---PC端", notes = "导出Excel---PC端",httpMethod = "GET")
    public ObjectRestResponse getCouponExcel(@RequestBody @ApiParam StatusParams params) {
        return bizCouponBiz.getCouponExcel(params.getId(), params.getUseStatus());
    }
}