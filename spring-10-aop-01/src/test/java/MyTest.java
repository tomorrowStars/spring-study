import com.kuang.service.Log;
import com.kuang.service.UserService;
import com.kuang.service.UserServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    /**
     * 依赖包 dependency :aspectjweaver
     */

    /**
     *   <!-- 【第一种方式】通过 Spring API && xml 实现 -->
     */
    @Test
    public void test001() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        UserService userService = context.getBean("userServiceImpl", UserService.class);
        userService.add();
        System.out.println("============");
        userService.delete();

//        com.kuang.service.UserServiceImpl的方法：add将要执行
//                增加了一个用户
//        执行了com.kuang.service.UserServiceImpl的方法：add；返回值null
//                ============
//        com.kuang.service.UserServiceImpl的方法：delete将要执行
//                删除了一个用户
//        执行了com.kuang.service.UserServiceImpl的方法：delete；返回值200
    }

    /**
     *  <!-- 【第二种方式】自定义类【DiyPointCut】 以及 xml 来实现Aop-->
     */
    @Test
    public void test002() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans2.xml");
        UserService userService = context.getBean("userServiceImpl", UserService.class);
        userService.add();
        System.out.println("=============");
        userService.delete();

//        ---------方法执行前---------
//                增加了一个用户
//        ---------方法执行后---------
//        ---------方法执行后,返回值：null---------
//                =============
//        ---------方法执行前---------
//                删除了一个用户
//        ---------方法执行后---------
//        ---------方法执行后,返回值：200---------
    }




}
