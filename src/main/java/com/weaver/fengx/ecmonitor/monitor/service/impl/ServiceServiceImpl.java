package com.weaver.fengx.ecmonitor.monitor.service.impl;

import com.weaver.fengx.ecmonitor.monitor.mapper.ServiceMapper;
import com.weaver.fengx.ecmonitor.monitor.model.ServiceModel;
import com.weaver.fengx.ecmonitor.monitor.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Fengx
 * 服务接口实现
 */
@Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    private ServiceMapper serviceMapper;

    @Override
    public List<ServiceModel> findService(String serviceDesc) {
        return serviceMapper.findService(serviceDesc);
    }

    @Override
    public void addService(ServiceModel serviceModel) {
        serviceMapper.addService(serviceModel);
    }

    @Override
    public ServiceModel findServiceById(String serviceId) {
        return serviceMapper.findServiceById(serviceId);
    }

    @Override
    public void updateService(ServiceModel serviceModel) {
        serviceMapper.updateService(serviceModel);
    }

    @Override
    public void deleteService(ServiceModel serviceModel) {
        serviceMapper.deleteService(serviceModel);
    }
}
