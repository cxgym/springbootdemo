package com.wondersgroup.springbootshirodemo.service;

import com.wondersgroup.springbootshirodemo.model.User;

public interface LoginService{
    User getUserByName(String userName);
}
