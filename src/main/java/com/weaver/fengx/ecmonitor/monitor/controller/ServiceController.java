package com.weaver.fengx.ecmonitor.monitor.controller;

import com.weaver.fengx.ecmonitor.common.result.AjaxResult;
import com.weaver.fengx.ecmonitor.monitor.entity.ServiceModel;
import com.weaver.fengx.ecmonitor.monitor.properties.CommonProperties;
import com.weaver.fengx.ecmonitor.monitor.service.ServiceService;
import com.weaver.fengx.ecmonitor.user.entity.UserModel;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    public ServiceService serviceService;

    @Autowired
    private CommonProperties commonProperties;

    @RequestMapping("")
    public String service(Model model) {
        model.addAttribute("username", ((UserModel) (SecurityUtils.getSubject().getPrincipal())).getUserName());
        return "serviceMain";
    }

    @GetMapping("/findService")
    // 将controller的方法返回的对象通过适当的转换器转换为指定的格式之后，写入到response对象的body区，通常用来返回JSON数据或者是XML数据。
    // 注意：在使用此注解之后不会再走视图处理器，而是直接将数据写入到输入流中，他的效果等同于通过response对象输出指定格式的数据。
//    @ResponseBody
    public AjaxResult<List<ServiceModel>> findService(@RequestParam("serviceDesc") String serviceDesc) {
        List<ServiceModel> serviceList = serviceService.findService(serviceDesc);
        Date nowDate = new Date();
        for (ServiceModel serviceModel : serviceList) {
            ServiceModel cacheServerModel = MonitorController.serviceMap.get(serviceModel.getServiceId());
            if (cacheServerModel != null) {
                serviceModel.setLastCheckTime(cacheServerModel.getLastCheckTime());
                serviceModel.setServiceStatus(cacheServerModel.getServiceStatus());
            }
            if (serviceModel.getLastCheckTime() == null) {
                serviceModel.setCheckTimeStatus("0");
            } else if ((nowDate.getTime() - serviceModel.getLastCheckTime().getTime()) > commonProperties.getLastCheckTimeDiff() * 1000) {
                serviceModel.setCheckTimeStatus("0");
            }
        }
        return AjaxResult.success(serviceList);
    }

    @PostMapping("/addService")
    public AjaxResult<String> addService(@RequestBody ServiceModel serviceModel) {
        if ("".equals(serviceModel.getServiceId())) {
            return AjaxResult.warn("服务id不能为空");
        }
        if ("".equals(serviceModel.getServiceDesc())) {
            return AjaxResult.warn("服务描述不能为空");
        }
        // 判断服务id是否存在
        ServiceModel dbServiceModel = serviceService.findServiceById(serviceModel.getServiceId());
        if (dbServiceModel != null) {
            return AjaxResult.warn("服务id已存在");
        }
        serviceService.addService(serviceModel);
        return AjaxResult.success();
    }

    @PostMapping("/updateService")
    public AjaxResult<String> updateService(@RequestBody ServiceModel serviceModel) {
        if ("".equals(serviceModel.getServiceId())) {
            return AjaxResult.warn("服务id不能为空");
        }
        if ("".equals(serviceModel.getServiceDesc())) {
            return AjaxResult.warn("服务描述不能为空");
        }
        //判断服务id是否存在
        ServiceModel dbServiceModel = serviceService.findServiceById(serviceModel.getServiceId());
        if (dbServiceModel == null) {
            return AjaxResult.warn("服务id不存在");
        }
        serviceService.updateService(serviceModel);
        return AjaxResult.success();
    }

    @PostMapping("/deleteService")
    public AjaxResult<String> deleteService(@RequestBody ServiceModel serviceModel) {
        if ("".equals(serviceModel.getServiceId())) {
            return AjaxResult.warn("服务id不能为空");
        }
        //判断服务id是否存在
        ServiceModel dbServiceModel = serviceService.findServiceById(serviceModel.getServiceId());
        if (dbServiceModel == null) {
            return AjaxResult.warn("服务id不存在");
        }
        serviceService.deleteService(serviceModel);
        return AjaxResult.success();
    }

}
