package com.aerfask.netty.study.client;

import com.aerfask.netty.study.protocol.MsgProtos;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProtoBufSendClient {

    static String content = "疯狂创客圈：高性能学习社群！";

    public void runClient() {
        EventLoopGroup workerLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerLoopGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress("127.0.0.1", 8080)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                            ch.pipeline().addLast(new ProtobufEncoder());
                        }
                    });
            ChannelFuture f = bootstrap.connect();
            f.addListener(future -> {
                if (future.isSuccess()) {
                    System.out.println("连接成功");
                } else {
                    System.out.println("连接失败");
                }
            });
            f.sync();

            Channel channel = f.channel();

            for (int i = 0; i < 1000; i++) {
                MsgProtos.Msg user = build(i, i + "->" + content);
                channel.writeAndFlush(user);
                log.info("发送报文数:{}", i);
            }
            channel.flush();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerLoopGroup.shutdownGracefully();
        }
    }

    public MsgProtos.Msg build(int id, String content) {
        MsgProtos.Msg.Builder personBuilder = MsgProtos.Msg.newBuilder();
        personBuilder.setId(id);
        personBuilder.setContent(content);
        return personBuilder.build();
    }

    public static void main(String[] args) {
        ProtoBufSendClient client = new ProtoBufSendClient();
        client.runClient();
    }
}
