package com.wpp.listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: jackgeeks
 * @ProjectName: springboot-llf
 * @Package: com.wpp.springbootllf.listener
 * @ClassName: ListenerOne
 * @Description: @Request监听器
 * @CreateDate: 2020/4/30 23:25
 * @Version: 1.0
 */
@WebListener
@Slf4j
public class RequestListener implements ServletRequestListener {


    @Override
    public void requestInitialized(ServletRequestEvent sce) {
        HttpServletRequest request = (HttpServletRequest) sce.getServletRequest();
        log.info("RequestListener----init---->"+request.getRequestURI());
    }
    @Override
    public void requestDestroyed(ServletRequestEvent sce) {
        HttpServletRequest request = (HttpServletRequest) sce.getServletRequest();
       log.info("RequestListener---desroyed----> "+request.getRequestURI());
    }
}
