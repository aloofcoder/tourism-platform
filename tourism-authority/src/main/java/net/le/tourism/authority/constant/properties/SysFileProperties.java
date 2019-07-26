package net.le.tourism.authority.constant.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author hanle
 * @version v1.0
 * @date 2019-06-14
 * @modify
 *
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */

@Data
@Component
@ConfigurationProperties(prefix = "system.file")
public class SysFileProperties {

    private String uploadPath;

    private String domain;
}
