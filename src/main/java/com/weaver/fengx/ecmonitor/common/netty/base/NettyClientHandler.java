package com.weaver.fengx.ecmonitor.common.netty.base;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author Fengx
 * 自定义客户端处理器
 **/

/**
 * ChannelInboundHandlerAdapter
 * 注册和激活：当客户端连接时，首先会触发注册，进行一些初始化的工作，然后激活连接，就可以收发消息了。
 * 断开和注销：当客户端断开时，反向操作，先断开，再注销。
 * 读取消息：当收到客户端消息时，首先读取，然后触发读取完成。
 * 发生异常：不多解释了。
 * 用户事件：由用户触发的各种非常规事件，根据evt的类型来判断不同的事件类型，从而进行不同的处理。
 * 可写状态变更：收到消息后，要回复消息，会先把回复内容写到缓冲区。而缓冲区大小是有一定限制的，当达到上限以后，可写状态就会变为否，不能再写。等缓冲区的内容被冲刷掉后，缓冲区又有了空间，可写状态又会变为是。
 */
// 为了线程安全
@ChannelHandler.Sharable
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 与服务器建立连接
     * 激活客户端并发送消息
     *
     * @param ctx ChannelHandlerContext，通道上下文，代指Channel
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ctx = " + ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("你好，服务端 ～～", CharsetUtil.UTF_8));
    }

    /**
     * 与服务器断开连接
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().close();
    }

    /**
     * 读取消息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("服务器回复的消息是 ： " + buf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器地址：" + ctx.channel().remoteAddress());
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
