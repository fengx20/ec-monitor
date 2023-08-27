package com.weaver.fengx.ecmonitor.autodeploy.service.impl;

import com.weaver.fengx.ecmonitor.autodeploy.domain.entity.UpgradeModel;
import com.weaver.fengx.ecmonitor.autodeploy.mapper.RollbackMapper;
import com.weaver.fengx.ecmonitor.autodeploy.service.IRollbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author Fengx
 * 回滚服务实现
 **/
@Slf4j
@Service
public class RollBackServiceImplI implements IRollbackService {

    @Resource
    private RollbackMapper rollbackMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String getCurUpgradeCode(String system) {
        return rollbackMapper.getCurUpgradeCode(system);
    }

    @Override
    public UpgradeModel getLastRollback(String system) {
        return rollbackMapper.getLastRollback(system);
    }

}
