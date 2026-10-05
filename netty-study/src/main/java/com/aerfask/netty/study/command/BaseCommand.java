package com.aerfask.netty.study.command;

import java.util.Scanner;

public interface BaseCommand {

    String getKey();

    String getTip();

    void exec(Scanner scanner);
}
