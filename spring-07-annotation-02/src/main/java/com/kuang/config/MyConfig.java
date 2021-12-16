package com.kuang.config;

import com.kuang.pojo.Cat;
import com.kuang.pojo.Dog;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
// 本身就是一个cmponent组件， 只是一个配置类， 相当于xml的配置文件
@Configuration
public class MyConfig {

    /**
     * 创建第三方bean,与config类不在同一包以及它的子包内的 时候，用这种方法
     * 类似于在xml中注册bean， 方法名是 beanId， 方法返回值是Bean的 class类型
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
