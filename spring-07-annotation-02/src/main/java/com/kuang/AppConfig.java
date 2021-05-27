package com.kuang;

import com.kuang.pojo.Cat;
import com.kuang.pojo.Dog;
import com.kuang.pojo.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class AppConfig {

//    AppConfig还标注了@ComponentScan，它告诉容器，自动搜索当前类所在的包以及子包，把所有标注为@Component的Bean自动创建出来，并根据@Autowired进行装配。
//
//    整个工程结构如下：
//
//    spring-ioc-annoconfig
//├── pom.xml
//└── src
//    └── main
//        └── java
//            └── com
//                └── itranswarp
//                    └── learnjava
//                        ├── AppConfig.java
//                        └── service
//                            ├── MailService.java
//                            ├── User.java
//                            └── UserService.java
//    使用Annotation配合自动扫描能大幅简化Spring的配置，我们只需要保证：
//
//    每个Bean被标注为@Component并正确使用@Autowired注入；
//    配置类被标注为@Configuration和@ComponentScan；
//    所有Bean均在指定包以及子包内。
//    使用@ComponentScan非常方便，但是，我们也要特别注意包的层次结构。通常来说，启动配置AppConfig位于自定义的顶层包（例如com.itranswarp.learnjava），其他Bean按类别放入子包。


    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        User user = context.getBean(User.class);
        System.out.println(user.toString());

    }
}
