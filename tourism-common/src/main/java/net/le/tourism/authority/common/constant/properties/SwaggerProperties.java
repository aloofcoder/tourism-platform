package net.le.tourism.authority.common.constant.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wenxiaolong
 * @version v1.0
 * @date 2018-11-19
 * @modify
 *
 */
@Data
@Component
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {

    private boolean enable;
    private String scanBasePackage;
    private String title;
    private String description;
    private Contact contact = new Contact();

    @Data
    public static class Contact {
        private String name;
        private String url;
        private String email;
    }
}
