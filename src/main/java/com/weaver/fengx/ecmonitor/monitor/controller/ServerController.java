package com.weaver.fengx.ecmonitor.monitor.controller;

import com.weaver.fengx.ecmonitor.common.result.AjaxResult;
import com.weaver.fengx.ecmonitor.monitor.entity.ServerModel;
import com.weaver.fengx.ecmonitor.monitor.properties.CommonProperties;
import com.weaver.fengx.ecmonitor.monitor.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/server")
public class ServerController {

    // 注解注入
    @Autowired
    public ServerService serverService;

    @Autowired
    private CommonProperties commonProperties;

//    public final ServerService serverService;
//
//    private final ServerProperties serverProperties;
//
//    // 使用构造方法注入
//    public ServerController(ServerService serverService, ServerProperties serverProperties) {
//        this.serverService = serverService;
//        this.serverProperties = serverProperties;
//    }

    /**
     * 获取服务器
     * @param serverDesc
     * @return
     */
    @GetMapping("/findServer/{serverDesc}")
    public AjaxResult<List<ServerModel>> findService(@PathVariable("serverDesc") String serverDesc) {
        List<ServerModel> serviceList = serverService.findServer(serverDesc);
        Date nowDate = new Date();
        for (ServerModel serverModel : serviceList) {
            // 从服务器Map获取最新数据
            ServerModel cacheServerModel = MonitorController.serverMap.get(serverModel.getServerIp());
            if (cacheServerModel != null) {
                serverModel.setLastCheckTime(cacheServerModel.getLastCheckTime());
                serverModel.setCpuRate(cacheServerModel.getCpuRate());
                serverModel.setMemRate(cacheServerModel.getMemRate());
                serverModel.setDiskRate(cacheServerModel.getDiskRate());
            }
            if (serverModel.getLastCheckTime() == null) {
                serverModel.setCheckTimeStatus("0");
            }
            // 服务最后探测时间距离当前时间的差值，如果大于该值，则页面的最后在线时间标红,单位：秒
            else if ((nowDate.getTime() - serverModel.getLastCheckTime().getTime()) > commonProperties.getLastCheckTimeDiff() * 1000) {
                serverModel.setCheckTimeStatus("0");
            }
            serverModel.setCpuRate(judgeQuota(serverModel.getCpuRate(), 1));
            serverModel.setMemRate(judgeQuota(serverModel.getMemRate(), 2));
            serverModel.setDiskRate(judgeQuota(serverModel.getDiskRate(), 3));
        }
        return AjaxResult.success(serviceList);
    }

    /**
     * 判断是否超过指标，检查cpu、内存和磁盘
     * @param value
     * @param type
     * @return
     */
    private String judgeQuota(String value, int type) {
        if (value == null || "".equals(value)) {
            return value;
        }
        double doubleValue;
        switch (type) {
            // cpu
            case 1:
                try {
                    doubleValue = Double.parseDouble(value);
                } catch (Exception e) {
                    return value;
                }
                // 判断是否大于阈值
                if (doubleValue >= commonProperties.getQuotaCpu()) {
                    return "<label style='color: red'>" + value + "</label>";
                } else {
                    return "<label style='color: greenyellow'>" + value + "</label>";
                }
            // mem
            case 2:
                try {
                    doubleValue = Double.parseDouble(value);
                } catch (Exception e) {
                    return value;
                }
                if (doubleValue >= commonProperties.getQuotaMem()) {
                    return "<label style='color: red'>" + value + "</label>";
                } else {
                    return "<label style='color: greenyellow'>" + value + "</label>";
                }
            // disk
            case 3:
                String result = "";
                String[] disksArr = value.split(",");
                for (String disk : disksArr) {
                    String[] diskArr = disk.split(":");
                    if (diskArr.length == 2) {
                        String path = diskArr[0];
                        double diskQuota;
                        try {
                            diskQuota = Double.parseDouble(diskArr[1]);
                        } catch (Exception e) {
                            return value;
                        }
                        if (diskQuota >= commonProperties.getQuotaDisk()) {
                            result += "<label style='color: red'>" + path + ":" + diskQuota + "</label>,";
                        } else {
                            result += "<label style='color: greenyellow'>" + path + ":" + diskQuota + "</label>,";
                        }
                    } else {
                        result += disk + ",";
                    }
                }
                if (result.length() > 0) {
                    result = result.substring(0, result.length() - 1);
                }
                return result;
        }
        return value;
    }

    /**
     * 添加服务器信息
     * @param serverModel
     * @return
     */
    @PostMapping("/addServer")
    // @RequestBody 参数需要与实体类字段名一致
    public AjaxResult<String> addServer(@RequestBody ServerModel serverModel) {
        if ("".equals(serverModel.getServerIp())) {
            return AjaxResult.warn("服务器ip不能为空");
        }
        if ("".equals(serverModel.getServerDesc())) {
            return AjaxResult.warn("服务器描述不能为空");
        }
        // 判断服务器ip是否存在
        ServerModel dbServerModel = serverService.findServerByIp(serverModel.getServerIp());
        if (dbServerModel != null) {
            return AjaxResult.warn("服务器ip已存在");
        }
        serverService.addServer(serverModel);
        return AjaxResult.success();
    }

    /**
     * 更新服务器信息
     * @param serverModel
     * @return
     */
    @PostMapping("/updateServer")
    public AjaxResult<String> updateServer(@RequestBody ServerModel serverModel) {
        if ("".equals(serverModel.getServerIp())) {
            return AjaxResult.warn("服务器ip不能为空");
        }
        if ("".equals(serverModel.getServerDesc())) {
            return AjaxResult.warn("服务器描述不能为空");
        }
        // 判断服务器ip是否存在
        ServerModel dbServerModel = serverService.findServerByIp(serverModel.getServerIp());
        if (dbServerModel == null) {
            return AjaxResult.warn("服务器ip不存在");
        }
        serverService.updateServer(serverModel);
        return AjaxResult.success();
    }

    /**
     * 删除服务器信息
     * @param serverModel
     * @return
     */
    @PostMapping("/deleteServer")
    public AjaxResult<String> deleteServer(@RequestBody ServerModel serverModel) {
        if ("".equals(serverModel.getServerIp())) {
            return AjaxResult.warn("服务器ip不能为空");
        }
        // 判断服务id是否存在
        ServerModel dbServerModel = serverService.findServerByIp(serverModel.getServerIp());
        if (dbServerModel == null) {
            return AjaxResult.warn("服务器ip不存在");
        }
        serverService.deleteServer(serverModel);
        return AjaxResult.success();
    }

}
