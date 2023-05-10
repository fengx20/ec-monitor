package com.weaver.fengx.ecmonitor.monitor.service;

import com.weaver.fengx.ecmonitor.monitor.model.ServiceModel;

import java.util.List;

/**
 * 系统服务接口
 */
public interface ServiceService {

    /**
     * 查找服务
     * @param serviceDesc
     * @return
     */
    List<ServiceModel> findService(String serviceDesc);

    /**
     * 新增服务
     * @param serviceModel
     */
    void addService(ServiceModel serviceModel);

    /**
     * 根据id查询服务
     * @param serviceId
     * @return
     */
    ServiceModel findServiceById(String serviceId);

    /**
     * 修改服务
     * @param serviceModel
     */
    void updateService(ServiceModel serviceModel);

    /**
     * 删除服务
     * @param serviceModel
     */
    void deleteService(ServiceModel serviceModel);
}
