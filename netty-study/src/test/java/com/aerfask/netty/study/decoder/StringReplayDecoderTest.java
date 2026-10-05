package com.aerfask.netty.study.decoder;


import com.aerfask.netty.study.handler.StringProcessHandler;
import com.aerfask.netty.study.util.RandomUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;

public class StringReplayDecoderTest {

    static String content = "疯狂创客圈：高性能学习社区！";

    @Test
    public void testByte2StringDecoder() {
        ChannelInitializer initializer = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(new StringReplayDecoder());
                ch.pipeline().addLast(new StringProcessHandler());
            }
        };

        EmbeddedChannel channel = new EmbeddedChannel(initializer);

        byte[] bytes = content.getBytes(Charset.defaultCharset());
        for (int i = 0; i < 100; i++) {
            int random = RandomUtil.randInMod(3);
            ByteBuf buf = Unpooled.buffer();
            buf.writeInt(bytes.length * random);
            for (int j = 0; j < random; j++) {
                buf.writeBytes(bytes);
            }
            channel.writeInbound(buf);
        }
    }
}
