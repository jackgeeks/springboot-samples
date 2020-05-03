package com.wpp.springbootthymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: jack
 * @ProjectName: springboot-thymeleaf
 * @Package: com.wpp.springbootthymeleaf.controller
 * @ClassName: indexController
 * @Description: @todo
 * @CreateDate: 2020/4/27 23:59
 * @Version: 1.0
 */
@Controller
public class IndexController {

        @GetMapping("/index")
        public String index(Model model) {
            Map user=new HashMap();
            user.put("name","jack");
            user.put("age",24);
            model.addAttribute("user", user);
            return "index";
        }


}
