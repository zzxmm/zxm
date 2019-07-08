package com.shouzan.task.biz;

import com.shouzan.task.mapper.CardrecordMapper;
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
 * @Date: 2018/12/16 21:42
 * @Description:
 */
@Component
@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CardrecordTaskBiz {

    @Autowired
    private CardrecordMapper cardrecordMapper;

    /**
     * @Description: (卡卷记录过期定时任务)
     * @param
     * @[param] []
     * @return void
     * @author:  bin.yang
     * @date:  2018/12/24 5:59 PM
     */
    @Scheduled(cron = "0 0 0 * * ?")
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

        // 卡卷过期 定时任务
        int count = 0;
        try {
            count = cardrecordMapper.updateCardrecorStatus(localeDate);
            log.info("**_定时任务_** : 卡卷有效期过期更新 , 更新记录时间 [{}] , 更新记录个数 [ {} ] ",new Date().toLocaleString(),count);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("**_定时任务_** : 卡卷记录更新失败 , 错误原因 :"+e.getMessage());
        }
    }

}
