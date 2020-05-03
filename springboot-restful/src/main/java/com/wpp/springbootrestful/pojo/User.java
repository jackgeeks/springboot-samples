package com.wpp.springbootrestful.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @Author: jackgeeks
 * @ProjectName: springboot-restful
 * @Package: com.wpp.springbootrestful.pojo
 * @ClassName: User
 * @Description: @todo
 * @CreateDate: 2020/5/1 22:18
 * @Version: 1.0
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private  String name;
    private  Integer age;

}
