package com.kuang.service;

public class DiyPointCut {
    public void before(){
        System.out.println("---------方法执行前---------");
    }
    public void after(){
        System.out.println("---------方法执行后---------");
    }
    public void after2(Object rtn){
        System.out.println("---------方法执行后,返回值：" + rtn + "---------");
    }
}
