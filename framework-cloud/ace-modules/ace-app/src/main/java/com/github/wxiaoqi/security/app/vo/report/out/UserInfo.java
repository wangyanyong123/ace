package com.github.wxiaoqi.security.app.vo.report.out;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfo implements Serializable {


    private String userId;

    private String name;

    private String tel;
}
