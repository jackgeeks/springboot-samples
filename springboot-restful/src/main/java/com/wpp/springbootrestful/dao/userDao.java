package com.wpp.springbootrestful.dao;

import com.wpp.springbootrestful.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: jackgeeks
 * @ProjectName: springboot-restful
 * @Package: com.wpp.springbootrestful.repository
 * @ClassName: userRepository
 * @Description: @todo
 * @CreateDate: 2020/5/1 22:29
 * @Version: 1.0
 */
public interface userDao extends JpaRepository<User, Long> {
}
