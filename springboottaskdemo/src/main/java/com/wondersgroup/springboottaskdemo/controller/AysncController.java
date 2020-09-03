package com.wondersgroup.springboottaskdemo.controller;

import com.wondersgroup.springboottaskdemo.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AysncController {

    @Autowired
    AsyncService asyncService;

    @GetMapping("/hello")
    public String hello()
    {
        asyncService.hello();
        return "success";
    }
}
