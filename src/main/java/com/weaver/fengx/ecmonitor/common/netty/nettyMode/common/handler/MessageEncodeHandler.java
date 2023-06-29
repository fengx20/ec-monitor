package com.weaver.fengx.ecmonitor.common.netty.nettyMode.common.handler;

import com.weaver.fengx.ecmonitor.common.netty.nettyMode.bean.MessageBean;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author Fengx
 * 10、消息编码处理器
 **/
public class MessageEncodeHandler extends MessageToByteEncoder<MessageBean> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MessageBean messageBean, ByteBuf byteBuf)
            throws Exception {
        byteBuf.writeInt(messageBean.getLen());
        byteBuf.writeBytes(messageBean.getContent());
    }
}
