import com.kuang.dao.UserDao;
import com.kuang.dao.UserDaoImpl;
import com.kuang.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class MyTest12_02 {

    /**
     * 整合实现一: 在dao的实现类impl中 ，注入spring的 sqlSessionTemple
     */
    @Test
    public void test01() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
//        UserDao userDao = context.getBean("userDao",UserDaoImpl.class);
        UserDao userDao = context.getBean("userDao",UserDao.class);
        List<User> allUsers = userDao.getAllUsers();
        for (User user : allUsers) {
            System.out.println(user);

        }
    }
}
