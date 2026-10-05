package com.aerfask.netty.study.decoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class IntegerProcessHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof Integer) {
            Integer integer = (Integer) msg;
            System.out.println("IntegerProcessHandler: " + integer);
        } else {
            System.out.println("IntegerProcessHandler: 不是Integer类型");
        }
    }
}
