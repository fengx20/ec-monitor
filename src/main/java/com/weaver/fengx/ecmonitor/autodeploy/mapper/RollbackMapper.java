package com.weaver.fengx.ecmonitor.autodeploy.mapper;

import com.weaver.fengx.ecmonitor.autodeploy.entity.UpgradeModel;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Fengx
 * 回滚记录mapper
 **/
@Mapper
public interface RollbackMapper {

    /**
     * 获取当前系统最新回滚版本号
     * @param system
     * @return
     */
    String getLastRollbackCode(String system);

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
