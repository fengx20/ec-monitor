package com.weaver.fengx.ecmonitor.autodeploy.constant;

/**
 * @author Fengx
 * 全局常量
 **/
public class GlobalConstant {

    /**
     * 开发环境业务系统
    */
    public static String ECOLOGY_SYSTEM_DEV = "1";

    /**
     * 验证环境业务系统
     */
    public static String ECOLOGY_SYSTEM_UAT = "2";

    /**
     * 生产环境业务系统
     */
    public static String ECOLOGY_SYSTEM_PROD = "3";

    /**
     * 开发环境业务补丁包上传的路径
     */
    public static String ECOLOGY_UPLOAD_FILE_DIR_DEV = "/newoa/weaver/update/dev-ecology/";

    /**
     * 验证环境业务补丁包上传的路径
     */
    public static String ECOLOGY_UPLOAD_FILE_DIR_UAT = "/newoa/weaver/update/uat-ecology/";

    /**
     * 生产环境业务补丁包上传的路径
     */
    public static String ECOLOGY_UPLOAD_FILE_DIR_PROP = "/newoa/weaver/update/oa-ecology/";

    /**
     * 补丁包名称
     */
    public static String UPGRADE_FILE_NAME = "ecology.zip";

    /**
     * 部署更新脚本名称
     */
    public static String UPGRADE_SHELL_FILE_NAME = "update-with-code.sh";

    /**
     * 回滚脚本名称
     */
    public static String ROLLBACK_SHELL_FILE_NAME = "rollback.sh";

    /**
     * 最大支持回滚的天数
     */
    public static int ROLLBACK_MAX_DAY = 1;

}
