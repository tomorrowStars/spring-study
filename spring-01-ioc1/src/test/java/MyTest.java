import com.kuang.dao.HelloSpring;
import com.kuang.dao.UserDaoImpl;
import com.kuang.dao.UserDaoOrcaleImpl;
import com.kuang.service.UserServiceImpl;
import com.kuang.service.UserSevice;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    @Test
    public void testIoc001() {
        UserSevice userService = new UserServiceImpl(new UserDaoImpl());
        userService.getUser();
    }

    @Test
    public void testIoc002() {
        UserSevice userService = new UserServiceImpl(new UserDaoOrcaleImpl());
        userService.getUser();
    }

    // 无参数构造方法
    @Test
    public void testIoc003() {
        HelloSpring hello = new HelloSpring();
        hello.setName("小明");
        hello.sayHello();
    }

    // 无参数构造方法
    @Test
    public void testIoc004() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        // ClassPathXmlApplicationContext调用时，bean就会被创建
        //
        HelloSpring hello = context.getBean(HelloSpring.class);
        hello.sayHello();


    }
}







