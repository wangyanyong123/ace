package com.github.wxiaoqi.security.api.vo.in;

import java.io.Serializable;

/**
 * 入参基类
 */
public abstract class BaseIn implements Serializable {

    private static final long serialVersionUID = -4153463283749021812L;

    /**
     * 校验入参的方法
     */
    public abstract void check();
}
