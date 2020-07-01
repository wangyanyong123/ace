package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizReservation;
import com.github.wxiaoqi.security.app.vo.reservation.out.ReservationInfo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;

/**
 * 预约服务表
 * 
 * @author huangxl
 * @Date 2019-03-12 09:26:32
 */
public interface BizReservationMapper extends CommonMapper<BizReservation> {
    /**
     * 查询服务详情
     * @param id
     * @return
     */
    ReservationInfo selectReservationInfo(String id);
}
