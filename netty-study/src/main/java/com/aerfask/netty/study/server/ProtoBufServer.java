package com.aerfask.netty.study.server;

import com.aerfask.netty.study.config.NettyConstants;
import com.aerfask.netty.study.protocol.MsgProtos;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProtoBufServer {

    private final int serverPort;

    public ProtoBufServer(int port) {
        this.serverPort = port;
    }

    public void runServer() {
        EventLoopGroup bossLoopGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerLoopGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            ChannelFuture channelFuture = bootstrap.group(bossLoopGroup, workerLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .localAddress(serverPort)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                            ch.pipeline().addLast(new ProtobufDecoder(MsgProtos.Msg.getDefaultInstance()));
                            ch.pipeline().addLast(new ProtobufBusinessDecoder());
                        }
                    }).bind();
            ChannelFuture closeFuture = channelFuture.channel().closeFuture();
            closeFuture.sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossLoopGroup.close();
            workerLoopGroup.close();
        }
    }

    static class ProtobufBusinessDecoder extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            MsgProtos.Msg msg1 = (MsgProtos.Msg) msg;
            log.info("收到一个ProtoBuf POJO ->>");
            log.info("id:{}", msg1.getId());
            log.info("content:{}", msg1.getContent());
        }
    }

    public static void main(String[] args) {
        new ProtoBufServer(NettyConstants.SERVER_PORT).runServer();
    }
}
