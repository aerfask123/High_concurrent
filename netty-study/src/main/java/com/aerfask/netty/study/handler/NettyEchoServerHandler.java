package com.aerfask.netty.study.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class NettyEchoServerHandler extends ChannelInboundHandlerAdapter {

    public static final NettyEchoServerHandler INSTANCE = new NettyEchoServerHandler();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        log.info("收到消息：{}", in.toString(io.netty.util.CharsetUtil.UTF_8));
        log.info(in.hasArray() ? "堆内存" : "直接内存");
        int len = in.readableBytes();
        byte[] arr = new byte[len];
        in.getBytes(0, arr);
        log.info("写回前：{}", in.refCnt());

        ChannelFuture future = ctx.writeAndFlush(msg);

        future.addListener(channelFuture -> {
            log.info("写回后：{}", in.refCnt());
        });
    }
}
