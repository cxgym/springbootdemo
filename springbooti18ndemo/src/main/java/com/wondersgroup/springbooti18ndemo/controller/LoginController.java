package com.wondersgroup.springbooti18ndemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String loginPage()
    {
        return "login";
    }
}
