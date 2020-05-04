package com.wpp.springbootwebflux.controller;

import com.wpp.springbootwebflux.dao.UserDao;
import com.wpp.springbootwebflux.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * @Author: jackgeeks
 * @ProjectName: springboot-webflux
 * @Package: com.wpp.springbootwebflux.controller
 * @ClassName: UserController
 * @Description: @todo
 * @CreateDate: 2020/5/3 22:25
 * @Version: 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserDao userDao;

    @GetMapping("/")
    public Flux<User> GetAll() {
        return userDao.findAll();
    }

    @GetMapping(value = "/stream/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> StreamGetAll() {
        return userDao.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> GetId(  @PathVariable String id) {
        return userDao.findById(id).map(u -> new ResponseEntity<User>(u, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
    @PostMapping(value = "/")
    public Mono<User> createUser(@Valid@RequestBody User user) {
        user.setId(null);
        user.setCreatedDate(LocalDateTime.now());
        return userDao.save(user);
    }

    @PutMapping(value = "/{id}")
    public Mono<ResponseEntity<User>> updateUser(@PathVariable String id, @Valid@RequestBody User user) {
        return userDao.findById(id).flatMap(u -> {
                    u.setAge(user.getAge());
                    u.setName(user.getName());
                    u.setCreatedDate(LocalDateTime.now());
                    return userDao.save(u);
                }
        ).map(u -> new ResponseEntity<User>(u, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));


    }

    @DeleteMapping(value = "/{id}")
    public Mono<ResponseEntity<Void>> DeleteUser(@PathVariable String id) {
        return userDao.findById(id)
                .flatMap(u -> userDao.deleteById(id)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));


    }
}
