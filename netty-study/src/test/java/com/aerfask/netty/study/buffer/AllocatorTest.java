package com.aerfask.netty.study.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class AllocatorTest {

    public static void printByteBuf(String action, ByteBuf buf) {
        log.info("{} {}", action, buf);
        log.info("buf.hasArray:()", buf.hasArray());
        log.info("buf.ByteBufAllocator:{}", buf.alloc());
    }

    public class AllocDemohandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            printByteBuf("channelRead", (ByteBuf) msg);
            ByteBuf buf = ctx.alloc().buffer();
            buf.writeInt(100);
            ctx.channel().writeAndFlush(buf);
        }
    }

    @Test
    public void testByteBufAlloc() {
        ChannelInitializer i = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) {
                ch.pipeline().addLast(new AllocDemohandler());
            }
        };

        EmbeddedChannel channel = new EmbeddedChannel(i);
        channel.config().setAllocator(PooledByteBufAllocator.DEFAULT);

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(1);
        channel.writeInbound(buf);
        ByteBuf outBuf = channel.readOutbound();
        printByteBuf("readOutbound", outBuf);
    }
}
