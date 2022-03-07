package com.kuang.pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
@Component
public class User {
    @Autowired
//    @Qualifier("cat1") // 有别名则用别名匹配，没有别名则用对象类型匹配
    private Cat cat;

    @Autowired(required = false)
    private Dog dog;

    // @Autowired(required=false)  说明：false，对象可以为null；true，对象必须存对象，不能为null。
    // xml 中没有定义对应类型的bean
    @Autowired(required = false)
    private Sheep sheep;

    @Value("111")
    private String name;

    @Override
    public String toString() {
        return "User{" +
                "cat=" + cat +
                ", dog=" + dog +
                ", sheep=" + sheep +
                ", name='" + name + '\'' +
                '}';
    }


}
