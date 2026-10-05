package com.aerfask.netty.study.buffer;

import com.aerfask.netty.study.handler.InHandlerDemo;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;


public class InHandlerDemoTest {

    @Test
    public void testInHandlerLifeCircle() {
        final InHandlerDemo demoInHandler = new InHandlerDemo();
        ChannelInitializer initializer = new ChannelInitializer() {

            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast(demoInHandler);
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(initializer);
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(1);
        channel.writeInbound(buf);
        channel.flush();
        channel.writeInbound(buf);
        channel.flush();
        channel.close();
    }
}
