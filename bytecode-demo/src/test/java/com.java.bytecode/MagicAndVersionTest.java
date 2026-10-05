package com.java.bytecode;

import com.java.bytecode.type.ClassFile;
import com.java.bytecode.util.ClassFileAnalysiser;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

public class MagicAndVersionTest {

    @Test
    public void testMagicAndVersionHandler() throws Exception {
        ByteBuffer codeBuf = Main.readFile("target\\classes\\com\\java\\bytecode\\Main.class");
        ClassFile classFile = ClassFileAnalysiser.analysis(codeBuf);
        System.out.println(classFile.getMagic().toHexString());
        System.out.println(classFile.getMajorVersion().toInt());
        System.out.println(classFile.getMinorVersion().toInt());
    }
}
