package com.library.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author Group One
 * @package com.library.config
 * @date 2020/6/25
 * @brief
 */
@SpringBootApplication
@EnableConfigServer
public class LibraryConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(LibraryConfigApplication.class, args);
    }
}
