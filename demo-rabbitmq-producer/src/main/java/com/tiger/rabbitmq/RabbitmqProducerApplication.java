package com.tiger.rabbitmq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = "com.tiger.rabbitmq")
@MapperScan(annotationClass = Repository.class, basePackages = { "com.tiger.rabbitmq"})
@EnableTransactionManagement
@EnableAsync
@ServletComponentScan
public class RabbitmqProducerApplication/* extends SpringBootServletInitializer*/ {

    /*@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ExePythonApplication.class);
    }*/

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqProducerApplication.class, args);


    }
}
