package com.weaver.fengx.ecmonitor.common.netty.nettyMode.annotation;

import java.lang.annotation.*;

/**
 * @author Fengx
 * 14、Netty客户服务端开关整合注解
 **/
// @Target和@Retention可以用来修饰注解，是注解的注解，称为元注解
@Target(ElementType.TYPE)
// @Retention 定义被它所注解的注解保留多久
// source：注解只保留在源文件，当Java文件编译成class文件的时候，注解被遗弃；被编译器忽略
// class：注解被保留到class文件，但jvm加载class文件时候被遗弃，这是默认的生命周期
// // runtime：注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在
// 这3个生命周期分别对应于：Java源文件(.java文件) ---> .class文件 ---> 内存中的字节码。
@Retention(RetentionPolicy.RUNTIME)
// 表明这个注解是由 javadoc记录的，在默认情况下也有类似的记录工具。 如果一个类型声明被注解了文档化，它的注解成为公共API的一部分
@Documented
@EnableNettyClient
@EnableNettyServer
public @interface EnableNetty {
}
