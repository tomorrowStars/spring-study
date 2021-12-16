package com.kuang.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration // 本身就是一个cmponent组件， 只是一个配置类， 相当于xml的配置文件
@ComponentScan("com.kuang.pojo") // 自动搜索当前类所在的包以及子包，把所有标注为@Component的Bean自动创建出来，并根据@Autowired进行装配。

public class MyConfig3 {
}
