package net.le.tourism.authority.common.configurate;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author hanle
 * @version v1.0
 * @date 2019-05-10
 * @modify
 *
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@Configuration
@MapperScan("net.le.tourism.authority.mapper")
public class MybatisConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
