package com.xxjob.demo.controller;

import com.xxjob.demo.dto.Address;
import com.xxjob.demo.dto.User;
import com.xxjob.demo.service.AddressService;
import com.xxjob.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final AddressService addressService;

    @GetMapping("/getUser/{id}")
    public User getUser(@PathVariable Integer id) throws InterruptedException {
        return userService.getUser(1);
    }

    @GetMapping("/getCustomer/{id}")
    public User getCustomer(@PathVariable Integer id) {
        return userService.getCustomer(2);
    }

    @GetMapping("/getAddress/{id}")
    public Address getAddress(@PathVariable Integer id) {
        return addressService.getAddress(id);
    }
}
