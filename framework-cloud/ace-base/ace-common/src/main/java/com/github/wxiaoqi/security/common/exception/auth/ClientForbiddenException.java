package com.github.wxiaoqi.security.common.exception.auth;


import com.github.ag.core.exception.BaseException;
import com.github.wxiaoqi.security.common.constant.RestCodeConstants;

/**
 * Created by ace on 2017/9/12.
 */
public class ClientForbiddenException extends BaseException {
    public ClientForbiddenException(String message) {
        super(message, RestCodeConstants.EX_CLIENT_FORBIDDEN_CODE);
    }

}
