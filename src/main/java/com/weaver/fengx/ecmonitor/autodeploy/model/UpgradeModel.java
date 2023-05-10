package com.weaver.fengx.ecmonitor.autodeploy.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Fengx
 * 补丁包实体类
 **/
public class UpgradeModel implements Serializable {

    private static final long serialVersionUID = -1525914055479353120L;

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

    public UpgradeModel(String upgradeCode, String upgradeDesc, String operateType, String operater, String systemType) {
        this.upgradeCode = upgradeCode;
        this.upgradeDesc = upgradeDesc;
        this.operateType = operateType;
        this.operater = operater;
        this.systemType = systemType;
    }

    public String getCanRollback() {
        return canRollback;
    }

    public void setCanRollback(String canRollback) {
        this.canRollback = canRollback;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUpgradeCode() {
        return upgradeCode;
    }

    public void setUpgradeCode(String upgradeCode) {
        this.upgradeCode = upgradeCode;
    }

    public String getUpgradeDesc() {
        return upgradeDesc;
    }

    public void setUpgradeDesc(String upgradeDesc) {
        this.upgradeDesc = upgradeDesc;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public Timestamp getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Timestamp operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperater() {
        return operater;
    }

    public void setOperater(String operater) {
        this.operater = operater;
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }
}
