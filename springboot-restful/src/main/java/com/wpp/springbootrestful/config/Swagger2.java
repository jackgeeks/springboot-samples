package com.wpp.springbootrestful.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;
/**
 * @Author: jackgeeks
 * @ProjectName: springboot-restful
 * @Package: com.wpp.springbootrestful.config;
 * @ClassName: Swagger2
 * @Description: @todo
 * @CreateDate: 2020/5/1 22:04
 * @Version: 1.0
 */

@Configuration
public class Swagger2 {
    @Bean
    public Docket createRestApi() {
        List<Parameter> pars = new ArrayList<Parameter>();
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select()
                // 这一段是配置需要扫描的包
                .apis(RequestHandlerSelectors.basePackage("com.wpp.springbootrestful"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("api文档")
                .description("jackgeeks")
                .termsOfServiceUrl("http://localhost:8080/")
                .version("1.0")
                .build();

    }

}
