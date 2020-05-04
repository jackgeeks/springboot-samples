package com.wpp.springbootwebflux.dao;

import com.wpp.springbootwebflux.pojo.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * @Author: jackgeeks
 * @ProjectName: springboot-webflux
 * @Package: com.wpp.springbootwebflux.Dao
 * @ClassName: UserDao
 * @Description: @todo
 * @CreateDate: 2020/5/3 22:22
 * @Version: 1.0
 */
public interface UserDao extends ReactiveMongoRepository<User,String> {

}
