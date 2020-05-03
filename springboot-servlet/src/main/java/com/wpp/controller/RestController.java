package com.wpp.controller;

import com.wpp.listener.MyApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

/**
 * @Author: jackgeeks
 * @ProjectName: springboot-llf
 * @Package: com.wpp.springbootllf.controller
 * @ClassName: RestController
 * @Description: @todo
 * @CreateDate: 2020/5/1 0:44
 * @Version: 1.0
 */
@org.springframework.web.bind.annotation.RestController
public class RestController {
    @GetMapping("/")
    public String Welcome() {
        return "Welcome To SpringBoot";
    }

    @GetMapping("/CreateSession")
    public String CreateSession(HttpSession Session) {
        Session.setAttribute("user", "jackgeeks");
        return "CreateSession";
    }

    @GetMapping("/DestroySession")
    public String DestroySession(HttpSession Session) {
        Session.invalidate();
        return "DestroySession";
    }

    @GetMapping("/Event")
    public String Event() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyApplicationListener.class);
        System.out.println("发布事件");
        context.publishEvent("event");
        context.close();
        return "发布事件";
    }
}
