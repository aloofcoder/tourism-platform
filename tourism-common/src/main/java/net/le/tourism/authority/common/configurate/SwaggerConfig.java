package net.le.tourism.authority.common.configurate;

import io.swagger.annotations.ApiOperation;
import net.le.tourism.authority.common.constant.Constants;
import net.le.tourism.authority.common.constant.properties.SwaggerProperties;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Arrays;
import java.util.List;

/**
 * @author hanle
 * @version v1.0
 * @date 2019/6/29
 * @modify 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Autowired
    private SwaggerProperties swaggerProperties;

    @Bean
    public Docket docketRegister() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("API接口")
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                // 开启/关闭swagger
                .enable(swaggerProperties.isEnable())
                .securitySchemes(Arrays.asList(apiKey()))
                .securityContexts(Arrays.asList(securityContexts()));
    }

    private ApiKey apiKey() {
        return new ApiKey(Constants.AUTHORITY_KEY, Constants.AUTHORITY_KEY, "header");
    }

    private SecurityContext securityContexts() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                //.forPaths(PathSelectors.any())
                .forPaths(PathSelectors.regex("^(?!login).*$"))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> referenceList = new ArrayList(1);
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = new AuthorizationScope("global", "accessEverything");
        referenceList.add(new SecurityReference(Constants.AUTHORITY_KEY, authorizationScopes));
        return referenceList;
    }

    /**
     * 构建 api文档的详细信息函数
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title(swaggerProperties.getTitle())
                //创建人
                .contact(new Contact(swaggerProperties.getContact().getName(), swaggerProperties.getContact().getUrl(),
                        swaggerProperties.getContact().getEmail()))
                //版本号
                .version("1.0.0")
                //描述
                .description(swaggerProperties.getDescription())
                .build();

    }
}
