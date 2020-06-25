package com.library.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author Group One
 * @package com.library.gateway
 * @date 2020/6/25
 * @brief
 */
@SpringBootApplication
@EnableZuulProxy
public class LibraryGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(LibraryGatewayApplication.class, args);
    }
}
