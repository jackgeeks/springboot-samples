package com.wpp.springbootwebflux.handle;

import com.wpp.springbootwebflux.dao.UserDao;
import com.wpp.springbootwebflux.pojo.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @Author: jackgeeks
 * @ProjectName: springboot-webflux
 * @Package: com.wpp.springbootwebflux.handle
 * @ClassName: UserHandle
 * @Description: @todo
 * @CreateDate: 2020/5/4 13:21
 * @Version: 1.0
 */
@Component
public class UserHandle {
    @Autowired
    private UserDao userDao;

    public Mono<ServerResponse> GetAll (ServerRequest request){
      return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
              .body(userDao.findAll(), User.class);
    }
    public Mono<ServerResponse> StreamGetAll (ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.valueOf(MediaType.TEXT_EVENT_STREAM_VALUE))
                .body(userDao.findAll(), User.class);
    }


    public Mono<ServerResponse> createUser (ServerRequest request){
        Mono<User> userMono = request.bodyToMono(User.class);
        return userMono.flatMap( u->{
            u.setCreatedDate(LocalDateTime.now());
             return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(userDao.saveAll(userMono), User.class);});
    }
    public Mono<ServerResponse> updateUser(ServerRequest request) {
        Mono<User> userMono = request.bodyToMono(User.class);
        String id = request.pathVariable("id");

        return  userMono.flatMap(updateUser -> {


            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                   .body(userDao.findById(id).flatMap(oldUser -> {
                       // 将新的用户数据覆盖旧的用户数据
                       BeanUtils.copyProperties(updateUser, oldUser);
                       oldUser.setId(id);
                       oldUser.setCreatedDate(LocalDateTime.now());
                       return userDao.save(oldUser);
                   }), User.class);
        });


    }



    public Mono<ServerResponse> deleteUserById (ServerRequest request) {
        String id = request.pathVariable("id");
        return userDao.findById(id)
                .flatMap(u -> userDao.delete(u)
                        .then(ServerResponse.ok().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }



}
