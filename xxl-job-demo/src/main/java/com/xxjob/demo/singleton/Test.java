package com.xxjob.demo.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Test {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        System.out.println("开始测试单例模式");
        Class.forName(HungrySingleton.class.getName());
        Class.forName(StaticInnerSingleton.class.getName());
        Class.forName(EnumSingleton.class.getName());

        Class<?> clazz = java.lang.Class.forName(LazySingleton.class.getName());
        Constructor constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        LazySingleton o = (LazySingleton) constructor.newInstance();
        LazySingleton.getInstance();
    }
}
