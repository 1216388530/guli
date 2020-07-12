package com.bili.eduStatistics;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//基础
@SpringBootApplication
//测试
@ComponentScan(basePackages = "com.bili")
//外部服务
@EnableDiscoveryClient
@EnableFeignClients
//数据库
@MapperScan("com.bili.eduStatistics.mapper")
//定时任务
@EnableScheduling
public class StatisticsApplication implements WebMvcConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(StatisticsApplication.class,args);
    }
}
