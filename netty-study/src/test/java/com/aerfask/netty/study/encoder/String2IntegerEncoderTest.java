package com.aerfask.netty.study.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

public class String2IntegerEncoderTest {

    @Test
    public void testStringToIntegerDecoder() {
        ChannelInitializer initializer = new ChannelInitializer<EmbeddedChannel>() {

            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(new Integet2ByteEncoder());
                ch.pipeline().addLast(new String2IntegerEncoder());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(initializer);
        for (int i = 1; i < 101; i++) {
            channel.writeOutbound("i am " + i);
        }
        channel.flush();
        ByteBuf buf = channel.readOutbound();
        while (buf != null) {
            System.out.println("o=" + buf.readInt());
            buf = channel.readOutbound();
        }
    }
}
