package com.xxjob.demo.processor;

import com.xxjob.demo.dto.Address;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class UserPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Address) {
            ((Address) bean).setDetail("深圳湾");
        }
        return bean;
    }
}
