package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.BizProductRecommendBiz;
import com.github.wxiaoqi.security.jinmao.vo.ProductRecommend.InputParam.SaveRecommendParam;
import com.github.wxiaoqi.security.jinmao.vo.ProductRecommend.OutputParam.ResultRecommendInfo;
import com.github.wxiaoqi.security.jinmao.vo.ProductRecommend.OutputParam.ResultRecommendInfoVo;
import com.github.wxiaoqi.security.jinmao.vo.ProductRecommend.OutputParam.ResultRecommendListVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品推荐表
 *
 * @author zxl
 * @Date 2018-12-10 10:10:55
 */
@RestController
@RequestMapping("web/bizProductRecommend")
@CheckClientToken
@CheckUserToken
@Api(tags = "商品推荐")
public class BizProductRecommendController  {

    @Autowired
    private BizProductRecommendBiz bizProductRecommendBiz;

    /**
     * 查询项目下推荐商品
     * @param projectId
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "/getProductRecommendListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询商品推荐列表---PC端", notes = "查询商品推荐列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="searchVal",value="搜索条件",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="10")
    })
    public TableResultResponse<ResultRecommendListVo> getProductRecommendListPc(String projectId,String searchVal,Integer page,Integer limit) {
        List<ResultRecommendListVo> productRecommendList = bizProductRecommendBiz.getProductRecommendList(projectId,searchVal, page, limit);
        int total = bizProductRecommendBiz.getProductRecommendCount(projectId,searchVal);
        return new TableResultResponse<ResultRecommendListVo>(total, productRecommendList);
    }

    /**
     * 根据项目ID下所有商品
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/getProductListPc", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据项目id查询商品列表---PC端", notes = "根据项目id查询商品列表---PC端",httpMethod = "GET")
    public TableResultResponse<ResultRecommendInfo> getProdcutListPc(String projectId,String searchVal) {
        List<ResultRecommendInfo> productList = bizProductRecommendBiz.getProductList(projectId,searchVal);
        return new TableResultResponse<ResultRecommendInfo>(productList.size(), productList);
    }

    /**
     * 保存推荐商品
     * @param param
     * @return
     */
    @RequestMapping(value = "/saveProductRecommendPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存商品推荐---PC端", notes = "保存商品推荐---PC端",httpMethod = "POST")
    public ObjectRestResponse saveProductRecommendPc(@RequestBody @ApiParam SaveRecommendParam param) {
        return bizProductRecommendBiz.saveProductRecommend(param);
    }

    /**
     * 编辑推荐商品
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateProductRecommendPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "编辑商品推荐---PC端", notes = "编辑商品推荐---PC端",httpMethod = "POST")
    public ObjectRestResponse updateProductRecommendPc(@RequestBody @ApiParam SaveRecommendParam param) {
        return bizProductRecommendBiz.updateProductRecommend(param);
    }

    /**
     * 删除商品推荐
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteProductRecommendPc/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation(value = "删除商品推荐---PC端", notes = "删除商品推荐---PC端",httpMethod = "DELETE")
    public ObjectRestResponse deleteProductRecommendPc(@PathVariable String id) {
        return bizProductRecommendBiz.deleteProductRecommendById(id);
    }

    /**
     * 查询商品推荐详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getProductRecommendInfoPc/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询商品推荐详情---PC端", notes = "查询商品推荐详情---PC端",httpMethod = "GET")
    public TableResultResponse<ResultRecommendInfoVo> getProductRecommendInfoPc(@PathVariable String id) {
        List<ResultRecommendInfoVo> recommendInfo = bizProductRecommendBiz.getProductRecommendById(id);
        return new TableResultResponse<ResultRecommendInfoVo>(recommendInfo.size(), recommendInfo);
    }
}