package com.weaver.fengx.ecmonitor.monitor.controller;

import com.weaver.fengx.ecmonitor.common.result.AjaxResult;
import com.weaver.fengx.ecmonitor.monitor.entity.ServerModel;
import com.weaver.fengx.ecmonitor.monitor.entity.ServiceModel;
import com.weaver.fengx.ecmonitor.monitor.service.ServerService;
import com.weaver.fengx.ecmonitor.monitor.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/monitor")
public class MonitorController {

    @Autowired
    public ServiceService serviceService;

    @Autowired
    public ServerService serverService;

    // 服务Map
    // 使用HashMap进行put操作时存在丢失数据的情况，使用线程安全类ConcurrentHashMap
    public static Map<String, ServiceModel> serviceMap = new ConcurrentHashMap<>();

    // 服务器Map
    public static Map<String, ServerModel> serverMap = new ConcurrentHashMap<>();

    /**
     * 进行服务探测，接收监听agent发送
     * @param requestBody
     * @return
     */
    @PostMapping("")
    public AjaxResult<String> monitor(@RequestParam("requestBody") String requestBody) {
        // # 前为 服务器信息，后为 服务信息；磁盘目录空间：/:65.81 ；/dev:0.00
        // 示例格式：ip:192.168.52.100;cpu:0.00;mem:28.79;disk:/:65.81,/dev:0.00 # 52-100:1 # 52-101:0
        // System.out.println("=============="+requestBody);
        if (!"".equals(requestBody)) {
            String[] monitorArr = requestBody.split("#");
            for (String monitor : monitorArr) {
                if ("".equals(monitor)) {
                    continue;
                }
                // 服务器信息
                if (monitor.startsWith("ip")) {
                    // 处理服务器
                    this.handleServer(monitor);
                }
                // 服务信息
                else {
                    // 处理服务
                    this.handleService(monitor);
                }
            }
        }
        return AjaxResult.success();
    }

    /**
     * 处理服务器
     * @param monitor
     */
    private void handleServer(String monitor) {
        String[] serverArr = monitor.split(";");
        String ip = "", cpuRate = "", memRate = "", diskRate = "";
        for (String server : serverArr) {
            if (server.startsWith("ip")) {
                ip = server.replace("ip:", "");
            } else if (server.startsWith("cpu:")) {
                cpuRate = server.replace("cpu:", "");
            } else if (server.startsWith("mem:")) {
                memRate = server.replace("mem:", "");
            } else if (server.startsWith("disk:")) {
                diskRate = server.replace("disk:", "");
            }
        }
        if (ip.length() > 32 || cpuRate.length() > 32
                || memRate.length() > 32 || diskRate.length() > 128) {
            return;
        }
        // 如果该服务器还未加入内存
        if (!serverMap.containsKey(ip)) {
            ServerModel dbServerModel = serverService.findServerByIp(ip);
            // 如果数据库存在该服务器
            if (dbServerModel != null) {
                ServerModel serverModel = new ServerModel();
                serverModel.setLastCheckTime(new Date());
                serverModel.setCpuRate(cpuRate);
                serverModel.setMemRate(memRate);
                serverModel.setDiskRate(diskRate);
                serverMap.put(ip, serverModel);
            }
        } else {
            serverMap.get(ip).setLastCheckTime(new Date());
            serverMap.get(ip).setCpuRate(cpuRate);
            serverMap.get(ip).setMemRate(memRate);
            serverMap.get(ip).setDiskRate(diskRate);
        }
    }

    /**
     * 处理服务
     * @param monitor
     */
    private void handleService(String monitor) {
        String[] serviceArr = monitor.split(":");
        if (serviceArr.length == 2) {
            // 服务id
            String serviceId = serviceArr[0];
            // 服务状态
            String serviceStatus = serviceArr[1];
            // 服务状态长度必须为1
            if (serviceStatus.length() != 1) {
                return;
            }
            // 如果该服务还未加入内存
            if (!serviceMap.containsKey(serviceId)) {
                ServiceModel dbServiceModel = serviceService.findServiceById(serviceId);
                // 如果数据库存在该服务
                if (dbServiceModel != null) {
                    ServiceModel serviceModel = new ServiceModel();
                    serviceModel.setLastCheckTime(new Date());
                    serviceModel.setServiceStatus(serviceStatus);
                    serviceMap.put(serviceId, serviceModel);
                }
            } else {
                serviceMap.get(serviceId).setLastCheckTime(new Date());
                serviceMap.get(serviceId).setServiceStatus(serviceStatus);
            }
        }
    }
}
