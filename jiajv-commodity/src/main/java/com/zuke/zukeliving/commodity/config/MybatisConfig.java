package com.zuke.zukeliving.commodity.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement //开启事务
@MapperScan("com.zuke.zukeliving.commodity.dao")
public class MybatisConfig {

    //引入分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        //做基本设置

        //溢出总页数，设置成第一页
        paginationInterceptor.setOverflow(true);
        //单页限制 500 条，小于 0 如 -1 不受限制
        paginationInterceptor.setLimit(100);
        return paginationInterceptor;
    }
}
