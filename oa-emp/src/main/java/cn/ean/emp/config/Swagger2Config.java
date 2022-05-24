package cn.ean.emp.config;

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
 * @author ean
 * @FileName Swagger2Config
 * @Date 2022/5/24 10:04
 **/
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.ean.emp.controller"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemes());
    }

    /**
     * 设置请求头信息
     * @return
     */
    //   private List<? extends SecurityScheme> securitySchemes() {
    private List<ApiKey> securitySchemes() {
        List<ApiKey> result = new ArrayList<>();

        ApiKey apiKey = new ApiKey("Authorization", "Authorization", "Header");

        result.add(apiKey);

        return result;

    }


    /**
     * 设置需要登录认证的路径
     * @return
     */
    private List<SecurityContext> securityContexts() {
        List<SecurityContext> result = new ArrayList<>();

        result.add(getContextByPath("/hello/.*"));
        return result;
    }



    private SecurityContext getContextByPath(String pathRegex) {
        return SecurityContext.builder()
                // 默认授权
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(pathRegex))
                .build();
    }


    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> references = new ArrayList<>();
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] auths = new AuthorizationScope[1];
        auths[0] = authorizationScope;
        references.add(new SecurityReference("Authorization", auths));
        return references;
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("api接口文档")
                .description("接口文档")
                .contact(new Contact("ean", "http:localhost:8081/doc.html", "xxx@xxx.com"))
                .version("1.0")
                .build();
    }
}
