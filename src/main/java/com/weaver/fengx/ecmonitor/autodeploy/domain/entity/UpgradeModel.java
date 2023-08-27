package com.weaver.fengx.ecmonitor.autodeploy.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Fengx
 * 补丁包实体类
 **/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpgradeModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;
    /**
     * 补丁包的编号
     */
    private String upgradeCode;
    /**
     * 补丁包的描述
     */
    private String upgradeDesc;
    /**
     * 操作类型：1-升级，0-回滚
     */
    private String operateType;
    /**
     * 操作时间
     */
    private Timestamp operateTime;
    /**
     * 操作人
     */
    private String operater;
    /**
     * 系统类型：1-开发环境业务，2-验证环境业务，3-生产环境业务
     */
    private String systemType;
    /**
     * 是否可以回滚，1-可以，0-不可以
     */
    private String canRollback;

}
