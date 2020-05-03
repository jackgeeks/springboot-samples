package com.wpp.springbootrestful;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SpringbootRestfulApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRestfulApplication.class, args);
    }

}
