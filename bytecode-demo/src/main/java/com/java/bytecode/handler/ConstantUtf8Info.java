package com.java.bytecode.handler;

import com.java.bytecode.type.U1;
import com.java.bytecode.type.U2;
import lombok.Data;

import java.nio.ByteBuffer;

@Data
public class ConstantUtf8Info extends CpInfo {

    private U2 length;
    private byte[] bytes;

    public ConstantUtf8Info(U1 tag) {
        super(tag);
    }

    @Override
    public void read(ByteBuffer codeBuf) throws Exception {

        length = new U2(codeBuf.get(), codeBuf.get());
        bytes = new byte[length.toInt()];
        codeBuf.get(bytes, 0, length.toInt());
    }
}
