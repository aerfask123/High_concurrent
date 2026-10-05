package com.xxjob.demo.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("address");
        beanDefinition.setInitMethodName("start");
        DefaultListableBeanFactory bf = (DefaultListableBeanFactory) beanFactory;
        String[] names = bf.getBeanDefinitionNames();
        log.info("----------------------------------开始打印BeanDefinition------------------------------------");
        for (String name : names) {
            BeanDefinition definition = bf.getBeanDefinition(name);
            log.info("Name:{},Definition:{}", name, definition.getBeanClassName());
        }
        log.info("----------------------------------结束打印BeanDefinition------------------------------------");
        
    }
}
