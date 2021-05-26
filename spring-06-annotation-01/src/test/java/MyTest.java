import com.kuang.dao.UserDao;
import com.kuang.pojo.Animal;
import com.kuang.pojo.Cat;
import com.kuang.pojo.Rabbit;
import com.kuang.pojo.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 使用注解开发（一）
 */
public class MyTest {
    @Before
    public void beforeTest() {
        System.out.println("=========");
    }
    /**
     *  @Component 在指定包下编写类，增加注解
     *  @value("值") 属性注入
     */
    @Test
    public void test001(){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Animal rabbit = context.getBean("rabbit", Rabbit.class);
        rabbit.run();
    }

    /**
     *  @Component（"cat1"） 注解别名
     */
    @Test
    public void test002(){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Animal cat = context.getBean("cat1", Cat.class);
        cat.run();
    }

    /**
     * @Component
     */
    @Test
    public void test003(){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        User user = context.getBean("user", User.class);
        System.out.println(user.toString());
    }

    /**
     * 衍生注解 【@Controller：web层 】【@Service层】【 @Repository：dao层】
     */
    @Test
    public void test004(){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        UserDao userDao = context.getBean("userDao", UserDao.class);
        UserDao userDao1 = context.getBean("userDao", UserDao.class);
        System.out.println(userDao == userDao1);
    }
}
