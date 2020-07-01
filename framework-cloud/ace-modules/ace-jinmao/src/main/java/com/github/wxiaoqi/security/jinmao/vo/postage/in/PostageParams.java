package com.github.wxiaoqi.security.jinmao.vo.postage.in;

import lombok.Data;

import java.io.Serializable;

@Data
public class PostageParams implements Serializable {
    private static final long serialVersionUID = 2612463015695978383L;

    private String startAmount;

    private String endAmount;

    private String postage;


}
