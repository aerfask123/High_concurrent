package com.aerfask.netty.study.nio;

import com.aerfask.netty.study.util.DateUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

public class UDPClient {

    public static void main(String[] args) throws IOException {
        new UDPClient().send();
    }

    private void send() throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String next = scanner.next();
            buffer.put((DateUtil.getNow() + " >>" + next).getBytes());
            buffer.flip();

            datagramChannel.send(buffer, new java.net.InetSocketAddress("127.0.0.1", 18899));
            buffer.clear();
        }
        datagramChannel.close();
    }
}
