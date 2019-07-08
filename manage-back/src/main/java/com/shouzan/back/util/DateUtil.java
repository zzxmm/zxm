package com.shouzan.back.util;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: bin.yang
 * @Date: 2018/12/21 18:28
 * @Description:  时间工具
 */
public class DateUtil {

    /**
     * @Description: (获取当前星期日)
     * @param
     * @[param] []
     * @return java.lang.Byte
     * @author:  bin.yang
     * @date:  2018/12/21 6:35 PM
     */
    public static Byte getWeek(){
        int[] weekDays = { 7, 1, 2, 3, 4, 5, 6 };
        Calendar cal = Calendar.getInstance();
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0){
            w = 0;
        }
        return Byte.parseByte(weekDays[w]+"");
    }

    /**
     * @Description: (获取当前时间)
     * @param
     * @[param] []
     * @return java.lang.Byte
     * @author:  bin.yang
     * @date:  2018/12/21 6:35 PM
     */
    public static Time getTime(){
        Date date = new Date();
        Time time = new Time(date.getTime());
        return time;
    }

    /**
     * @Description: 获取毫秒值
     * @param
     * @[param] []
     * @return java.lang.String
     * @author:  bin.yang
     * @date:  2019/5/31 4:42 PM
     */
    public static String getMillisecond(){
        Date date = new Date();
        return date.getTime()+"";
    }

    /**
     * @Description: description
     * @param null
     * @[param]
     * @return
     * @author:  bin.yang
     * @date:  2019/5/31 4:42 PM
     */

    public static String getDate(){
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String format = sim.format(date);
        return format;
    }

}
