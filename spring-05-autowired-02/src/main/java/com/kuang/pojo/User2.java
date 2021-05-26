package com.kuang.pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;

public class User2 {

    // 1,默认的byName方式进行装配；
    // 2,不成功，则按byType的方式自动装配
    // 3,@Resource如有指定的name属性，先按该属性进行byName方式查找装配；
    @Resource
    private Rabbit rabbit;

    @Resource
    private Cat cat;


    @Resource(name = "dog11")
    private Dog dog;

    // @Autowired(required=false)  说明：false，对象可以为null；true，对象必须存对象，不能为null。
    // xml 中没有定义对应类型的bean
    private Sheep sheep;

    @Value("222")
    private String name;

    public Cat getCat() {
        return cat;
    }

    public Dog getDog() {
        return dog;
    }

    public String getName() {
        return name;
    }

    public Rabbit getRabbit() {
        return rabbit;
    }

    public Sheep getSheep() {
        return sheep;
    }
}
