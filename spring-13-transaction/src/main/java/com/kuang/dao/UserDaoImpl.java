package com.kuang.dao;

import com.kuang.pojo.User;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

public class UserDaoImpl extends SqlSessionDaoSupport implements UserDao{

    /**
     * 查询所有用户
     * @return
     */
    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = getSqlSession().getMapper(UserDao.class).getAllUsers();
        return allUsers;
    }

    @Override
    public int addUser(User user) {
        return getSqlSession().getMapper(UserDao.class).addUser(user);
    }

    @Override
    public int updateUser(User user) {
        return getSqlSession().getMapper(UserDao.class).updateUser(user);
    }

    @Override
    public int deleteUserById(int id) {
        return getSqlSession().getMapper(UserDao.class).deleteUserById(id);
    }


}
