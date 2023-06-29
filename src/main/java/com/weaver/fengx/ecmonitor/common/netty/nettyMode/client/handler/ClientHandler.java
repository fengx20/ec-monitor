package com.weaver.fengx.ecmonitor.common.netty.nettyMode.client.handler;

import com.weaver.fengx.ecmonitor.common.netty.nettyMode.common.handler.MessageDecodeHandler;
import com.weaver.fengx.ecmonitor.common.netty.nettyMode.common.handler.MessageEncodeHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author Fengx
 * 7、客户端处理器
 **/
public class ClientHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new MessageEncodeHandler());
        pipeline.addLast(new MessageDecodeHandler());
        pipeline.addLast(new ClientListenerHandler());
    }
}
