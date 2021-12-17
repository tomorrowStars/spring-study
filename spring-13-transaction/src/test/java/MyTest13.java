import com.kuang.dao.UserDao;
import com.kuang.dao.UserDaoImpl;
import com.kuang.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class MyTest13 {
    @Test
    public void test01() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        UserDao userDao = context.getBean("userDao", UserDaoImpl.class);
        List<User> allUsers = userDao.getAllUsers();
        System.out.println(allUsers.size());
    }

    @Test
    public void test02() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        UserDao userDao = context.getBean(UserDao.class);
        // 更新前确认
        List<User> allUsers = userDao.getAllUsers();
        allUsers.forEach(user -> System.out.println(user));

        // 增加
        new
        // 删除
        // 修改

    }
}
