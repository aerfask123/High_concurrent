package com.xxjob.demo.dto;

import lombok.Data;

@Data
public class User {
    public User() {
    }

    public User(Integer id, String name, String password, Integer age) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.age = age;
    }


    private Integer id;

    private String name;

    private String password;

    private Integer age;
}
