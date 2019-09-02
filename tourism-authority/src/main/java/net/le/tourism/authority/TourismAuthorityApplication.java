package net.le.tourism.authority;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author hanle
 */
@ComponentScan(basePackages = "net.le.tourism")
@SpringBootApplication
public class TourismAuthorityApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(TourismAuthorityApplication.class, args);
    }
}
