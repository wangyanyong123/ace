package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizProductComment;
import com.github.wxiaoqi.security.app.vo.productcomment.in.QueryProductCommentListIn;
import com.github.wxiaoqi.security.app.vo.productcomment.out.ProductCommentListVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;

import java.util.List;

/**
 * 
 * 
 * @author guohao
 * @Date 2020-04-24 10:43:17
 */
public interface BizProductCommentMapper extends CommonMapper<BizProductComment> {

    List<ProductCommentListVo> selectProductCommentList(QueryProductCommentListIn queryProductCommentListIn);
}
