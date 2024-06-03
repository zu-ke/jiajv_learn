package com.zuke.zukeliving.commodity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//如果dao没有加上@Mapper注解的话记得加上下面注解
//@MapperScan("com.zuke.zukeliving.commodity.dao")
@SpringBootApplication
//启用nacos注册发现
@EnableDiscoveryClient
public class LivingCommodityApplicaiton {
    public static void main(String[] args) {
        SpringApplication.run(LivingCommodityApplicaiton.class,args);
    }
}
