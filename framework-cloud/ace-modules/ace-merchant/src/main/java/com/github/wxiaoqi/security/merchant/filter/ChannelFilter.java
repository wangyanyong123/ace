package com.github.wxiaoqi.security.merchant.filter;

import com.github.wxiaoqi.security.merchant.context.AppContextHandler;
import com.github.wxiaoqi.security.auth.client.interceptor.RequestWrapper;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 过滤器Filter，用来把request传递下去
 */
@WebFilter(urlPatterns = "/*",filterName = "channelFilter")
public class ChannelFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
 
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if(servletRequest instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            requestWrapper = new RequestWrapper(httpServletRequest);

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
        }
        if(requestWrapper == null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(requestWrapper, servletResponse);
        }
    }
 
    @Override
    public void destroy() {

    }
}