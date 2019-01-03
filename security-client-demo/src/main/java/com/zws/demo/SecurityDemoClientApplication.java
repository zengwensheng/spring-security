package com.zws.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/11/1
 */
@SpringBootApplication
@RestController
public class SecurityDemoClientApplication {

    @GetMapping("/user")
    public Authentication user(Authentication user) {
        return user;
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    public static void main(String[] args) {
        SpringApplication.run(SecurityDemoClientApplication.class,args);
    }

}
