import com.kuang.pojo.Address;
import org.junit.Test;

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

}
