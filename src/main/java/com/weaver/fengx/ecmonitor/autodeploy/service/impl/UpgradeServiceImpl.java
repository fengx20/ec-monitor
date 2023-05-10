package com.weaver.fengx.ecmonitor.autodeploy.service.impl;

import com.weaver.fengx.ecmonitor.autodeploy.constant.GlobalConstant;
import com.weaver.fengx.ecmonitor.autodeploy.mapper.UpgradeMapper;
import com.weaver.fengx.ecmonitor.autodeploy.model.UpgradeModel;
import com.weaver.fengx.ecmonitor.autodeploy.service.UpgradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Fengx
 * 升级服务实现
 **/
@Service
public class UpgradeServiceImpl implements UpgradeService {

    @Autowired
    private UpgradeMapper upgradeMapper;

    @Override
    public int addUpgrade(UpgradeModel upgrade) {
        return upgradeMapper.addUpgrade(upgrade);
    }

    @Override
    public String getMaxUpgradeCode(String system) {
        String upgradeCode = upgradeMapper.getMaxUpgradeCode(system);
        if(upgradeCode == null){
            upgradeCode = "0001";
        }
        return upgradeCode;
    }

    @Override
    public List<UpgradeModel> findUpgrades(String system) {
        return upgradeMapper.findUpgrades(system, GlobalConstant.ROLLBACK_MAX_DAY);
    }

    @Override
    public UpgradeModel getLastUpgrade(String system) {
        return upgradeMapper.getLastUpgrade(system);
    }

    @Override
    public UpgradeModel getUpgradeByCode(String system, String upgradeCode) {
        return upgradeMapper.getUpgradeByCode(system, upgradeCode, GlobalConstant.ROLLBACK_MAX_DAY);
    }

    @Override
    public List<UpgradeModel> getUpgradeByIds(String system, Integer startId, Integer endId) {
        return upgradeMapper.getUpgradeByIds(system, startId, endId, GlobalConstant.ROLLBACK_MAX_DAY);
    }
}
