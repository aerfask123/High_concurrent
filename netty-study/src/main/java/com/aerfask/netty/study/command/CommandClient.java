package com.aerfask.netty.study.command;

import org.springframework.stereotype.Service;

@Service("CommandClient")
public class CommandClient {

    public void startCommandThread() {
        Thread.currentThread().setName("主线程");
        while (true) {

        }
    }
}
