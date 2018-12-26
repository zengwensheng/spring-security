package com.zws.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/12/3
 */
@EnableSwagger2
@Configuration
public class SwaggerConfigurer {

    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2).
                useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zws.demo.web.controller"))
                .paths(PathSelectors.regex("^(?!auth).*$"))
                .build()
                .apiInfo(productApiInfo())
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                ;

    }

    private List<ApiKey> securitySchemes() {
        return new ArrayList<ApiKey>(){
            {
                new ApiKey("Authorization", "Authorization", "header");
            }
        };
    }

    private List<SecurityContext> securityContexts() {
        return new ArrayList<SecurityContext>(){
            {
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build();
            }
        };

    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return new ArrayList<SecurityReference>(){
            {
                new SecurityReference("Authorization", authorizationScopes);
            }
        };

    }
    private ApiInfo productApiInfo() {
        return new ApiInfoBuilder()
                .title("zws APIs")
                .description("")
                .termsOfServiceUrl("")
                .contact(new Contact("zws", "", "2848392861@qq.com"))
                .version("1.0")
                .build();
    }
}
