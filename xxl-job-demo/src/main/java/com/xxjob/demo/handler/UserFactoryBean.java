package com.xxjob.demo.handler;

import com.xxjob.demo.dto.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class UserFactoryBean implements FactoryBean<User> {

    @PostConstruct
    public void init() {
        System.out.println("UserFactoryBean init");
    }

    @Override
    public User getObject() throws Exception {
        return new User(100, "aerfask", "123456", 18);
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
