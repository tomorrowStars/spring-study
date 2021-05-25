import com.kuang.pojo.Address;
import com.kuang.pojo.Student;
import com.kuang.pojo.Teacher;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest_Di001 {

    @Test
    public void test001() {
        Address address = new Address();
        address.setAddress("中国山东济南市。");
        System.out.println(address.getAddress());
    }

    /**
     * set注入
     */
    @Test
    public void test002() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Student student = context.getBean("student", Student.class);
        student.toString();
        student.show();

    }

    /**
     * c命名空间注入
     */
    @Test
    public void test003() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Teacher teacher = context.getBean("teacher", Teacher.class);
        Teacher teacher2 = context.getBean("teacher", Teacher.class);
        System.out.println(teacher.toString());

        System.out.println(teacher == teacher2); // true(※ bean的作用域为Singleton,容器中只会存在一个共享的bean实例 )
    }

    /**
     * p命名空间注入
     */
    @Test
    public void test004() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Address address = context.getBean("address", Address.class);
        Address address2 = context.getBean("address", Address.class);
        System.out.println(address.toString());
        System.out.println(address == address2); // false（※ bean的作用域为Prototype,容器中存在多个的bean实例)
    }

}
