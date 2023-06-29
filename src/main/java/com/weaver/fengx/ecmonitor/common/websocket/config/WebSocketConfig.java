package com.weaver.fengx.ecmonitor.common.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author Fengx
 * 注册ws请求处理器
 **/
@Configuration
@Import(WebSocketProperties.class)
public class WebSocketConfig {

    /**
     * 注册一个扫描ServerEndpoint注解处理器
     *
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
