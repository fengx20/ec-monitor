package com.weaver.fengx.ecmonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcMonitorApplication.class, args);
        System.out.println("ec-monitor启动成功");
    }

}
