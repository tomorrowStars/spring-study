import com.kuang.pojo.Address;
import com.kuang.pojo.Student;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest_Di001 {
    public static void main(String[] args) {
        Address address = new Address();
        address.setAddress("中国山东济南市。");
        System.out.println(address.getAddress());
    }

    @Test
    public void test001() {
        Address address = new Address();
        address.setAddress("中国山东济南市。");
        System.out.println(address.getAddress());

    }

    @Test
    public void test002(){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Student student = context.getBean("student", Student.class);
        student.toString();
        student.show();

    }

}
