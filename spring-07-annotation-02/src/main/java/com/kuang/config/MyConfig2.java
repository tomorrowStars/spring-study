package com.kuang.config;

import com.kuang.pojo.Cat;
import com.kuang.pojo.Dog;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
//@ComponentScan
@Import(MyConfig.class)  // 导入合并其他配置类，类似于配置文件中的 inculde 标签
public class MyConfig2 {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        Cat cat = context.getBean(Cat.class);
        cat.run();
    }
}
