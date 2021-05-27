package com.kuang.service;

//真实对象，完成增删改查操作的人
public class UserServiceImpl implements UserService {
    public void addUser() {
        System.out.println("增加了一个用户");
    }

    public void deleteUser() {
        System.out.println("删除了一个用户");

    }

    public void updateUser() {
        System.out.println("更新了一个用户");

    }

    public void searchUser() {
        System.out.println("检索用户");

    }
}
