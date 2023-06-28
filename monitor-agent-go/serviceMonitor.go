package main

import (
	"fmt"
	// 获取各种系统和硬件信息
	"github.com/shirou/gopsutil/cpu"
	"github.com/shirou/gopsutil/disk"
	"github.com/shirou/gopsutil/mem"
	"net"
	"os/exec"
	"strings"
	"time"
	//log "github.com/sirupsen/logrus"
	//"github.com/robfig/cron"
	"net/http"
)

// 获取CPU使用率
func GetCpuPercent() string {
	percent, _ := cpu.Percent(3*time.Second, false)
	cpuRate := fmt.Sprintf("%.2f", percent[0])
	return cpuRate
}

// 获取内存使用率
func GetMemPercent() string {
	memInfo, _ := mem.VirtualMemory()
	memRate := fmt.Sprintf("%.2f", memInfo.UsedPercent)
	return memRate
}

// 获取磁盘空间使用率
func GetDiskPercent() string {
	resultStr := ""
	if serviceConfig.DiskPath != "" {
		disksArr := strings.Split(serviceConfig.DiskPath, ",")
		for _, v := range disksArr {
			diskInfo, _ := disk.Usage(v)
			diskRate := fmt.Sprintf("%.2f", diskInfo.UsedPercent)
			resultStr += v + ":" + diskRate + ","
		}
	}
	if resultStr != "" {
		resultStr = resultStr[:len(resultStr)-1]
	}
	return resultStr
}

// 获取本地ip
func GetLocalIp() string {
	if serviceConfig.IP == "" {
		addrs, err := net.InterfaceAddrs()
		if err != nil {
			//log.Info("get local ip failed")
		}
		for _, address := range addrs {
			if ipnet, ok := address.(*net.IPNet); ok && !ipnet.IP.IsLoopback() {
				if ipnet.IP.To4() != nil {
					return ipnet.IP.String()
				}
			}
		}
	}
	return ""
}

// 获取端口的状态
func GetPortStatus() string {
	resultStr := "#"
	if serviceConfig.Ports != "" {
		portsArr := strings.Split(serviceConfig.Ports, ",")
		for _, v := range portsArr {
			portArr := strings.Split(v, ":")
			// 判断端口是否在监听
			commandStr := "netstat -anp 2>/dev/null | grep :" + portArr[1] + " | grep LISTEN | wc -l ;"
			res, _ := exec.Command("sh", "-c", commandStr).Output()
			resString := strings.Replace(string(res), "\n", "", -1)
			// 端口在监听，判断防火墙是否开启对应端口
			if resString != "0" {
				commandStr = "firewall-cmd --query-port=" + portArr[1] + "/tcp ;"
				res, _ = exec.Command("sh", "-c", commandStr).Output()
				resString = strings.Replace(string(res), "\n", "", -1)
				// 防火墙没有开启对应端口
				if resString == "no" {
					resultStr += portArr[0] + ":0#"
				} else {
					// 防火墙对应端口已开启或者防火墙处于关闭状态
					resultStr += portArr[0] + ":1#"
				}

			} else {
				resultStr += portArr[0] + ":0#"
			}
		}
		resultStr = strings.Replace(resultStr, "\n", "", -1)
	}
	resultStr = resultStr[:len(resultStr)-1]
	return resultStr
}

// 获取服务状态
func GetServiceStatus() string {
	resultStr := "#"
	if serviceConfig.ServiceNames != "" {
		servicesArr := strings.Split(serviceConfig.ServiceNames, ",")
		for _, v := range servicesArr {
			serviceArr := strings.Split(v, ":")
			commandStr := "ps -ef|grep -v sh|grep " + serviceArr[1] + "|grep -v grep|awk '{print $2}'|wc -l ;"
			res, _ := exec.Command("sh", "-c", commandStr).Output()
			resString := strings.Replace(string(res), "\n", "", -1)
			if resString == "0" {
				resultStr += serviceArr[0] + ":0#"
			} else {
				resultStr += serviceArr[0] + ":1#"
			}

		}
		resultStr = strings.Replace(resultStr, "\n", "", -1)
	}
	resultStr = resultStr[:len(resultStr)-1]
	return resultStr
}

// 向服务端发送监听消息
func sendMonitor(serverInfo string) {
	// 服务url不为空才发送
	if serviceConfig.MonitorServerUrl != "" {
		// 超时时间：5秒
		client := &http.Client{Timeout: 5 * time.Second}
		_, err := client.Post(serviceConfig.MonitorServerUrl, "text/plain", strings.NewReader(serverInfo))
		if err != nil {
			//log.Info(err)
		}
	}
}

//func main() {
//    // 精确到秒
//	tarCron := cron.New(cron.WithSeconds())
//	// 定时任务发送http请求获取补丁包
//	tarCron.AddFunc(serviceConfig.MonitorTimerCron, func() {
//		serverInfo := "ip:"+GetLocalIp()+";cpu:"+GetCpuPercent()+";mem:"+GetMemPercent()+";disk:"+GetDiskPercent();
//		serverInfo += GetPortStatus()
//		serverInfo += GetServiceStatus()
//		sendMonitor(serverInfo)
//		log.Info(serverInfo)
//	})
//	tarCron.Start()
//	log.Info("monitor agent已启动")
//	// 阻塞主线程停止
//	select {}
//}
