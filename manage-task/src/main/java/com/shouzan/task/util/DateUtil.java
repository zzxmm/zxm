package com.shouzan.task.util;

import java.sql.Time;
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
}
