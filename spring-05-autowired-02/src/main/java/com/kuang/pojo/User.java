package com.kuang.pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

public class User {
    @Autowired
    private Cat cat;

    @Qualifier(value = "dog22")
    @Autowired
    private Dog dog;

    // @Autowired(required=false)  说明：false，对象可以为null；true，对象必须存对象，不能为null。
    // xml 中没有定义对应类型的bean
    @Autowired(required = false)
    private Sheep sheep;

    @Value("111")
    private String name;
//
//    public void setCat(Cat cat) {
//        this.cat = cat;
//    }
//
//    public void setDog(Dog dog) {
//        this.dog = dog;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

    public Cat getCat() {
        return cat;
    }

    public Dog getDog() {
        return dog;
    }

    public String getName() {
        return name;
    }
}
