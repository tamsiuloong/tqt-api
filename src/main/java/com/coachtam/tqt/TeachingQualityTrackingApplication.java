package com.coachtam.tqt;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class TeachingQualityTrackingApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeachingQualityTrackingApplication.class, args);
    }

}

