package com.weaver.fengx.ecmonitor.autodeploy.controller;

import com.weaver.fengx.ecmonitor.autodeploy.constant.GlobalConstant;
import com.weaver.fengx.ecmonitor.autodeploy.entity.UpgradeModel;
import com.weaver.fengx.ecmonitor.autodeploy.service.RollbackService;
import com.weaver.fengx.ecmonitor.autodeploy.service.UpgradeService;
import com.weaver.fengx.ecmonitor.common.result.AjaxResult;
import com.weaver.fengx.ecmonitor.common.result.ResultMsgEnum;
import com.weaver.fengx.ecmonitor.user.entity.UserModel;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Fengx
 * 回滚
 **/
@RestController
@RequestMapping("/rollback")
public class RollbackController {

    @Autowired
    public RollbackService rollbackService;
    @Autowired
    public UpgradeService upgradeService;

    /**
     * 开发环境业务的回滚消息集合
     */
    List<String> ecologyDevMsgList = new ArrayList<>();
    /**
     * 验证环境业务的回滚消息集合
     */
    List<String> ecologyUatMsgList = new ArrayList<>();
    /**
     * 生产环境业务的回滚消息集合
     */
    List<String> ecologyPropMsgList = new ArrayList<>();

    /**
     * 判断是否支持回滚
     * @param upgradeCode
     * @param system
     * @return
     */
    @PostMapping("/check")
    public AjaxResult<Map<String, String>> checkRollback(@RequestParam("upgradeCode") String upgradeCode, @RequestParam("system") String system) {
        Map<String, String> resultMap = new HashMap<>();
        // 权限判断
        if((UserModel)(SecurityUtils.getSubject().getPrincipal()) == null){
            return AjaxResult.warn(ResultMsgEnum.UNAUTHORIZED.getCode(), ResultMsgEnum.UNAUTHORIZED.getMessage());
        }
        String roles = ((UserModel)(SecurityUtils.getSubject().getPrincipal())).getRoles();
        if(roles.length() != 3){
            return AjaxResult.warn(ResultMsgEnum.UNAUTHORIZED.getCode(), ResultMsgEnum.UNAUTHORIZED.getMessage());
        }
        if("1".equals(system)){
            if(roles.charAt(0) != '1'){return AjaxResult.warn(ResultMsgEnum.UNAUTHORIZED.getCode(), ResultMsgEnum.UNAUTHORIZED.getMessage()); }
        } else if("2".equals(system)){
            if(roles.charAt(1) != '1'){return AjaxResult.warn(ResultMsgEnum.UNAUTHORIZED.getCode(), ResultMsgEnum.UNAUTHORIZED.getMessage()); }
        } else if("3".equals(system)){
            if(roles.charAt(2) != '1'){return AjaxResult.warn(ResultMsgEnum.UNAUTHORIZED.getCode(), ResultMsgEnum.UNAUTHORIZED.getMessage()); }
        } else{return AjaxResult.error(); }
        if(upgradeCode == null || "".equals(upgradeCode)){
            return AjaxResult.warn("回滚的编码不能为空");
        }

        /**
         * 获取最新的更新记录
         * 如果没有记录，则返回无记录
         * 如果是回滚的操作，那么回滚的版本只能低于该版本
         * 如果是升级的操作，则还需要查找最近的回滚版本，本次回滚的版本要介于前面的两个版本之间
         */
        // 判断是否大于最大回滚时间限制
        UpgradeModel upgradeModel = upgradeService.getUpgradeByCode(system,upgradeCode);
        if(upgradeModel == null){
            return AjaxResult.warn("系统没有该版本的更新记录，不能进行回滚");
        }
        String canRollback = upgradeModel.getCanRollback();
        if("0".equals(canRollback)){
            return AjaxResult.warn("该版本已超过最大的回滚时间（"+ GlobalConstant.ROLLBACK_MAX_DAY +"天），不能进行回滚");
        }
        StringBuilder rollbackCodes = new StringBuilder();
        UpgradeModel lastUpgradeModel = upgradeService.getLastUpgrade(system);
        Integer lastupgradeid;
        Integer rollbackid = upgradeModel.getId();
        if(lastUpgradeModel == null){
            return AjaxResult.warn("系统没有升级记录，不能进行回滚");
        }else{
            lastupgradeid = lastUpgradeModel.getId();
        }
        List<UpgradeModel> upgradeList = upgradeService.getUpgradeByIds(system, rollbackid, lastupgradeid);
        int maxCode = Integer.parseInt(lastUpgradeModel.getUpgradeCode());
        int minCode = Integer.parseInt(upgradeModel.getUpgradeCode());
        if(upgradeList == null || upgradeList.size() == 0){
            return AjaxResult.warn("没有满足条件的回滚版本，不能进行回滚");
        }else{
            for(UpgradeModel upgradeSingle: upgradeList){
                int tmpCode = Integer.parseInt(upgradeSingle.getUpgradeCode());
                if(maxCode < tmpCode){
                    return AjaxResult.warn("当前最新版本<"+lastUpgradeModel.getUpgradeCode()+">小于<"+upgradeSingle.getUpgradeCode()+">，不能进行还原");
                }
                if(minCode > tmpCode){
                    return AjaxResult.warn("当前最新版本<"+lastUpgradeModel.getUpgradeCode()+">大于中间的<"+upgradeSingle.getUpgradeCode()+">，不能进行还原");
                }
                rollbackCodes.append(upgradeSingle.getUpgradeCode()).append(" ");
            }
        }
        String rollbackCodesStr = rollbackCodes.toString().trim();
        resultMap.put("maxCode", lastUpgradeModel.getUpgradeCode());
        resultMap.put("msg", rollbackCodesStr);
        resultMap.put("status", "success");
        return AjaxResult.success(resultMap);
    }

    /**
     * 回滚升级包
     * @param system
     * @param maxCode
     * @param minCode
     * @param rollbackCodes
     * @return
     */
    @PostMapping("/rollback")
    public AjaxResult<String> rollback(@RequestParam("system") String system
            ,@RequestParam("maxCode") String maxCode, @RequestParam("minCode") String minCode
            ,@RequestParam("rollbackCodes") String rollbackCodes) {
        if((UserModel)(SecurityUtils.getSubject().getPrincipal()) == null){
            return AjaxResult.warn(ResultMsgEnum.UNAUTHORIZED.getCode(), ResultMsgEnum.UNAUTHORIZED.getMessage());
        }
        String roles = ((UserModel)(SecurityUtils.getSubject().getPrincipal())).getRoles();
        if(roles.length() != 3){
            return AjaxResult.warn(ResultMsgEnum.UNAUTHORIZED.getCode(), ResultMsgEnum.UNAUTHORIZED.getMessage());
        }
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
        msgList.add("开始进入回滚的操作......");
        // 执行脚本
        rollbackCodes = rollbackCodes.replace(" ", "-");
        // 去掉最后一个版本
        int index = rollbackCodes.lastIndexOf("-");
        rollbackCodes = rollbackCodes.substring(0, index);
        execUpgradeShell(uploadDir.toString()+GlobalConstant.ROLLBACK_SHELL_FILE_NAME, msgList, rollbackCodes);
        if(!msgList.contains("error")){
            msgList.add("success");
            // 添加回滚记录
            UpgradeModel upgrade = new UpgradeModel(minCode, "从<"+maxCode+">回滚到<"+minCode+">", "0",((UserModel)(SecurityUtils.getSubject().getPrincipal())).getUserName(), system);
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
    @PostMapping("/rollbackHeart")
    public AjaxResult<Map<String, Object>> rollbackHeart(@RequestParam("system") String system, @RequestParam("curIndex") Integer curIndex){
        Map<String, Object> resultMap = new HashMap<>();
        StringBuilder uploadDir = new StringBuilder();
        // 初始化上传的信息
        List<String> msgList = initUploadInfo(system, uploadDir);
        int curLenth = 0;
        List<String> data = new ArrayList<>();
        if (msgList != null) {
            curLenth = msgList.size();
            for(int i = 0; i < (curLenth - curIndex); i++){
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
     * 清空消息列表
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
     * 执行回滚的脚本
     * @param shellPath
     * @param msgList
     * @param rollbackCodes
     */
    private void execUpgradeShell(String shellPath, List<String> msgList, String rollbackCodes){
        try {
            ProcessBuilder pb = new ProcessBuilder(shellPath,rollbackCodes);
            Process p = pb.start();
            BufferedReader stdInout = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = stdInout.readLine()) != null) {
                msgList.add(line);
            }
            p.waitFor();
        }
        catch (Exception e) {
            e.printStackTrace();
            if(e.getMessage().length()>100){
                msgList.add(e.getMessage().substring(0,100));
            }
            else{
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
            uploadDir.append(GlobalConstant.ECOLOGY_UPLOAD_FILE_DIR_DEV);
            return ecologyDevMsgList;
        } else if(system.equals(GlobalConstant.ECOLOGY_SYSTEM_UAT)){
            uploadDir.append(GlobalConstant.ECOLOGY_UPLOAD_FILE_DIR_UAT);
            return ecologyUatMsgList;
        } else if(system.equals(GlobalConstant.ECOLOGY_SYSTEM_PROD)){
            uploadDir.append(GlobalConstant.ECOLOGY_UPLOAD_FILE_DIR_PROP);
            return ecologyPropMsgList;
        }
        return null;
    }

}
