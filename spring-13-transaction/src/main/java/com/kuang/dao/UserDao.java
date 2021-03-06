package com.kuang.dao;

import com.kuang.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    /**
     * 查询所有用户
     * @return
     */
    List<User> getAllUsers();

    /**
     * 增加一个用户
     * @param user
     * @return
     */
    int addUser(User user);

    /**
     * 更新用户
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 删除用户
     * @param id
     * @return
     */
    int deleteUserById( int id);

    /**
     * 删除用户
     * @param id
     * @return
     */
    int deleteUserByIdErr( int id);

    /**
     * 更新并返回 user一览数据
     * @return
     */
    List<User> updateAndGetList();
}
