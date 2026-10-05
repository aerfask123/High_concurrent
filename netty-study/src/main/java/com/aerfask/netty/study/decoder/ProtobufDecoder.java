package com.aerfask.netty.study.decoder;

import com.aerfask.netty.study.protocol.ProtoMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ProtobufDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        in.markReaderIndex();
        if (in.readableBytes() < 2) {
            return;
        }
        int length = in.readShort();
        if (length < 0) {
            ctx.close();
        }
        if (length > in.readableBytes()) {
            in.resetReaderIndex();
            return;
        }
        byte[] array;
        if (in.hasArray()) {
            ByteBuf slice = in.slice();
            array = slice.array();
        } else {
            array = new byte[length];
            in.readBytes(array, 0, length);
        }
        ProtoMsg.Message outmsg = ProtoMsg.Message.parseFrom(array);
        if (outmsg != null) {
            out.add(outmsg);
        }
    }
}
