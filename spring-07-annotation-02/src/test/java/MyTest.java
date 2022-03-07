import com.kuang.AppConfig;
import com.kuang.config.MyConfig;
import com.kuang.config.MyConfig2;
import com.kuang.config.MyConfig3;
import com.kuang.pojo.Cat;
import com.kuang.pojo.Dog;
import com.kuang.pojo.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MyTest {
    @Before
    public void before() {
        System.out.println("===============before=============");
    }

    /**
     * @Configuration
     * @ComponentScan
     * 自动搜索当前类所在的包以及子包，把所有标注为@Component的Bean自动创建出来，并根据@Autowired进行装配。
     */
    @Test
    public void test01() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Dog dog = context.getBean("dog", Dog.class);
        Cat cat = context.getBean(Cat.class);
        cat.run();
        dog.shout();

        User user = context.getBean(User.class);
        System.out.println(user.toString());

    }

    /**
     * @Configuration
     * 创建第三方bean
     */
    @Test
    public void test02() {
        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        Dog dog = context.getBean("dog", Dog.class);
        Cat cat = context.getBean(Cat.class);
        cat.run();
        dog.shout();

        User user = context.getBean(User.class);
        System.out.println(user.toString());
    }

    /**
     * @Configuration
     * @Import(AppConfig.class)    // 导入合并其他配置类，类似于配置文件中的 inculde 标签
     * 创建第三方bean
     * @Import(AppConfig.class)    // 导入合并其他配置类，类似于配置文件中的 inculde 标签
     */
    @Test
    public void test03() {
        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig2.class);
        Dog dog = context.getBean("dog", Dog.class);
        Cat cat = context.getBean(Cat.class);
        cat.run();
        dog.shout();

        User user = context.getBean(User.class);
        System.out.println(user.toString());
    }

    /**
     * @Configuration
     * @ComponentScan("com.kuang.pojo")
     * 扫描指定的包
     */
    @Test
    public void test04() {
        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig3.class);
        Dog dog = context.getBean(Dog.class);
        dog.shout();

    }
}