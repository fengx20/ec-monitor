package com.weaver.fengx.ecmonitor.common.netty.nettyMode.annotation;

import com.weaver.fengx.ecmonitor.common.netty.nettyMode.client.ClientBoot;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Fengx
 * 12、客户端启动开关
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ClientBoot.class)
public @interface EnableNettyClient {
}
