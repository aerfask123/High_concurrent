package com.aerfask.netty.study.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

@Slf4j
public class UDPServer {

    public void receive() throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();

        datagramChannel.configureBlocking(false);

        datagramChannel.bind(new java.net.InetSocketAddress("127.0.0.1", 18899));
        log.info("UDP Server Started");

        Selector selector = Selector.open();
        datagramChannel.register(selector, SelectionKey.OP_READ);

        while (selector.select() > 0) {
            Iterator<SelectionKey> selectionKeys = selector.selectedKeys().iterator();
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            while (selectionKeys.hasNext()) {
                SelectionKey selectionKey = selectionKeys.next();
                if (selectionKey.isReadable()) {
                    datagramChannel.receive(buffer);
                    buffer.flip();
                    log.info("UDP Server Received: {}", new String(buffer.array(), 0, buffer.limit()));
                    buffer.clear();
                }
            }
            selectionKeys.remove();
        }

        selector.close();
        datagramChannel.close();
    }

    public static void main(String[] args) throws IOException {
        UDPServer udpServer = new UDPServer();
        udpServer.receive();
    }
}
