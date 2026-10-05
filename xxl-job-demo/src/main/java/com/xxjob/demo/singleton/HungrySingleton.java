package com.xxjob.demo.singleton;

public class HungrySingleton {

    private static final HungrySingleton instance = new HungrySingleton();

    private HungrySingleton() {
        if (instance != null) {
            throw new RuntimeException("单例构造器禁止反射调用");
        }
        System.out.println("Hungry单例构造器被调用");
    }

    public static HungrySingleton getInstance() {
        System.out.println("Hungry单例被获取");
        return instance;
    }
}
