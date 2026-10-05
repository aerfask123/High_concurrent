package com.aerfask.netty.study.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyDiscardHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            ByteBuf in = (ByteBuf) msg;
            log.info("收到消息：{}", in.toString(io.netty.util.CharsetUtil.UTF_8));
            while (in.isReadable()) {
                log.info("收到消息：{}", in.readByte());
            }
            System.out.println();
            super.channelRead(ctx, msg);
        } finally {
            //  ReferenceCountUtil.release(msg);
        }
    }
}
