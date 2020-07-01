package com.github.wxiaoqi.security.jinmao.vo.account;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class ProjectAccountInfo implements Serializable {

    private List<Map<String,String>> porderAccount;

    private List<Map<String,String>> psalesAccount;
}
