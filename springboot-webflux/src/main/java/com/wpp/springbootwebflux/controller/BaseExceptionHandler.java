package com.wpp.springbootwebflux.controller;
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
import org.springframework.web.bind.support.WebExchangeBindException;

@RestControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> error(WebExchangeBindException e) {
        return  new ResponseEntity<String>( toStr(e), HttpStatus.BAD_GATEWAY);
    }

    private String toStr(WebExchangeBindException ex) {
      return   ex.getFieldErrors().stream().map(e->e.getField()+":"+e.getDefaultMessage())
                .reduce("",(s1,s2)->s1+"\n"+s2);
    }
}
