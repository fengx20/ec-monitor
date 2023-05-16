package com.weaver.fengx.ecmonitor.common.netty.bootnetty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Fengx
 * 自定义服务端处理器
 * 处理建立连接，关闭连接，接收消息，异常等
 **/
public class ChatServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ChatServerHandler.class);

    // 创建ChannelGroup对象存储所有连接的用户
    private static final ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 客户端上线的时候调用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("{}客户端连接进来了", ctx.channel().remoteAddress());
        ctx.fireChannelActive();
    }

    /**
     * 有新的连接建立时
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //加入通道组
        clients.add(ctx.channel());
    }

    /**
     * 读取客户端消息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("ctx = " + ctx);
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送的消息是 ： " + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址：" + ctx.channel().remoteAddress());

        // 遍历出所有连接的通道
        for (Channel channel : clients) {
            // 推送给所有的通道
            channel.writeAndFlush(new TextWebSocketFrame("服务器: 收到客户端发送来的消息: " + buf.toString(CharsetUtil.UTF_8)));
        }
    }

    /**
     * 消息读取完成后回复给客户端
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端～～", CharsetUtil.UTF_8));
    }

    /**
     * 不活跃时会调用这个方法
     * 客户端掉线的时候调用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("{}连接断开了", ctx.channel().remoteAddress());
        // 移除出通道组
        clients.remove(ctx.channel());
    }

    /**
     * 发生异常，通道关闭
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
