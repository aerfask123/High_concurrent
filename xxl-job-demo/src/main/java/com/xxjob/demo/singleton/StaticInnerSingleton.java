package com.xxjob.demo.singleton;

public class StaticInnerSingleton {

    private static class Holder {
        private static StaticInnerSingleton instance = new StaticInnerSingleton();
    }

    private StaticInnerSingleton() {
        if (Holder.instance != null) {
            throw new RuntimeException("StaticInnerSingleton不允许创建多个实例");
        }
        System.out.println("StaticInnerSingleton init");
    }

    public static StaticInnerSingleton getInstance() {
        System.out.println("StaticInnerSingleton get");
        return Holder.instance;
    }
}
