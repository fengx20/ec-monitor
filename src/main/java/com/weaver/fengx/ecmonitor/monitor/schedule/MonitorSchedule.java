package com.weaver.fengx.ecmonitor.monitor.schedule;

import com.weaver.fengx.ecmonitor.monitor.controller.MonitorController;
import com.weaver.fengx.ecmonitor.monitor.entity.ServerModel;
import com.weaver.fengx.ecmonitor.monitor.entity.ServiceModel;
import com.weaver.fengx.ecmonitor.monitor.service.ServerService;
import com.weaver.fengx.ecmonitor.monitor.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Iterator;
import java.util.Map;

// 主要用于标记配置类，兼备Component的效果。
@Configuration
// 开启定时任务
@EnableScheduling
public class MonitorSchedule {

    @Autowired
    public ServiceService serviceService;

    @Autowired
    public ServerService serverService;

    // 保存服务状态到数据库，每5分钟执行一次
    @Scheduled(cron = "0 0/5 * * * ?")
    private void saveServiceTask() {
        // 从内存获取服务数据
        Map<String, ServiceModel> serviceMap = MonitorController.serviceMap;
        if (serviceMap != null) {
            Iterator<Map.Entry<String, ServiceModel>> entries = serviceMap.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, ServiceModel> entry = entries.next();
                // 判断服务id是否存在
                ServiceModel dbServiceModel = serviceService.findServiceById(entry.getKey());
                if (dbServiceModel != null) {
                    dbServiceModel.setLastCheckTime(entry.getValue().getLastCheckTime());
                    dbServiceModel.setServiceStatus(entry.getValue().getServiceStatus());
                    serviceService.updateService(dbServiceModel);
                } else {
                    entries.remove();
                }
            }
        }
        System.out.println(MonitorController.serviceMap);
    }

    // 保存服务器使用率到数据库，每5分钟执行一次
    @Scheduled(cron = "0 0/5 * * * ?")
    private void saveServerTask() {
        // 从内存获取服务器数据
        Map<String, ServerModel> serverMap = MonitorController.serverMap;
        if (serverMap != null) {
            Iterator<Map.Entry<String, ServerModel>> entries = serverMap.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, ServerModel> entry = entries.next();
                // 判断服务器ip是否存在
                ServerModel dbServerModel = serverService.findServerByIp(entry.getKey());
                if (dbServerModel != null) {
                    dbServerModel.setLastCheckTime(entry.getValue().getLastCheckTime());
                    dbServerModel.setCpuRate(entry.getValue().getCpuRate());
                    dbServerModel.setMemRate(entry.getValue().getMemRate());
                    dbServerModel.setDiskRate(entry.getValue().getDiskRate());
                    serverService.updateServer(dbServerModel);
                } else {
                    entries.remove();
                }
            }
        }
        System.out.println(MonitorController.serverMap);
    }

}