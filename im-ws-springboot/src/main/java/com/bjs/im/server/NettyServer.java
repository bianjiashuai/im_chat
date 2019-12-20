package com.bjs.im.server;

import com.bjs.im.config.NettyConfig;
import com.bjs.im.handler.IMHandler;
import com.bjs.im.handler.IMIdleStateHandler;
import com.bjs.im.handler.WebSocketServerHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Date 2019-12-19 16:53:26
 * @Author BianJiashuai
 */
@Slf4j
public class NettyServer {

    public static void run(NettyConfig nettyConfig, String... args) {
        int port = nettyConfig.getPort();
        if (null != args && args.length == 1) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException ex) {
                // 不作处理, 使用默认端口
            }
        }
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap server = new ServerBootstrap();
        server.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new IMIdleStateHandler())
                                .addLast(new HttpServerCodec())
                                .addLast(new HttpObjectAggregator(65535))
                                .addLast(new ChunkedWriteHandler())
                                .addLast(new WebSocketServerHandler());
                    }
                });

        bind(server, port, IMHandler.INSTANCE);
    }

    private static void bind(ServerBootstrap server, int port, IMHandler imHandler) {
        imHandler.loadHandler();
        server.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                log.info("****************端口[{}]绑定成功...", port);
            } else {
                log.error("****************端口[{}]绑定失败...", port);
            }
        });
    }
}
