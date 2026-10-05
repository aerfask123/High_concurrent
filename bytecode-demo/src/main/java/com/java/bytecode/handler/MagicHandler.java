package com.java.bytecode.handler;

import com.java.bytecode.type.ClassFile;
import com.java.bytecode.type.U4;

import java.nio.ByteBuffer;

public class MagicHandler implements BaseByteCodeHandler {

    @Override
    public int order() {
        return 0;
    }

    @Override
    public void read(ByteBuffer codeBuf, ClassFile classFile) throws Exception {

        classFile.setMagic(new U4(codeBuf.get(), codeBuf.get(), codeBuf.get(), codeBuf.get()));
        if (!"0xCAFEBABE".equalsIgnoreCase(classFile.getMagic().toHexString())) {
            throw new Exception("不是Class文件");
        }
    }
}
