package com.kuang.pojo;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Student {
    private String name;
    private Address address;
    private String[] books;
    private List<String> hobbys;
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

    public List<String> getHobbys() {
        return hobbys;
    }

    public void setHobbys(List<String> hobbys) {
        this.hobbys = hobbys;
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
        System.out.println("name:" + name + ",address:" + address.getAddress() + "Books:[" + getBookNameList());
        System.out.println("\n爱好:"+hobbys);

        System.out.println("card:"+ cards);

        System.out.println("games:"+ games);

        System.out.println("wife:"+ wifeName);

        System.out.println("info:" + info);
    }

    private String getBookNameList() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < books.length; i++) {
            if (i == 0) {
                str = str.append(books[0]);
            } else {
                str = str.append(",").append(books[0]);
            }
        }
        return str.toString();
    }
}
