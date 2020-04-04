package com.kcqnly.application.utils;


import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 日期操作工具类
 *
 * @author Perfree
 */
public class TimeUtil {
    /**
     * 日期格式化
     */
    public static String getFormatDate(String format) {
        if (StrUtil.isBlank(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        return DateUtil.format(new Date(), format);
    }

    /**
     * 日期格式化
     */
    public static String getFormatDate(Date date) {
        return DateUtil.format(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 日期格式化
     */
    public static String getFormatDate(Date date, String format) {
        if (StrUtil.isBlank(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        return DateUtil.format(date, format);
    }

    /**
     * 字符串转换成日期
     *
     * @param str 格式为yyyy-MM-dd HH:mm:ss
     */
    public static Date StrToDate(String str) {
        Date date =DateUtil.parse(str,"yyyy-MM-dd HH:mm:ss");
        return date;
    }

    /**
     * 字符串转换成日期
     *
     * @param str 格式为yyyy-MM-dd HH:mm:ss
     * @return date
     */
    public static Date StrToDate(String str, String format) {
        if (StrUtil.isBlank(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        Date date = DateUtil.parse(str,format);
        return date;
    }

    /**
     * 获得间隔后的格式化后的时间
     *
     * @param intervalTime 时间间隔 单位秒
     * @param format       时间格式 （yyyy-MM-dd HH:mm:ss）
     */
    public static String getIntervalFormatDate(long intervalTime, String format) {
        if (StrUtil.isBlank(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        long currentTime = System.currentTimeMillis() + intervalTime;
        Date date = new Date(currentTime);
        return DateUtil.format(date,format);
    }

    /**
     * 两个时间比较大小
     *
     * @param date1 入参1
     * @param date2 入参2
     * @return 1：date1晚于date2，-1：date1早于date2，0：传入时间等于当前时间
     */
    public static int compareDate(Date date1, Date date2) {
        if (date1.getTime() > date2.getTime()) {
            return 1;
        } else if (date1.getTime() < date2.getTime()) {
            return -1;
        } else {//相等
            return 0;
        }
    }

    /**
     * 两个时间比较大小
     *
     * @param dateStr1 入参1
     * @param dateStr2 入参2
     * @return 1：date1晚于date2，-1：date1早于date2，0：传入时间等于当前时间
     */
    public static int compareDate(String dateStr1, String dateStr2, String format) {
        Date date1 = TimeUtil.StrToDate(dateStr1, format);
        Date date2 = TimeUtil.StrToDate(dateStr2, format);
        return compareDate(date1, date2);
    }

    /**
     * 获取时间戳
     *
     * @return
     */
    public static String getTimestamp() {
        return Long.toString(new Date().getTime() / 1000);
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param date1         较小的时间
     * @param date2          较大的时间
     * @return 相差天数
     */
    public static int daysBetween(Date date1, Date date2)  {
       return (int) DateUtil.between(date1, date2, DateUnit.DAY);
    }

    /**
     * 字符串的日期格式的计算
     */
    public static int daysBetween(String dateStr1, String dateStr2) {
        Date date1 = DateUtil.parse(dateStr1);

        Date date2 = DateUtil.parse(dateStr2);

        long betweenDay = DateUtil.between(date1, date2, DateUnit.DAY);
        return (int)betweenDay;
    }


    /**
     * 日期向后或者向前推移
     *
     * @param dateStr     日期字符串，如："2019-11-11"
     * @param intervalDay 日期推移值；如：1，传入日期参数向后推一天；-1，传入日期参数向前推一天
     * @return 返回推移后的日期字符串
     */
    public static String dayAddOrCut(String dateStr, int intervalDay) {
        Date date = DateUtil.parse(dateStr);
        Date newDate = DateUtil.offsetDay(date,  intervalDay);
        return DateUtil.formatDate(newDate);
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @param format
     * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        return DateUtil.formatDate(new Date(Long.parseLong(seconds + "000")));
    }
}