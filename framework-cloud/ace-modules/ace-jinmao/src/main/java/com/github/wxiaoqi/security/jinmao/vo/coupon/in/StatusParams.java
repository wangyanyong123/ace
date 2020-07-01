package com.github.wxiaoqi.security.jinmao.vo.coupon.in;

import lombok.Data;

import java.io.Serializable;


@Data
public class StatusParams implements Serializable {

    private static final long serialVersionUID = 2735663097341370310L;
    private String id;

    private String useStatus;

}
