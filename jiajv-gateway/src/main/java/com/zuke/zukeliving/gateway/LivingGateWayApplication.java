package com.zuke.zukeliving.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LivingGateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(LivingGateWayApplication.class, args);
    }
}
