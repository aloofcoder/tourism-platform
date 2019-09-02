package net.le.tourism.mp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "net.le.tourism")
@SpringBootApplication
public class TourismMpApplication {

    public static void main(String[] args) {
        SpringApplication.run(TourismMpApplication.class, args);
    }

}
