package com.github.wxiaoqi.security.app.vo.crm.in;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ZhongtaiMemberResponse implements Serializable {

    private String code;
    private List<CheckMemberBody> data;
    private String msg;


}
