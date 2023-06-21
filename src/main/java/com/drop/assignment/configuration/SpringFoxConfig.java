package com.drop.assignment.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

/**
 * Configuration class for swagger
 * @author Bijaya Bhaskar Swain
 */
@Configuration
@EnableSwagger2
public class SpringFoxConfig {
    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(Arrays.asList(apiKey()))
                .apiInfo(apiInfo()).select().build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Car Parking API")
                .description("Car Parking API reference for developers")
                .contact("Bijaya Bhaskar Swain").version("1.0").build();
    }

    private ApiKey apiKey() {
        return new ApiKey("Bearer", "Authorization", "header");
    }
}
