package com.aerfask.netty.study.nio;

import com.aerfask.netty.study.util.IOUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Slf4j
public class FileNIOCopyDemo {

    public static void main(String[] args) {
        nioCopyResourceFile();
    }

    private static void nioCopyResourceFile() {
        String srcPath = "F:\\视频教程\\lecture-langchain-20250525\\documents\\nocode.txt";
        log.info("srcPath={}", srcPath);

        String destDecodePath = "src/main/resources/nocode.txt";
        log.info("destDecodePath={}", destDecodePath);

        nioCopyFile(srcPath, destDecodePath);
    }

    private static void nioCopyFile(String srcPath, String destDecodePath) {
        File srcFile = new File(srcPath);
        File destFile = new File(destDecodePath);
        try {
            if (!destFile.exists()) {
                destFile.createNewFile();
            }
            long startTime = System.currentTimeMillis();
            FileInputStream fis = null;
            FileOutputStream fos = null;
            FileChannel inChannel = null;
            FileChannel outChannel = null;
            try {
                fis = new FileInputStream(srcFile);
                fos = new FileOutputStream(destFile);
                inChannel = fis.getChannel();
                outChannel = fos.getChannel();
                int length = -1;
                ByteBuffer buf = ByteBuffer.allocate(1024);
                while ((length = inChannel.read(buf)) != -1) {
                    buf.flip();
                    int outlength = 0;
                    while ((outlength = outChannel.write(buf)) != 0) {
                        log.info("写入的字节数，outlength={}", outlength);
                    }
                    buf.clear();
                }
                outChannel.force(true);
            } finally {
                IOUtil.closeQuietly(inChannel);
                IOUtil.closeQuietly(outChannel);
                IOUtil.closeQuietly(fis);
                IOUtil.closeQuietly(fos);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
