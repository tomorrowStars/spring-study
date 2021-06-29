package com.kuang.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@EnableAspectJAutoProxy // 方式四：可以代替xml中的<aop:aspectj-autoproxy/>
@Component
public class AnnotationPointcut {

    // 自定义多个切入点可以重复利用
    @Pointcut(value = "execution(* com.kuang.service.UserServiceImpl.*(..))")
    public void pointcut1() {}

//    @Before("execution(* com.kuang.service.UserServiceImpl.*(..))")
    @Before(value = "pointcut1()")
    public void before() {
        System.out.println("方法执行前");
    }

    @After("execution(* com.kuang.service.UserServiceImpl.*(..) )")
    public void after() {
        System.out.println("方法执行之后");
    }

    @Around("execution(* com.kuang.service.UserServiceImpl.*(..) )")
    public void around(ProceedingJoinPoint jp) throws Throwable {
        System.out.println(" 环绕前");
        System.out.println("签名：" + jp.getSignature());
        // 执行目标方法proceed
        Object result = jp.proceed();

        System.out.println(" 环绕后");
        System.out.println("执行结果：" + result);
    }

    @AfterReturning(returning = "rtn", pointcut = "execution(* com.kuang.service.UserServiceImpl.add(..))")
    public void afterRtn(int rtn) throws Throwable {
        System.out.println("获取目标方法返回值:" + rtn);
    }

}
