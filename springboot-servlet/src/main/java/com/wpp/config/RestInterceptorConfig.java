package com.wpp.config;

import com.wpp.Interceptor.RestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: jackgeeks
 * @ProjectName: springboot-llf
 * @Package: com.wpp.springbootllf.config
 * @ClassName: RestInterceptorConfig
 * @Description: @todo
 * @CreateDate: 2020/4/30 21:44
 * @Version: 1.0
 */

/**
 * implements WebMvcConfigurer ： 不会覆盖@EnableAutoConfiguration关于WebMvcAutoConfiguration的配置
 * @EnableWebMvc + implements WebMvcConfigurer ： 会覆盖@EnableAutoConfiguration关于WebMvcAutoConfiguration的配置
 * extends WebMvcConfigurationSupport ：会覆盖@EnableAutoConfiguration关于WebMvcAutoConfiguration的配置
 * extends DelegatingWebMvcConfiguration ：会覆盖@EnableAutoConfiguration关于WebMvcAutoConfiguration的配置
 */

@Configuration
public class RestInterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RestInterceptor()).addPathPatterns("/*");
    }
}
