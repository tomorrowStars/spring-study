package com.kuang.service;

/**
 * 我们在不改变原来的代码的情况下，实现了对原有功能的增强，这是AOP中最核心的思想
 */

public class TestUserService {

    public static void main(String[] args) {
        //真实业务
        UserServiceImpl service = new UserServiceImpl();
        //代理类
        UserServiceImplProxy userServiceImplProxy = new UserServiceImplProxy(service);

        //使用代理类实现日志功能！
        userServiceImplProxy.addUser();
        userServiceImplProxy.deleteUser();
        userServiceImplProxy.updateUser();
        userServiceImplProxy.searchUser();
//        执行了add方法！
//        增加了一个用户
//        执行了delete方法！
//        删除了一个用户
//        执行了update方法！
//        更新了一个用户
//        执行了search方法！
//        检索用户
    }
}
