package com.xxjob.demo;

import com.xxjob.demo.dto.Address;
import org.springframework.util.StopWatch;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestReflective {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Address address = new Address("广东省", "深圳市", "南山区", "深圳湾");
        Class clazz = address.getClass();
        Method method = clazz.getMethod("start");

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 1000000; i++) {
            method.invoke(address);
        }
        stopWatch.stop();

        System.out.println(stopWatch.getTotalTimeMillis());
    }
}
