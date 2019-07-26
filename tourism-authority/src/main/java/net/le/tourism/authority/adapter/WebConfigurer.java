package net.le.tourism.authority.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * @author hanle
 * @version v1.0
 * @date 2019-04-09
 * @modify
 *
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Autowired
    private AuthorityInterceptorAdapter authorityInterceptorAdapter;

    /**
     * 配置接口授权验证拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 配置不需要拦截的url
        String str = "/;/error;/swagger-ui.html;/webjars/**;/v2/api-docs;/swagger-resources/**;/csrf;/actuator/health";
        List<String> patterns = Arrays.asList(str.split(";"));
        registry.addInterceptor(authorityInterceptorAdapter).addPathPatterns("/**")
                .excludePathPatterns(patterns);
    }
}
