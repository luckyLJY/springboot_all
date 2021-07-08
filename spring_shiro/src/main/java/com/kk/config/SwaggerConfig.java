package com.kk.config;

import com.google.common.collect.ImmutableSet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletionStage;

/**
 * Swagger Config
 *
 * @author kkmystery
 * @version 1.0 2021/7/8
 * @since 1.0.0
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = "swagger2", value = {"enable"}, havingValue = "true")
public class SwaggerConfig implements WebMvcConfigurer {
    private static final String UUID_HEADER_NAME = "uuid";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html", "doc.html")
            .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .directModelSubstitute(LocalDateTime.class, Date.class)
            .directModelSubstitute(LocalDate.class, String.class)
            .directModelSubstitute(LocalTime.class, String.class)
            .directModelSubstitute(ZonedDateTime.class, String.class)
            .directModelSubstitute(Long.class, String.class)
         /*.ignoredParameterTypes(LanguageTypeEnum.class)*/
            .consumes(ImmutableSet.of(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
            .produces(ImmutableSet.of(MediaType.APPLICATION_JSON_VALUE))
            .apiInfo(new ApiInfoBuilder()
                .title("Api Doc")
                .version("1.0.0")
                .description("所有提交采用form表单，所有返回json格式，不转换参数风格，即不做snake_case和camelCase的转换。\n\n所有返回值采用以下格式:\n{\"success\":true,\"errorCode\":0,\"data\":\"\",\"errorMsg\":\"OK\"}")
                .build())
            .useDefaultResponseMessages(false)
            // 解决CompletionStage返回值问题
            .genericModelSubstitutes(CompletionStage.class)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.kk"))
            .paths(PathSelectors.any())
            .build()
            .globalOperationParameters(setHeaderToken())
            .securitySchemes(securitySchemes())
            .securityContexts(securityContexts());
    }

    private List<Parameter> setHeaderToken() {
        // 携带header信息
        ParameterBuilder tokenParam = new ParameterBuilder();
        List<Parameter> parameters = new ArrayList<>();
        Parameter lang = tokenParam.name("Accept-Language")
            .description("lang")
            .modelRef(new ModelRef("String"))
            .parameterType("header")
            .defaultValue("zh-CN")
            .required(false)
            .build();
        parameters.add(lang);
        Parameter ua = tokenParam.name("User-Agent")
            .description("User-Agent")
            .modelRef(new ModelRef("String"))
            .parameterType("header")
            .defaultValue("facemagic/1.2.0+2 android/8.1.0(Redmi 5 Plus)")
            .required(false)
            .build();
        parameters.add(ua);
        return parameters;
    }

    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeys = new ArrayList<>();
        apiKeys.add(new ApiKey("Authorization", "Authorization", "header"));
        apiKeys.add(new ApiKey(UUID_HEADER_NAME, UUID_HEADER_NAME, "header"));
        return apiKeys;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> contexts = new ArrayList<>();
        contexts.add(SecurityContext.builder()
            .securityReferences(securityReferences())
            .forPaths(PathSelectors.regex("^(?!auth).*$"))
            .build());
        return contexts;
    }

    public List<SecurityReference> securityReferences() {
        AuthorizationScope[] scopes = new AuthorizationScope[]{new AuthorizationScope("global", "")};
        SecurityReference auth = new SecurityReference("Authorization", scopes);
        SecurityReference uuid = new SecurityReference(UUID_HEADER_NAME, scopes);
        List<SecurityReference> references = new ArrayList<>();
        references.add(auth);
        references.add(uuid);
        return references;
    }
}
