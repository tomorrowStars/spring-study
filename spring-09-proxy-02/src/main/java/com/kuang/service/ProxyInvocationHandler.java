package com.kuang.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyInvocationHandler implements InvocationHandler {
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    //生成代理类，重点是第二个参数，获取要代理的抽象角色！之前都是一个角色，现在可以代理一类角色


    public Object getProxy() {
        //参数
        //ClassLoader: 用于定义代理类的类加载器
        //Interfaces: 要实现的代理类的接口列表
        //h:  调度方法调用的调用处理程序
        Object instance = Proxy.newProxyInstance(
                this.getClass().getClassLoader(), this.userService.getClass().getInterfaces(), this);

        // 结果
        // 具有指定的代理类调用处理程序的代理实例，该代理类由指定的类加载器定义并实现指定的接口
        return instance;
//        return Proxy.newProxyInstance(this.getClass().getClassLoader(), userService.getClass().getInterfaces(), this);
    }

    //参数
    // proxy: 代理类 调用该方法的代理实例
    // method: 代理类的调用处理程序的方法对象， 所述方法对应于调用代理实例上的接口方法的实例。
    //          方法对象的声明类将是该方法声明的接口，它可以是代理类继承该方法的代理接口的超级接口。
    //args -包含的方法调用传递代理实例的参数值的对象的阵列，或null如果接口方法没有参数。原始类型的参数包含在适当的原始包装器类的实例中，例如java.lang.Integer或java.lang.Boolean 。

    // 处理代理实例上的方法调用并返回结果
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 参数
        // userService: - 从中调用基础方法的对象
        // args - 用于方法调用的参数
        System.out.println("123");
        Object result = method.invoke(userService, args);
        return result;
    }
}
