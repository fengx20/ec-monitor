package com.weaver.fengx.ecmonitor.autodeploy.service.impl;

import com.weaver.fengx.ecmonitor.autodeploy.mapper.RollbackMapper;
import com.weaver.fengx.ecmonitor.autodeploy.model.UpgradeModel;
import com.weaver.fengx.ecmonitor.autodeploy.service.RollbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Fengx
 * 回滚服务实现
 **/
@Service
public class RollBackServiceImpl implements RollbackService {

    @Autowired
    private RollbackMapper rollbackMapper;

    @Override
    public String getCurUpgradeCode(String system) {
        return rollbackMapper.getCurUpgradeCode(system);
    }

    @Override
    public UpgradeModel getLastRollback(String system) {
        return rollbackMapper.getLastRollback(system);
    }

}
