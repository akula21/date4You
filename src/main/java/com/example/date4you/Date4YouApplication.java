package com.example.date4you;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@EnableRabbit
@SpringBootApplication
public class Date4YouApplication {

    public static void main(String[] args) {
        SpringApplication.run(Date4YouApplication.class, args);
    }


}
