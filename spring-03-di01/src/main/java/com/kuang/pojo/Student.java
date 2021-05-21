package com.kuang.pojo;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Student {
    private String name;
    private Address address;
    private String[] books;
    private List<String> hobbes;
    private Map<String, String> cards;
    private Set<String> games;
    private String wifeName;
    private Properties info;
    private boolean marry;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String[] getBooks() {
        return books;
    }

    public void setBooks(String[] books) {
        this.books = books;
    }

    public List<String> getHobbes() {
        return hobbes;
    }

    public void setHobbes(List<String> hobbes) {
        this.hobbes = hobbes;
    }

    public Map<String, String> getCards() {
        return cards;
    }

    public void setCards(Map<String, String> cards) {
        this.cards = cards;
    }

    public Set<String> getGames() {
        return games;
    }

    public void setGames(Set<String> games) {
        this.games = games;
    }

    public String getWifeName() {
        return wifeName;
    }

    public void setWifeName(String wifeName) {
        this.wifeName = wifeName;
    }

    public Properties getInfo() {
        return info;
    }

    public void setInfo(Properties info) {
        this.info = info;
    }

    public boolean isMarry() {
        return marry;
    }

    public void setMarry(boolean marry) {
        this.marry = marry;
    }

    public void show() {
        System.out.println("Name:" + name + "\nAddress:" + address.getAddress() + "\nBooks:[" + getBookNameList());
        System.out.println("爱好:"+ hobbes);

        System.out.println("games:"+ games);

        System.out.println("cards:"+ cards);

        System.out.println("info:" + info);

        System.out.println("wife:"+ wifeName);

        System.out.println("婚姻状况：" + (marry ? "已婚" : "未婚"));
    }

    private String getBookNameList() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < books.length; i++) {
            if (i == 0) {
                str = str.append(books[i]);
            } else {
                str = str.append(",").append(books[i]);
            }

            if (i==books.length -1) {
                str = str.append("]");
            }

        }
        return str.toString();
    }
}
