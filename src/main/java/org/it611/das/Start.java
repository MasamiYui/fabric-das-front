package org.it611.das;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("org.it611.das.mapper")
@EnableTransactionManagement//开启事务
@ServletComponentScan
public class Start {

    public static void main(String[] args) {
        SpringApplication.run(Start.class, args);
    }
}