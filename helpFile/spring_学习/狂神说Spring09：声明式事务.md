## 狂神说Spring09：声明式事务

秦疆 [狂神说](javascript:void(0);) *2020-04-24*

> 狂神说Spring系列连载课程，通俗易懂，基于Spring最新版本，欢迎各位狂粉转发关注学习。禁止随意转载，转载记住贴出B站视频链接及公众号链接！

![图片](https://mmbiz.qpic.cn/mmbiz_gif/uJDAUKrGC7L1vFQMnaRIJSmeZ58T2eZicAHqMeOptckiacohSnX6DTIYSic2Uic7GLWuezVDk3bYqJa4vQwPwrLJXQ/640?wx_fmt=gif&tp=webp&wxfrom=5&wx_lazy=1)



# 1，回顾事务

## 1-1 回顾事务

> #### 回顾事务

- 事务在项目开发过程非常重要，涉及到数据的一致性的问题，不容马虎！
- 事务管理是企业级应用程序开发中必备技术，用来确保数据的完整性和一致性。

事务就是把一系列的动作当成一个独立的工作单元，这些动作要么全部完成，要么全部不起作用。

## **1-2 事务四个属性ACID**

1. 原子性（atomicity）

   事务是原子性操作，由一系列动作组成，事务的原子性确保动作要么全部完成，要么完全不起作用

2. 一致性（consistency）

   一旦所有事务动作完成，事务就要被提交。数据和资源处于一种满足业务规则的一致性状态中

3. 隔离性（isolation）

   可能多个事务会同时处理相同的数据，因此每个事务都应该与其他事务隔离开来，防止数据损坏

7. 持久性（durability）

   事务一旦完成，无论系统发生什么错误，结果都不会受到影响。通常情况下，事务的结果被写到持久化存储器中

## 1-3，没有事务管理的程序

> #### 测试

将上面的代码拷贝到一个新项目中

在之前的案例中，我们给userDao接口新增两个方法，删除和增加用户；

```java
//添加一个用户
int addUser(User user);

//根据id删除用户
int deleteUser(int id);
```

mapper文件，我们故意把 deletes 写错，测试！

```xml
<insert id="addUser" parameterType="com.User">
insert into user (id,name,pwd) values (#{id},#{name},#{pwd})
</insert>

<delete id="deleteUser" parameterType="int">
deletes from user where id = #{id}
</delete>
```

编写接口的实现类，在实现类中，我们去操作一波

```java
public class UserDaoImpl extends SqlSessionDaoSupport implements UserMapper {

   //增加一些操作
   public List<User> selectUser() {
       User user = new User(4,"小明","123456");
       UserMapper mapper = getSqlSession().getMapper(UserMapper.class);
       mapper.addUser(user);
       mapper.deleteUser(4);
       return mapper.selectUser();
  }

   //新增
   public int addUser(User user) {
       UserMapper mapper = getSqlSession().getMapper(UserMapper.class);
       return mapper.addUser(user);
  }
   //删除
   public int deleteUser(int id) {
       UserMapper mapper = getSqlSession().getMapper(UserMapper.class);
       return mapper.deleteUser(id);
  }

}
```

测试

```java
@Test
public void test2(){
   ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
   UserMapper mapper = (UserMapper) context.getBean("userDao");
   List<User> user = mapper.selectUser();
   System.out.println(user);
}
```

报错：sql异常，delete写错了

结果 ：插入成功！

没有进行事务的管理；我们想让他们都成功才成功，有一个失败，就都失败，我们就应该需要**事务！**

以前我们都需要自己手动管理事务，十分麻烦！

但是Spring给我们提供了事务管理，我们只需要配置即可；



# 2，spring中的事务管理

> Spring中的事务管理

Spring在不同的事务管理API之上定义了一个抽象层，使得开发人员不必了解底层的事务管理API就可以使用Spring的事务管理机制。Spring支持编程式事务管理和声明式的事务管理。

## **编程式事务管理**

- 将事务管理代码嵌到业务方法中来控制事务的提交和回滚
- 缺点：必须在每个事务操作业务逻辑中包含额外的事务管理代码

## **声明式事务管理**

- 一般情况下比编程式事务好用。
- 将事务管理代码从业务方法中分离出来，以声明的方式来实现事务管理。
- 将事务管理作为横切关注点，**通过aop方法模块化**。Spring中通过Spring AOP框架支持声明式事务管理。

**使用Spring管理事务，注意头文件的约束导入 : tx**

```xml
xmlns:tx="http://www.springframework.org/schema/tx"

http://www.springframework.org/schema/tx
http://www.springframework.org/schema/tx/spring-tx.xsd">
```

**事务管理器**

- 无论使用Spring的哪种事务管理策略（编程式或者声明式）事务管理器都是必须的。
- 就是 Spring的核心事务管理抽象，管理封装了一组独立于技术的方法。

**JDBC事务**

```xml
<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
       <property name="dataSource" ref="dataSource" />
</bean>
```

**配置好事务管理器后我们需要去配置事务的通知**

```xml
<!--配置事务通知-->
<tx:advice id="txAdvice" transaction-manager="transactionManager">
   <tx:attributes>
       <!--配置哪些方法使用什么样的事务,配置事务的传播特性-->
       <tx:method name="add" propagation="REQUIRED"/>
       <tx:method name="delete" propagation="REQUIRED"/>
       <tx:method name="update" propagation="REQUIRED"/>
       <tx:method name="search*" propagation="REQUIRED"/>
       <tx:method name="get" read-only="true"/>
       <tx:method name="*" propagation="REQUIRED"/>
   </tx:attributes>
</tx:advice>
```

**spring事务传播特性：**

事务传播行为就是多个事务方法相互调用时，事务如何在这些方法间传播。spring支持7种事务传播行为：

- propagation_requierd：如果当前没有事务，就新建一个事务，如果已存在一个事务中，加入到这个事务中，这是最常见的选择。
- propagation_supports：支持当前事务，如果没有当前事务，就以非事务方法执行。
- propagation_mandatory：使用当前事务，如果没有当前事务，就抛出异常。
- propagation_required_new：新建事务，如果当前存在事务，把当前事务挂起。
- propagation_not_supported：以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。
- propagation_never：以非事务方式执行操作，如果当前事务存在则抛出异常。
- propagation_nested：如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则执行与propagation_required类似的操作

***Spring 默认的事务传播行为是 PROPAGATION_REQUIRED***，它适合于绝大多数的情况。

假设 ServiveX#methodX() 都工作在事务环境下（即都被 Spring 事务增强了），假设程序中存在如下的调用链：Service1#method1()->Service2#method2()->Service3#method3()，那么这 3 个服务类的 3 个方法通过 Spring 的事务传播机制都工作在同一个事务中。

就好比，我们刚才的几个方法存在调用，所以会被放在一组事务当中！

**配置AOP**

导入aop的头文件！

```xml
<!--配置aop织入事务-->
<aop:config>
   <aop:pointcut id="txPointcut" expression="execution(* com.kuang.dao.*.*(..))"/>
   <aop:advisor advice-ref="txAdvice" pointcut-ref="txPointcut"/>
</aop:config>
```

**进行测试**

删掉刚才插入的数据，再次测试！

```
@Test
public void test2(){
   ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
   UserMapper mapper = (UserMapper) context.getBean("userDao");
   List<User> user = mapper.selectUser();
   System.out.println(user);
}
```

> 思考问题？

为什么需要配置事务？

- 如果不配置，就需要我们手动提交控制事务；
- 事务在项目开发过程非常重要，涉及到数据的一致性的问题，不容马虎！


```

```



![图片](https://mmbiz.qpic.cn/mmbiz_png/uJDAUKrGC7JicnvW4708YZgXPQAcr3JTia8Y39JMY2G6jbR5C8NP2ecF7ocDpwNU2XeCHKga62ToC8SKrbGnJRiaw/640?wx_fmt=gif&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)





# 3，附录1

# ---[spring配置事务管理为什么用aop:advisor](https://www.cnblogs.com/wmguang/p/14843834.html)

事务配置有aop:aspect和aop:advisor两种方式,但是在spring的事务管理的配置中会用aop:advisor配置,而不是aop:aspect

aop:aspect可以有多个pointcut,只能以类和方法作为参数.而aop:advisor只能有一个pointcut,但是aop:advisor可以接受策略参数,在spring中事务配置使用策略方式,这种方式只能用aop:advisor配置

***以 <aop:pointcut id="serviceMethod" expression="execution(* ..Service.*(..))" />为例讲解***

首先：这个表达式是分为4块的，即：方法返回类型 包 +（子包）+ 方法名 + 参数个数或者类型

1、第一个 * 表示：对任意的返回类型方法进行匹配

2、第二个 * 表示：  对任意的包并且包的最后是以Service结尾的包

3、第三个 * 表示：  对任意的方法名进行匹配

 4、第四个(..)表示： 通配，即方法中可以有0个或者多个参数，如果想执行参数为2个，即(*, String)表示2个参数，第二个参数为String类型。

到了这里，AOP的思想和使用相信大家就没问题了！



# 4，附录2 

# ---Spring中的几种事务处理方式



- 博客分类：

- 

- [spring](https://www.iteye.com/category/118036)

[Spring](http://www.iteye.com/blogs/tag/Spring)[Bean](http://www.iteye.com/blogs/tag/Bean)[配置管理](http://www.iteye.com/blogs/tag/配置管理)[DAO](http://www.iteye.com/blogs/tag/DAO)[AOP](http://www.iteye.com/blogs/tag/AOP) 

1、用原始的transactionfactorybean的,代理dao事务处理
2、用aop:config声明要进行事务增强的切面,用tx:advice声明具体方法的事务属性,及应用到的事务管理器
3、使用@transactional注解配置声明事务

***如有一代表用户的域对象user:***

```java
package com.domain;
import java.io.serializable;
public class user implements serializable{
    private int user_id;
    private string user_name;
    private string user_password;
    private string user_desc;
....//省略set、get方法
}

```

***user的数据库操作接口：***

```java
package com.dao;
import com.domain.user;
public interface userdao {
    public void adduser(user user);
}

```



有一继承spring jdbc支持类的userdao接口实现类,实现添加一个user的方法。它需要注入一个spring jdbc模板类jdbctemplate：

```java
package com.dao.jdbc;
import com.domain.user;
import com.dao.userdao;
import org.springframework.jdbc.core.support.jdbcdaosupport;
public class userjdbcdao extends jdbcdaosupport implements userdao{
    public void adduser(user user){
         string  sql =
         "insert into user(user_name,user_password,user_desc) values(?,?,?)";
        object[] params = new object[]{
           user.getuser_name(),
           user.getuser_password(),
           user.getuser_desc()
        } ;
        this.getjdbctemplate().update(sql, params);
    }
}

```



以上dao层的类对应的bean的基本配置文件***app_dao.xml***如下

（数据源的属性放入了外部的资源文件"prop.properties"）：

```xml
 	<bean class="org.springframework.beans.factory.config.propertyplaceholderconfigurer">
        <property name="location" value="classpath:prop.properties"/>
    </bean>
<!--数据源-->
    <bean id="datasource" class="org.apache.commons.dbcp.basicdatasource"
     destroy-method="close">
        <property name="driverclassname" value="${jdbc.driverclassname}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>
<!--spring jdbc模板bean，它注入了上面的数据源-->
    <bean id="jdbctemplate" class="org.springframework.jdbc.core.jdbctemplate">
        <property name="datasource" ref="datasource"/>
    </bean>
<!--user数据操作的bean声明，它注入了上面的spring jdbc模板bean:jdbctemplate-->
    <bean id="userjdbcdao"    class="com.dao.jdbc.userjdbcdao">
		<property name="jdbctemplate" ref="jdbctemplate"/>
    </bean>
</beans>

```



这里我简单地模拟业务类(业务接口userservice省略)：

```java
package com.service.impl;
import com.dao.userdao;
import com.domain.user;
import com.service.userservice;
public class userserviceimpl implements userservice {
    private userdao userdao;
    public void setuserdao(userdao userdao){
        this.userdao = userdao;
    }
    public void adduser(user user){
        this.userdao.adduser(user);
    }
}
```

为了在业务类中使用事务管理功能,有如下几个方法：

## ***1、用原始的transactionfactorybean的app.xml基本配置：***

```xml
<!--导入dao层的配置-->
    <import resource="classpath:app_dao.xml"/>
<!--spring jdbc的事务管理bean,引入了dbcp数据源-->
    <bean id="txmanager" class="org.springframework.jdbc.datasource.datasourcetransactionmanager">
        <property name="datasource" ref="datasource"/>
    </bean>
<!--业务类bean-->
    <bean id="userserviceimpltarget" class="com.service.impl.userserviceimpl">
        <property name="userdao" ref="userjdbcdao"/>
    </bean>
<!--应用原始的transactionfactorybean进行事务管理bean的声明-->
    <bean id="userserviceimpl"
          class="org.springframework.transaction.interceptor.transactionproxyfactorybean">
		<!--指定事务管理bean-->
        <property name="transactionmanager" ref="txmanager"/>
        <!--指定业务bean-->
        <property name="target" ref="userserviceimpltarget"/>
		<!--事务的属性设置列表-->
        <property name="transactionattributes">
            <props>
                <prop key="add*">propagation_required,isolation_serializable</prop>
                <!--设置事务为只读时，添加数据将会产生异常-->
                <!--<prop key="add*">propagation_required,isolation_serializable,readonly</prop>-->
            </props>
        </property>
    </bean>
```

***测试：***
......
userserviceimpl usi = (userserviceimpl)ctx.getbean("userserviceimpl");
......



## ***2、用tx/aop命名空间配置：***

```xml
<?xml version="1.0" encoding="utf-8"?>
<beans .....
    xmlns:tx="http://www.springframework.org/schema/tx"
    xsp:schemalocation="http://www.springframework.org/schema/beans
    ...........
    http://www.springframework.org/schema/tx
    http://www.springframework.org/schema/tx/spring-tx-2.0.xsd">
    
    <import resource="classpath:app_dao.xml"/>
    
    <bean id="txmanager" class="org.springframework.jdbc.datasource.datasourcetransactionmanager">
        <property name="datasource" ref="datasource"/>
    </bean>

    <bean id="userserviceimpltarget" class="com.service.impl.userserviceimpl">
        <property name="userdao" ref="userjdbcdao"/>
    </bean>
<!--应用tx/aop命名空间进行事务声明-->
<!--用tx:advice声明具体方法的事务属性,及应用到的事务管理器-->
    <tx:advice id="txadvice" transaction-manager="txmanager">
        <tx:attributes>
            <tx:method name="add*" read-only="true"/>
        </tx:attributes>
    </tx:advice>
<!--用aop:config声明要进行事务增强的切面-->
    <aop:config>
        <aop:pointcut id="servicemethod"
        expression="execution(* com.service..add*(..))"/>
        <aop:advisor pointcut-ref="servicemethod" advice-ref="txadvice"/>
    </aop:config>
</beans>
```



***测试：***
.......
userservice usi = (userservice)ctx.getbean("userserviceimpltarget");
..........



## ***3、使用@transactional注解配置声明事务(最简单实用的方法)：***



在需要事务管理增强的业务类加入@transactional注解标记,如：

```java
......
import org.springframework.transaction.annotation.transactional; //注解式事务
@transactional(readonly=false) //对业务类进行事务增强的标注
public class userserviceimpl implements userservice {
...........
}
```

再在配置文件中用

```xml
<!--驱动自动为标记@transactional注解的类织入事务管理增强：-->
	<tx:annotation-driven>
    
	<import resource="classpath:app_dao.xml"/>
        
    <bean id="txmanager" class="org.springframework.jdbc.datasource.datasourcetransactionmanager">
        <property name="datasource" ref="datasource"/>
    </bean>
    <!--注解式事务配置驱动-->
    <tx:annotation-driven transaction-manager="txmanager" proxy-target-class="true"/>
    <!--业务类bean的实现类标注了@transactional注解，所以会被
tx:annotation-driven注解驱动自动织入事务增强-->
    <bean id="userservice" class="com.service.impl.userserviceimpl">
        <property name="userdao" ref="userjdbcdao"/>
    </bean>

```



***测试：***
.........
userserviceimpl usi = (userserviceimpl)ctx.getbean("userservice");
.........
