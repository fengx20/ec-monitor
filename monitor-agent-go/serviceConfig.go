// 每个 Go 程序都是由包构成的。
// 程序从 main 包开始运行。
package main

// 分组形式导入
import (
	"encoding/json"
	rotatelogs "github.com/lestrrat-go/file-rotatelogs"
	"io/ioutil"
	"log"
	"time"
	// 日志库中的“明星库”
	//log "github.com/sirupsen/logrus"
	"os"
	"os/exec"
)

// 配置文件的对象
// 一个结构体（struct）就是一组字段（field）
type ServiceConfig struct {
	MonitorServerUrl string
	MonitorTimerCron string
	Ports            string
	ServiceNames     string
	LogDir           string
	RotationCount    uint
	IP               string
	DiskPath         string
}

type JsonStruct struct {
}

// 结构体赋值
var serviceConfig = ServiceConfig{}

func init() {

	// 读取文件监控配置文件
	// 简洁赋值语句 := 可在类型明确的地方代替 var 声明，不能在函数外使用
	// JsonParse 指向 JsonStruct 结构体
	JsonParse := NewJsonStruct()
	JsonParse.Load("./serviceConfig.json", &serviceConfig)
	// 结构体字段使用点号来访问。
	path := serviceConfig.LogDir + "/monitor.log"
	// _（下划线）是个特殊的变量名，任何赋予它的值都会被丢弃。在这个例子中，我们将值35赋予b，并同时丢弃34；_, b := 34, 35
	// 通过这种方式获取有多个返回值的函数对应的返回值
	isStaticDirExist, _ := PathExists(serviceConfig.LogDir)
	if !isStaticDirExist {
		// 执行命令的库是 os/exec，exec.Command 函数返回一个 Cmd 对象
		cmd := exec.Command("sh", "-c", " mkdir -p "+serviceConfig.LogDir+" ;")
		// 运行命令
		cmd.Run()
	}
	/* 日志轮转相关函数
	`WithLinkName` 为最新的日志建立软连接
	`WithRotationTime` 设置日志分割的时间，隔多久分割一次
	WithMaxAge 和 WithRotationCount二者只能设置一个
	  `WithMaxAge` 设置文件清理前的最长保存时间
	  `WithRotationCount` 设置文件清理前最多保存的个数
	*/
	// 下面配置日志每隔 24小时轮转一个新文件，保留最近十年的日志文件，多余的自动清理掉。
	//Second/Minute/Hour/%H%M
	// 使用rotatelogs完成日志分割、日志定期清理、生成软链文件指向最新日志
	fileLogWwriter, _ := rotatelogs.New(
		path+".%Y%m%d",
		rotatelogs.WithLinkName(path),
		rotatelogs.WithRotationCount(serviceConfig.RotationCount),
		rotatelogs.WithRotationTime(time.Duration(24)*time.Hour),
	)
	log.SetOutput(fileLogWwriter)
	//log.SetFormatter(&log.TextFormatter{
	//	TimestampFormat: "2006-01-02 15:04:05",//时间格式化
	//})
}

// Go 拥有指针。指针保存了值的内存地址
// 类型 *T 是指向 T 类型值的指针。其零值为 nil。
// * 操作符表示指针指向的底层值。
func NewJsonStruct() *JsonStruct {
	// & 操作符会生成一个指向其操作数的指针，创建一个 *JsonStruct 类型的结构体（指针）
	return &JsonStruct{}
}

func (jst *JsonStruct) Load(filename string, v interface{}) {
	// ReadFile函数会读取文件的全部内容，并将结果以[]byte类型返回
	// func ReadFile(filename string) ([]byte, error)
	// error 如果读取失败，返回错误信息，否则，返回 nil。
	data, err := ioutil.ReadFile(filename)
	if err != nil {
		//log.Error(err)
		return
	}
	// 读取的数据为json格式，需要进行解码
	err = json.Unmarshal(data, v)
	if err != nil {
		//log.Error(err)
		return
	}

}

// 函数可以返回任意数量的返回值。
// 判断对应path是否存在
func PathExists(path string) (bool, error) {
	_, err := os.Stat(path)
	if err == nil {
		return true, nil
	}
	if os.IsNotExist(err) {
		return false, nil
	}
	return false, err
}
