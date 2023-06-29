package com.weaver.fengx.ecmonitor.common.netty.nettyMode.client;

import com.weaver.fengx.ecmonitor.common.netty.nettyMode.bean.MessageBean;
import com.weaver.fengx.ecmonitor.common.netty.nettyMode.config.MyNettyProperties;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Fengx
 * 6、客户端启动类
 **/
@Component
public class ClientBoot {

    // @Autowired和@Resource区别一个是默认按照类型，一个默认按照名字
    // 创建客户端启动对象
    @Resource
    Bootstrap bootstrap;
    @Resource
    MyNettyProperties myNettyProperties;

    /**
     * 主端口连接
     *
     * @return
     * @throws InterruptedException
     */
    public Channel connect() throws InterruptedException {
        // 连接服务器
        ChannelFuture channelFuture = bootstrap.connect(myNettyProperties.getHost(), myNettyProperties.getPort()).sync();
        // 监听关闭
        Channel channel = channelFuture.channel();
        return channel;
    }

    /**
     * 备用端口连接
     *
     * @return
     * @throws InterruptedException
     */
    public Channel connectSlave() throws InterruptedException {
        // 连接服务器
        ChannelFuture channelFuture = bootstrap.connect(myNettyProperties.getHost(), myNettyProperties.getPort()).sync();
        // 监听关闭
        Channel channel = channelFuture.channel();
        channel.closeFuture().sync();
        return channel;
    }

    /**
     * 发送消息到服务器端
     *
     * @return
     */
    public void sendMsg(MessageBean messageBean) throws InterruptedException {
        connect().writeAndFlush(messageBean);
    }
}
