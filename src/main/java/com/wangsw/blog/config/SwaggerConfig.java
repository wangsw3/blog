package com.wangsw.blog.config;


import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by wangsw on 2019/12/11.
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private final String version = "1.0";

    private final String title = "blog";

    private final String description = "API接口";


    @Bean
    public Docket buildDocket() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(buildApiInf()).select().apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)).build();
    }

    private ApiInfo buildApiInf() {
        return new ApiInfoBuilder().title(title+version).description(description).version(version).build();
    }

}
