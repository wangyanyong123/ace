package com.github.wxiaoqi.security.app.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lzx
 * @create 2019/3/5.
 */
@Data
public class AuthUser implements Serializable {
	private static final long serialVersionUID = 5617874851272677982L;
    private String id;

    private String username;

    private String password;

    private String name;

    private String departId;

    private String tenantId;

    public AuthUser() {
    }
}
