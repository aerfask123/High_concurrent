package com.aerfask.netty.study.serializer;

import com.aerfask.netty.study.protocol.MsgProtos;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
public class ProtobufDemo {

    public static MsgProtos.Msg buildMsg() {
        MsgProtos.Msg.Builder personBuilder = MsgProtos.Msg.newBuilder();
        personBuilder.setId(1000);
        personBuilder.setContent("疯狂创客圈：高性能学习社群");
        MsgProtos.Msg message = personBuilder.build();
        return message;
    }

    @Test
    public void serAndDesr1() throws IOException {
        MsgProtos.Msg message = buildMsg();
        byte[] data = message.toByteArray();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(data);
        data = outputStream.toByteArray();
        MsgProtos.Msg msg = MsgProtos.Msg.parseFrom(data);
        log.info("id:{}", msg.getId());
        log.info("content:{}", msg.getContent());
    }

    @Test
    public void serAndDesr2() throws IOException {
        MsgProtos.Msg message = buildMsg();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        message.writeTo(outputStream);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        MsgProtos.Msg msg = MsgProtos.Msg.parseFrom(inputStream);
        log.info("id:{}", msg.getId());
        log.info("content:{}", msg.getContent());
    }

    @Test
    public void serAndDesr3() {
        MsgProtos.Msg message = buildMsg();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            message.writeDelimitedTo(outputStream);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            MsgProtos.Msg inMsg = MsgProtos.Msg.parseDelimitedFrom(inputStream);

            log.info("id:{}", inMsg.getId());
            log.info("content:{}", inMsg.getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
