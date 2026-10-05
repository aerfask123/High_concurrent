package com.xxjob.demo;

import com.xxjob.demo.singleton.EnumSingleton;
import com.xxjob.demo.singleton.HungrySingleton;
import com.xxjob.demo.singleton.LazySingleton;
import com.xxjob.demo.singleton.StaticInnerSingleton;
import org.junit.jupiter.api.Test;

public class SingletonTest {

    @Test
    public void testSingleton() {
        HungrySingleton singleton = HungrySingleton.getInstance();
        EnumSingleton singleton1 = EnumSingleton.SINGLETON;
        LazySingleton singleton2 = LazySingleton.getInstance();
        StaticInnerSingleton singleton3 = StaticInnerSingleton.getInstance();
    }
}
