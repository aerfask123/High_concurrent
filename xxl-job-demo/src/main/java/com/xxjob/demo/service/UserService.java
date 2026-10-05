package com.xxjob.demo.service;

import com.xxjob.demo.annotation.Cached;
import com.xxjob.demo.dto.User;
import com.xxjob.demo.handler.UserFactoryBean;
import com.xxjob.demo.service.interf.CarInterface;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserService {

    @Autowired
    @Qualifier("benz")
    private CarInterface car;

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserFactoryBean userBean;

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        car.go();
    }

    @Cached(key = "user:getUser:", expire = 60, title = "获取用户信息", logService = "operLogServiceImpl", isSaveRequestData = true, isTrack = true)
    public User getUser(Integer id) throws InterruptedException {
        Thread.sleep(3);
        return new User(id, "张三", "123456", 18);
    }

    @Transactional
    public User getCustomer(Integer id) {

        log.info("userFactoryBean:{}", applicationContext.getBean("userFactoryBean"));
        log.info("&userFactoryBean:{}", applicationContext.getBean("&userFactoryBean"));
        try {
            log.info("userBean:{}", userBean.getObject());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new User(id, "李四", "123456", 18);
    }
}
