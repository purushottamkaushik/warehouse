package com.warehouse.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createDocketApi(){
        return new Docket(DocumentationType.SWAGGER_2)  // Use Swagger UI
                .select() //  ApiSelectorBuilder
                .apis(RequestHandlerSelectors.basePackage("com.warehouse.controller.rest"))  // Select Rest Controllers Base package
                .paths(PathSelectors.regex("/rest.*")) // Path starting from /rest will be considered
                .build()
                .apiInfo(apiInfo());  // Api Info is Optional
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "WareHouse Application ",
                "Warehouse Application For Seagate",
                "v1.2",
                "http://google.com",
                "purushottamkaushik96@gmail.com",
                "MIT Licence",
                "http://google.com"
        );
    }

}
