package com.wpp.springbootrestful.controller;

import com.wpp.springbootrestful.pojo.User;
import com.wpp.springbootrestful.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: jackgeeks
 * @ProjectName: springboot-restful
 * @Package: com.wpp.springbootrestful.controller
 * @ClassName: webController
 * @Description: @todo
 * @CreateDate: 2020/5/1 22:04
 * @Version: 1.0
 */
@RestController
@RequestMapping("/user")
@Api
public class webController {
    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        List<User> all = userService.findAll();
        return ResponseEntity.ok(all);
    }
    @PostMapping
    public ResponseEntity<String> save( @RequestBody User user){
        User save = userService.save(user);
        if (ObjectUtils.isEmpty(save)){
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok("添加成功");
    }
    @PutMapping
    public ResponseEntity<String> updet(@RequestBody User user){
        User update = userService.save(user);
        if (ObjectUtils.isEmpty(update)){
            return  new ResponseEntity<>("修改失败",HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok("修改成功");
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> Delete(@PathVariable Long id){
        User user = userService.findById(id);
        if (!ObjectUtils.isEmpty(user)){
            return  new ResponseEntity<>("删除成功",HttpStatus.BAD_REQUEST);
        }
        userService.deleteById(id);
        return ResponseEntity.ok("删除成功");
    }
}
