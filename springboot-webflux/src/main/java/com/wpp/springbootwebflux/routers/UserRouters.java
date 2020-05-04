package com.wpp.springbootwebflux.routers;

import com.wpp.springbootwebflux.handle.UserHandle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @Author: jackgeeks
 * @ProjectName: springboot-webflux
 * @Package: com.wpp.springbootwebflux.routers
 * @ClassName: UserRouters
 * @Description: @todo
 * @CreateDate: 2020/5/4 13:39
 * @Version: 1.0
 */
@Configuration
public class UserRouters {
    @Bean
    RouterFunction<ServerResponse> userRouter(UserHandle handle){

        return RouterFunctions.nest(
                //相当于UserController里的 @RequestMapping("/user")
                RequestPredicates.path("/userhandle"),
                //相当于UserController里的  @GetMapping("/")
                RouterFunctions.route(RequestPredicates.GET("/")
                        ,handle::GetAll)
                        //相当于UserController里的  @GetMapping("/stream/all")
                .andRoute(RequestPredicates.GET("/stream/all")
                        ,handle::StreamGetAll)
                   //相当于UserController里的  @PostMapping("/")
                .andRoute(RequestPredicates.POST("/")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),handle::createUser)
                        //相当于UserController里的  @PutMapping("/{id}")
               .andRoute(RequestPredicates.PUT("/{id}")
                    .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),handle::updateUser)
                        //相当于UserController里的  @DeleteMapping("/{id}")
                .andRoute(RequestPredicates.DELETE("/{id}"),handle::deleteUserById)

        );

    }
}
