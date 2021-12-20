import com.kuang.dao.UserDao;
import com.kuang.dao.UserDaoImpl;
import com.kuang.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class MyTest_12_03 {
    /**
     * 整合实现二： 可以在xml中省略SqlSessionTemplate的 bean
     *             只需要在dao的实现类impl 继承 SqlSessionDaoSupport类 ，然后直接注入sqlSessionFactory即可
     */
    @Test
    public void test01() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        UserDao userDao = context.getBean("userDao", UserDaoImpl.class);
        List<User> allUsers = userDao.getAllUsers();
        for (User user: allUsers) {
            System.out.println(user);
        }
    }
}
