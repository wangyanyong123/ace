package com.github.wxiaoqi.security.jinmao.vo.account;

import lombok.Data;

import java.io.Serializable;

@Data
public class BusInfo implements Serializable {

    private String name;

    private String salesTotal;

    private String oderCostTotal;
}
