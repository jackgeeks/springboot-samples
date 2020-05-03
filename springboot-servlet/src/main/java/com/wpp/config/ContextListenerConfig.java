package com.wpp.config;

import com.wpp.listener.ContextListener;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: jackgeeks
 * @ProjectName: springboot-llf
 * @Package: com.wpp.springbootllf.config
 * @ClassName: ListenerTowConfig
 * @Description: @todo
 * @CreateDate: 2020/4/30 23:36
 * @Version: 1.0
 */
@Configuration
public class ContextListenerConfig {

    @Bean
    public ServletListenerRegistrationBean ListenerTow(){
        ServletListenerRegistrationBean<ContextListener>servletListenerRegistrationBean = new ServletListenerRegistrationBean<>(new ContextListener());
        return servletListenerRegistrationBean;
    }
}
