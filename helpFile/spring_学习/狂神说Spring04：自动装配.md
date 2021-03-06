# **狂神说Spring04：自动装配**

秦疆 [狂神说](javascript:void(0);) *2020-04-19*

> 狂神说Spring系列连载课程，通俗易懂，基于Spring最新版本，欢迎各位狂粉转发关注学习。禁止随意转载，转载记住贴出B站视频链接及公众号链接！

![图片](https://mmbiz.qpic.cn/mmbiz_gif/uJDAUKrGC7L1vFQMnaRIJSmeZ58T2eZicAHqMeOptckiacohSnX6DTIYSic2Uic7GLWuezVDk3bYqJa4vQwPwrLJXQ/640?wx_fmt=gif&tp=webp&wxfrom=5&wx_lazy=1)





Bean的自动装配

## 1，自动装配说明

### 1-1 说明

- 自动装配是使用spring满足bean依赖的一种方法
- spring会在应用上下文中为某个bean寻找其依赖的bean。

Spring中bean有三种装配机制，分别是：

1. 在xml中显式配置；
2. 在java中显式配置；
3. 隐式的bean发现机制和自动装配。

这里我们主要讲第三种：自动化的装配bean。

Spring的自动装配需要从两个角度来实现，或者说是两个操作：

1.  **组件扫描**(component scanning)：spring会自动发现应用上下文中所创建的bean；
2.  **自动装配**(autowiring)：spring自动满足bean之间的依赖，也就是我们说的IoC/DI；

组件扫描和自动装配组合发挥巨大威力，使得显示的配置降低到最少。

**推荐不使用自动装配xml配置 , 而使用注解 .**

**
**

### 1-2 测试环境搭建

1、新建一个项目

2、新建两个实体类，Cat  Dog  都有一个叫的方法

```java
public class Cat {
   public void shout() {
       System.out.println("miao~");
  }
}
public class Dog {
   public void shout() {
       System.out.println("wang~");
  }
}
```

3、新建一个用户类 User

```java
public class User {
   private Cat cat;
   private Dog dog;
   private String str;
}
```

4、编写Spring配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

   <bean id="dog" class="com.Dog"/>
   <bean id="cat" class="com.Cat"/>

   <bean id="user" class="com.User">
       <property name="cat" ref="cat"/>
       <property name="dog" ref="dog"/>
       <property name="str" value="qinjiang"/>
   </bean>
</beans>
```

5、测试

```java
public class MyTest {
   @Test
   public void testMethodAutowire() {
       ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
       User user = (User) context.getBean("user");
       user.getCat().shout();
       user.getDog().shout();
  }
}
```

结果正常输出，环境OK

## 2，方式1，在xml中显式配置；

byName和byType自动装配

### 2-1，byName

**autowire byName (按名称自动装配)**

由于在手动配置xml过程中，常常发生字母缺漏和大小写等错误，而无法对其进行检查，使得开发效率降低。

采用自动装配将避免这些错误，并且使配置简单化。

测试：

1、修改bean配置，增加一个属性  autowire="byName"

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

   <bean id="dog" class="com.Dog"/>
   <bean id="cat" class="com.Cat"/>

<!--
	<bean id="user" class="com.User">
       <property name="cat" ref="cat"/>
       <property name="dog" ref="dog"/>
       <property name="str" value="qinjiang"/>
   </bean>
-->
<bean id="user" class="com.User" autowire="byName">
   <property name="str" value="qinjiang"/>
</bean>
</beans>
```

2、再次测试，结果依旧成功输出！



3、bean的Id不对，会报错

​     我们将 cat 的bean id修改为 catXXX

```xml
<bean id="dog" class="com.Dog"/>
<bean id="cat111" class="com.Cat"/>// 修改

<bean id="user" class="com.User" autowire="byName">
   <property name="str" value="qinjiang"/>
</bean>
```

4、时报空指针java.lang.NullPointerException。

因为按byName规则找不对应set方法，真正的setCat就没执行，对象就没有初始化，所以调用时就会报空指针错误。



5，出现多个类型相同的bean时不会报错，只要有对的beanId就行

```xml
<bean id="dog" class="com.Dog"/>// ID正常
<bean id="cat" class="com.Cat"/>// ID正常
<bean id="cat111" class="com.Cat"/>// 追加

<bean id="user" class="com.User" autowire="byName">
   <property name="str" value="qinjiang"/>
</bean>
```

 

**小结：**

当一个bean节点带有 autowire byName的属性时。

1. 将查找其类中所有的set方法名，例如setCat，获得将set去掉并且首字母小写的字符串，即cat。

2. 去spring容器中寻找是否有此字符串名称BeanID的对象。

3. 如果有，就取出注入；如果没有，就报空指针异常。

   

### 2-2，byType

**autowire byType (按类型自动装配)**

使用autowire byType首先需要保证：**同一类型的对象，在spring容器中唯一**。如果不唯一，会报不唯一的异常。

​		**NoUniqueBeanDefinitionException**

测试：

1、将user的bean配置修改一下 ： autowire="byType"

2、测试，正常输出

3、在注册一个cat 的bean对象！

```xml
<bean id="dog" class="com.Dog"/>
<bean id="cat" class="com.Cat"/>
<bean id="cat2" class="com.Cat"/> // 重复的Bean类型 会报错

<bean id="user" class="com.User" autowire="byType">
   <property name="str" value="qinjiang"/>
</bean>
```

4、测试，报错：NoUniqueBeanDefinitionException

5、删掉cat2，将cat的bean名称改掉！测试！因为是按类型装配，所以并不会报异常，也不影响最后的结果。甚至将id属性去掉，也不影响结果。

```xml
<bean id="dog" class="com.Dog"/>

<bean id="cat2" class="com.Cat"/> // bean的类型正确， id不对， 执行正常。

<bean id="user" class="com.User" autowire="byType">
   <property name="str" value="qinjiang"/>
</bean>
```



这就是按照类型自动装配！

## 3, 方式2： 在java中显式配置；使用注解

> 使用注解

jdk1.5开始支持注解，spring2.5开始全面支持注解。

准备工作：利用注解的方式注入属性。

1、在spring配置文件中引入context文件头

```xml
xmlns:context="http://www.springframework.org/schema/context"

http://www.springframework.org/schema/context
http://www.springframework.org/schema/context/spring-context.xsd
```

2、开启属性注解支持！

```xml
<context:annotation-config/>
```

####  

#### @Autowired

- @**Autowired是按类型自动转配**的，不支持id匹配。

- 需要导入 spring-aop的包！（高版本的spring-mvc的中已经包含，不需要单独导入）

  

测试：

1、将User类中的set方法去掉，使用@Autowired注解

```java
public class User {
   @Autowired
   private Cat cat;
   @Autowired
   private Dog dog;
   private String str;

   public Cat getCat() {
       return cat;
  }
   public Dog getDog() {
       return dog;
  }
   public String getStr() {
       return str;
  }
}
```

2、此时配置文件内容

```xml
<context:annotation-config/>

<bean id="dog" class="com.Dog"/>
<bean id="cat" class="com.Cat"/>
    <!--    没有和 注入类型名【cat】一致的id的bean时， 不能定义多个该类型的bean，加上@Qualifier则可以-->
<bean id="cat22" class="com.kuang.pojo.Cat"/>
<bean id="user" class="com.User"/>
```

3、测试，成功输出结果！



4,  没有和 注入类型名【cat】一致的id的bean时， 不能定义多个该类型的bean，加上@Qualifier则可以

```xml
<context:annotation-config/>

<bean id="dog" class="com.Dog"/>
<bean id="cat111" class="com.Cat"/>
    <!--    没有和 注入类型名【cat】一致的id的bean时， 不能定义多个该类型的bean，加上@Qualifier则可以-->
<bean id="cat22" class="com.kuang.pojo.Cat"/>
<bean id="user" class="com.User"/>
```





【小狂神科普时间】

@Autowired(required=false)  说明：false，对象可以为null；true，对象必须存对象，不能为null。

```java
//如果允许对象为null，设置required = false,默认为true
@Autowired(required = false)
private Cat cat;
```



####  

#### @Qualifier

- **@Autowired**是根据**类型自动装配**的，
- **加上@Qualifier**则可以根据**byName**的方式自动装配
- @Qualifier**不能单独使用**。

测试实验步骤：

1、配置文件修改内容，保证类型存在对象。且名字不为类的默认名字！

```xml
<bean id="dog1" class="com.Dog"/>
<bean id="dog2" class="com.Dog"/>

<!--    没有和 注入类型名【cat】一致的id的bean时， 不能定义多个该类型的bean，加上@Qualifier则可以-->
<bean id="cat1" class="com.Cat"/>
<bean id="cat2" class="com.Cat"/>
```

2、没有加Qualifier测试，直接报错

3、在属性上添加Qualifier注解, 会根据value值去查找beanID

```java
@Autowired
@Qualifier(value = "cat2")
private Cat cat;
@Autowired
@Qualifier(value = "dog2")
private Dog dog;
```

测试，成功输出！

####  

#### @Resource

- @Resource属于J2EE的包，java 11 版本 resource从jdk中删除了，需要手动添加包 或者使用JDK8版本

```xml
<dependency>
    <groupId>javax.annotation</groupId>
    <artifactId>javax.annotation-api</artifactId>
    <version>1.3.2</version>
</dependency>
```



- @Resource如有指定的name属性，先按该属性进行byName方式查找装配；
- 其次再进行**默认的byName**方式进行装配；
- 如果以上都不成功，则按byType的方式自动装配。
- 都不成功，则报异常。

实体类：

```java
public class User {
   //如果允许对象为null，设置required = false,默认为true
   // 去找beanId为cat2 的bean
    @Resource(name = "cat2")
   private Cat cat;
   @Resource
   private Dog dog;
   private String str;
}
```

beans.xml

```xml
<bean id="dog" class="com.Dog"/>
<bean id="cat1" class="com.Cat"/>
<bean id="cat2" class="com.Cat"/> //

<bean id="user" class="com.User"/>
```

测试：结果OK

配置文件2：beans.xml ， 删掉cat2

```xml
<bean id="dog" class="com.Dog"/>
<bean id="cat1" class="com.Cat"/>
```

实体类上只保留注解

```java
@Resource
private Cat cat; //先进行byName查找，失败；再进行byType查找，成功。
@Resource
private Dog dog;
```

结果：OK

结论：先进行byName查找，失败；再进行byType查找，成功。

###  

### 小结

@Autowired与@Resource异同：

1、@Autowired与@Resource都可以用来装配bean。都可以写在字段上，或写在setter方法上。

2、@Autowired默认按类型装配（属于spring规范），默认情况下必须要求依赖对象必须存在，如果要允许null 值，可以设置它的required属性为false，如：@Autowired(required=false) ，如果我们想使用名称装配可以结合@Qualifier注解进行使用

3、@Resource（属于J2EE复返），默认按照名称进行装配，名称可以通过name属性进行指定。如果没有指定name属性，当注解写在字段上时，默认取字段名进行按照名称查找，如果注解写在setter方法上默认取属性名进行装配。当找不到与名称匹配的bean时才按照类型进行装配。但是需要注意的是，如果name属性一旦指定，就只会按照名称进行装配。

它们的作用相同都是用注解方式注入对象，但执行顺序不同。@Autowired先byType，@Resource先byName。

![图片](https://mmbiz.qpic.cn/mmbiz_png/uJDAUKrGC7JicnvW4708YZgXPQAcr3JTia8Y39JMY2G6jbR5C8NP2ecF7ocDpwNU2XeCHKga62ToC8SKrbGnJRiaw/640?wx_fmt=gif&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

视频同步更新

如果觉得帮助到了您，不妨赞赏支持一下吧！

![图片](https://mmbiz.qpic.cn/mmbiz_jpg/uJDAUKrGC7KaCZTnzpTQ4y0unN9icJaRPdGy06vUfzQgzpibBctoiaZbTiaVibavlK6Ww0OIavHmSBf5luzDibthmgBA/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

![图片](https://mmbiz.qpic.cn/mmbiz_jpg/uJDAUKrGC7LBEiaxgibdgic7wYWNIvwhj8xsu8hCvVFXOgVZ3icwleHSeDiaeAZjqA8FhpUxUCumevPok6qViaU2e2Ng/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

喜欢此内容的人还喜欢

[饶毅：55年前，袁隆平发表论文的意义饶毅：55年前，袁隆平发表论文的意义...知识分子不喜欢不看的原因确定内容质量低 不看此公众号](javascript:void(0);)[默哀！袁隆平院士逝世默哀！袁隆平院士逝世...侠客岛不喜欢不看的原因确定内容质量低 不看此公众号](javascript:void(0);)[蕲春浠水对比，谁更具有优势？蕲春浠水对比，谁更具有优势？...蕲阳号外不喜欢不看的原因确定内容质量低 不看此公众号](javascript:void(0);)

![img](https://mp.weixin.qq.com/mp/qrcode?scene=10000004&size=102&__biz=Mzg2NTAzMTExNg==&mid=2247484114&idx=1&sn=e5c923959587068e5cbeaf0fe6971912&send_time=)

微信扫一扫
关注该公众号