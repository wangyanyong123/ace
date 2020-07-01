package com.github.wxiaoqi.security.jinmao.vo.productorder.out;

import lombok.Data;

import java.util.Date;

@Data
public class ProductOrderOperationVo {

    private String orderId;

    private String stepStatus;

    private String currStep;

    private String description;

    private Date createTime;
}
