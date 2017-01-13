package com.jereibaselibrary.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhush on 2016/10/10.
 * E-mail zhush@jerei.com
 */
public class JRTimeUtils {
    /**
     * 获取当前时间字符串
     * @return
     */
    public static String  getNow(){
        Date date = new Date();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       return format.format(date);
    }


    public static String removeTime(String timeStr){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date= format.parse(timeStr);
            return format2.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }


    /**
     * 时间转换
     * @param timeStr
     * @return
     */
    public static String timeTOtimeStr(String timeStr){

        String resulTtimeStr="";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        try {

            format.setLenient(false);
            Date date= format.parse(timeStr);
            resulTtimeStr = format2.format(date);
            return resulTtimeStr;
        } catch (ParseException e) {
            throw new RuntimeException("时间格式不合法");

        }
    }


    public static String timeStrTOtime(String timeStr){

        String resulTtimeStr="";
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {

            format.setLenient(false);
            Date date= format.parse(timeStr);
            resulTtimeStr = format2.format(date);
            return resulTtimeStr;
        } catch (ParseException e) {
            throw new RuntimeException("时间格式不合法");

        }
    }

    /**
     * 去年年限
     * @param timeStr
     * @return
     */
    public static String removeYear(String timeStr){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("MM-dd HH:mm");
        try {
            Date date= format.parse(timeStr);
            return format2.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
