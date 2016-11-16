package com.etong.pt.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chenlinyang on 2015/11/26.
 */
public class TimestampUtils {
    /**date格式化*/
    public static String FormatDate(Date date) {
        String str = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            str = dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**时间戳转日期*/
    public static Date Timestamp2Date(int times) {
        long temp = (long)times*1000;
        Timestamp ts = new Timestamp(temp);
        Date date = new Date();
        try {
            date = ts;
            //System.out.println(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**格式化"yyyy-MM-dd HH:mm:ss"转时间戳*/
    public static long Str2Timestamp(String str) {
        long times = 0;
        try {
            times = ((Timestamp.valueOf(str).getTime())/1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(times == 0){
            System.out.println("String转10位时间戳失败");
        }
        return times;
    }

    public static String Timestamp2Str(int times) {
        Date date = Timestamp2Date(times);
        return FormatDate(date);
    }

}
