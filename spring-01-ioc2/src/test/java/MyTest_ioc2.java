import com.kuang.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest_ioc2 {
    @Test
    public void test001() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
//        UserSevice userService = context.getBean(UserServiceImpl.class);
        UserService userService = (UserService) context.getBean("userService");
        userService.getUser();
    }
}
