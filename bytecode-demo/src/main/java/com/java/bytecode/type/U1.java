package com.java.bytecode.type;

public class U1 {

    private byte value;

    public U1(byte b) {
        value = b;
    }

    public Integer toInt() {
        return value & 0xff;
    }

    public String tohexString() {
        char[] hexChar = new char[]{
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        StringBuilder hexStr = new StringBuilder();
        int v = value & 0xff;
        while (v > 0) {
            int c = v % 16;
            v = v >>> 4;
            hexStr.insert(0, hexChar[c]);
        }
        if ((hexStr.length() & 0x01) == 1) {
            hexStr.insert(0, '0');
        }
        return "0x" + (hexStr.length() == 0 ? "0" : hexStr.toString());
    }
}
