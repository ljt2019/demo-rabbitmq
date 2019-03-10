package com.tiger.rabbitmq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *  访问路径：https://ip:port/swagger-ui.html#
 * @author tiger
 * @date 2018年12月5日
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket python() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("-rabbitmq学习-").apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.tiger.rabbitmq.controller")).paths(PathSelectors.any()).build()
                .apiInfo(apiInfo());
    }

    /**
     * 项目构建的基本信息 构建 api文档的详细信息函数,注意这里的注解引用的是哪个
     *
     * @return ：接口基本描述信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 页面标题
                .title("rabbitmq")
                // 描述
                .description("rabbitmq 学习")
                .termsOfServiceUrl("hello！！！")
                // 创建人
                .contact(new Contact("LinJiTai ", "...", "1165069099@qq.com"))
                // 版本号
                .version("1.0").license("The Apache License, Version 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html").build();
    }

}
