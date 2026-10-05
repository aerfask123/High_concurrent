package com.aerfask.netty.study;

public class TestLock {

    public static void main(String[] args) {
        final Object lock = new Object();
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                try {
                    System.out.println("t1 start");
                    Thread.sleep(1000);
                    lock.wait();

                    System.out.println("t1 notify");
                    for (int i = 0; i < 50; i++) {
                        System.out.println("t1 loop " + i);
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        synchronized (lock) {
            lock.notifyAll();

            for (int i = 0; i < 50; i++) {
                System.out.println("main loop " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
