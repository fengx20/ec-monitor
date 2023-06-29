package com.weaver.fengx.ecmonitor.common.netty.nettyMode.common.handler;

import com.weaver.fengx.ecmonitor.common.netty.nettyMode.bean.MessageBean;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author Fengx
 * 11、消息解码处理器
 **/
public class MessageDecodeHandler extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int len = byteBuf.readInt();
        byte[] content = new byte[len];
        byteBuf.readBytes(content);
        MessageBean messageBean = new MessageBean();
        messageBean.setContent(content);
        messageBean.setLen(len);
        list.add(messageBean);
    }
}
