package com.library.order.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Group One
 * @package com.library.order.controller
 * @date 2020/6/25
 * @brief
 */
@RestController
public class HelloWorldController {
    @GetMapping("/hello")
    public String hello() {
        return "hello world!";
    }
}
