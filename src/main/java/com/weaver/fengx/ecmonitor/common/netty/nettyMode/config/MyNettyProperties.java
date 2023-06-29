package com.weaver.fengx.ecmonitor.common.netty.nettyMode.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Fengx
 * 9、netty属性类
 **/
@ConfigurationProperties(prefix = "netty")
// 把properties或者yml配置文件转化为bean来使用
@Data
@Configuration
public class MyNettyProperties {

    /**
     * boss线程数量 默认为cpu线程数*2
     */
    private Integer boss;
    /**
     * worker线程数量 默认为cpu线程数*2
     */
    private Integer worker;
    /**
     * 连接超时时间 默认为30s
     */
    private Integer timeout = 30000;
    /**
     * 服务器主端口 默认7000
     */
    private Integer port = 7000;
    /**
     * 服务器备用端口 默认70001
     */
    private Integer portSalve = 7001;
    /**
     * 服务器地址 默认为本地
     */
    private String host = "127.0.0.1";
}
