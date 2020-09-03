package com.wondersgroup.springboottaskdemo.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledService {
    /**
     * 快速生成方法注解：在方法上输入/** + 回车
     * second, minute, hour, day of month, month, day of week(周几)
     * 0 * * * * MON-FRI      //代表周一到周五每分钟执行一次，0代表秒数是00，所以就是每分钟
     * cron表达式具体使用详解自行百度
     */
    @Scheduled(cron = "0 * * * * MON-FRI")
    public void hello()
    {
        System.out.println("hello...");
    }
}
