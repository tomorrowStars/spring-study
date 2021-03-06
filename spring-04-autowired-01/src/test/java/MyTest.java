import com.kuang.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {

    /**
     * xml中autowire 【byName】、 【byType】自动注入
     */
    @Test
    public void testAutowired_01() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        User user = context.getBean("user3", User.class);
        user.getCat().shout();
        user.getDog().shout();
    }
}
