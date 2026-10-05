package com.aerfask.netty.study.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class IntegerAddDecoder extends ReplayingDecoder<IntegerAddDecoder.PHASE> {

    enum PHASE {
        PHASE_1,
        PHASE_2
    }

    private int first;
    private int second;

    public IntegerAddDecoder() {
        super(PHASE.PHASE_1);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        switch (state()) {
            case PHASE_1:
                first = in.readInt();
                checkpoint(PHASE.PHASE_2);
                break;
            case PHASE_2:
                second = in.readInt();
                Integer sum = first + second;
                out.add(sum);
                checkpoint(PHASE.PHASE_1);
                break;
            default:
                break;
        }
    }
}
