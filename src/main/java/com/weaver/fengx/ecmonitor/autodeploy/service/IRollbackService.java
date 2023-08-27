package com.weaver.fengx.ecmonitor.autodeploy.service;

import com.weaver.fengx.ecmonitor.autodeploy.domain.entity.UpgradeModel;

/**
 * @author Fengx
 * 回滚服务
 **/
public interface IRollbackService {

    /**
     * 获取当前系统最大版本号
     * @param system
     * @return
     */
    String getCurUpgradeCode(String system);


    /**
     * 获取最新的回滚记录
     * @param system
     * @return
     */
    UpgradeModel getLastRollback(String system);

}
