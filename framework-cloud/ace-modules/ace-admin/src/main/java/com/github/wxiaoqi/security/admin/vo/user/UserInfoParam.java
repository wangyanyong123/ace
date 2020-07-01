package com.github.wxiaoqi.security.admin.vo.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfoParam implements Serializable {

    private String id;

    private String username;

    private String name;

    private String sex;

    private String description;
}
