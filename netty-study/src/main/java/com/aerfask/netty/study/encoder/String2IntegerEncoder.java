package com.aerfask.netty.study.encoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class String2IntegerEncoder extends MessageToMessageEncoder<String> {

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        char[] array = msg.toCharArray();
        for (char c : array) {
            if (c >= 48 && c <= 57) {
                out.add(Integer.valueOf(c));
            }
        }
    }
}
