package com.weaver.fengx.ecmonitor.common.netty.nettyMode.server.handler;

import com.weaver.fengx.ecmonitor.common.netty.nettyMode.common.handler.MessageDecodeHandler;
import com.weaver.fengx.ecmonitor.common.netty.nettyMode.common.handler.MessageEncodeHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author Fengx
 * 4、服务端主处理器
 **/
public class ServerHandler extends ChannelInitializer<SocketChannel> {
    /**
     * 初始化通道以及配置对应管道的处理器
     *
     * @param socketChannel
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 用于存放ChannelHandler的容器
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 添加消息解码处理器
        pipeline.addLast(new MessageDecodeHandler());
        // 添加消息编码处理器
        pipeline.addLast(new MessageEncodeHandler());
        // 添加服务端监听
        pipeline.addLast(new ServerListenerHandler());
    }
}
