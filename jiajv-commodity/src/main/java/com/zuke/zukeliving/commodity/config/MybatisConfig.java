package com.zuke.zukeliving.commodity.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement //��������
@MapperScan("com.zuke.zukeliving.commodity.dao")
public class MybatisConfig {

    //�����ҳ���
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        //����������

        //�����ҳ�������óɵ�һҳ
        paginationInterceptor.setOverflow(true);
        //��ҳ���� 500 ����С�� 0 �� -1 ��������
        paginationInterceptor.setLimit(100);
        return paginationInterceptor;
    }
}
