package com.weaver.fengx.ecmonitor.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * 异常打印工具类
 */
public class ExceptionUtil {

    public static String printStackTrace(Exception e) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        e.printStackTrace(printWriter);
        printWriter.close();
        return writer.toString();
    }
}
