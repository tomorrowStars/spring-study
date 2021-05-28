import com.kuang.service.Log;
import com.kuang.service.UserService;
import com.kuang.service.UserServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    @Test
    public void test001() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        UserService userService = context.getBean("userServiceImpl", UserService.class);
        userService.add();
        userService.delete();

//        ---------方法执行前---------
//            增加了一个用户
//        ---------方法执行后---------
//        ---------方法执行后,返回值：null---------
//
//        ---------方法执行前---------
//            删除了一个用户
//        ---------方法执行后---------
//        ---------方法执行后,返回值：null---------

    }
}
