package com.github.wxiaoqi.security.app.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.entity.BaseAppClientUser;
import com.github.wxiaoqi.security.app.entity.BizProductComment;
import com.github.wxiaoqi.security.app.entity.BizProductOrder;
import com.github.wxiaoqi.security.app.mapper.BizProductCommentMapper;
import com.github.wxiaoqi.security.app.vo.order.out.OrderIdResult;
import com.github.wxiaoqi.security.app.vo.productcomment.in.CommitProductCommentIn;
import com.github.wxiaoqi.security.app.vo.productcomment.in.QueryProductCommentListIn;
import com.github.wxiaoqi.security.app.vo.productcomment.out.ProductCommentListVo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.constant.OrderOperationType;
import com.github.wxiaoqi.security.common.constant.RestCodeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateTimeUtil;
import com.github.wxiaoqi.security.common.util.DateUtils;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * 
 *
 * @author guohao
 * @Date 2020-04-24 10:43:17
 */
@Service
public class BizProductCommentBiz extends BusinessBiz<BizProductCommentMapper,BizProductComment> {

    @Autowired
    private BizProductOrderBiz bizProductOrderBiz;
    @Autowired
    private BizProductOrderDetailBiz bizProductOrderDetailBiz;
    @Autowired
    private BizOrderOperationRecordBiz bizOrderOperationRecordBiz;

    @Autowired
    private BaseAppClientUserBiz baseAppClientUserBiz;

    @Transactional(rollbackFor = Exception.class)
    public ObjectRestResponse commitProductComment(CommitProductCommentIn commitProductCommentIn) {
        commitProductCommentIn.check();
        ObjectRestResponse restResponse = new ObjectRestResponse();
        BizProductOrder order = bizProductOrderBiz.selectById(commitProductCommentIn.getOrderId());

        if(!canComment(order)){
            restResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            restResponse.setMessage("当前订单尚不能评论。");
            return restResponse;
        }

        BizProductComment productComment = buildProductComment(commitProductCommentIn);
        int i = this.mapper.insertSelective(productComment);

        if(i > 0){
            updateOrderCommentStatus(commitProductCommentIn);

            OrderIdResult orderIdResult = new OrderIdResult(order.getId()
                    ,order.getParentId(),order.getOrderStatus());
            String desc = OrderOperationType.getFormatDescription(OrderOperationType.CommentOrder,commitProductCommentIn.getCommentScore().toString(),commitProductCommentIn.getContent());
            bizOrderOperationRecordBiz.addRecordByOrderIdResult(orderIdResult, OrderOperationType.CommentOrder,desc);
        }else{
            restResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            restResponse.setMessage("订单评论失败。");
            return restResponse;
        }

        return restResponse;
    }
    private boolean canComment(BizProductOrder order){

        if(AceDictionary.PRODUCT_COMMENT_COM.equals(order.getCommentStatus())){
            //已评论
            return false;
        }

        if(!AceDictionary.ORDER_STATUS_COM.equals(order.getOrderStatus())){
            //订单不是待评论状态
            return  false;
        }
        return true;
    }

    private BizProductComment buildProductComment(CommitProductCommentIn commitProductCommentIn){
        BaseAppClientUser appClientUser = baseAppClientUserBiz.selectById(BaseContextHandler.getUserID());

        BizProductComment productComment = new BizProductComment();
        productComment.setId(UUIDUtils.generateUuid());
        productComment.setOrderId(commitProductCommentIn.getOrderId());
        productComment.setOrderDetailId(commitProductCommentIn.getOrderDetailId());
        productComment.setProductId(commitProductCommentIn.getProductId());
        productComment.setCommentScore(commitProductCommentIn.getCommentScore());
        productComment.setContent(commitProductCommentIn.getContent());
        productComment.setImgUrl(commitProductCommentIn.getImgUrl());
        productComment.setCreateBy(BaseContextHandler.getUserID());
        productComment.setCreateTime(DateTimeUtil.getLocalTime());
        productComment.setStatus(AceDictionary.DATA_STATUS_VALID);
        productComment.setUserId(appClientUser.getId());
        productComment.setNickName(appClientUser.getNickname());
        productComment.setUserHeadUrl(appClientUser.getProfilePhoto());
        return productComment;
    }

    private void updateOrderCommentStatus(CommitProductCommentIn commitProductCommentIn){

        boolean isAllComment = true;
        List<String> odIdList = null;
         if(StringUtils.isNotEmpty(commitProductCommentIn.getOrderDetailId())){
             List<String> unCommentDetailIdList =
                     bizProductOrderDetailBiz.findUnCommentDetailIdList(commitProductCommentIn.getOrderId());
            unCommentDetailIdList.remove(commitProductCommentIn.getOrderDetailId());
            isAllComment = unCommentDetailIdList.size()==0;
             odIdList = Collections.singletonList(commitProductCommentIn.getOrderDetailId());
         }

         if(isAllComment){
             BizProductOrder productOrder = new BizProductOrder();
             productOrder.setId(commitProductCommentIn.getOrderId());
             productOrder.setOrderStatus(AceDictionary.ORDER_STATUS_COM);
             productOrder.setCommentStatus(AceDictionary.PRODUCT_COMMENT_COM);
             productOrder.setModifyBy(BaseContextHandler.getUserID());
             bizProductOrderBiz.updateSelectiveById(productOrder);
         }
        bizProductOrderDetailBiz.updateCommentStatusByOrderId(AceDictionary.PRODUCT_COMMENT_COM
                ,commitProductCommentIn.getOrderId(),odIdList,BaseContextHandler.getUserID());

    }

    public List<ProductCommentListVo> findProductCommentList(QueryProductCommentListIn queryProductCommentListIn) {

        return this.mapper.selectProductCommentList(queryProductCommentListIn);
    }
}