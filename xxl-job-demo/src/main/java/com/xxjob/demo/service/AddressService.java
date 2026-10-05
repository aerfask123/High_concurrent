package com.xxjob.demo.service;

import com.xxjob.demo.dto.Address;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Lazy
@Service
public class AddressService implements BeanNameAware {

    @Autowired
    private UserService userService;

    @Autowired
    @Lazy
    private Address address;

    public Address getAddress(Integer id) {
        System.out.println("address:" + address);
        return address;
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("beanName:" + name);
    }
}
