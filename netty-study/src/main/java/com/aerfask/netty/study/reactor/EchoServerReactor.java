package com.aerfask.netty.study.reactor;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

@Slf4j
public class EchoServerReactor implements Runnable {

    Selector selector;
    ServerSocketChannel serverSocketChannel;

    EchoServerReactor() throws IOException {
        selector = Selector.open();  // epoll_create
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new java.net.InetSocketAddress(18899));
        serverSocketChannel.configureBlocking(false);
        // epoll_ctl
        SelectionKey key = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        key.attach(new AcceptorHandler());
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                // epoll_wait
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    dispatch(key);
                    it.remove();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void dispatch(SelectionKey key) {
        Runnable handler = (Runnable) key.attachment();
        if (handler != null) {
            handler.run();
        }
    }

    class AcceptorHandler implements Runnable {

        @Override
        public void run() {
            SocketChannel socketChannel = null;
            try {
                socketChannel = serverSocketChannel.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (socketChannel != null) {
                try {
                    new EchoHandler(selector, socketChannel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class EchoHandler implements Runnable {
        final SocketChannel socketChannel;
        final SelectionKey selectionKey;

        final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        static final int RECEIVING = 0, SENDING = 1;
        int state = RECEIVING;

        EchoHandler(Selector selector, SocketChannel socketChannel) throws IOException {
            this.socketChannel = socketChannel;

            socketChannel.configureBlocking(false);

            selectionKey = socketChannel.register(selector, 0);
            selectionKey.interestOps(SelectionKey.OP_READ);
            selectionKey.attach(this);
            selector.wakeup();
        }

        @Override
        public void run() {
            try {
                if (state == SENDING) {
                    socketChannel.write(byteBuffer);
                    byteBuffer.clear();
                    selectionKey.interestOps(SelectionKey.OP_READ);
                    state = RECEIVING;
                } else if (state == RECEIVING) {
                    int length = 0;
                    while ((length = socketChannel.read(byteBuffer)) > 0) {
                        log.info("收到客户端消息：{}", new String(byteBuffer.array(), 0, length));
                    }
                    byteBuffer.flip();
                    selectionKey.interestOps(SelectionKey.OP_WRITE);
                    state = SENDING;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        EchoServerReactor echoServerReactor = new EchoServerReactor();
        new Thread(echoServerReactor).start();
    }
}
