package com.weaver.fengx.ecmonitor.common.netty.nettyMode.annotation;

import com.weaver.fengx.ecmonitor.common.netty.nettyMode.server.ServerBoot;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Fengx
 * 13、服务端端启动开关
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ServerBoot.class)
public @interface EnableNettyServer {
}
