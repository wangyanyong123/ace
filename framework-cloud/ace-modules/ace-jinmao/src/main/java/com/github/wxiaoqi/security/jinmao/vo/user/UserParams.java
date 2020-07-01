package com.github.wxiaoqi.security.jinmao.vo.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：wangjl
 * @date ：Created in 2019/8/14 14:21
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class UserParams implements Serializable {

    private static final long serialVersionUID = 6771296992429417747L;

    private String phone;

    private String isOperation;

}
