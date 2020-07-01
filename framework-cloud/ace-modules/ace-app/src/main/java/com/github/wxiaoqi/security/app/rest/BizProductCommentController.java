package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.vo.productcomment.in.CommitProductCommentIn;
import com.github.wxiaoqi.security.app.vo.productcomment.in.QueryProductCommentListIn;
import com.github.wxiaoqi.security.app.vo.productcomment.out.ProductCommentListVo;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.app.biz.BizProductCommentBiz;
import com.github.wxiaoqi.security.app.entity.BizProductComment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

import java.util.List;

/**
 * 
 *
 * @author guohao
 * @Date 2020-04-24 10:43:17
 */
@RestController
@RequestMapping("bizProductComment")
@CheckClientToken
@CheckUserToken
@Api(tags="商品评价")
public class BizProductCommentController extends BaseController<BizProductCommentBiz,BizProductComment,String> {


    @Autowired
    private BizProductCommentBiz bizProductCommentBiz;

    /**
     * 提交商品评价
     */
    @RequestMapping(value = "/commitProductComment" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "提交商品评论", notes = "提交商品评论",httpMethod = "POST")
    public ObjectRestResponse commitProductComment(CommitProductCommentIn commitProductCommentIn){

        return bizProductCommentBiz.commitProductComment(commitProductCommentIn);
    }

    /**
     * 获取商品评论
     */
    @IgnoreUserToken
    @IgnoreClientToken
    @RequestMapping(value = "/findProductCommentList" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取商品评论列表", notes = "获取商品评论列表",httpMethod = "POST")
    public ObjectRestResponse findProductCommentList(@ApiParam QueryProductCommentListIn queryProductCommentListIn){
       List<ProductCommentListVo> result = bizProductCommentBiz.findProductCommentList(queryProductCommentListIn);
        return ObjectRestResponse.ok(result);
    }
}