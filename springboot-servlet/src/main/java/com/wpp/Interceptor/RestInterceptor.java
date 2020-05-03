package com.wpp.Interceptor;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.omg.PortableInterceptor.Interceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author: jackgeeks
 * @ProjectName: springboot-llf
 * @Package: com.wpp.springbootllf.Interceptor
 * @ClassName: RestInterceptor
 * @Description: @todo
 * @CreateDate: 2020/4/30 21:37
 * @Version: 1.0
 */


@Slf4j
public class RestInterceptor extends HandlerInterceptorAdapter {

    private static final String START_TIME = "requestStartTime";

  //preHandle在业务处理器处理请求之前被调用。预处理，可以进行编码、安全控制等处理；
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //根据请求信息判断是否需要拦截

        String url = request.getRequestURI();
        Map parameterMap = request.getParameterMap();
        log.info("request start. url:{}", url);
        long start = System.currentTimeMillis();
        request.setAttribute(START_TIME, start);
        return true;
    }
    //postHandle在业务处理器处理请求执行完成后，生成视图之前执行。后处理（调用了Service并返回ModelAndView，但未进行页面渲染），有机会修改ModelAndView；
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String url = request.getRequestURI().toString();
        long start = (Long) request.getAttribute(START_TIME);
        long end = System.currentTimeMillis();
        log.info("request finished. url:{}, cost:{}", url, end - start);
    }
    //afterCompletion在DispatcherServlet完全处理完请求后被调用，可用于清理资源等。返回处理（已经渲染了页面），可以根据ex是否为null判断是否发生了异常，进行日志记录；
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String url = request.getRequestURI();
        long start = (Long) request.getAttribute(START_TIME);
        long end = System.currentTimeMillis();
        log.info("request completed. url:{}, cost:{}", url, end - start);

    }

}
