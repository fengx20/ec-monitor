package com.weaver.fengx.ecmonitor.common.netty.base;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author Fengx
 * 服务端
 **/
public class NettyServer {

    public static void main(String[] args) throws InterruptedException {

        /**
         * 创建BossGroup和 WorkerGroup
         * 1、创建两个线程组 bossGroup 和 workerGroup
         * 2、bossGroup 只是处理连接请求 ，真正的和客户端业务处理，会交给 workerGroup完成
         * 3、两个都是无限循环
         * 4、bossGroup 和 workerGroup 含有的子线程(NioEventLoop)的个数
         * 默认实际 cpu核数 * 2
         */
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup work = new NioEventLoopGroup();

        try {
            // 创建服务器端的启动对象，配置参数
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 使用链式编程进行配置
            // 设置两个线程组
            serverBootstrap.group(boss, work)
                    // 使用 NioServerSocketChannel 作为服务端的通道实现
                    .channel(NioServerSocketChannel.class)
                    // 设置线程队列得到连接个数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // 设置保持活动连接状态
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    // 给workerGroup 的 EventLoop 对应的管道设置处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 自定义服务端处理器
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            System.out.println("服务器准备好了");
            // 绑定一个端口并且同步，生成了一个 ChannelFuture 对象
            // 启动服务器(并绑定端口)
            ChannelFuture sync = serverBootstrap.bind(6668).sync();

            // 注册监听器，监控关心的事件
            sync.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (sync.isSuccess()) {
                        System.out.println("监听 6668 端口成功");
                    } else {
                        System.out.println("监听 6668 端口失败");
                    }
                }
            });

            // 对关闭通道进行监听
            sync.channel().closeFuture().sync();
        } finally {
            // 优雅关闭
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }
}
