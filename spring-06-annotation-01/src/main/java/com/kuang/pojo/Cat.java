package com.kuang.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component("cat1")
public class Cat extends Animal {
    private String name = "little cat";
//    @Nullable
    private String age;

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public void run() {
        System.out.println(name + " run~");
        System.out.println("age:" + age);
    }


}
