package com.weaver.fengx.ecmonitor.common.netty.bootnetty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Fengx
 * 初始化通道加载器
 **/
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketChannelInitializer.class);

    /**
     * 配置对应的ChannelHandler
     *
     * @param socketChannel
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 获取对应的管道
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline
                // 添加HTTP编码解码器
                .addLast(new HttpServerCodec())
                // 添加对大数据流的支持
                .addLast(new ChunkedWriteHandler())
                // 添加聚合器
                .addLast(new HttpObjectAggregator(1024 * 64))
                // 设置websocket连接前缀前缀
                .addLast(new WebSocketServerProtocolHandler("/ws"))
                // 添加自定义处理器ChatServerHandler
                .addLast(new ChatServerHandler());
    }
}
