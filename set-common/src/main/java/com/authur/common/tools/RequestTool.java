package com.authur.common.tools;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author: jibing.Li
 * @Date: 2022/7/19 18:28
 */
public class RequestTool {

    /**
     * 获取path路径
     * @param servletRequest
     */
    public void RequestURL(ServletRequest servletRequest){
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String URL = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
    }
}
