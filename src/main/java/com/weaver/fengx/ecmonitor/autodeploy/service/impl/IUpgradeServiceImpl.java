package com.weaver.fengx.ecmonitor.autodeploy.service.impl;

import com.weaver.fengx.ecmonitor.autodeploy.constant.GlobalConstant;
import com.weaver.fengx.ecmonitor.autodeploy.domain.entity.UpgradeModel;
import com.weaver.fengx.ecmonitor.autodeploy.mapper.UpgradeMapper;
import com.weaver.fengx.ecmonitor.autodeploy.service.IUpgradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Fengx
 * 升级服务实现
 **/
@Slf4j
@Service
public class IUpgradeServiceImpl implements IUpgradeService {

    @Resource
    private UpgradeMapper upgradeMapper;

    @Override
    // Throwable是最顶层的父类，有Error和Exception两个子类。
    // Error表示严重的错误（如OOM等）；
    // Exception可以分为运行时异常（RuntimeException及其子类）和非运行时异常（Exception的子类中，除了RuntimeException及其子类之外的类）。
    // 非运行时异常是检查异常（checked exceptions），一定要try catch，因为这类异常是可预料的，编译阶段就检查的出来；
    // Error和运行时异常是非检查异常（unchecked exceptions），不需要try catch，因为这类异常是不可预料的，编辑阶段不会检查，没必要检查，也检查不出来。
    // 在方法内或者注解上说明发生异常时如何回滚
    @Transactional(rollbackFor = Exception.class)
    public int addUpgrade(UpgradeModel upgrade) {
        return upgradeMapper.addUpgrade(upgrade);
    }

    @Override
    public String getMaxUpgradeCode(String system) {
        log.info("system：{}", system);
        String upgradeCode = upgradeMapper.getMaxUpgradeCode(system);
        if (upgradeCode == null) {
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
