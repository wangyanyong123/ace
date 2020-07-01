package com.github.wxiaoqi.security.jinmao.vo.productdelivery;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductDeliveryData implements Serializable {

    private static final long serialVersionUID = -6641158913807701257L;

    private String id;

    //区域编码
    private String procCode;

    //区域名称
    private String procName;

    //城市编码
    private String cityCode;

    //城市名称
    private String cityName;

    //区域全称
    private String fullName;
}
