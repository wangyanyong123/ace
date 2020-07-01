package com.github.wxiaoqi.security.app.oauth.exception;

import org.springframework.security.core.AuthenticationException;

public class CustomException extends AuthenticationException {

    public CustomException(String msg) {
        super(msg);
    }

    public CustomException(String msg, Throwable t) {
        super(msg, t);
    }
}