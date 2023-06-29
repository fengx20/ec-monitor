package com.weaver.fengx.ecmonitor.common.websocket.client;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.*;

/**
 * @author Fengx
 * WebSocket客户端
 **/
// 客户端注解
@ClientEndpoint
// 为什么使用@Slf4j？
// 很简单的就是为了能够少写两行代码，不用每次都在类的最前边写上：
// private static final Logger logger = LoggerFactory.getLogger(this.XXX.class);
@Slf4j
public class WebSocketClient {

    @OnOpen
    public void open(Session s) {
        log.info("客户端开启了");
    }

    @OnClose
    public void close(CloseReason c) {
        log.info("客户端关闭了:" + c.getReasonPhrase());
    }

    @OnError
    public void error(Throwable t) {
        log.info("客户端发生错误");
    }

    @OnMessage
    public void message(String message, Session session) {
        log.info("客户端获取到服务端的信息:" + message);
        log.info("session:" + JSON.toJSONString(session));
    }

}
