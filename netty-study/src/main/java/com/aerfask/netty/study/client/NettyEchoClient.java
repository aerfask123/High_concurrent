package com.aerfask.netty.study.client;

import com.aerfask.netty.study.handler.NettyEchoClientHandler;
import com.aerfask.netty.study.util.DateUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class NettyEchoClient {

    private int serverPort;

    private String serverIp;

    Bootstrap bootstrap = new Bootstrap();

    public NettyEchoClient(int serverPort, String serverIp) {
        this.serverPort = serverPort;
        this.serverIp = serverIp;
    }

    public void runClient() {
        EventLoopGroup workerLoopGroup = new NioEventLoopGroup();
        try {
            bootstrap.group(workerLoopGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(serverIp, serverPort)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(NettyEchoClientHandler.INSTANCE);
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
            Scanner scanner = new Scanner(System.in);
            log.info("请输入要发送的内容:");
            while (scanner.hasNext()) {
                String next = scanner.next();
                byte[] bytes = (DateUtil.getNow() + " >>" + next).getBytes();
                ByteBuf buffer = channel.alloc().buffer();
                buffer.writeBytes(bytes);
                channel.writeAndFlush(buffer);
                log.info("发送内容:{}", next);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        NettyEchoClient client = new NettyEchoClient(8080, "127.0.0.1");
        client.runClient();
    }
}
