package com.wpp.config;

import com.wpp.filter.RestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * @Author: jackgeeks
 * @ProjectName: springboot-llf
 * @Package: com.wpp.springbootllf.config
 * @ClassName: RestFilterConfig
 * @Description: @todo
 * @CreateDate: 2020/4/30 21:17
 * @Version: 1.0
 */

//如果加了@WebFilter就不需要配置文件注入
//@Configuration
public class RestFilterConfig {

    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new RestFilter());
        filterRegistrationBean.setName("RestFilter");
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}
