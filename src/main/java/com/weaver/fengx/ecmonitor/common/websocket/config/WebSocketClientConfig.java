package com.weaver.fengx.ecmonitor.common.websocket.config;

import com.weaver.fengx.ecmonitor.common.websocket.client.WebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;

/**
 * @author Fengx
 * 客户端配置
 **/
@Configuration
public class WebSocketClientConfig {

    @Autowired
    WebSocketProperties webSocketProperties;

    /**
     * 连接服务器端
     *
     * @param userName
     * @return
     * @throws Exception
     */
    public Session connect(String userName) throws Exception {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        //设置消息大小最大为10M
        container.setDefaultMaxBinaryMessageBufferSize(10 * 1024 * 1024);
        container.setDefaultMaxTextMessageBufferSize(10 * 1024 * 1024);
        String uri = "ws://" + webSocketProperties.getUrl() + "/" + userName;
        return container.connectToServer(WebSocketClient.class, URI.create(uri));
    }

    /**
     * 发送信息
     *
     * @param msg
     * @param userName
     * @throws Exception
     */
    public void sendMsg(String msg, String userName) throws Exception {
        Session session = connect(userName);
        session.getBasicRemote().sendText(msg);
    }
}
