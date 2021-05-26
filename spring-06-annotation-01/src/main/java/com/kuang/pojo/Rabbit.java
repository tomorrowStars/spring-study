package com.kuang.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component //等价于 <bean id="rabbit" class="com.yuan.pojo.Rabbit"/>
public class Rabbit extends Animal {
    @Value("rabbit")
    private String name;

    private String age;

    @Value("2")
    public void setAge(String age) {
        this.age = age;
    }
    @Override
    public void run() {
        System.out.println(name + " run!");
        System.out.println("age:" + age);
    }


}
