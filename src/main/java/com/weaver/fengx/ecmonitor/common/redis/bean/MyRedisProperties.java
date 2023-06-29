//package com.weaver.fengx.ecmonitor.common.redis.bean;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;
//
//import java.time.Duration;
//
//import lombok.Data;
//
///**
// * @author Fengx
// * 属性配置
// **/
//@Data
//@ConfigurationProperties(
//        prefix = "spring.myredis"
//)
//@Component(value = "MyRedisProperties")
//public class MyRedisProperties {
//
//    /**备用数据库 */
//    private Integer backupdb;
//    private int database = 0;
//    private String url;
//    private String host = "localhost";
//    private String username;
//    private String password;
//    private int port = 6379;
//    private boolean ssl;
//    private Duration timeout;
//    private Duration connectTimeout;
//}
