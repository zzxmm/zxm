package com.shouzan.task.biz;

import com.shouzan.task.mapper.CouponMapper;
import com.shouzan.task.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: bin.yang
 * @Date: 2018/10/10 11:06
 * @Description: 卡卷时间过期自动下架
 */
@Component
@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CouponTaskBiz {

    @Autowired
    private CouponMapper couponMapper;

    /**
     * @Description: (卡卷定时任务调度)
     * @param
     * @[param] []
     * @return void
     * @author:  bin.yang
     * @date:  2018/12/25 10:24 AM
     */
    @Scheduled(cron = "0 */15 * * * ?")
    public void couponTask() {
        Time time = DateUtil.getTime();
        if (time.getMinutes() == 0) {
            Date date = new Date();
            date.setMinutes(5);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String localeDate = null;
            try {
                localeDate = format.format(date);
            } catch (Exception e) {
                localeDate = date.toLocaleString();
                e.printStackTrace();
            }
            boolean update = couponTimingUpdate(localeDate);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            couponSellUpdate(time);
        }else {
            couponSellUpdate(time);
        }
    }

    /**
     * @param
     * @param localeDate
     * @return void
     * @Description: (卡卷定时上下架任务)
     * @[param] []
     * @author: bin.yang
     * @date: 2018/12/24 5:58 PM
     */
    private boolean couponTimingUpdate(String localeDate) {

        // 定时上架
        int count = 0;
        try {
            count = couponMapper.couponTimingUpper(localeDate);
            log.info("**_定时任务_** : 卡卷上架更新 , 更新卡卷时间 [{}] , 更新卡卷个数 [ {} ] ", new Date().toLocaleString(), count);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("**_定时任务_** : 卡卷上架更新失败 , 错误原因 :" + e.getMessage());
        }

        // 定时下架
        int number = 0;
        try {
            number = couponMapper.couponTimingLower(localeDate);
            log.info("**_定时任务_** : 卡卷下架更新 , 更新卡卷时间 [{}] , 更新卡卷个数 [ {} ] ", new Date().toLocaleString(), number);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("**_定时任务_** : 卡卷下架更新失败 , 错误原因 :" + e.getMessage());
        }
        return true;
    }

    /**
     * @param
     * @param time
     * @return void
     * @Description: (卡卷开售规则定时任务)
     * @[param] []
     * @author: bin.yang
     * @date: 2018/12/24 5:58 PM
     */
    private void couponSellUpdate(Time time) {
        Byte week = DateUtil.getWeek();
        // 定时售卖
        int number = 0;
        try {
            number = couponMapper.couponIsSellUpdate(week, time);
            log.info("**_定时任务_** : 卡卷定时售卖开始更新 , 售卖开始时间 [{}] , 更新卡卷个数 [ {} ] ", new Date().toLocaleString(), number);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("**_定时任务_** : 卡卷定时售卖更新失败 , 错误原因 :" + e.getMessage());
        }

        // 定时售卖
        int count = 0;
        if(time.getHours() == 0 && time.getMinutes() == 0){
            int parseInt = Integer.parseInt(week + "");
            if((parseInt - 1) == 0){
                week =7;
            }else{
                week = Byte.parseByte(parseInt-1+"");
            }
            time.setHours(23);
            time.setMinutes(59);
            time.setSeconds(59);
        }
        try {
            count = couponMapper.couponNoSellUpdate(week, time);
            log.info("**_定时任务_** : 卡卷定时售卖关闭更新 , 售卖关闭时间 [{}] , 更新卡卷个数 [ {} ] ", new Date().toLocaleString(), count);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("**_定时任务_** : 卡卷定时售卖关闭更新失败 , 错误原因 :" + e.getMessage());
        }
    }
}
