package com.weaver.fengx.ecmonitor.monitor.mapper;

import com.weaver.fengx.ecmonitor.monitor.model.ServiceModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Fengx
 * 服务mapper
 **/
@Mapper
public interface ServiceMapper {

    List<ServiceModel> findService(String serviceDesc);

    void addService(ServiceModel serviceModel);

    ServiceModel findServiceById(String serviceId);

    void updateService(ServiceModel serviceModel);

    void deleteService(ServiceModel serviceModel);

}
