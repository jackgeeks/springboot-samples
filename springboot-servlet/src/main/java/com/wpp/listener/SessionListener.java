package com.wpp.listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionListener;

/**
 * @Author: jackgeeks
 * @ProjectName: springboot-llf
 * @Package: com.wpp.springbootllf.listener
 * @ClassName: SessionListener
 * @Description: @todo
 * @CreateDate: 2020/5/1 0:09
 * @Version: 1.0
 */
@WebListener
@Slf4j
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(javax.servlet.http.HttpSessionEvent se) {

        log.info("SessionListener----init---->"+se.getSession().getAttribute("user"));
    }


    @Override
    public void sessionDestroyed(javax.servlet.http.HttpSessionEvent se) {

        log.info("SessionListener---desroyed----> "+se.getSession().getAttribute("user"));
    }
}
