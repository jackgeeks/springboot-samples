package com.wpp.listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @Author: jackgeeks
 * @ProjectName: springboot-llf
 * @Package: com.wpp.springbootllf.listener
 * @ClassName: ListenerOne
 * @Description: @todo
 * @CreateDate: 2020/4/30 23:25
 * @Version: 1.0
 */
@Slf4j
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        log.info("ContextListener--init .. ");
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        log.info("ContextListener---desroyed .. ");
    }
}
