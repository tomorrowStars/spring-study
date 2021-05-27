package com.kuang.pojo;

/**
 * 代理角色：中介
 */
public class Proxy implements Rent {
    private Host host;

    public Proxy(Host host) {
        this.host = host;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    //租房
    public void rent() {
        seeHouse();
        this.host.rent();
        fare();
    }

    //看房
    public void seeHouse() {
        System.out.println("看房子");
    }

    //收房租
    public void fare() {
        System.out.println("收房租");
    }
}
