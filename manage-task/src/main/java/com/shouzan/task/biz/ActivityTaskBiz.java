package com.shouzan.task.biz;

import com.shouzan.task.mapper.ActivityMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: bin.yang
 * @Date: 2018/10/10 11:04
 * @Description:  活动时间开始自动上线
 */
@Component
@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ActivityTaskBiz {

    @Autowired
    private ActivityMapper activityMapper;

    /**
     * @Description: (活动定时上下架任务)
     * @param
     * @[param] []
     * @return void
     * @author:  bin.yang
     * @date:  2018/12/24 5:58 PM
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void couponTimingUpdate(){
        Date date = new Date();
        date.setMinutes(30);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String localeDate = null ;
        try {
            localeDate = format.format(date);
        } catch (Exception e) {
            localeDate = date.toLocaleString();
            e.printStackTrace();
        }

        // 定时上架
        int count = 0;
        try {
            count = activityMapper.activityTimingUpper(localeDate);
            log.info("**_定时任务_** : 活动上架更新 , 更新活动时间[{}] , 更新活动个数[{}] ",new Date().toLocaleString(),count);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("**_定时任务_** : 活动上架更新失败 , 错误原因 :"+e.getMessage());
        }

        // 定时下架
        int number = 0;
        try {
            number = activityMapper.activityTimingLower(localeDate);
            log.info("**_定时任务_** : 活动下架更新 , 更新活动时间[{}] , 更新活动个数[{}] ",new Date().toLocaleString(),number);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("**_定时任务_** : 活动下架更新失败 , 错误原因 :"+e.getMessage());
        }
    }
}
