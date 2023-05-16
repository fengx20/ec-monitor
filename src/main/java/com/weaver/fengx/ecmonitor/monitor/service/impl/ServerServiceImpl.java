package com.weaver.fengx.ecmonitor.monitor.service.impl;

import com.weaver.fengx.ecmonitor.monitor.mapper.ServerMapper;
import com.weaver.fengx.ecmonitor.monitor.entity.ServerModel;
import com.weaver.fengx.ecmonitor.monitor.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Fengx
 * 服务器接口实现
 */
@Service
public class ServerServiceImpl implements ServerService {

    @Autowired
    private ServerMapper serverMapper;

    @Override
    public List<ServerModel> findServer(String serverDesc) {
        return serverMapper.findServer(serverDesc);
    }

    @Override
    public void addServer(ServerModel serverModel) {
        serverMapper.addServer(serverModel);
    }

    @Override
    public ServerModel findServerByIp(String serverIp) {
        return serverMapper.findServerByIp(serverIp);
    }

    @Override
    public void updateServer(ServerModel serverModel) {
        serverMapper.updateServer(serverModel);
    }

    @Override
    public void deleteServer(ServerModel serverModel) {
        serverMapper.deleteServer(serverModel);
    }
}
