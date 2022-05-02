package com.example.loginservice.utils;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Lang
 * @Desc
 * @data 2022/5/2 13:00
 **/
@Component
public class TimeService {

    // 定时执行，参数：cron表达式
    @Scheduled(cron = "0/2 * * * * ?")
    public void count() {

        System.out.println("执行了: 1 次");
    }
}
