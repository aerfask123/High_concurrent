package com.aerfask.jvm.thread;

public class Test1 {
    public static void main(String[] args) throws InterruptedException {
        int i = 1;
        System.out.println(~i);

        byte postSpins = 1;
        byte spin = (byte) ((postSpins << 1) | 1);
        System.out.println(spin);
        return;

//        CountDownLatch countDownLatch = new CountDownLatch(5);
//
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//        for (int j = 0; j < 5; j++) {
//            executorService.execute(() -> {
//                System.out.println("hello");
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                countDownLatch.countDown();
//            });
//        }
//
//        countDownLatch.await();
//
//        System.out.println("主线程完成");
    }
}
