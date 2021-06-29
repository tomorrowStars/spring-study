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

```
<!-- https://mvnrepository.com/artifact/org.aspectj/aspectjweaver -->
<dependency>
   <groupId>org.aspectj</groupId>
   <artifactId>aspectjweaver</artifactId>
   <version>1.9.4</version>
</dependency>
```

# 3, 实现方式

## **第一种方式**

**通过 Spring API 实现**

首先编写我们的业务接口和实现类

```
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

```
public class Log implements MethodBeforeAdvice {

   //method : 要执行的目标对象的方法
   //objects : 被调用的方法的参数
   //Object : 目标对象
   @Override
   public void before(Method method, Object[] objects, Object o) throws Throwable {
       System.out.println( o.getClass().getName() + "的" + method.getName() + "方法被执行了");
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

```
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

```
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

```
<!--第二种方式自定义实现-->
<!--注册bean-->
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

```
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

```
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

```
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





到了这里，AOP的思想和使用相信大家就没问题了！



![图片](https://mmbiz.qpic.cn/mmbiz_png/uJDAUKrGC7JicnvW4708YZgXPQAcr3JTia8Y39JMY2G6jbR5C8NP2ecF7ocDpwNU2XeCHKga62ToC8SKrbGnJRiaw/640?wx_fmt=gif&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

视频同步更新

如果觉得帮助到了您，不妨赞赏支持一下吧！

![图片](https://mmbiz.qpic.cn/mmbiz_jpg/uJDAUKrGC7KaCZTnzpTQ4y0unN9icJaRPdGy06vUfzQgzpibBctoiaZbTiaVibavlK6Ww0OIavHmSBf5luzDibthmgBA/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

![图片](https://mmbiz.qpic.cn/mmbiz_jpg/uJDAUKrGC7LBEiaxgibdgic7wYWNIvwhj8xsu8hCvVFXOgVZ3icwleHSeDiaeAZjqA8FhpUxUCumevPok6qViaU2e2Ng/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

喜欢此内容的人还喜欢

[严惩！高层女住户往楼下狂扔100多件东西严惩！高层女住户往楼下狂扔100多件东西...青小小不喜欢不看的原因确定内容质量低 不看此公众号](javascript:void(0);)[优质基金经理之朱少醒：十年磨一剑！优质基金经理之朱少醒：十年磨一剑！...青橙的养基记录仪不喜欢不看的原因确定内容质量低 不看此公众号](javascript:void(0);)[一个农村博士的独白：全家为什么只有我读到了博士一个农村博士的独白：全家为什么只有我读到了博士...CodeSheep不喜欢不看的原因确定内容质量低 不看此公众号](javascript:void(0);)

![img](https://mp.weixin.qq.com/mp/qrcode?scene=10000004&size=102&__biz=Mzg2NTAzMTExNg==&mid=2247484138&idx=1&sn=9fb187c7a2f53cc465b50d18e6518fe9&send_time=)

微信扫一扫
关注该公众号