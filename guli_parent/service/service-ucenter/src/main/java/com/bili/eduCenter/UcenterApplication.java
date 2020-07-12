package com.bili.eduCenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
//这个就可以扫描到测试模块的com.bili
@ComponentScan(basePackages={"com.bili"})
@EnableDiscoveryClient //nacos注册
@EnableFeignClients  //服务调用者

public class UcenterApplication implements WebMvcConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class,args);
    }
}
