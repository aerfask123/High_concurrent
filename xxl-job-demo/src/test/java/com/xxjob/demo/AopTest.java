package com.xxjob.demo;

import com.xxjob.demo.service.UserService;

public class AopTest {

    public static void main(String[] args) throws Exception {

        UserService userService = new UserService();
        userService.getUser(1);
    }
}
