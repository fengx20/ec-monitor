package com.weaver.fengx.ecmonitor.autodeploy.mapper;

import com.weaver.fengx.ecmonitor.autodeploy.model.UpgradeModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Fengx
 * 升级记录mapper
 **/
@Mapper
public interface UpgradeMapper {

    /**
     * 新增更新记录
     * @param upgrade
     * @return
     */
    int addUpgrade(UpgradeModel upgrade);

    /**
     * 获取指定环境下最大的补丁编号
     * @param system
     * @return
     */
    String getMaxUpgradeCode(String system);

    /**
     * 查询指定环境更新记录
     * @param system 环境
     * @param rollbackDay 天数
     * @return
     */
    List<UpgradeModel> findUpgrades(String system, int rollbackDay);

    /**
     * 获取最新的更新记录
     * @param system
     * @return
     */
    UpgradeModel getLastUpgrade(String system);

    /**
     * 获取指定id范围内的更新记录
     * @param system
     * @param startId
     * @param endId
     * @param rollbackDay
     * @return
     */
    List<UpgradeModel> getUpgradeByIds(String system, Integer startId, Integer endId, int rollbackDay);

    /**
     * 获取补丁记录
     * @param system
     * @param upgradeCode
     * @param rollbackDay
     * @return
     */
    UpgradeModel getUpgradeByCode(String system, String upgradeCode, int rollbackDay);
}
