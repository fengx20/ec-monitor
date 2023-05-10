package com.weaver.fengx.ecmonitor.monitor.mapper;

import com.weaver.fengx.ecmonitor.monitor.model.ServerModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Fengx
 * 服务器mapper
 **/
@Mapper
public interface ServerMapper {

    List<ServerModel> findServer(String serverDesc);

    void addServer(ServerModel serverModel);

    ServerModel findServerByIp(String serverIp);

    void updateServer(ServerModel serverModel);

    void deleteServer(ServerModel serverModel);

}
