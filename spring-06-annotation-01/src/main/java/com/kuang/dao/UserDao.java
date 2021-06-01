package com.kuang.dao;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope(value = "prototype")
public class UserDao {
    public void addUser() {
        System.out.println("add a user!");
    }
}
