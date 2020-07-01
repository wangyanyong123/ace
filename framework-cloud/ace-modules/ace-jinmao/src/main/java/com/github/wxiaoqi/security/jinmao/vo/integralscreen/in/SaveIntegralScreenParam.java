package com.github.wxiaoqi.security.jinmao.vo.integralscreen.in;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author qs
 * @date 2019/8/29
 */
@Data
public class SaveIntegralScreenParam implements Serializable {

    private List<Map<String,String>> content;

    private String startIntegral;

    private String endIntegral;

    private String id;
}
