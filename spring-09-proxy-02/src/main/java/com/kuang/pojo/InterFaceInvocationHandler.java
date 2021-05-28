package com.kuang.pojo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class InterFaceInvocationHandler implements InvocationHandler {
    private Cat cat;

    public InterFaceInvocationHandler(Cat cat) {
        this.cat = cat;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("invoke before");
        method.invoke(this.cat, args);
        System.out.println("invoke after");
        return null;
    }
}
