package com.aerfask.netty.study.decoder;

import com.aerfask.netty.study.handler.StringProcessHandler;
import com.aerfask.netty.study.util.RandomUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import org.junit.jupiter.api.Test;

public class NettyOpenBoxDecoder {

    public static final int VERSION = 100;
    static String spliter = "\r\n";
    static String spliter2 = "\t";
    static String content = "疯狂创客圈：高性能学习社群！";

    @Test
    public void testLineBasedFrameDecoder() {
        ChannelInitializer initializer = new ChannelInitializer<EmbeddedChannel>() {

            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new StringProcessHandler());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(initializer);
        for (int i = 0; i < 100; i++) {
            int random = RandomUtil.randInMod(3);
            ByteBuf buf = Unpooled.buffer();
            for (int k = 0; k < random; k++) {
                buf.writeBytes(content.getBytes());
            }
            buf.writeBytes(spliter.getBytes());
            channel.writeInbound(buf);
        }
    }

    @Test
    public void testDelimiterBasedFrameDecoder() {
        final ByteBuf delimiter = Unpooled.copiedBuffer(spliter2.getBytes());
        ChannelInitializer initializer = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new StringProcessHandler());
            }
        };

        EmbeddedChannel channel = new EmbeddedChannel(initializer);
        for (int i = 0; i < 100; i++) {
            int random = RandomUtil.randInMod(3);
            ByteBuf buf = Unpooled.buffer();
            for (int k = 0; k < random; k++) {
                buf.writeBytes(content.getBytes());
            }
            buf.writeBytes(spliter2.getBytes());
            channel.writeInbound(buf);
        }
    }

    @Test
    public void testLengthFieldBasedFrameDecoder() {
        final LengthFieldBasedFrameDecoder decoder = new LengthFieldBasedFrameDecoder(1024, 2, 4, 4, 10);
        ChannelInitializer initializer = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(decoder);
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new StringProcessHandler());
            }
        };

        EmbeddedChannel channel = new EmbeddedChannel(initializer);
        for (int i = 1; i < 101; i++) {
            ByteBuf buf = Unpooled.buffer();
            String s = i + "次发送->" + content;
            byte[] bytes = s.getBytes();
            buf.writeChar(VERSION);
            buf.writeInt(bytes.length);
            buf.writeInt(10);
            buf.writeBytes(bytes);
            channel.writeInbound(buf);
        }
    }
}
