## 狂神说Spring07：AOP就这么简单

秦疆 [狂神说](javascript:void(0);) *2020-04-22*

> 狂神说Spring系列连载课程，通俗易懂，基于Spring最新版本，欢迎各位狂粉转发关注学习。禁止随意转载，转载记住贴出B站视频链接及公众号链接！

![图片](https://mmbiz.qpic.cn/mmbiz_gif/uJDAUKrGC7L1vFQMnaRIJSmeZ58T2eZicAHqMeOptckiacohSnX6DTIYSic2Uic7GLWuezVDk3bYqJa4vQwPwrLJXQ/640?wx_fmt=gif&tp=webp&wxfrom=5&wx_lazy=1)





AOP

上一讲中我们讲解了代理模式，这是AOP的基础，一定要先搞懂它

[狂神说Spring06：静态/动态代理模式](http://mp.weixin.qq.com/s?__biz=Mzg2NTAzMTExNg==&mid=2247484130&idx=1&sn=73741a404f7736c02bcdf69f565fe094&chksm=ce610441f9168d57265a3b4216b0a63c1d970457896c6b94949fe6dda8d98d04f4381cd059da&scene=21#wechat_redirect)

那我们接下来就来聊聊AOP吧！

# 1, 什么是AOP

AOP（Aspect Oriented Programming）意为：面向切面编程，通过预编译方式和运行期动态代理实现程序功能的统一维护的一种技术。AOP是OOP的延续，是软件开发中的一个热点，也是Spring框架中的一个重要内容，是函数式编程的一种衍生范型。利用AOP可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的耦合度降低，提高程序的可重用性，同时提高了开发的效率。



![图片](https://mmbiz.qpic.cn/mmbiz_png/uJDAUKrGC7JAeTYOaaH6rZ6WmLLgwQLHf5pmH30gj6mZm81PC7iauicFu55sicJtspU7K3vTCVdZCDTSHq7D5XHlw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

> Aop在Spring中的作用

提供声明式事务；允许用户自定义切面

以下名词需要了解下：

> - 横切关注点：跨越应用程序多个模块的方法或功能。即是，与我们业务逻辑无关的，但是我们需要关注的部分，就是横切关注点。如日志 , 安全 , 缓存 , 事务等等 ....
> - 切面（ASPECT）：横切关注点 被模块化 的特殊对象。即，它是一个类。
> - 通知（Advice）：切面必须要完成的工作。即，它是类中的一个方法。
> - 目标（Target）：被通知对象。
> - 代理（Proxy）：向目标对象应用通知之后创建的对象。
> - 切入点（PointCut）：切面通知 执行的 “地点”的定义。
> - 连接点（JointPoint）：与切入点匹配的执行点。



> 在AOP编程中，我们经常会遇到下面的概念：
>
> - Aspect：切面，即一个横跨多个核心逻辑的功能，或者称之为系统关注点；
> - Advice：增强，指特定连接点上执行的动作；
> - Target Object：目标对象，即真正执行业务的核心逻辑对象；
> - AOP Proxy：AOP代理，是客户端持有的增强后的对象引用。
> - Pointcut：切入点，即一组连接点的集合；
> - Joinpoint：连接点，即定义在应用程序流程的何处插入切面的执行；
> - 
> - Introduction：引介，指为一个已有的Java对象动态地增加新的接口；
> - Weaving：织入，指将切面整合到程序的执行流程中；
> - Interceptor：拦截器，是一种实现增强的方式；



![图片](https://mmbiz.qpic.cn/mmbiz_png/uJDAUKrGC7JAeTYOaaH6rZ6WmLLgwQLHVOZ1JpRb7ViaprZCRXsUbH0bZpibiaTjqib68LQHOWZicSvuU8Y1dquUVGw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

SpringAOP中，通过Advice定义横切逻辑，Spring中支持5种类型的Advice:



![图片](https://mmbiz.qpic.cn/mmbiz_png/uJDAUKrGC7JAeTYOaaH6rZ6WmLLgwQLHbAWH8haUQeJ0LVBxxX0icC5TZlBkEBGibibey7jFrCbibPzQcRhkNFcGAA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

即 Aop 在 不改变原有代码的情况下 , 去增加新的功能 .



![](E:\software\微信图片_20210527174249.png)

<img src="E:\software\微信图片_202105271742491.png" alt="微信图片_202105271742491" style="zoom: 200%;" />





# 2, 依赖包

> 使用Spring实现Aop

【重点】使用AOP织入，需要导入一个依赖包！

```xml
<!-- https://mvnrepository.com/artifact/org.aspectj/aspectjweaver -->
<dependency>
   <groupId>org.aspectj</groupId>
   <artifactId>aspectjweaver</artifactId>
   <version>1.9.4</version>
</dependency>
```

# 3, 实现方式

## **第一种方式: **通过 Spring API 实现****

**通过 Spring API 实现**

首先编写我们的业务接口和实现类

```java
public interface UserService {

   public void add();

   public void delete();

   public void update();

   public void search();

}
public class UserServiceImpl implements UserService{

   @Override
   public void add() {
       System.out.println("增加用户");
  }

   @Override
   public void delete() {
       System.out.println("删除用户");
  }

   @Override
   public void update() {
       System.out.println("更新用户");
  }

   @Override
   public void search() {
       System.out.println("查询用户");
  }
}
```

然后去写我们的增强类 , 我们编写两个 , 一个前置增强 一个后置增强

```java
public class Log implements MethodBeforeAdvice {

   //method : 要执行的目标对象的方法
   //args : 被调用的方法的参数
   //target : 目标对象
   @Override
   public void before(Method method, Object[] args, Object target) throws Throwable {
       System.out.println( target.getClass().getName() + "的" + method.getName() + "方法被执行了");
  }
}
public class AfterLog implements AfterReturningAdvice {
   //returnValue 返回值
   //method被调用的方法
   //args 被调用的方法的对象的参数
   //target 被调用的目标对象
   @Override
   public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
       System.out.println("执行了" + target.getClass().getName()
       +"的"+method.getName()+"方法,"
       +"返回值："+returnValue);
  }
}
```

最后去spring的文件中注册 , 并实现aop切入实现 , 注意导入约束 .

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xmlns:aop="http://www.springframework.org/schema/aop"
      xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop.xsd">

   <!--注册bean-->
   <bean id="userService" class="com.kuang.service.UserServiceImpl"/>
   <bean id="log" class="com.kuang.log.Log"/>
   <bean id="afterLog" class="com.kuang.log.AfterLog"/>

   <!--aop的配置-->
   <aop:config>
       <!--切入点 expression:表达式匹配要执行的方法-->
       <aop:pointcut id="pointcut" expression="execution(* com.kuang.service.UserServiceImpl.*(..))"/>
       <!--执行环绕; advice-ref执行方法 . pointcut-ref切入点-->
       <aop:advisor advice-ref="log" pointcut-ref="pointcut"/>
       <aop:advisor advice-ref="afterLog" pointcut-ref="pointcut"/>
   </aop:config>

</beans>
```

测试

```java
public class MyTest {
   @Test
   public void test(){
       ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
       // 动态代理的是接口 *****注意点*********
       UserService userService = (UserService) context.getBean("userService");
       userService.search();
  }
}
```

Aop的重要性 : 很重要 . 一定要理解其中的思路 , 主要是思想的理解这一块 .

Spring的Aop就是将公共的业务 (日志 , 安全等) 和领域业务结合起来 , 当执行领域业务时 , 将会把公共业务加进来 . 实现公共业务的重复利用 . 领域业务更纯粹 , 程序猿专注领域业务 , 其本质还是动态代理 . 

### **【execution】**

> 例如定义切入点表达式 execution (* com.sample.service.impl..***. \***(..))
>
> execution()是最常用的切点函数，其语法如下所示：
>
> 整个表达式可以分为五个部分：
>
> 1、execution(): 表达式主体。
>
> 2、第一个\*号：表示返回类型， \*号表示所有的类型。
>
> 3、包名：表示需要拦截的包名，后面的两个句点【..】表示当前包和当前包的所有子包，com.sample.service.impl包、子孙包下所有类的方法。
>
> 4、第二个\*号：表示类名，\*号表示所有的类。
>
> 5、*(..):最后这个星号表示方法名，\*号表示所有的方法，后面括弧里面表示方法的参数，两个句点表示任何参数



## **第二种方式**

**自定义类来实现Aop**

目标业务类不变依旧是userServiceImpl

第一步 : 写我们自己的一个切入类

```java
public class DiyPointcut {

   public void before(){
       System.out.println("---------方法执行前---------");
  }
   public void after(){
       System.out.println("---------方法执行后---------");
  }
   
}
```

去spring中配置

```xml
<!--第二种方式自定义实现-->
<!--注册bean-->
<bean id="userService" class="com.kuang.service.UserServiceImpl"/>
<bean id="diy" class="com.kuang.config.DiyPointcut"/>

<!--aop的配置-->
<aop:config>
   <!--第二种方式：使用AOP的标签实现-->
   <aop:aspect ref="diy">
       <aop:pointcut id="diyPonitcut" expression="execution(* com.kuang.service.UserServiceImpl.*(..))"/>
       <aop:before pointcut-ref="diyPonitcut" method="before"/>
       <aop:after pointcut-ref="diyPonitcut" method="after"/>
   </aop:aspect>
</aop:config>
```

测试：

```java
public class MyTest {
   @Test
   public void test(){
       ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
       UserService userService = (UserService) context.getBean("userService");
       userService.add();
  }
}
```



## **第三种方式**

**使用注解实现**

第一步：编写一个注解实现的增强类

```java
package com.kuang.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class AnnotationPointcut {
   @Before("execution(* com.kuang.service.UserServiceImpl.*(..))")
   public void before(){
       System.out.println("---------方法执行前---------");
  }

   @After("execution(* com.kuang.service.UserServiceImpl.*(..))")
   public void after(){
       System.out.println("---------方法执行后---------");
  }

   @Around("execution(* com.kuang.service.UserServiceImpl.*(..))")
   public void around(ProceedingJoinPoint jp) throws Throwable {
       System.out.println("环绕前");
       System.out.println("签名:"+jp.getSignature());
       //执行目标方法proceed
       Object proceed = jp.proceed();
       System.out.println("环绕后");
       System.out.println(proceed);
  }
}
```

第二步：在Spring配置文件中，注册bean，并增加支持注解的配置

```xml
<bean id="userServiceImpl" class="com.kuang.service.UserServiceImpl"/>
<!--第三种方式:注解实现-->
<bean id="annotationPointcut" class="com.kuang.config.AnnotationPointcut"/>
<aop:aspectj-autoproxy/>
```

aop:aspectj-autoproxy：说明

> 通过aop命名空间的<aop:aspectj-autoproxy />声明自动为spring容器中那些配置@aspectJ切面的bean创建代理，织入切面。当然，spring 在内部依旧采用AnnotationAwareAspectJAutoProxyCreator进行自动代理的创建工作，但具体实现的细节已经被<aop:aspectj-autoproxy />隐藏起来了
>
> **<aop:aspectj-autoproxy />有一个proxy-target-class属性，默认为false，表示使用jdk动态代理织入增强**，
>
> 当配为<aop:aspectj-autoproxy  poxy-target-class="true"/>时，表示使用CGLib动态代理技术织入增强。
>
> 不过即使proxy-target-class设置为false，如果目标类没有声明接口，则spring将自动使用CGLib动态代理。



# 附录1

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



# 附录2 

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