package com.wingsiwoo.www;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author WingsiWoo
 * @date 2021/11/10
 */
@SpringBootApplication
@MapperScan("com.wingsiwoo.www.dao")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
