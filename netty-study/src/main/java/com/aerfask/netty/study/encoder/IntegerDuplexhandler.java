package com.aerfask.netty.study.encoder;

import com.aerfask.netty.study.decoder.Byte2IntegerDecoder;
import io.netty.channel.CombinedChannelDuplexHandler;

public class IntegerDuplexhandler extends CombinedChannelDuplexHandler<Byte2IntegerDecoder, Integet2ByteEncoder> {
    public IntegerDuplexhandler() {
        super(new Byte2IntegerDecoder(), new Integet2ByteEncoder());
    }


}
