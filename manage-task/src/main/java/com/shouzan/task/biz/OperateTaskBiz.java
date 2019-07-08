package com.shouzan.task.biz;

import com.shouzan.task.mapper.OperateMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: bin.yang
 * @Date: 2019/1/22 11:27
 * @Description:  运营卡卷定时任务
 */
@Component
@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class OperateTaskBiz {

    @Autowired
    private OperateMapper operateMapper;

    @Scheduled(cron = "0 0 * * * ?")
    public void advertTimingUpdate(){
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
            count = operateMapper.UpperShelfToTime(localeDate);
            log.info("**_定时任务_** : 运营卡卷上架更新 , 更新活动时间[{}] , 更新卡卷个数[{}] ",new Date().toLocaleString(),count);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("**_定时任务_** : 运营卡卷上架更新失败 , 错误原因 :"+e.getMessage());
        }

        // 定时下架
        int number = 0;
        try {
            number = operateMapper.LowerShelfToTime(localeDate);
            log.info("**_定时任务_** : 运营卡卷下架更新 , 更新卡卷时间 [{}] , 更新卡卷个数 [ {} ] ",new Date().toLocaleString(),number);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("**_定时任务_** : 运营卡卷下架更新失败 , 错误原因 :"+e.getMessage());
        }
    }

}
