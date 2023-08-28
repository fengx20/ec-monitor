package com.weaver.fengx.ecmonitor.monitor.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Fengx
 * 服务器实体类
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServerModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 服务器IP
     */
    private String serverIp;

    /**
     * 服务器描述
     */
    private String serverDesc;

    /**
     * 最后在线时间
     *
     * @JsonFormat：格式化时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date lastCheckTime;

    /**
     * 最后探测时间距离当前时间的差值状态，如果大于设定的值，则返回0，否则返回1
     */
    private String checkTimeStatus;

    /**
     * cpu使用率
     */
    private String cpuRate;

    /**
     * 内存使用率
     */
    private String memRate;

    /**
     * 磁盘使用率
     */
    private String diskRate;

}
