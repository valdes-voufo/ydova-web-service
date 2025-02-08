package com.ydova;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@AllArgsConstructor
@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition(
        info = @Info(title = "User API", version = "1.0", description = "User Authentication APIs")
)
public class YdovaApplication {
    public static void main(String[] args) {
        SpringApplication.run(YdovaApplication.class, args);
    }

}