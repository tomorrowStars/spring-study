import com.kuang.pojo.User;
import com.kuang.pojo.User2;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest1 {

    /**
     * java中 @autowired + xml 自动注入
     * @Qualifier 别名的用法
     */
    @Test
    public void testAutowired_01() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        User user = context.getBean("user", User.class);
        user.getCat().shout();
        user.getDog().shout();
        System.out.println(user.getName());
    }

    /**
     * java中 @resource + xml 自动注入
     *
     */
    @Test
    public void testAutowired_02() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        User2 user2 = context.getBean("user2", User2.class);
        user2.getCat().shout();
        user2.getDog().shout();
//        user2.getSheep().shout();
        System.out.println(user2.getName());
    }

    // @Autowired先byType，@Resource先byName。
}
