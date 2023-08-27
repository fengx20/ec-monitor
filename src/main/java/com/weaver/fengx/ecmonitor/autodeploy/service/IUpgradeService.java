package com.weaver.fengx.ecmonitor.autodeploy.service;

import com.weaver.fengx.ecmonitor.autodeploy.domain.entity.UpgradeModel;

import java.util.List;

/**
 * @author Fengx
 * 升级服务
 **/
public interface IUpgradeService {

    /**
     * 新增更新记录
     *
     * @param upgrade
     * @return
     */
    int addUpgrade(UpgradeModel upgrade);

    /**
     * 获取指定系统下最大的补丁编号
     *
     * @param system
     * @return
     */
    String getMaxUpgradeCode(String system);

    /**
     * 查询更新记录
     *
     * @param system
     * @return
     */
    List<UpgradeModel> findUpgrades(String system);

    /**
     * 获取最新的更新记录
     *
     * @param system
     * @return
     */
    UpgradeModel getLastUpgrade(String system);

    /**
     * 获取补丁记录
     *
     * @param system
     * @param upgradeCode
     * @return
     */
    UpgradeModel getUpgradeByCode(String system, String upgradeCode);

    /**
     * 获取指定id范围内的更新记录
     *
     * @param system
     * @param startId
     * @param endId
     * @return
     */
    List<UpgradeModel> getUpgradeByIds(String system, Integer startId, Integer endId);

}
