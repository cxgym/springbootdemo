package com.wondersgroup.springbootjpademo.repository;

import com.wondersgroup.springbootjpademo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * JpaRepository<类名，主键类型>
 * 继承JpaRepository完成对数据库的操作
 */
public interface UserRepositoty extends JpaRepository<User,Integer> {
}
