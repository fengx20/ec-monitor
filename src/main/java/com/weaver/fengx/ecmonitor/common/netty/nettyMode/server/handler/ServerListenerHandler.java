package com.weaver.fengx.ecmonitor.common.netty.nettyMode.server.handler;

import com.weaver.fengx.ecmonitor.common.netty.nettyMode.bean.MessageBean;
import com.weaver.fengx.ecmonitor.common.utils.ExceptionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Fengx
 * 5、服务端监听
 **/
@Slf4j
@ChannelHandler.Sharable
// @Sharable是用来修饰ChannelHandler的
// ChannelHandler单例模式下需要添加多个ChannelPipelines 也就是要拦截多个Channel，就需要使用到@Sharable来修饰ChannelHandler
public class ServerListenerHandler extends SimpleChannelInboundHandler<MessageBean> {

    /**
     * 客户端上线的时候调用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("服务端监听：{} 客户端连接进来了", ctx.channel().remoteAddress());
        ctx.fireChannelActive();
    }

    /**
     * 客户端掉线的时候调用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("服务端监听：{} 连接断开了", ctx.channel().remoteAddress());
        ctx.fireChannelInactive();
    }


    /**
     * 读取客户端信息
     *
     * @param channelHandlerContext 通信管道的上下文
     * @param messageBean
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageBean messageBean) throws Exception {
        String remoteAddress = channelHandlerContext.channel().remoteAddress().toString();
        log.info("服务端监听：来自客户端{}的消息{}", remoteAddress, new String(messageBean.getContent(), CharsetUtil.UTF_8));
        channelHandlerContext.writeAndFlush(new MessageBean("收到了客户端" + remoteAddress + "的消息"));
    }

    /**
     * 异常发生时候调用
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("服务端监听：{} 连接出异常了", ctx.channel().remoteAddress());
        log.error(ExceptionUtil.printStackTrace((Exception) cause));
        ctx.close();
    }

}
