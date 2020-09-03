package com.wondersgroup.springbootsecuritydemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class KungfuController {
    private final String PREFIX="pages/";

    @GetMapping("/")
    public String index()
    {
        return "welcome";
    }

    @GetMapping("/userlogin")
    public String loginPage()
    {
        return PREFIX+"login";
    }

    @GetMapping("/level1/{path}")
    public String level1(@PathVariable("path")String path)
    {
        return PREFIX+"level1/"+path;
    }

    @GetMapping("/level2/{path}")
    public String level2(@PathVariable("path")String path)
    {
        return PREFIX+"level2/"+path;
    }

    @GetMapping("/level3/{path}")
    public String level3(@PathVariable("path")String path)
    {
        return PREFIX+"level3/"+path;
    }
}
