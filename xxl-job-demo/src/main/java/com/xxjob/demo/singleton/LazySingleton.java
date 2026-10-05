package com.xxjob.demo.singleton;

public class LazySingleton {

    private static LazySingleton instance;

    private LazySingleton() {
        if (instance != null) {
            throw new RuntimeException("单例已被创建");
        }
        System.out.println("LazySingleton init");
    }

    public static LazySingleton getInstance() {
        if (instance == null) {
            synchronized (LazySingleton.class) {
                if (instance == null) {
                    instance = new LazySingleton();
                }
            }
        }
        System.out.println("LazySingleton:" + instance);
        return instance;
    }
}
