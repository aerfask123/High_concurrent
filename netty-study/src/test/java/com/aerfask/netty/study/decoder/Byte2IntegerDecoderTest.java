package com.aerfask.netty.study.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

public class Byte2IntegerDecoderTest {

    @Test
    public void testByte2IntegerDecoder() {
        ChannelInitializer initializer = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(new Byte2IntegerDecoder());
                ch.pipeline().addLast(new IntegerProcessHandler());
            }
        };

        EmbeddedChannel channel = new EmbeddedChannel(initializer);
        for (int i = 0; i < 10; i++) {
            ByteBuf buf = Unpooled.buffer();
            buf.writeInt(i);
            channel.writeInbound(buf);
        }
    }
}
