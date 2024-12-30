package com.ydova;


import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;





@AllArgsConstructor
@SpringBootApplication
public class YdovaApplication {
    public static void main(String[] args) {
        SpringApplication.run(YdovaApplication.class, args);
    }

}