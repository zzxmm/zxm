package com.shouzan.back.util;

/**
 * @Author: bin.yang
 * @Date: 2019/3/28 16:23
 * @Description:
 */
public class FastUtil {

    /**
     * @Author: bin.yang
     * @Date: 2019/4/15 2:30 PM
     *
     * @Description:  获取日期标识码
     */
    public static String getDateSign(){
        long sign = System.currentTimeMillis();
        return sign+"";
    }
}
