package com.aerfask.jvm.memory;

public class JavaVMStackOOM {
    int threadCount = 0;

    private void dontStop() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                
            }
        }
    }

    public void stackLeakByThread() {
        while (true) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    threadCount++;
                    dontStop();
                }
            });
            thread.start();
        }
    }

    public static void main(String[] args) {
        JavaVMStackOOM oom = new JavaVMStackOOM();
        try {
            oom.stackLeakByThread();
        } catch (Throwable e) {
            System.out.println("stack length:" + oom.threadCount);
            throw e;
        }
    }
}
