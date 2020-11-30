package com.kk.learning.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @author : K k
 * @date : 16:14 2020/6/24
 */
@Configuration
@EnableSwagger2 //开启swagger2
public class SwaggerConfig {
    @Bean
    public Docket docket(Environment environment){
        Profiles profiles=Profiles.of("dev","test");
        boolean b =environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("WebApi")
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.kk.miaosha.controller"))
                //.paths(Predicates.not(PathSelectors.regex("/admin/.*")))
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();
    }

    //配置Swagger信息
    private ApiInfo apiInfo(){
        Contact contact = new Contact("kk","http://kk.com/","1020803064@qq.com");

        return new ApiInfo(
                "秒杀系统的SwaggerAPI文档",
                "Swagger整合springboot",
                "v1.0",
                "http://kk.com/",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }
}
