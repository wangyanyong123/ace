package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizReservationOrderComment;
import com.github.wxiaoqi.security.app.reservation.vo.ReservationOrderCommentVO;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;

import java.util.List;

/**
 * 
 * 
 * @author wangyanyong
 * @Date 2020-04-25 18:23:26
 */
public interface BizReservationOrderCommentMapper extends CommonMapper<BizReservationOrderComment> {

    List<ReservationOrderCommentVO> queryComment(String productId);
	
}
