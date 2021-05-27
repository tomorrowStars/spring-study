package com.kuang.config;

import com.kuang.pojo.Cat;
import com.kuang.pojo.Dog;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
//@Component
public class MyConfig {

    /**
     * 创建第三方bean
     */
    @Bean
    public Dog dog() {
        return new Dog();
    }
    @Bean
    public Cat cat() {
        return new Cat();
    }

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        Dog dog = context.getBean(Dog.class);
        dog.shout();
    }

}
