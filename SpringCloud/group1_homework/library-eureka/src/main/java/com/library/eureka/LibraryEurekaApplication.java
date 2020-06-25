package com.library.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author Group One
 * @package com.library.eureka
 * @date 2020/6/25
 * @brief 注册中心
 */
@SpringBootApplication
@EnableEurekaServer
public class LibraryEurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(LibraryEurekaApplication.class, args);
    }
}
