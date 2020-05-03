package com.wpp.springbootrestful.config;
/**
 * @Author wpp
 * @Date 2019/11/30 3:38
 * @Version 1.0
 * 统一异常处理类
 */

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> error(Exception e) {
        e.printStackTrace();
        return  new ResponseEntity<>( "服务器异常", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
