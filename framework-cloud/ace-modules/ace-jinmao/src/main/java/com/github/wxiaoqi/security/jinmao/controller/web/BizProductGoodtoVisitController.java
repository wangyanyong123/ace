package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.BizProductGoodtoVisitBiz;
import com.github.wxiaoqi.security.jinmao.vo.productgoodvisit.inputparam.DeleteGoodVisit;
import com.github.wxiaoqi.security.jinmao.vo.productgoodvisit.inputparam.GVUpdateStatus;
import com.github.wxiaoqi.security.jinmao.vo.productgoodvisit.inputparam.SaveGoodVisitParam;
import com.github.wxiaoqi.security.jinmao.vo.productgoodvisit.outputparam.ProductListVo;
import com.github.wxiaoqi.security.jinmao.vo.productgoodvisit.outputparam.ResultGoodVisitInfoVo;
import com.github.wxiaoqi.security.jinmao.vo.productgoodvisit.outputparam.ResultGoodVisitVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 好物探访表
 *
 * @author zxl
 * @Date 2018-12-10 10:10:53
 */
@RestController
@RequestMapping("web/bizProductGoodtoVisit")
@CheckClientToken
@CheckUserToken
@Api(tags = "好物探访")
public class BizProductGoodtoVisitController {

    @Autowired
    private BizProductGoodtoVisitBiz bizProductGoodtoVisitBiz;

    /**
     * 查询好物探访列表
     * @param enableStatus
     * @param searchVal
     * @param projectId
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "/getGoodVisitProductListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询好物探访列表---PC端", notes = "查询好物探访列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="enableStatus",value="状态查询(1-草稿，2-已发布，3-已撤回,4-全部)",dataType="String",paramType = "query",example="1"),
            @ApiImplicitParam(name="searchVal",value="根据标题，商品名称模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="projectId",value="根据项目分类查询(没有传空)",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<ResultGoodVisitVo> getGoodVisitProductListPc(String enableStatus, String searchVal, String projectId, Integer page, Integer limit) {
        List<ResultGoodVisitVo> goodVisitList = bizProductGoodtoVisitBiz.getGoodVisitList(enableStatus, searchVal, projectId, page, limit);
        int total = bizProductGoodtoVisitBiz.selectGoodVisitCount(enableStatus, searchVal, projectId);
        return new TableResultResponse<ResultGoodVisitVo>(total, goodVisitList);
    }

    /**
     * 保存好物探访信息
     * @param param
     * @return
     */
    @RequestMapping(value = "/saveGoodVisitProductPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存好物探访---PC端", notes = "保存好物探访---PC端",httpMethod = "POST")
    public ObjectRestResponse saveGoodVisitProductPc(@RequestBody @ApiParam SaveGoodVisitParam param) {
        return bizProductGoodtoVisitBiz.saveGoodVisitInfo(param);
    }

    /**
     * 查询好物探访详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getGoodVisitInfoPc/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询好物探访详情---PC端", notes = "根据好物探访ID详情---PC端",httpMethod = "GET")
    public TableResultResponse<ResultGoodVisitInfoVo> getGoodVisitInfoPc(@PathVariable String id) {
        List<ResultGoodVisitInfoVo> goodVisitInfo = bizProductGoodtoVisitBiz.getGoodVisitInfo(id);
        return new TableResultResponse<ResultGoodVisitInfoVo>(goodVisitInfo.size(), goodVisitInfo);
    }

    /**
     * 编辑好物探访信息
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateGoodVisitProductInfoPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "编辑好物探访信息---PC端", notes = "编辑好物探访信息---PC端",httpMethod = "POST")
    public ObjectRestResponse updateGoodVisitProductInfoPc(@RequestBody @ApiParam SaveGoodVisitParam param) {
        return bizProductGoodtoVisitBiz.updateGoodVisitInfo(param);
    }

    /**
     * 根据项目查询关联商品

     * @return
     */
    @RequestMapping(value = "/getProductInfoPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询项目的关联商品---PC端", notes = "查询项目的关联商品---PC端",httpMethod = "GET")
    @ApiImplicitParams( @ApiImplicitParam(name="searchVal",value="根据商品名称模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"))
    public TableResultResponse<ProductListVo> getProductInfoPc(String searchVal) {
        List<ProductListVo> productList = bizProductGoodtoVisitBiz.getProductList(searchVal);
        return new TableResultResponse<ProductListVo>(productList.size(), productList);
    }

    /**
     * 修改禁用与启用状态
     *
     */
    @RequestMapping(value = "/updateGoodVisitStatusPc", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "修改禁用与启用状态---PC端", notes = "修改禁用与启用状态---PC端", httpMethod = "POST")
    public ObjectRestResponse updateGoodVisitStatusPc(@RequestBody @ApiParam GVUpdateStatus param) {
        return bizProductGoodtoVisitBiz.updateGoodVisitStatus(param);
    }

    /**
     * 删除好物探访商品
     * @param
     * @return
     */
    @RequestMapping(value = "/deleteGoodVisitPc", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "删除好物探访商品---PC端", notes = "删除好物探访商品---PC端", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="status",value="状态(0-删除，1-正常)",dataType="String",paramType = "query",example="1"),
            @ApiImplicitParam(name="id",value="主键",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse deleteGoodVisitPc(@RequestBody @ApiParam DeleteGoodVisit param) {
        return bizProductGoodtoVisitBiz.deleteGoodVisit(param);
    }
}