package com.kuang.service;

public class UserServiceImpl implements UserService {

    public int add() {
        System.out.println("增加了一个用户");
        return 1;
    }

    public int delete() {
        System.out.println("删除了一个用户");
        return 200;

    }

    public void update() {
        System.out.println("更新了一个用户");

    }

    public void search() {
        System.out.println("检索用户");

    }
}
