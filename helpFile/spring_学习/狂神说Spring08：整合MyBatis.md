## 狂神说Spring08：整合MyBatis

秦疆 [狂神说](javascript:void(0);) *2020-04-23*

> 狂神说Spring系列连载课程，通俗易懂，基于Spring最新版本，欢迎各位狂粉转发关注学习。禁止随意转载，转载记住贴出B站视频链接及公众号链接！

![图片](https://mmbiz.qpic.cn/mmbiz_gif/uJDAUKrGC7L1vFQMnaRIJSmeZ58T2eZicAHqMeOptckiacohSnX6DTIYSic2Uic7GLWuezVDk3bYqJa4vQwPwrLJXQ/640?wx_fmt=gif&tp=webp&wxfrom=5&wx_lazy=1)





# 一 整合MyBatis

> #### 步骤

## 1、导入相关jar包

junit

```xml
<dependency>
   <groupId>junit</groupId>
   <artifactId>junit</artifactId>
   <version>4.12</version>
</dependency>
```

mybatis

```xml
<dependency>
   <groupId>org.mybatis</groupId>
   <artifactId>mybatis</artifactId>
   <version>3.5.2</version>
</dependency>
```

mysql-connector-java

```xml
<dependency>
   <groupId>mysql</groupId>
   <artifactId>mysql-connector-java</artifactId>
   <version>5.1.47</version>
</dependency>
```

spring相关

```xml
<dependency>
   <groupId>org.springframework</groupId>
   <artifactId>spring-webmvc</artifactId>
   <version>5.1.10.RELEASE</version>
</dependency>
<dependency>
   <groupId>org.springframework</groupId>
   <artifactId>spring-jdbc</artifactId>
   <version>5.1.10.RELEASE</version>
</dependency>
```

aspectJ AOP 织入器

```xml
<!-- https://mvnrepository.com/artifact/org.aspectj/aspectjweaver -->
<dependency>
   <groupId>org.aspectj</groupId>
   <artifactId>aspectjweaver</artifactId>
   <version>1.9.4</version>
</dependency>
```

mybatis-spring整合包 【重点】

```xml
<dependency>
   <groupId>org.mybatis</groupId>
   <artifactId>mybatis-spring</artifactId>
   <version>2.0.2</version>
</dependency>
```

配置Maven静态资源过滤问题！

jdk版本问题!

```xml
 <build>
        <!--1, Maven静态资源过滤问题-->
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
        </resources>
        <!--2, jdk版本问题-->
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>11</source>
                    <target>11</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
```

2、编写配置文件

3、代码实现



## 2，回忆MyBatis

> #### 回忆MyBatis

**编写pojo实体类**

```java
package com.kuang.pojo;

public class User {
   private int id;  //id
   private String name;   //姓名
   private String pwd;   //密码
}
```

**实现mybatis的配置文件**

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
       PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
       "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

   <typeAliases>
       <package name="com.kuang.pojo"/>
   </typeAliases>

   <environments default="development">
       <environment id="development">
           <transactionManager type="JDBC"/>
           <dataSource type="POOLED">
               <property name="driver" value="com.mysql.jdbc.Driver"/>
               <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=true&amp;useUnicode=true&amp;characterEncoding=utf8"/>
               <property name="username" value="root"/>
               <property name="password" value="123456"/>
           </dataSource>
       </environment>
   </environments>

   <mappers>
       <package name="com.kuang.dao"/>
   </mappers>
</configuration>
```

**UserDao接口编写**

```java
public interface UserMapper {
   public List<User> selectUser();
}
```

**接口对应的Mapper映射文件**

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
       PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
       "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.kuang.dao.UserMapper">

   <select id="selectUser" resultType="User">
    select * from user
   </select>

</mapper>
```

**测试类**

```java
@Test
public void selectUser() throws IOException {

   String resource = "mybatis-config.xml";
   InputStream inputStream = Resources.getResourceAsStream(resource);
   SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
   SqlSession sqlSession = sqlSessionFactory.openSession();

   UserMapper mapper = sqlSession.getMapper(UserMapper.class);

   List<User> userList = mapper.selectUser();
   for (User user: userList){
       System.out.println(user);
  }

   sqlSession.close();
}
```

# 二， MyBatis-Spring学习

> #### MyBatis-Spring学习

引入Spring之前需要了解mybatis-spring包中的一些重要类；

http://www.mybatis.org/spring/zh/index.html

![图片](data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVQImWNgYGBgAAAABQABh6FO1AAAAABJRU5ErkJggg==)

**什么是 MyBatis-Spring？**

MyBatis-Spring 会帮助你将 MyBatis 代码无缝地整合到 Spring 中。

**知识基础**

在开始使用 MyBatis-Spring 之前，你需要先熟悉 Spring 和 MyBatis 这两个框架和有关它们的术语。这很重要

MyBatis-Spring 需要以下版本：

| MyBatis-Spring | MyBatis | Spring 框架 | Spring Batch | Java    |
| :------------- | :------ | :---------- | :----------- | :------ |
| 2.0            | 3.5+    | 5.0+        | 4.0+         | Java 8+ |
| 1.3            | 3.4+    | 3.2.2+      | 2.1+         | Java 6+ |

如果使用 Maven 作为构建工具，仅需要在 pom.xml 中加入以下代码即可：

```xml
<dependency>
   <groupId>org.mybatis</groupId>
   <artifactId>mybatis-spring</artifactId>
   <version>2.0.2</version>
</dependency>
```

要和 Spring 一起使用 MyBatis，需要在 Spring 应用上下文中定义至少两样东西：一个 SqlSessionFactory 和至少一个数据映射器类。

在 MyBatis-Spring 中，可使用SqlSessionFactoryBean来创建 SqlSessionFactory。要配置这个工厂 bean，只需要把下面代码放在 Spring 的 XML 配置文件中：

```xml
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
 <property name="dataSource" ref="dataSource" />
</bean>
```

注意：SqlSessionFactory需要一个 DataSource（数据源）。这可以是任意的 DataSource，只需要和配置其它 Spring 数据库连接一样配置它就可以了。

在基础的 MyBatis 用法中，是通过 SqlSessionFactoryBuilder 来创建 SqlSessionFactory 的。而在 MyBatis-Spring 中，则使用 SqlSessionFactoryBean 来创建。

在 MyBatis 中，你可以使用 SqlSessionFactory 来创建 SqlSession。一旦你获得一个 session 之后，你可以使用它来执行映射了的语句，提交或回滚连接，最后，当不再需要它的时候，你可以关闭 session。

SqlSessionFactory有一个唯一的必要属性：用于 JDBC 的 DataSource。这可以是任意的 DataSource 对象，它的配置方法和其它 Spring 数据库连接是一样的。

一个常用的属性是 configLocation，它用来指定 MyBatis 的 XML 配置文件路径。它在需要修改 MyBatis 的基础配置非常有用。通常，基础配置指的是 < settings> 或 < typeAliases>元素。

需要注意的是，这个配置文件并不需要是一个完整的 MyBatis 配置。确切地说，任何环境配置（<environments>），数据源（<DataSource>）和 MyBatis 的事务管理器（<transactionManager>）都会被忽略。SqlSessionFactoryBean 会创建它自有的 MyBatis 环境配置（Environment），并按要求设置自定义环境的值。

SqlSessionTemplate 是 MyBatis-Spring 的核心。作为 SqlSession 的一个实现，这意味着可以使用它无缝代替你代码中已经在使用的 SqlSession。

模板可以参与到 Spring 的事务管理中，并且由于其是线程安全的，可以供多个映射器类使用，你应该总是用 SqlSessionTemplate 来替换 MyBatis 默认的 DefaultSqlSession 实现。在同一应用程序中的不同类之间混杂使用可能会引起数据一致性的问题。

可以使用 SqlSessionFactory 作为构造方法的参数来创建 SqlSessionTemplate 对象。

```xml
<bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
 <constructor-arg index="0" ref="sqlSessionFactory" />
</bean>
```

现在，这个 bean 就可以直接注入到你的 DAO bean 中了。你需要在你的 bean 中添加一个 SqlSession 属性，就像下面这样：

```java
public class UserDaoImpl implements UserDao {

 private SqlSession sqlSession;

 public void setSqlSession(SqlSession sqlSession) {
   this.sqlSession = sqlSession;
}

 public User getUser(String userId) {
   return sqlSession.getMapper...;
}
}
```

按下面这样，注入 SqlSessionTemplate：

```xml
<bean id="userDao" class="org.mybatis.spring.sample.dao.UserDaoImpl">
 <property name="sqlSession" ref="sqlSession" />
</bean>
```



# 三 整合实现

## 1，整合实现一

> #### 整合实现一

1、引入Spring配置文件beans.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">
```

2、配置数据源替换mybaits的数据源

```xml
<!--配置数据源：数据源有非常多，可以使用第三方的，也可使使用Spring的-->
<bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
   <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
   <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=true&amp;useUnicode=true&amp;characterEncoding=utf8"/>
   <property name="username" value="root"/>
   <property name="password" value="123456"/>
</bean>
```

3、配置SqlSessionFactory，关联MyBatis

```xml
<!--配置SqlSessionFactory-->
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
   <property name="dataSource" ref="dataSource"/>
   <!--关联Mybatis-->
   <property name="configLocation" value="classpath:mybatis-config.xml"/>
   
   <property name="mapperLocations" value="classpath:com/kuang/dao/*.xml"/>
</bean>

```

  **// classpath 和 classpath* 区别参考下方**



4、注册sqlSessionTemplate，关联sqlSessionFactory；

```xml
<!--注册sqlSessionTemplate , 关联sqlSessionFactory-->
<bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
   <!--利用构造器注入-->
   <constructor-arg index="0" ref="sqlSessionFactory"/>
</bean>
```

5、增加Dao接口的实现类；私有化sqlSessionTemplate

```java
public class UserDaoImpl implements UserMapper {

   //sqlSession不用我们自己创建了，Spring来管理
   private SqlSessionTemplate sqlSession;

   public void setSqlSession(SqlSessionTemplate sqlSession) {
       this.sqlSession = sqlSession;
  }

   public List<User> selectUser() {
       UserMapper mapper = sqlSession.getMapper(UserMapper.class);
       return mapper.selectUser();
  }
   
}
```

6、注册bean实现

```xml
<bean id="userDao" class="com.kuang.dao.UserDaoImpl">
   <property name="sqlSession" ref="sqlSession"/>
</bean>
```

7、测试

```java
   @Test
   public void test2(){
       ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
       UserMapper mapper = (UserMapper) context.getBean("userDao");
       List<User> user = mapper.selectUser();
       System.out.println(user);
  }
```

结果成功输出！现在我们的Mybatis配置文件的状态！发现都可以被Spring整合！

**mybatis-config.xml**

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
       PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
       "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <settings>
        <setting name="logImpl" value="STDOUT_LOGGING"/>
    </settings>
    <!--
       <environments default="development">
       <environment id="development">
           <transactionManager type="JDBC"/>
           <dataSource type="POOLED">
               <property name="driver" value="com.mysql.jdbc.Driver"/>
               <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=true&amp;useUnicode=true&amp;characterEncoding=utf8"/>
               <property name="username" value="root"/>
               <property name="password" value="123456"/>
           </dataSource>
       </environment>
   </environments>

   <mappers>
       <package name="com.kuang.dao"/>
   </mappers>
    -->
   <typeAliases>
       <package name="com.kuang.pojo"/>
   </typeAliases>
</configuration>
```

**beans.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd">

<!--    可以使用注解版-->
<!--    <context:annotation-config/>-->

    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=true&amp;useUnicode=true&amp;characterEncoding=utf8"/>
        <property name="username" value="root"/>
        <property name="password" value="123456"/>
    </bean>

    <bean id="sessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
        <property name="mapperLocations" value="classpath:com/kuang/dao/*.xml"/>
<!--        <property name="typeAliasesPackage" value="com.kuang.pojo"/>-->
<!--        <property name="typeAliases" value="com.kuang.pojo.User"/>-->
<!--        <property name="typeAliases" >-->
<!--            <array>-->
<!--                <value>com.kuang.pojo.User</value>-->
<!--            </array>-->
<!--        </property>-->
    </bean>

    <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
        <constructor-arg index="0" ref="sessionFactory"/>
    </bean>

<!--    方式1-->
    <bean id="userDao" class="com.kuang.dao.UserDaoImpl">
        <property name="sqlSession" ref="sqlSessionTemplate"/>
<!--        <constructor-arg index="0" value="sessionTemplate"/>-->
    </bean>

<!--    方式2 ；可以不用写DaoImpl-->
    <!-- 4.配置扫描Dao接口包，动态实现Dao接口注入到spring容器中 可以不用写DaoImpl -->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <!-- 注入sqlSessionFactory -->
        <property name="sqlSessionFactoryBeanName" value="sessionFactory"/>
        <!-- 给出需要扫描Dao接口包 -->
        <property name="basePackage" value="com.kuang.dao"/>
    </bean>
</beans>
```



## 2，整合实现二

> #### 整合实现二

mybatis-spring1.2.3版以上的才有这个 .

官方文档截图 :

dao继承Support类 , 直接利用 getSqlSession() 获得 , 然后直接注入SqlSessionFactory . 比起方式1 , 不需要管理SqlSessionTemplate , 而且对事务的支持更加友好 . 可跟踪源码查看

![图片](https://mmbiz.qpic.cn/mmbiz_png/uJDAUKrGC7JXnZ2vicYHdibF3mvk0w89Cq6qzPguHd15ribJeF3ickXAy98VG8YFjxwftEdsvvCLWkWLPeiaiaVibbPxQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

测试：

1、将我们上面写的UserDaoImpl修改一下

```java
public class UserDaoImpl extends SqlSessionDaoSupport implements UserMapper {
   public List<User> selectUser() {
       UserMapper mapper = getSqlSession().getMapper(UserMapper.class);
       return mapper.selectUser();
  }
}
```

2、修改bean的配置

```xml
<bean id="userDao" class="com.kuang.dao.UserDaoImpl">
   <property name="sqlSessionFactory" ref="sqlSessionFactory" />
</bean>
```

3、测试

```java
@Test
public void test2(){
   ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
   UserMapper mapper = (UserMapper) context.getBean("userDao");
   List<User> user = mapper.selectUser();
   System.out.println(user);
}
```

**总结 : 整合到spring以后可以完全不要mybatis的配置文件，除了这些方式可以实现整合之外，我们还可以使用注解来实现，这个等我们后面学习SpringBoot的时候还会测试整合！**



**mybatis-config.xml**

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <settings>
        <setting name="logImpl" value="STDOUT_LOGGING"/>
    </settings>
    <typeAliases>
        <package name="com.kuang.pojo"/>
    </typeAliases>

</configuration>
```



**beans.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306?useSSL=true&amp;useUnicode=true&amp;characterEncoding=utf8"/>
        <property name="username" value="root"/>
        <property name="password" value="123456"/>
    </bean>

    <bean id="sessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
        <property name="mapperLocations" value="classpath:com/kuang/dao/*.xml"/>
    </bean>
<!--
    [spring]xml配置文件中的"classpath:"与"classpath*:"的区别
        classpath：
        只会到你的class路径中查找找文件；
        有多个classpath路径，并同时加载多个classpath路径的情况下，只会从第一个classpath中加载。
        classpath*：
        不仅包含class路径，还包括jar文件中（class路径）进行查找；
        有多个classpath路径，并同时加载多个classpath路径的情况下，会从所有的classpath中加载；
        用classpath*:需要遍历所有的classpath，所以加载速度是很慢的；因此，在规划的时候，应该尽可能规划好资源文件所在的路径，尽量避免使用classpath*。
-->
<!--    <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">-->
<!--        <constructor-arg index="0" ref="sessionFactory"/>-->
<!--    </bean>-->

    <bean class="com.kuang.dao.UserDaoImpl" id="userDao">
        <property name="sqlSessionFactory" ref="sessionFactory"/>
    </bean>

</beans>
```





# 四，附录："classpath:"与"classpath*:"的区别]

(https://www.cnblogs.com/vickylinj/p/9475990.html)

 

```xml
<bean id="sessionFactorySaas" class="org.mybatis.spring.SqlSessionFactoryBean">  
        <property name="dataSource" ref="dataSourceSaasdb"/>
        <!-- mapper和resultmap配置路径 -->
        <property name="mapperLocations" value="classpath*:mapper/**/saas.*.xml" />
        <property name="configLocation" value="classpath:mybatis-config.xml"/></bean>
```

 

classpath：

- 只会到你的class路径中查找找文件；
- 有多个classpath路径，并同时加载多个classpath路径的情况下，只会从第一个classpath中加载。

classpath*：

- 不仅包含class路径，还包括jar文件中（class路径）进行查找；
- 有多个classpath路径，并同时加载多个classpath路径的情况下，会从所有的classpath中加载；
- 用classpath*:需要遍历所有的classpath，所以加载速度是很慢的；因此，在规划的时候，应该尽可能规划好资源文件所在的路径，尽量避免使用classpath*。





# 五，附录： MapperFactoryBean 配置

**Mybatis整合Spring**

​    根据官方的说法，在ibatis3，也就是Mybatis3问世之前，Spring3的开发工作就已经完成了，所以Spring3中还是没有对Mybatis3的支持。

因此由Mybatis社区自己开发了一个Mybatis-Spring用来满足Mybatis用户整合Spring的需求。下面就将通过Mybatis-Spring来整合Mybatis跟

Spring的用法做一个简单的介绍

***MapperFactoryBean***

​    首先，我们需要从Mybatis官网上下载Mybatis-Spring的jar包添加到我们项目的类路径下，当然也需要添加Mybatis的相关jar包和Spring的

相关jar包。我们知道在Mybatis的所有操作都是基于一个SqlSession的，而SqlSession是由SqlSessionFactory来产生的，SqlSessionFactory又

是由SqlSessionFactoryBuilder来生成的。但是Mybatis-Spring是基于SqlSessionFactoryBean的。

在使用Mybatis-Spring的时候，我们也需要SqlSession

，而且这个SqlSession是内嵌在程序中的，一般不需要我们直接访问。

SqlSession也是由SqlSessionFactory来产生的，但是Mybatis-Spring

给我们封装了一个SqlSessionFactoryBean，在这个bean里面还是通过SqlSessionFactoryBuilder来建立对应的SqlSessionFactory，

进而获取到对应的SqlSession。通过SqlSessionFactoryBean我们可以通过对其指定一些属性来提供Mybatis的一些配置信息。

所以接下来我们需要在Spring的applicationContext配置文件中

**定义一个SqlSessionFactoryBean**



```xml
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">  
       <property name="dataSource" ref="dataSource" />  
       <property name="configLocation" value="classpath:mybatis-config.xml"/>
       <property name="mapperLocations"  
              value="classpath:com/tiantian/ckeditor/mybatis/mappers/*Mapper.xml" />  
<!--        <property name="typeAliasesPackage" value="com.kuang.pojo"/>-->
<!--        <property name="typeAliases"        value="com.kuang.pojo.User"/>-->
       <property name="typeAliases">  
        <array>  
            <value>com.tiantian.mybatis.model.Blog</value>  
            <value>com.tiantian.mybatis.model.Comment</value>  
        </array>  
</property>  
</bean>  
```



 在定义SqlSessionFactoryBean的时候，

- dataSource属性是必须指定的，它表示用于连接数据库的数据源。

-  mapperLocations：它表示我们的Mapper文件存放的位置，当我们的Mapper文件跟对应的Mapper接口处于同一位置的时候可以不用指定该属性的值

- configLocation：用于指定Mybatis的配置文件位置。如果指定了该属性，那么会以该配置文件的内容作为配置信息构建对应的SqlSessionFactoryBuilder，

  ​                             但是后续属性指定的内容会覆盖该配置文件里面指定的对应内容

-  typeAliasesPackage：它一般对应我们的实体类所在的包，这个时候会自动取对应包中不包括包名的简单类名作为包括包名的别名。

  ​                                     多个package之间可以用逗号或者分号等来进行分隔（**value的值一定要是包的全名**）

-  typeAliases：数组类型，用来指定别名的。指定了这个属性后，Mybatis会把这个类型的**短名称**作为这个类型的别名，

  ​                        前提是该类上没有标注@Alias注解，否则将使用该注解对应的值作为此种类型的别名（**value的值一定要是类的完全限定名**）

- plugins：数组类型，用来指定Mybatis的Interceptor
-  typeHandlersPackage：用来指定TypeHandler所在的包，如果指定了该属性，SqlSessionFactoryBean会自动把该包下面的类注册为对应的TypeHandler。多个package之间可以用逗号或者分号等来进行分隔
-  typeHandlers：数组类型，表示TypeHandler



![图片](https://mmbiz.qpic.cn/mmbiz_png/uJDAUKrGC7LUziamJeeiaLFt7YwxJtAgSMJeByJtUH7v6CqSCHYu4ibBe0cPDkfsE95xd2UQOOFfrZXZmVmfIDd5g/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

end

![图片](https://mmbiz.qpic.cn/mmbiz_png/uJDAUKrGC7LUziamJeeiaLFt7YwxJtAgSMGnHjd2QQEZuDaQILmHqcMzYvQiaasptQOgicqPxTZWiaInwElicmx9IYgQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

![图片](https://mmbiz.qpic.cn/mmbiz_png/uJDAUKrGC7JicnvW4708YZgXPQAcr3JTia8Y39JMY2G6jbR5C8NP2ecF7ocDpwNU2XeCHKga62ToC8SKrbGnJRiaw/640?wx_fmt=gif&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

