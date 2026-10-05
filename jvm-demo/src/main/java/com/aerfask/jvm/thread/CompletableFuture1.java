package com.aerfask.jvm.thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFuture1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("Async 运行完了");
                return 1;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
//        ExecutorService executorService = Executors.newFixedThreadPool(1);
//        executorService.execute(() -> {
//            System.out.println("t 运行中");
//            while (true) {
//
//            }
//            System.out.println("t 运行完了");
//        });

//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    System.out.println("t 运行中");
//                    Thread.sleep(2000);
//                    System.out.println("t 运行完了");
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//        t.start();
        System.out.println("开始等待了");
        System.out.println("main 运行中");
        Thread.sleep(500);
        System.out.println("main 运行完了");
        future.get();
        System.out.println("main 结束");
    }
}
