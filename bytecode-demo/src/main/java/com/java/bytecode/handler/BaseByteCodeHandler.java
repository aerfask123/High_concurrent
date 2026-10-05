package com.java.bytecode.handler;

import com.java.bytecode.type.ClassFile;

import java.nio.ByteBuffer;

public interface BaseByteCodeHandler {

    int order();

    void read(ByteBuffer codeBuf, ClassFile classFile) throws Exception;
}
