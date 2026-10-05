package com.aerfask.netty.study.server;

import com.aerfask.netty.study.config.NettyConstants;
import com.aerfask.netty.study.handler.NettyDiscardHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;

@Slf4j
public class NettyDiscardServer {

    private final int serverPort;

    ServerBootstrap bootstrap = new ServerBootstrap();

    public NettyDiscardServer(int serverPort) {
        this.serverPort = serverPort;
    }

    public void runServer() {
        EventLoopGroup bossLoopGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerLoopGroup = new NioEventLoopGroup();
        try {
            bootstrap.group(bossLoopGroup, workerLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .localAddress(serverPort)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyDiscardHandler());
                            socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    log.info(Thread.currentThread().getName());
                                    log.info("channelRead");
                                    Executors.newSingleThreadExecutor().execute(() -> {
                                        // 此时不在Netty的EventLoop线程中！
                                        log.info(Thread.currentThread().getName());
                                        ctx.fireChannelRead(msg); // 会触发invokeChannelRead，且inEventLoop()为false
                                    });
//                                    super.channelRead(ctx, msg);
                                }
                            });
                        }
                    });

            ChannelFuture channelFuture = bootstrap.bind().sync();
            log.info("Netty Discard Server Start Success");
            ChannelFuture closeFuture = channelFuture.channel().closeFuture();
            closeFuture.sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                workerLoopGroup.shutdownGracefully().sync();
                bossLoopGroup.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        NettyDiscardServer server = new NettyDiscardServer(NettyConstants.SERVER_PORT);
        server.runServer();
    }
}
