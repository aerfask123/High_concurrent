package com.aerfask.netty.study.nio;

import lombok.extern.slf4j.Slf4j;

import java.nio.IntBuffer;

@Slf4j
public class UseBuffer {

    public static void main(String[] args) {
        allocateTest();

        putTest();

        flipTest();

        getTest();

        rewindTest();

        clear();
    }

    static IntBuffer intBuffer = null;

    public static void allocateTest() {
        intBuffer = IntBuffer.allocate(20);
        log.debug("------------after allocate------------");
        log.debug("position:{},limit:{},capacity:{}", intBuffer.position(), intBuffer.limit(), intBuffer.capacity());
    }

    public static void putTest() {
        for (int i = 0; i < 5; i++) {
            intBuffer.put(i);
        }
        log.debug("------------after put------------");
        log.debug("position:{},limit:{},capacity:{}", intBuffer.position(), intBuffer.limit(), intBuffer.capacity());
    }

    public static void flipTest() {
        intBuffer.flip();
        log.debug("------------after flip------------");
        log.debug("position:{},limit:{},capacity:{}", intBuffer.position(), intBuffer.limit(), intBuffer.capacity());
    }

    public static void getTest() {
        while (intBuffer.hasRemaining()) {
            log.debug("{}", intBuffer.get());
        }

        log.debug("------------after get------------");
        log.debug("position:{},limit:{},capacity:{}", intBuffer.position(), intBuffer.limit(), intBuffer.capacity());
    }

    public static void rewindTest() {
        intBuffer.rewind();

        log.debug("------------after rewind------------");
        log.debug("position:{},limit:{},capacity:{}", intBuffer.position(), intBuffer.limit(), intBuffer.capacity());

        for (int i = 0; i < 5; i++) {
            if (i == 2) {
                intBuffer.mark();
            }
            int j = intBuffer.get();
            log.info("{}", j);
        }
        intBuffer.reset();
        log.debug("------------after get------------");
        log.debug("position:{},limit:{},capacity:{}", intBuffer.position(), intBuffer.limit(), intBuffer.capacity());
    }

    public static void clear() {
        intBuffer.clear();

        log.debug("------------after clear------------");
        log.debug("position:{},limit:{},capacity:{}", intBuffer.position(), intBuffer.limit(), intBuffer.capacity());
    }
}
