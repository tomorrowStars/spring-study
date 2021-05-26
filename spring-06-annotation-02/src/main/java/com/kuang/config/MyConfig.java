package com.kuang.config;

import com.kuang.pojo.Dog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

    @Bean
    public Dog dog() {
        return new Dog();
    }
}
