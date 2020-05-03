package com.wpp.springbootrestful.service;


import com.wpp.springbootrestful.dao.userDao;
import com.wpp.springbootrestful.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @Author: jackgeeks
 * @ProjectName: springboot-restful
 * @Package: com.wpp.springbootrestful.service
 * @ClassName: UserService
 * @Description: @todo
 * @CreateDate: 2020/5/1 22:04
 * @Version: 1.0
 */

@Service
public class UserService {
    @Autowired
    private userDao userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
    public User findById(Long id) {
       return  userRepository.findById(id).get();
    }
}



