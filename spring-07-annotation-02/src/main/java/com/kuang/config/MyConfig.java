package com.kuang.config;

import com.kuang.pojo.Cat;
import com.kuang.pojo.Dog;
import com.kuang.pojo.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ComponentScan
public class MyConfig {

    /**
     * 创建第三方bean,与config类不在同一包以及它的子包内的 时候，用这种方法
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



        User user = context.getBean(User.class);
        System.out.println(user.toString());
    }

}
