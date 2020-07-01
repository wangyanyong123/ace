package com.github.wxiaoqi.security.jinmao.vo.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：wangjl
 * @date ：Created in 2019/8/14 10:52
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class UserInfoVo implements Serializable {
    private static final long serialVersionUID = 453280827279418705L;

    private String id;

    private String name;

    private String phone;

    private String sex;

    private String email;

    private String isOperation;

    private String photo;
}
