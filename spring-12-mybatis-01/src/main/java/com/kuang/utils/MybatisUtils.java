package com.kuang.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MybatisUtils {
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            String str = "mybatis-config.xml";
            InputStream inputStream  = Resources.getResourceAsStream(str);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取sqlSession
     * @param autoCommitFlg
     * @return
     *      autoCommitFlg: true, 返回自动提交的sqlSession、
     *      false：返回不自动提交的sqlsession（需要手动sqlSession.commit()）
     */
    public static SqlSession getSqlsession(boolean autoCommitFlg) {
        return sqlSessionFactory.openSession(autoCommitFlg);
    }
}
