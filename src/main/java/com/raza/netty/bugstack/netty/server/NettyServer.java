package com.raza.netty.bugstack.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    public static void main(String[] args) {
       new NettyServer().bing(7515);

    }


    private void bing (int port){
        // 创建两个EventLoopGroup，用于处理不同阶段的事件，一个用于处理接收连接，一个用于处理连接的I/O操作。
        System.out.println("创建两个EventLoopGroup，用于处理不同阶段的事件，一个用于处理接收连接，一个用于处理连接的I/O操作。");
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();

        try {
            // 创建一个ServerBootstrap实例，用于配置服务器参数和启动服务器。
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            System.out.println("创建一个ServerBootstrap实例，用于配置服务器参数和启动服务器。");

            // 配置ServerBootstrap，设置父子EventLoopGroup，指定服务器通信的底层通道类型为NioServerSocketChannel。
            serverBootstrap
                    .group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128) // 设置服务器的一些选项，这里设置了连接请求的最大队列长度。
                    .childHandler(new MyChannelInitializer()); // 设置用于处理新连接的ChannelInitializer。

            System.out.println("配置ServerBootstrap，设置父子EventLoopGroup，指定服务器通信的底层通道类型为NioServerSocketChannel。");

            System.out.println("绑定服务器到指定端口，启动服务器并等待绑定完成。");
            ChannelFuture f = serverBootstrap.bind(port).sync();
            System.out.println("netty 服务启动完成");

            // 绑定服务器到指定端口，启动服务器并等待绑定完成。

            // 等待服务器关闭，阻塞当前线程。
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            // 处理可能发生的中断异常。
            throw new RuntimeException(e);
        } finally {
            // 关闭EventLoopGroup，释放资源。
            childGroup.shutdownGracefully();
            parentGroup.shutdownGracefully();
        }

    }

}
