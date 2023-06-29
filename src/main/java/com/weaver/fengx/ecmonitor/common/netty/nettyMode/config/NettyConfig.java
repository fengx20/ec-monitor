package com.weaver.fengx.ecmonitor.common.netty.nettyMode.config;

import com.weaver.fengx.ecmonitor.common.netty.nettyMode.client.handler.ClientHandler;
import com.weaver.fengx.ecmonitor.common.netty.nettyMode.server.handler.ServerHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Fengx
 * 2、netty配置
 **/
@Configuration
@EnableConfigurationProperties
// 1、让使用了 @ConfigurationProperties 注解的类生效
// 2、将该类注入到 IOC 容器中,交由 IOC 容器进行管理
public class NettyConfig {

    @Autowired
    MyNettyProperties myNettyProperties;

    /**
     * boss 线程池
     * 负责客户端连接
     *
     * @return
     */
    @Bean
    public NioEventLoopGroup boosGroup() {
        // 设置boss线程数量
        return new NioEventLoopGroup(myNettyProperties.getBoss());
    }

    /**
     * worker线程池
     * 负责业务处理
     *
     * @return
     */
    @Bean
    public NioEventLoopGroup workerGroup() {
        // 设置worker线程数量
        return new NioEventLoopGroup(myNettyProperties.getWorker());
    }

    /**
     * 服务器启动器
     *
     * @return
     */
    @Bean
    public ServerBootstrap serverBootstrap() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                // 指定使用的线程组
                .group(boosGroup(), workerGroup())
                // 指定使用的通道
                .channel(NioServerSocketChannel.class)
                // 指定连接超时时间
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, myNettyProperties.getTimeout())
                // 指定worker处理器
                .childHandler(new ServerHandler());
        return serverBootstrap;
    }

    /**
     * 客户端启动器
     *
     * @return
     */
    @Bean
    public Bootstrap bootstrap() {
        // 新建一组线程池
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup(myNettyProperties.getBoss());
        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                // 指定线程组
                .group(eventExecutors)
                // 设置TCP长连接,一般如果两个小时内没有数据的通信时,TCP会自动发送一个活动探测数据报文
                .option(ChannelOption.SO_KEEPALIVE, true)
                // 指定通道
                .channel(NioSocketChannel.class)
                // 指定处理器
                .handler(new ClientHandler());
        return bootstrap;
    }

}
