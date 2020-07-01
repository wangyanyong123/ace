package com.github.wxiaoqi.security.app.oauth;

import com.alibaba.fastjson.JSON;
import com.github.wxiaoqi.security.app.oauth.exception.CustomException;
import com.github.wxiaoqi.security.common.msg.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        if (log.isWarnEnabled()) {
            log.warn("errorMsg:{}", e.getMessage(), e);
        }
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = httpServletResponse.getWriter();


        BaseResponse baseResponse = new BaseResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        writer.write(JSON.toJSONString(baseResponse));
    }
}
