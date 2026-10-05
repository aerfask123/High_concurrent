package com.aerfask.netty.study.nio;

import com.aerfask.netty.study.util.IOUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

@Slf4j
public class NioSendClient {

    private Charset charset = Charset.forName("UTF-8");

    public void sendFile() throws IOException {
        String srcPath = "F:\\视频教程\\lecture-langchain-20250525\\documents\\nocode.txt";
        log.info("srcPath={}", srcPath);

        String destDecodePath = "src/main/resources/nocode.txt";

        File file = new File(srcPath);
        if (!file.exists()) {
            log.debug("文件不存在");
            return;
        }
        FileChannel fileChannel = new FileInputStream(file).getChannel();
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.socket().connect(new java.net.InetSocketAddress("127.0.0.1", 18899));
        socketChannel.configureBlocking(false);
        while (!socketChannel.finishConnect()) {
            log.debug("正在连接...");
        }

        ByteBuffer buffer = sengFileNameAndLength(destDecodePath, file, socketChannel);

        int length = sendContent(file, fileChannel, socketChannel, buffer);

        if (length == -1) {
            IOUtil.closeQuietly(fileChannel);
            socketChannel.shutdownOutput();
            IOUtil.closeQuietly(socketChannel);
        }
        log.debug("文件发送完毕");
    }

    private int sendContent(File file, FileChannel fileChannel, SocketChannel socketChannel, ByteBuffer buffer) throws IOException {
        log.debug("开始传输文件");
        int length = 0;
        int progress = 0;
        while ((length = fileChannel.read(buffer)) > 0) {
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
            progress += length;
            log.debug("文件传输进度：{}%", progress * 100 / file.length());
        }
        return length;
    }

    private ByteBuffer sengFileNameAndLength(String destDecodePath, File file, SocketChannel socketChannel) throws IOException {
        ByteBuffer fileNameByteBuffer = charset.encode(destDecodePath);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int filenameLength = fileNameByteBuffer.capacity();
        buffer.putInt(filenameLength);
        buffer.flip();
        socketChannel.write(buffer);
        buffer.clear();
        log.info("发送文件名长度：{}", filenameLength);
        socketChannel.write(fileNameByteBuffer);
        log.info("发送文件名：{}", destDecodePath);
        buffer.putLong(file.length());
        buffer.flip();
        socketChannel.write(buffer);
        buffer.clear();
        log.info("发送文件长度：{}", file.length());
        return buffer;
    }
}
