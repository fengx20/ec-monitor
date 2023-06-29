package com.weaver.fengx.ecmonitor.common.websocket.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Fengx
 * 服务端配置
 **/
@Configuration
@ConfigurationProperties(prefix = "websocket")
@Data
@EnableConfigurationProperties
public class WebSocketProperties {

    /**
     * 服务器url
     */
    private String url;
}
