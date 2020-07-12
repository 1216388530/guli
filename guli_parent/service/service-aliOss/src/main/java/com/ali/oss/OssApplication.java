package com.ali.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 因为此项目不需要进行数据库操作，只要上传到阿里云
 */
//在@SpringBootApplication注解上加上exclude，解除自动加载DataSourceAutoConfiguration
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
//这个就可以扫描到测试模块的com.bili
@ComponentScan(basePackages={"com.bili","com.ali"})
@EnableDiscoveryClient
public class OssApplication implements WebMvcConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class,args);
    }
}
