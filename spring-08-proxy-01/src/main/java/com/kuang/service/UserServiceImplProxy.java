package com.kuang.service;

// 代理角色，在这里面增加日志的实现
public class UserServiceImplProxy implements UserService {
    // 真实角色
    private UserService userService;

    public UserServiceImplProxy(UserService userService) {
        this.userService = userService;
    }

    // 在原有方法基础上增加日志功能
    public void addUser() {
        log("add");
        userService.addUser();

    }

    public void deleteUser() {
        log("delete");
        userService.deleteUser();

    }

    public void updateUser() {
        log("update");
        userService.updateUser();


    }

    public void searchUser() {
        log("search");
        userService.searchUser();

    }

    public void log(String logMessage) {
        System.out.println("执行了" + logMessage + "方法！");
    }
}
