package com.kuang.pojo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class Address {
    private String zipCode;
    private String address;

    public Address(String zipCode, String address) {
        this.zipCode = zipCode;
        this.address = address;
    }

    public Address() {
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Address{" +
                "zipCode='" + zipCode + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
