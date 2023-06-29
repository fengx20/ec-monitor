package com.weaver.fengx.ecmonitor.common.netty.nettyMode.bean;

import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;

/**
 * @author Fengx
 * 1、消息对象，为了防止tcp粘包拆包问题
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageBean {
    /**
     * 数据长度
     */
    private Integer len;
    /**
     * 通讯数据
     */
    private byte[] content;

    public MessageBean(Object object) {
        content = JSONUtil.toJsonStr(object).getBytes(StandardCharsets.UTF_8);
        len = content.length;
    }
}
