import com.kuang.AppConfig;
import com.kuang.pojo.Cat;
import com.kuang.pojo.Dog;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MyTest {

    @Test
    public void test01() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Dog dog = context.getBean("dog", Dog.class);
        Cat cat = context.getBean(Cat.class);
        cat.run();
        dog.shout();


    }
}