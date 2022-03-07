import com.kuang.dao.UserMapper;
import com.kuang.pojo.User;
import com.kuang.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class MyTest12_01 {

    /**
     * 复习mybatis
     */
    @Test
    public void test01() {
        SqlSession sqlsession = MybatisUtils.getSqlsession(true);
        UserMapper mapper = sqlsession.getMapper(UserMapper.class);
        List<User> users = mapper.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
        sqlsession.close();
    }
}
