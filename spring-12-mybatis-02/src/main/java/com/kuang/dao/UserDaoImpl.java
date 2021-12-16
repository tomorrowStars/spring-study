package com.kuang.dao;

import com.kuang.pojo.User;
import lombok.Data;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

@Data
public class UserDaoImpl implements UserDao {
    @Autowired
//    @Qualifier("sqlSession")
    private SqlSession sqlSession;
    @Override
    public List<User> getAllUsers() {
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        List<User> allUsers = mapper.getAllUsers();
        return allUsers;
    }
}
