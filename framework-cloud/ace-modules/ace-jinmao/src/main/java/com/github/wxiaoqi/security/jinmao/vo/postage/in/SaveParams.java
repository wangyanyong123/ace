package com.github.wxiaoqi.security.jinmao.vo.postage.in;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class SaveParams implements Serializable {
    private static final long serialVersionUID = -5975459818819491794L;

    private List<Map<String,String>> content;

    private String startAmount;

    private String endAmount;

    private String postage;

    private String id;
}
