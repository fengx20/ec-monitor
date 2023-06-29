package com.weaver.fengx.ecmonitor.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 */

public class StringUtil {


    /**
     * 拼接字符串		使用StringBuilder的append方法
     * 10个以内的字符串拼接能够维持良好的性能.
     *
     * @description
     * @author gyl  @date 2019/07/03
     */
    public static String append(String... array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
        }
        return sb.toString();
    }

    /**
     * @description 判断一个字符串是否为空指针, 空字符串, "null"字符串, 剔除空格前后空格后判断
     * @author gyl   @date 2019-07-25 11:29:02
     */
    public static boolean isNullOrEmptyAfterTrim(String str) {
        return null == str || "".equals(str.trim()) || "null".equalsIgnoreCase(str.trim());
    }

    /**
     * @description 是否有空字段（空指针或者空字符串，或者"null"字符串）
     * @author gyl   @date 2019-10-24 09:56:27
     */
    public static boolean isAnyOneNullOrEmptyAfterTrim(String... params) {
        for (String item : params) {
            if (isNullOrEmptyAfterTrim(item)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断一个对象是否为空指针, 空字符串, "null" 字符串
     *
     * @param obj
     * @return
     */
    public static boolean isNullOrEmptyAfterTrim(Object obj) {
        if (null == obj) {
            return true;
        }
        // 如果该对象是字符串,还要它不是空字符串,是否是"null"字符串
        if ((obj instanceof String)) {
            String str = (String) obj;
            return isNullOrEmptyAfterTrim(str);
        } else {
            // 如果不是字符串,那不是空指针,就非空了.
            return false;
        }
    }

    /**
     * /**
     *
     * @description 字符串的首字符转化为大写
     * @author gyl   @date 2020-03-06 17:12:16
     */
    public String firstCharToUpperCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 根据长度左补或从左往右根据长度截取
     *
     * @param str
     * @param strLength
     * @param addStr
     * @return
     * @author Jeb
     * @date 2021-03-30
     */
    public static String strCutOrLAD(String str, int strLength, String addStr) {
        if (str == null) {
            str = "";
        }
        if (str.length() > strLength) {
            return str.substring(str.length() - strLength);
        }
        return strLAD(str, strLength, addStr);
    }

    /**
     * 默认4位补0
     *
     * @param str
     * @return
     */
    public static String strCutOrLAD(String str) {
        if (str == null) {
            str = "";
        }
        if (str.length() > 2) {
            return str.substring(str.length() - 2);
        }
        return strLAD(str, 4, "0");
    }

    /**
     * 字符串左补函数，默认补0
     *
     * @param str
     * @param strLength
     * @param addStr
     * @return
     * @author Jeb
     * @date 2021-03-30
     */
    public static String strLAD(String str, int strLength, String addStr) {
        if (addStr == null || addStr.equals("")) {
            addStr = "0";
        }
        int strLen = str.length();
        StringBuffer sb = null;
        while (strLen < strLength) {
            sb = new StringBuffer();
            sb.append(addStr).append(str);
            str = sb.toString();
            strLen = str.length();
        }
        return str;
    }

    /**
     * 字符串左补空格
     *
     * @param str          要处理的字符串
     * @param targetLength 目标长度
     */
    public static String appendSpaceToLeft(String str, int targetLength) {
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < targetLength - str.length(); i++) {
            temp.append(" ");
        }
        temp.append(str);

        return temp.toString();
    }

    /**
     * 16进制转成中文
     *
     * @param s
     * @return
     */
    public static String toStringHex2(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "GBK");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }


    /**
     * 中文转成16进制
     *
     * @param str
     * @return
     */
    public static String gbkStrToHexStr(String str) {// 将汉字转换为16进制数
        String st = "";
        try {
            // 这里要非常的注意,在将字符串转换成字节数组的时候一定要明确是什么格式的,这里使用的是gb2312格式的,还有utf-8,ISO-8859-1等格式
            byte[] by = str.getBytes("GBK");
            for (int i = 0; i < by.length; i++) {
                String strs = Integer.toHexString(by[i]);
                if (strs.length() > 2) {
                    strs = strs.substring(strs.length() - 2);
                }
                st += strs;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return st;
    }

    /**
     * ascii字符串转16进制字符
     *
     * @param str
     * @return
     */
    public static String stringASCIIToHexString(String str) {
        if (str == null || str.trim().equals("")) {
            return "";
        }
        byte[] ascii = str.getBytes();
        return byte2hex(ascii);
    }


    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }

        }
        return hs.toUpperCase();
    }

    /**
     * @param hex
     * @return
     */
    public static String hexStringToASCII(String hex) {
        if (hex == null || hex.trim().equals("")) {
            return "";
        }
        Long strLong = Long.parseLong(hex, 16);
        return String.valueOf(strLong);
    }

    public static String convertHexToString(String hex) {

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();


        for (int i = 0; i < hex.length() - 1; i += 2) {
            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            sb.append((char) decimal);

            temp.append(decimal);
        }

        return sb.toString();
    }

    /**
     * 版本号比较
     *
     * @param leftVersion
     * @param rightVersion
     * @return 0代表相等，1代表左边大，-1代表右边大
     * Utils.compareVersion("1.0.358_20180820090554","1.0.358_20180820090553")=1
     */
    public static int compareVersion(String leftVersion, String rightVersion) {
        //直接相等
        if (leftVersion.equals(rightVersion)) {
            return 0;
        }
        String[] leftVersionArray = leftVersion.split("[._]");
        String[] rightVersionArray = rightVersion.split("[._]");
        int idx = 0;
        //取最小长度值
        int minLength = Math.min(leftVersionArray.length, rightVersionArray.length);
        int diff = 0;
        //再比较字符
        while (idx < minLength
                && (diff = leftVersionArray[idx].compareTo(rightVersionArray[idx])) == 0) {
            ++idx;
        }
        //如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
        diff = (diff != 0) ? diff : leftVersionArray.length - rightVersionArray.length;
        return diff;

    }

    /**
     * 去掉指定字符串的开头的指定字符
     *
     * @param stream 原始字符串
     * @param trim   要删除的字符串
     * @return
     */
    public static String StringStartTrim(String stream, String trim) {
        // null或者空字符串的时候不处理
        if (stream == null || stream.length() == 0 || trim == null || trim.length() == 0) {
            return stream;
        }
        // 要删除的字符串结束位置
        int end;
        // 正规表达式
        String regPattern = "[" + trim + "]*+";
        Pattern pattern = Pattern.compile(regPattern, Pattern.CASE_INSENSITIVE);
        // 去掉原始字符串开头位置的指定字符
        Matcher matcher = pattern.matcher(stream);
        if (matcher.lookingAt()) {
            end = matcher.end();
            stream = stream.substring(end);
        }
        // 返回处理后的字符串
        return stream;
    }

    /**
     * 根据长度获取字符串
     *
     * @param msg
     * @param length
     * @return
     */
    public static String getShortString(String msg, int length) {
        if (msg.length() >= length) {
            return msg.substring(0, length);
        }
        return msg;
    }

    /**
     * 获取10位长度的字符串
     *
     * @param msg
     * @return
     */
    public static String getShortString10(String msg) {
        return getShortString(msg, 10);
    }

}


