package com.kuang.service;

import com.kuang.dao.UserDao;
import com.kuang.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserService {
    @Autowired
    private UserDao userDao;

    public void updateUser() {
        System.out.println("=========更新前====");
        List<User> allUsers = userDao.getAllUsers();
        for (User user : allUsers) {
            System.out.println(user);
        }
        System.out.println("===========更新=========");
        userDao.updateUser(new User(13,"aaa668","bbb111"));
        System.out.println("==========删除============");
        userDao.deleteUserById(12);
        System.out.println("============增加=========");
        userDao.addUser(new User(14,"aaa","bbb"));
        System.out.println("=========更新后====");
        List<User> allUsers2 = userDao.getAllUsers();
        for (User user : allUsers2) {
            System.out.println(user);
        }
    }
}
