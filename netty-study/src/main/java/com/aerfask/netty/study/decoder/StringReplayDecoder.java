package com.aerfask.netty.study.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class StringReplayDecoder extends ReplayingDecoder<StringReplayDecoder.PHASE> {


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        switch (state()) {
            case PHASE_1:
                length = in.readInt();
                inBytes = new byte[length];
                checkpoint(PHASE.PHASE_2);
                break;
            case PHASE_2:
                in.readBytes(inBytes, 0, length);
                out.add(new String(inBytes, StandardCharsets.UTF_8));
                checkpoint(PHASE.PHASE_1);
                break;
            default:
                break;
        }
    }

    public enum PHASE {
        PHASE_1,
        PHASE_2
    }

    private int length;
    private byte[] inBytes;

    public StringReplayDecoder() {
        super(PHASE.PHASE_1);
    }
}
