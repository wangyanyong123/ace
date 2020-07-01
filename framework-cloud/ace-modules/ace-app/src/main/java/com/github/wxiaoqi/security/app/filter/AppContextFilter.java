package com.github.wxiaoqi.security.app.filter;

import com.github.wxiaoqi.security.app.context.AppContextHandler;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 获取app头信息
 */
@WebFilter(filterName = "appContextFilter", urlPatterns = {"/api"})
public class AppContextFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String header = httpServletRequest.getHeader(AppContextHandler.HEADER);
        if (StringUtils.isNotEmpty(header)) {
            String[] headerArr = header.split("&");
            for (String tmpHeader : headerArr) {
                if (StringUtils.isNotEmpty(tmpHeader)) {
                    String[] kv = tmpHeader.split("=");
                    if (kv.length == 2) {
                        AppContextHandler.set(kv[0], kv[1]);
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
