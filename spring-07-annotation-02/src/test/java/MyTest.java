import com.kuang.AppConfig;
import com.kuang.config.MyConfig;
import com.kuang.config.MyConfig2;
import com.kuang.pojo.Cat;
import com.kuang.pojo.Dog;
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
     * 自动搜索当前类所在的包以及子包，把所有标注为@Component的Bean自动创建出来，并根据@Autowired进行装配。
     */
    @Test
    public void test01() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Dog dog = context.getBean("dog", Dog.class);
        Cat cat = context.getBean(Cat.class);
        cat.run();
        dog.shout();

    }

    /**
     * 创建第三方bean
     */
    @Test
    public void test02() {
        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        Dog dog = context.getBean("dog", Dog.class);
        Cat cat = context.getBean(Cat.class);
        cat.run();
        dog.shout();
    }

    /**
     * 创建第三方bean
     */
    @Test
    public void test03() {
        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig2.class);
        Dog dog = context.getBean("dog", Dog.class);
        Cat cat = context.getBean(Cat.class);
        cat.run();
        dog.shout();
    }
}