package com.weaver.fengx.ecmonitor.monitor.service;

import com.weaver.fengx.ecmonitor.monitor.model.ServerModel;

import java.util.List;

/**
 * @author Fengx
 * 服务器接口
 */
public interface ServerService {

    /**
     * 查找服务器
     * @param serverDesc
     * @return
     */
    List<ServerModel> findServer(String serverDesc);

    /**
     * 新增服务器
     * @param serverModel
     */
    void addServer(ServerModel serverModel);

    /**
     * 根据ip查询服务器
     * @param serverIp
     * @return
     */
    ServerModel findServerByIp(String serverIp);

    /**
     * 修改服务器
     * @param serverModel
     */
    void updateServer(ServerModel serverModel);

    /**
     * 删除服务器
     * @param serverModel
     */
    void deleteServer(ServerModel serverModel);
}
