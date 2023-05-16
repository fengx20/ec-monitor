package com.weaver.fengx.ecmonitor.common.netty.bootnetty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Fengx
 * 启动监听器
 **/
@Component
public class NettyStartListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(NettyStartListener.class);

    /**
     * 注入启动器
     */
    @Resource
    private WebSocketNettyServer webSocketNettyServer;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 判断event上下文中的父级是否为空
        if (event.getApplicationContext().getParent() == null) {
            try {
                // 为空则调用start方法
//                webSocketNettyServer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
