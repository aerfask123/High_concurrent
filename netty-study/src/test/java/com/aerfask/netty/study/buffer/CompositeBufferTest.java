package com.aerfask.netty.study.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class CompositeBufferTest {
    static Charset utf8 = Charset.forName("UTF-8");

    @Test
    public void byteBufferComposite() {
        CompositeByteBuf cbuf = ByteBufAllocator.DEFAULT.compositeBuffer();
        ByteBuf headerBuf = Unpooled.copiedBuffer("疯狂创客圈：", utf8);
        ByteBuf bodyBuf = Unpooled.copiedBuffer("高性能Netty", utf8);
        cbuf.addComponents(headerBuf, bodyBuf);
        sendMsg(cbuf);
        headerBuf.retain();
        cbuf.release();
        cbuf = ByteBufAllocator.DEFAULT.compositeBuffer();
        bodyBuf = Unpooled.copiedBuffer("高性能学习社群", utf8);
        cbuf.addComponents(headerBuf, bodyBuf);
        sendMsg(cbuf);
        cbuf.release();
    }

    private void sendMsg(CompositeByteBuf cbuf) {
        for (ByteBuf buf : cbuf) {
            int length = buf.readableBytes();
            byte[] array = new byte[length];
            buf.getBytes(buf.readerIndex(), array);
            System.out.print(new String(array, utf8));
        }
        System.out.println();
    }

    @Test
    public void initCompositeBufComposite() {
        CompositeByteBuf cbuf = Unpooled.compositeBuffer(3);
        cbuf.addComponent(Unpooled.wrappedBuffer(new byte[]{1, 2, 3}));
        cbuf.addComponent(Unpooled.wrappedBuffer(new byte[]{4, 5}));
        cbuf.addComponent(Unpooled.wrappedBuffer(new byte[]{7, 8, 9}));

        ByteBuffer nioBuffer = cbuf.nioBuffer(0, 8);
        byte[] bytes = nioBuffer.array();
        System.out.print("nioBuffer:");
        for (byte b : bytes) {
            System.out.print(b + " ");
        }
        cbuf.release();
    }
}
