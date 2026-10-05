package com.aerfask.netty.study.command;

import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class LoginConsoleCommand implements BaseCommand {

    public static final String KEY = "1";

    private String userName;

    private String password;

    @Override
    public String getKey() {
        return "";
    }

    @Override
    public String getTip() {
        return "";
    }

    @Override
    public void exec(Scanner scanner) {
        log.info("请输入用户信息(id:password) ");
        String[] info = null;
        while (true) {
            String input = scanner.next();
            info = input.split(":");
            if (info.length != 2) {
                log.info("请输入正确的用户信息(id:password) ");
            } else {
                break;
            }
        }
        userName = info[0];
        password = info[1];
    }
}
