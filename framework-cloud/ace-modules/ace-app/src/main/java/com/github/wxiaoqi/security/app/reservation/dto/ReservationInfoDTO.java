package com.github.wxiaoqi.security.app.reservation.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReservationInfoDTO implements Serializable {
    private int boughtNum; // 已经购买的数量
    private int limitNum; // 限制购买数量
}
