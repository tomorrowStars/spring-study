import com.kuang.service.UserService;
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

//        环绕前
//        签名：void com.kuang.service.UserService.add()
//          方法执行前
//                增加了一个用户
//          方法执行之后
//        环绕后
//        执行结果：null

//        环绕前
//        签名：void com.kuang.service.UserService.delete()
//          方法执行前
//                删除了一个用户
//          方法执行之后
//        环绕后
//        执行结果：null
    }
}
