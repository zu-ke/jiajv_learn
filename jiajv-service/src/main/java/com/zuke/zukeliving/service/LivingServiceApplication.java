package com.zuke.zukeliving.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//启用nacos服务注册发现
@EnableDiscoveryClient
public class LivingServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(LivingServiceApplication.class,args);
    }
}
