package com.weaver.fengx.ecmonitor.common.netty.base;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author Fengx
 * 客户端
 **/
public class NettyClient {

    public static void main(String[] args) throws InterruptedException {
        // 客户端需要一个事件循环组
        // NioEventLoopGroup 相当于一个事件循环线程组, 这个组中含有多个事件循环线程 ， 每一个事件循环线程是NioEventLoop
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        try {
            // 创建客户端启动对象，注意不是服务端启动对象 ServerBootstrap
            Bootstrap bootstrap = new Bootstrap();
            // 设置相关参数
            // 设置线程组
            bootstrap.group(eventExecutors)
                    // 设置客户端通道的实现类(反射)
                    .channel(NioSocketChannel.class)
                    // ChannelInitializer是一个特殊的处理类，他的目的是帮助使用者配置一个新的Channel,
                    // 用于把许多自定义的处理类增加到pipline上来
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 自定义客户端处理器
                            // pipeline()：连接建立后，都会自动创建一个管道pipeline，这个管道也被称为责任链，
                            // 保证顺序执行，同时又可以灵活的配置各类Handler，这是一个很精妙的设计，
                            // 既减少了线程切换带来的资源开销、避免好多麻烦事，同时性能又得到了极大增强。
                            socketChannel.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            System.out.println("客户端 OK 。。。");
            // 连接到远程节点，等待连接完成
            ChannelFuture sync = bootstrap.connect("127.0.0.1", 6668).sync();
            // 阻塞操作，closeFuture()开启了一个channel的监听器（这期间channel在进行各项工作），直到链路断开
            sync.channel().closeFuture().sync();
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }
}
