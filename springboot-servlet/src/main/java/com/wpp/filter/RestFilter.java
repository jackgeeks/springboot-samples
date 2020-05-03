package com.wpp.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;
import javax.servlet.annotation.WebFilter;
/**
 * @Author: jackgeeks
 * @ProjectName: springboot-llf
 * @Package: com.wpp.springbootllf.filter
 * @ClassName: RestFilter
 * @Description: @todo
 * @CreateDate: 2020/4/30 21:01
 * @Version: 1.0
 */
@WebFilter(urlPatterns = "/*", filterName = "RestFilter")
@Slf4j
public class RestFilter  implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("RestFilter --- init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("RestFilter --- doFilter");
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        log.info("RestFilter --- destroy");
    }
}
