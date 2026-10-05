package com.java.bytecode;

import com.java.bytecode.type.ClassFile;
import com.java.bytecode.util.ClassFileAnalysiser;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws Exception {
        ByteBuffer codeBuf = readFile("xx.class");
        ClassFile classFile = ClassFileAnalysiser.analysis(codeBuf);
        System.out.println(classFile);
    }

    public static ByteBuffer readFile(String classFilePath) {
        Path path = Paths.get(classFilePath);
        System.out.println("读取路径：" + path.toAbsolutePath()); // 调试

        if (!Files.exists(path)) {
            System.out.println("文件不存在！");
            return null;
        }

        try {
            byte[] bytes = Files.readAllBytes(path);
            return ByteBuffer.wrap(bytes).asReadOnlyBuffer();
        } catch (IOException e) {
            System.err.println("读取文件失败：" + e.getMessage());
            return null;
        }
    }
}