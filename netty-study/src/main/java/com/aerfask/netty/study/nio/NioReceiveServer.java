package com.aerfask.netty.study.nio;

import com.aerfask.netty.study.config.NettyConstants;
import com.aerfask.netty.study.util.IOUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
public class NioReceiveServer {

    private static final String RECEIVE_PATH = NettyConstants.RECEIVE_PATH;

    private Charset charset = Charset.forName("UTF-8");

    static class Client {
        String fileName;

        long fileLength;

        long startTime;

        InetSocketAddress remoteAddress;

        FileChannel outChannel;

        long receiveLength;

        public boolean isFinished() {
            return receiveLength >= fileLength;
        }
    }

    private ByteBuffer buffer = ByteBuffer.allocate(NettyConstants.SERVER_BUFFER_SIZE);

    Map<SelectableChannel, Client> clientMap = new HashMap<>();

    public void startServer() throws Exception {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();

        serverSocketChannel.configureBlocking(false);
        InetSocketAddress address = new InetSocketAddress(18899);

        serverSocket.bind(address);

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        log.info("NioReceiveServer Started");
        while (selector.select() > 0) {
            if (null == selector.selectedKeys()) {
                continue;
            }

            Iterator<SelectionKey> selectionKeys = selector.selectedKeys().iterator();
            while (selectionKeys.hasNext()) {
                SelectionKey key = selectionKeys.next();
                if (key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = server.accept();
                    if (socketChannel == null) {
                        continue;
                    }
                    socketChannel.configureBlocking(false);
                    SelectionKey selectionKey = socketChannel.register(selector, SelectionKey.OP_READ);
                    Client client = new Client();
                    client.remoteAddress = (InetSocketAddress) socketChannel.getRemoteAddress();
                    clientMap.put(socketChannel, client);
                    log.debug("客户端连接成功");
                } else if (key.isReadable()) {
                    processData(key);
                }
                selectionKeys.remove();
            }
        }
    }

    private void processData(SelectionKey key) {

        Client client = clientMap.get(key.channel());
        SocketChannel socketChannel = (SocketChannel) key.channel();
        int num = 0;
        try {
            buffer.clear();
            while ((num = socketChannel.read(buffer)) > 0) {
                buffer.flip();
                if (null == client.fileName) {
                    if (buffer.capacity() < 4) {
                        continue;
                    }
                    int fileNameLen = buffer.getInt();
                    byte[] fileNameBytes = new byte[fileNameLen];
                    buffer.get(fileNameBytes);
                    String fileName = new String(fileNameBytes, charset);

                    File directory = new File(RECEIVE_PATH);

                    if (!directory.exists()) {
                        directory.mkdir();
                    }
                    log.info("接收到文件：{}", fileName);

                    client.fileName = fileName;
                    String fullName = directory.getAbsolutePath() + File.separator + fileName;
                    log.info("文件保存路径：{}", fullName);
                    File file = new File(fullName.trim());
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    FileChannel fileChannel = new FileOutputStream(file).getChannel();
                    client.outChannel = fileChannel;
                    if (buffer.capacity() < 0) {
                        continue;
                    }

                    long fileLength = buffer.getLong();
                    client.fileLength = fileLength;
                    client.startTime = System.currentTimeMillis();
                    log.info("文件大小：{}", fileLength);
                    client.receiveLength += buffer.capacity();
                    if (buffer.capacity() > 0) {
                        client.outChannel.write(buffer);
                    }
                    if (client.isFinished()) {
                        finished(key, client);
                    }
                    buffer.clear();
                } else {
                    client.receiveLength += buffer.capacity();
                    client.outChannel.write(buffer);
                    if (client.isFinished()) {
                        finished(key, client);
                    }
                    buffer.clear();
                }
            }
            key.cancel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (num == -1) {
            finished(key, client);
            buffer.clear();
        }
    }

    private void finished(SelectionKey key, Client client) {
        IOUtil.closeQuietly(client.outChannel);
        log.info("文件接收完成，文件名：{}，文件大小：{}，耗时：{}秒", client.fileName, IOUtil.getFormatFileSize(client.fileLength));
        key.cancel();
        long endTime = System.currentTimeMillis();
    }

    public static void main(String[] args) throws Exception {
        NioReceiveServer server = new NioReceiveServer();
        server.startServer();
    }
}


