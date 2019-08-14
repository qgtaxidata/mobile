package com.example.taxidata.util;

public class StringUtil {
    private StringUtil(){}

    /**
     * 将中文格式的时间转化为标准格式
     * @param chineseTime 中文时间
     * @return String
     */
    public static String ChineseToStandardFormat(String chineseTime){
        StringBuffer timeBuilder = new StringBuffer(chineseTime);
        timeBuilder.setCharAt(timeBuilder.indexOf("年"),'-');
        timeBuilder.setCharAt(timeBuilder.indexOf("月"),'-');
        timeBuilder.deleteCharAt(timeBuilder.indexOf("日"));

        return timeBuilder.toString();
    }
}
