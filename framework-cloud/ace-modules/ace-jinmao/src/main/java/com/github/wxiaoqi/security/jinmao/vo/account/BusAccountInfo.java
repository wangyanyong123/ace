package com.github.wxiaoqi.security.jinmao.vo.account;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class BusAccountInfo implements Serializable {

    private List<Map<String,String>> busOrderAccount;

    private List<Map<String,String>> busSalesAccount;

    private List<Map<String,String>> classifyOrderAccount;

    private List<Map<String,String>> classifySalesAccount;
}
