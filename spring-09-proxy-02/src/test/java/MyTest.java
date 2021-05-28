import com.kuang.service.ProxyInvocationHandler2;
import com.kuang.pojo.Animal;
import com.kuang.pojo.Cat;
import com.kuang.pojo.InterFaceInvocationHandler;
import com.kuang.service.*;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyTest {

    @Before
    public void before() {
        System.out.println("====test before===");
    }


    @Test
    public void test001() throws InvocationTargetException, IllegalAccessException {

        try {
            // 要调用的Cat中的方法名变量
            String methodName = "shout";

            // method：methodName生成对应的Method函数对象
            Method method = Cat.class.getDeclaredMethod(methodName);

            // 调用目标对象cat中对应的方法，null：参数可以为空
            Cat cat = new Cat();
            Object rtn = method.invoke(cat, null);
            System.out.println("rtn:" + rtn);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    /**
     * 动态代理1
     */
    @Test
    public void test001_2() {

        Cat cat = new Cat();
        Object o = Proxy.newProxyInstance(Cat.class.getClassLoader(), cat.getClass().getInterfaces(), new InterFaceInvocationHandler(cat));

        // 此处getProxy()必须强转成接口，如用实例类型会报错
        Animal catProxy = (Animal) o;

        catProxy.shout();
        catProxy.run();

    }

    /**
     * 动态代理2
     */
    @Test
    public void test002() {
        UserServiceImpl impl = new UserServiceImpl();
        ProxyInvocationHandler proxy = new ProxyInvocationHandler();
        proxy.setUserService(impl);

        // 此处getProxy()必须强转成接口，如用实例类型会报错
        UserService userService = (UserService) proxy.getProxy();
        userService.addUser();
    }

    /**
     * 通用的动态代理实现的类！所有的代理对象设置为Object即可
     */
    @Test
    public void test003() {
        Cat cat = new Cat();

        ProxyInvocationHandler2 handler2 = new ProxyInvocationHandler2();
        handler2.setTarget(cat);

        // 此处getProxy()必须强转成接口，如用实例类型会报错
        Animal proxyCat = (Animal) handler2.getProxy();
        proxyCat.shout();

    }

}
