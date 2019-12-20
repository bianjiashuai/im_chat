package com.bjs.im.server;

import com.bjs.im.server.handler.IMHandler;
import com.bjs.im.server.handler.IMIdleStateHandler;
import com.bjs.im.server.handler.WebSocketServerHandler;
import com.bjs.im.util.IMUtil;

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

/**
 * @Description
 * @Date 2019-12-03 16:35:54
 * @Author BianJiashuai
 */
public class NettyServer {

    public static final int PORT = 8000;

    public static void main(String[] args) {
        int port = PORT;
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
                System.out.println(IMUtil.getDateStr() + " : 端口[" + port + "]绑定成功");
            } else {
                System.out.println("端口[" + port + "]绑定失败");
            }
        });
    }
}
