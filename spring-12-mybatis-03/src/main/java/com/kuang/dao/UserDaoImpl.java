package com.kuang.dao;

import com.kuang.pojo.User;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

public class UserDaoImpl extends SqlSessionDaoSupport implements UserDao{

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = getSqlSession().getMapper(UserDao.class).getAllUsers();
        return allUsers;
    }
}
