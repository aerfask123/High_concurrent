package com.java.bytecode.handler;

import com.java.bytecode.type.U1;
import com.java.bytecode.type.U2;
import lombok.Data;

import java.nio.ByteBuffer;

@Data
public class ConstantClassInfo extends CpInfo {
    private U2 nameIndex;

    public ConstantClassInfo(U1 tag) {
        super(tag);
    }

    @Override
    public void read(ByteBuffer codeBuf) throws Exception {
        this.nameIndex = new U2(codeBuf.get(), codeBuf.get());
    }
}
