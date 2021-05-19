package com.kuang.dao;

public class HelloSpring {
    private String name;

    public HelloSpring() {
        System.out.println("无参构造方法！");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void sayHello() {
        System.out.println("hello " + name + "!");
    }
    @Override
    public String toString() {
        return "Hello{" +
                "name='" + name + '\'' +
                '}';
    }
}
