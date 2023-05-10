package com.weaver.fengx.ecmonitor.monitor.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author Fengx
 * 读取配置数据
 **/
// 读取指定 properties 文件，ignoreResourceNotFound：指示 value 资源位置查找失败时是否忽略，默认 false，找不到就会报错。
@PropertySource(value = {"classpath:common.properties", "file:common.properties"}, ignoreResourceNotFound = true, encoding = "UTF-8")
@Component
public class CommonProperties {

    // @Value：将配置文件的属性读出来
    // ${ property : default_value }
    // #{ obj.property? :default_value }
    // 第一个注入的是外部配置文件对应的property，第二个则是SpEL表达式对应的内容
    @Value("${service.lastCheckTime.diff}")
    public Integer lastCheckTimeDiff;

    @Value("${server.quota.cpu}")
    public Integer quotaCpu;

    @Value("${server.quota.mem}")
    public Integer quotaMem;

    @Value("${server.quota.disk}")
    public Integer quotaDisk;

    public Integer getLastCheckTimeDiff() {
        return lastCheckTimeDiff;
    }

    public void setLastCheckTimeDiff(Integer lastCheckTimeDiff) {
        this.lastCheckTimeDiff = lastCheckTimeDiff;
    }

    public Integer getQuotaCpu() {
        return quotaCpu;
    }

    public void setQuotaCpu(Integer quotaCpu) {
        this.quotaCpu = quotaCpu;
    }

    public Integer getQuotaMem() {
        return quotaMem;
    }

    public void setQuotaMem(Integer quotaMem) {
        this.quotaMem = quotaMem;
    }

    public Integer getQuotaDisk() {
        return quotaDisk;
    }

    public void setQuotaDisk(Integer quotaDisk) {
        this.quotaDisk = quotaDisk;
    }
}
