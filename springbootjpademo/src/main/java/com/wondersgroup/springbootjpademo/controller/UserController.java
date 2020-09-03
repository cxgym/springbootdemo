package com.wondersgroup.springbootjpademo.controller;

import com.wondersgroup.springbootjpademo.entity.User;
import com.wondersgroup.springbootjpademo.repository.UserRepositoty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserRepositoty userRepositoty;

    /**
     * 测试地址：http://localhost:8080/user/1
     * @param id
     * @return
     */
    @GetMapping("/user/{id}")
    public Optional<User> getUser(@PathVariable("id") Integer id)
    {
        Optional<User> user = userRepositoty.findById(id);   //java8新特性解决对象得空指针问题
        return user;
    }

    /**
     * 测试地址：http://localhost:8080/insertuser?lastname=陈新&email=2297227301@qq.com
     * @param user
     * @return
     */
    @GetMapping("/insertuser")
    public User insertUser(User user)
    {
        User save = userRepositoty.save(user);
        return save;
    }
}
