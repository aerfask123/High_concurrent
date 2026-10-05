package com.java.bytecode.handler;

import com.java.bytecode.type.U1;
import lombok.Data;

@Data
public abstract class CpInfo implements ConstantInfoHandler {
    private U1 tag;

    public CpInfo() {
    }

    public CpInfo(U1 tag) {
        this.tag = tag;
    }
}
