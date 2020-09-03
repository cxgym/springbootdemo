package com.wondersgroup.springbootjpademo.entity;

import javax.persistence.*;

/**
 * 使用JPA注解配置映射关系
 */
@Entity    //告诉JPA这是一个实体类(和数据表映射的类)
@Table(name = "tbl_user")    //@Table指定和哪个数据表对应，默认是类名小写
public class User {
    @Id   //这是一个主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //自增主键
    private Integer id;

    @Column(name = "last_name",length = 50)   //这是和数据表对应的一个列
    private String lastname;

    @Column   //省略的话默认列名就是属性名
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
