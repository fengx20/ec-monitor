package com.weaver.fengx.ecmonitor.autodeploy.controller;

import com.weaver.fengx.ecmonitor.autodeploy.constant.GlobalConstant;
import com.weaver.fengx.ecmonitor.autodeploy.model.UpgradeModel;
import com.weaver.fengx.ecmonitor.autodeploy.service.UpgradeService;
import com.weaver.fengx.ecmonitor.common.result.AjaxResult;
import com.weaver.fengx.ecmonitor.common.result.ResultMsgEnum;
import com.weaver.fengx.ecmonitor.user.model.UserModel;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Fengx
 * 升级
 **/
// 等同于@Controller + @ResponseBody
@RestController
@RequestMapping("/upgrade")
public class UpgradeController {

    @Autowired
    public UpgradeService upgradeService;

    /**
     * 开发环境业务的更新消息集合
     */
    List<String> ecologyDevMsgList = new ArrayList<>();
    /**
     * 验证环境业务的更新消息集合
     */
    List<String> ecologyUatMsgList = new ArrayList<>();
    /**
     * 生产环境业务的更新消息集合
     */
    List<String> ecologyPropMsgList = new ArrayList<>();

    /**
     * 更新列表
     * @param system
     * @return
     */
    @GetMapping("/upgradeList/{system}")
    public AjaxResult<List<UpgradeModel>> upgradeList(@PathVariable(value = "system", required = false) String system) {
        // 权限判断
        if((UserModel)(SecurityUtils.getSubject().getPrincipal()) == null){
            return AjaxResult.warn(ResultMsgEnum.UNAUTHORIZED.getCode(), ResultMsgEnum.UNAUTHORIZED.getMessage());
        }
        String roles = ((UserModel)(SecurityUtils.getSubject().getPrincipal())).getRoles();
        if(roles.length() != 3){
            return AjaxResult.warn(ResultMsgEnum.UNAUTHORIZED.getCode(), ResultMsgEnum.UNAUTHORIZED.getMessage());
        }
        // 开发环境
        if(system.equals(GlobalConstant.ECOLOGY_SYSTEM_DEV)){
            // 权限：100 第一位为1时 才有权限
            if(roles.charAt(0) != '1'){return AjaxResult.warn(ResultMsgEnum.UNAUTHORIZED.getCode(), ResultMsgEnum.UNAUTHORIZED.getMessage()); }
        }
        // 验证环境
        else if(system.equals(GlobalConstant.ECOLOGY_SYSTEM_UAT)){
            // 权限：010 第二位为1时 才有权限
            if(roles.charAt(1) != '1'){return AjaxResult.warn(ResultMsgEnum.UNAUTHORIZED.getCode(), ResultMsgEnum.UNAUTHORIZED.getMessage()); }
        }
        // 生产环境
        else if(system.equals(GlobalConstant.ECOLOGY_SYSTEM_PROD)){
            if(roles.charAt(2) != '1'){return AjaxResult.warn(ResultMsgEnum.UNAUTHORIZED.getCode(), ResultMsgEnum.UNAUTHORIZED.getMessage()); }
        } else{
            return AjaxResult.error();
        }
        // 获取更新列表
        List<UpgradeModel> upgradeList = upgradeService.findUpgrades(system);
        return AjaxResult.success(upgradeList);
    }

    /**
     * 升级
     * @param system
     * @return
     */
    @PostMapping("/addupgrade")
    public AjaxResult<String> addupgrade(@RequestParam("system") String system) {
        // 权限判断
        if((UserModel)(SecurityUtils.getSubject().getPrincipal()) == null){
            return AjaxResult.warn(ResultMsgEnum.UNAUTHORIZED.getCode(), ResultMsgEnum.UNAUTHORIZED.getMessage());
        }
        String roles = ((UserModel)(SecurityUtils.getSubject().getPrincipal())).getRoles();
        if(roles.length() != 3){
            return AjaxResult.warn(ResultMsgEnum.UNAUTHORIZED.getCode(), ResultMsgEnum.UNAUTHORIZED.getMessage());
        }
        if(system.equals(GlobalConstant.ECOLOGY_SYSTEM_DEV)){
            if(roles.charAt(0) != '1'){return AjaxResult.warn(ResultMsgEnum.UNAUTHORIZED.getCode(), ResultMsgEnum.UNAUTHORIZED.getMessage()); }
        } else if(system.equals(GlobalConstant.ECOLOGY_SYSTEM_UAT)){
            if(roles.charAt(1) != '1'){return AjaxResult.warn(ResultMsgEnum.UNAUTHORIZED.getCode(), ResultMsgEnum.UNAUTHORIZED.getMessage()); }
        } else if(system.equals(GlobalConstant.ECOLOGY_SYSTEM_PROD)){
            if(roles.charAt(2) != '1'){return AjaxResult.warn(ResultMsgEnum.UNAUTHORIZED.getCode(), ResultMsgEnum.UNAUTHORIZED.getMessage()); }
        } else{return AjaxResult.error(); }
        return AjaxResult.success();
    }

    /**
     * 上传补丁包
     * @param file
     * @param system
     * @param upgradeDesc
     * @return
     */
    @PostMapping("/upload")
    public AjaxResult<String> upgrade(@RequestParam("file") MultipartFile file
            ,@RequestParam("system") String system, @RequestParam("kbdesc") String upgradeDesc) {
        // 权限判断
        if((UserModel)(SecurityUtils.getSubject().getPrincipal()) == null){
            return AjaxResult.warn(ResultMsgEnum.UNAUTHORIZED.getCode(), ResultMsgEnum.UNAUTHORIZED.getMessage());
        }
        String roles = ((UserModel)(SecurityUtils.getSubject().getPrincipal())).getRoles();
        if(roles.length() != 3){
            return AjaxResult.warn(ResultMsgEnum.UNAUTHORIZED.getCode(), ResultMsgEnum.UNAUTHORIZED.getMessage());
        }
        // 升级的环境与权限对应
        if("1".equals(system)){
            if(roles.charAt(0) != '1'){return AjaxResult.warn(ResultMsgEnum.UNAUTHORIZED.getCode(), ResultMsgEnum.UNAUTHORIZED.getMessage()); }
        } else if("2".equals(system)){
            if(roles.charAt(1) != '1'){return AjaxResult.warn(ResultMsgEnum.UNAUTHORIZED.getCode(), ResultMsgEnum.UNAUTHORIZED.getMessage()); }
        } else if("3".equals(system)){
            if(roles.charAt(2) != '1'){return AjaxResult.warn(ResultMsgEnum.UNAUTHORIZED.getCode(), ResultMsgEnum.UNAUTHORIZED.getMessage()); }
        } else{return AjaxResult.error(); }

        // 初始化上传的信息
        StringBuilder uploadDir = new StringBuilder();
        List<String> msgList = initUploadInfo(system, uploadDir);
        if (msgList != null && msgList.size() > 0) {
            return AjaxResult.warn("当前环境正在更新补丁，请稍后进行更新...");
        }
        // 补丁文件不能为空
        if (file.isEmpty()) {
           return AjaxResult.warn("补丁文件不存在，请选择文件");
        }
        // 获取最新编号赋值
        String upgradeCode = upgradeService.getMaxUpgradeCode(system);
        msgList.add("自动生成补丁编号:"+upgradeCode);
        msgList.add("开始进入更新的操作......");
        // 将文件传输到指定的目录
        File dest = new File(uploadDir.toString() + GlobalConstant.UPGRADE_FILE_NAME);
        try {
            // 将文件上传到服务器指定位置，只能使用一次，之后不可以再使用getInputStream()方法，原因：文件流只可以接收读取一次，传输完毕则关闭流
            file.transferTo(dest);
        }catch (IOException e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        msgList.add("补丁文件上传成功");
        msgList.add("开始执行更新的脚本......");
        // 执行脚本
        execUpgradeShell(uploadDir.toString()+GlobalConstant.UPGRADE_SHELL_FILE_NAME, msgList, upgradeCode);
        if(!msgList.contains("error")){
            msgList.add("success");
            // 添加更新记录
            UpgradeModel upgrade = new UpgradeModel(upgradeCode, upgradeDesc.trim(), "1", ((UserModel)(SecurityUtils.getSubject().getPrincipal())).getUserName(), system);
            upgradeService.addUpgrade(upgrade);
        }
        clearMsg(msgList);
        return AjaxResult.success();
    }

    /**
     * 更新的心跳包，获取实时更新进度
     * @param system
     * @param curIndex
     * @return
     */
    @PostMapping("/upgradeHeart")
    public AjaxResult<Map<String, Object>> upgradeHeart(@RequestParam("system") String system, @RequestParam("curIndex") Integer curIndex){
        Map<String, Object> resultMap = new HashMap<>();
        StringBuilder uploadDir = new StringBuilder();
        // 初始化上传的信息
        List<String> msgList = initUploadInfo(system, uploadDir);
        List<String> data = new ArrayList<>();
        int curLenth = 0;
        if (msgList != null) {
            curLenth = msgList.size();
            for (int i = 0; i < (curLenth - curIndex); i++) {
                data.add(msgList.get(curIndex + i));
            }
        }
        resultMap.put("data", data);
        resultMap.put("curIndex", curLenth + "");
        // 清空消息列表
        if (msgList != null && (msgList.contains("error") || msgList.contains("success"))) {
            msgList.clear();
        }
        return AjaxResult.success(resultMap);
    }

    /**
     * 清除消息记录
     * @param msgList
     */
    private void clearMsg(List<String> msgList){
        try{
            Thread.sleep(2000);
            msgList.clear();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    /**
     * 执行更新的脚本
     * @param shellPath 脚本程序路径
     * @param msgList 消息集合
     * @param upgradeCode 补丁包编码
     */
    private void execUpgradeShell(String shellPath, List<String> msgList, String upgradeCode){
        try {
            // ProcessBuilder类是J2SE 1.5在java.lang中新添加的一个新类，
            // 此类用于创建操作系统进程，它提供一种启动和管理进程（也就是应用程序）的方法。在J2SE 1.5之前，都是由Process类处来实现进程的控制管理。
            ProcessBuilder pb = new ProcessBuilder(shellPath, upgradeCode);
            // 每个 ProcessBuilder 实例管理一个进程属性集。
            // 它的start()方法利用这些属性创建一个新的Process 实例。start() 方法可以从同一实例重复调用，以利用相同的或相关的属性创建新的子进程。
            Process p = pb.start();

            // BufferedReader:从字符输入流中读取文本并缓冲字符，以便有效地读取字符，数组和行
            // InputStreamReader类是从字节流到字符流的桥接器：它使用指定的字符集读取字节并将它们解码为字符
            BufferedReader stdInout = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = stdInout.readLine()) != null) {
                // 进程执行返回信息写入消息集合
                msgList.add(line);
            }
            // 等待写入过程完成
            p.waitFor();
        }
        catch (Exception e) {
            e.printStackTrace();
            if(e.getMessage().length()>100){
                msgList.add(e.getMessage().substring(0,100));
            } else{
                msgList.add(e.getMessage());
            }
            msgList.add("error");
        }
    }

    /**
     * 初始化上传的信息
     * @param system
     * @param uploadDir
     * @return
     */
    private List<String> initUploadInfo(String system, StringBuilder uploadDir){
        if(system.equals(GlobalConstant.ECOLOGY_SYSTEM_DEV)){
            // 开发环境上传路径
            uploadDir.append(GlobalConstant.ECOLOGY_UPLOAD_FILE_DIR_DEV);
            return ecologyDevMsgList;
        } else if(system.equals(GlobalConstant.ECOLOGY_SYSTEM_UAT)){
            // 验证环境上传路径
            uploadDir.append(GlobalConstant.ECOLOGY_UPLOAD_FILE_DIR_UAT);
            return ecologyUatMsgList;
        } else if(system.equals(GlobalConstant.ECOLOGY_SYSTEM_PROD)){
            // 生产环境上传路径
            uploadDir.append(GlobalConstant.ECOLOGY_UPLOAD_FILE_DIR_PROP);
            return ecologyPropMsgList;
        }
        return null;
    }

}
