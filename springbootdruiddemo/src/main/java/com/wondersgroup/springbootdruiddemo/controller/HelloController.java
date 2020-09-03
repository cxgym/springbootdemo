package com.wondersgroup.springbootdruiddemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class HelloController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    //执行http://localhost:8080/query
    //即可在这个页面http://localhost:8080/druid/sql.html看到sql监控信息
    @GetMapping("/query")
    public Map<String,Object> map()
    {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from product_category");
        return maps.get(0);
    }
}
