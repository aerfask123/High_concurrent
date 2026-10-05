package com.xxjob.demo.dto;

import lombok.Data;

@Data
public class Address {

    private String province;

    private String city;

    private String area;

    private String detail;

    public Address() {
    }

    public Address(String province, String city, String area, String detail) {
        this.province = province;
        this.city = city;
        this.area = area;
        this.detail = detail;
    }

    public void start() {
        System.out.println("address start.........................");
    }
}
