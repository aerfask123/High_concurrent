package com.xxjob.demo.service;

import com.xxjob.demo.service.interf.CarInterface;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Component;

@Component
public class Porsche implements CarInterface, BeanNameAware {
    private String name;

    @Override
    public void go() {
        System.out.println(name + " Porsche go");
    }

    @PostConstruct
    public void init() {
        System.out.println("Porsche init");
    }

    @Override
    public void setBeanName(String name) {
        this.name = name;
    }
}
