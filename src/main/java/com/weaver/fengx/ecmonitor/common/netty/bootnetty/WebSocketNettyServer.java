package com.weaver.fengx.ecmonitor.common.netty.bootnetty;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * @author Fengx
 * 启动器
 **/
@Component
public class WebSocketNettyServer {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketNettyServer.class);

    /**
     * boss 线程组用于处理连接工作
     */
    private final EventLoopGroup boss = new NioEventLoopGroup();
    /**
     * work 线程组用于数据处理
     */
    private final EventLoopGroup work = new NioEventLoopGroup();

    @Value("${netty.port}")
    private Integer port;

    /**
     * 在springboot 项目中使用 Netty ,所以我们将Netty 服务器的启动封装在一个 start()方法
     *
     * @PostConstruct 表示该方法在 Spring 初始化 NettyServer类后调用
     */
//    @PostConstruct
//    public void start() throws Exception {
//        logger.info("端口：{}", port);
//        // Netty服务器启动对象
//        ServerBootstrap serverBootstrap = new ServerBootstrap();
//        // 初始化服务器启动对象
//        serverBootstrap
//                // 指定使用上面创建的两个线程池
//                .group(boss, work)
//                // 指定Netty通道类型
//                .channel(NioServerSocketChannel.class)
//                // 使用指定的端口设置套接字地址
//                .localAddress(new InetSocketAddress(port))
//                // 服务端可连接队列数,对应TCP/IP协议listen函数中backlog参数
//                .option(ChannelOption.SO_BACKLOG, 1024)
//                // 心跳机制
//                // 设置TCP长连接,一般如果两个小时内没有数据的通信时,TCP会自动发送一个活动探测数据报文
//                .childOption(ChannelOption.SO_KEEPALIVE, true)
//                // 将小的数据包包装成更大的帧进行传送,提高网络的负载,即TCP延迟传输
//                .childOption(ChannelOption.TCP_NODELAY, true)
//                // 指定通道初始化器用来加载当Channel收到事件消息后
//                .childHandler(new WebSocketChannelInitializer());
//        // 监听
//        ChannelFuture future = serverBootstrap.bind().sync();
//        if (future.isSuccess()) {
//            logger.info("启动 Netty Server");
//        }
//    }

    /**
     * 关闭
     *
     * @throws Exception
     * @PreDestroy 会在服务器卸载Servlet的时候运行，并且只会被服务器调用一次，类似于Servlet的destroy()方法。
     * 被@PreDestroy修饰的方法会在destroy()方法之后运行，在Servlet被彻底卸载之前
     */
    @PreDestroy
    public void destory() throws Exception {
        boss.shutdownGracefully().sync();
        work.shutdownGracefully().sync();
        logger.info("关闭Netty");
    }

}
