//package com.weaver.fengx.ecmonitor.common.redis.config;
//
//
//import com.weaver.fengx.ecmonitor.common.redis.bean.MyRedisProperties;
//import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.redis.connection.RedisConfiguration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
//import org.springframework.data.redis.core.RedisTemplate;
//
//import java.time.Duration;
//
//import javax.annotation.Resource;
//
///**
// * @author Fengx
// * redis配置
// * 主从库配置
// **/
//@Configuration
//@EnableConfigurationProperties
//@EnableCaching
//public class RedisConfig {
//
//    @Resource
//    MyRedisProperties myRedisProperties;
//
//    /**
//     * 默认的redis模板类，使用默认数据库访问
//     * @param redisConnectionFactory
//     * @return
//     */
//    @Bean
//    @Primary
//    public RedisTemplate redisTemplateMaster(RedisConnectionFactory redisConnectionFactory){
//        // 创建redis序列化器
//        RedisTemplate redisTemplate = new RedisTemplate<>();
//        FastJsonRedisSerializer<Object> objectFastJson2JsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
//        redisTemplate.setKeySerializer(objectFastJson2JsonRedisSerializer);
//        redisTemplate.setValueSerializer(objectFastJson2JsonRedisSerializer);
//        redisTemplate.setHashKeySerializer(objectFastJson2JsonRedisSerializer);
//        redisTemplate.setHashValueSerializer(objectFastJson2JsonRedisSerializer);
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        return redisTemplate;
//    }
//
//    /**
//     * 备用数据库
//     * @param connectionFactory
//     * @return
//     */
//    @Bean
//    public RedisTemplate redisTemplateSlave(RedisConnectionFactory connectionFactory){
//        // 创建redis序列化器
//        RedisTemplate redisTemplate = new RedisTemplate<>();
//        FastJsonRedisSerializer<Object> objectFastJson2JsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
//        redisTemplate.setKeySerializer(objectFastJson2JsonRedisSerializer);
//        redisTemplate.setValueSerializer(objectFastJson2JsonRedisSerializer);
//        redisTemplate.setHashKeySerializer(objectFastJson2JsonRedisSerializer);
//        redisTemplate.setHashValueSerializer(objectFastJson2JsonRedisSerializer);
//        if(connectionFactory instanceof  LettuceConnectionFactory){
//            //创建客户端连接
//            LettuceConnectionFactory lettuceConnectionFactory =
//                    createLettuceConnectionFactory
//                            (myRedisProperties.getBackupdb(),myRedisProperties.getHost(),myRedisProperties.getPort(),myRedisProperties.getPassword(),myRedisProperties.getTimeout().getSeconds()*1000);
//            redisTemplate.setConnectionFactory(lettuceConnectionFactory);
//        }
//        return redisTemplate;
//    }
//
//    /**
//     * 创建lettuce连接工厂
//     * @param dbIndex
//     * @param hostName
//     * @param port
//     * @param password
//     * @param timeOut
//     * @return
//     */
//    private  LettuceConnectionFactory createLettuceConnectionFactory(
//            int dbIndex, String hostName, int port, String password,
//            Long timeOut){
//        //redis配置
//        RedisConfiguration redisConfiguration = new
//                RedisStandaloneConfiguration(hostName,port);
//        ((RedisStandaloneConfiguration) redisConfiguration).setDatabase(dbIndex);
//        ((RedisStandaloneConfiguration) redisConfiguration).setPassword(password);
//        //连接池配置
//        GenericObjectPoolConfig genericObjectPoolConfig =
//                new GenericObjectPoolConfig();
//        //redis客户端配置
//        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder
//                builder =  LettucePoolingClientConfiguration.builder().
//                commandTimeout(Duration.ofMillis(timeOut));
//        builder.poolConfig(genericObjectPoolConfig);
//        LettuceClientConfiguration lettuceClientConfiguration = builder.build();
//        //根据配置和客户端配置创建连接
//        LettuceConnectionFactory lettuceConnectionFactory = new
//                LettuceConnectionFactory(redisConfiguration,lettuceClientConfiguration);
//        lettuceConnectionFactory .afterPropertiesSet();
//        return lettuceConnectionFactory;
//    }
//}
