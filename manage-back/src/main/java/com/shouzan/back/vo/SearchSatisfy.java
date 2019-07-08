package com.shouzan.back.vo;

import lombok.Data;
import org.junit.validator.PublicClassValidator;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: bin.yang
 * @Date: 2018/9/11 13:10
 * @Description:
 */
@Data
public class SearchSatisfy implements Serializable {

    private static final long serialVersionUID = -5327568808389085204L;

    private String id = null;

    //名称
    private String name ;

    // 状态
    private Byte status = null;

    // 类型
    private Integer types = null;

    private String createTimeStart ;

    private String createTimeEnd ;

    private String upshelfTimeStart ;

    private String upshelfTimeEnd ;

    private String downshelfTimeStart ;

    private String downshelfTimeEnd ;

    private String startTimeStart ;

    private String startTimeEnd ;

    private String endTimeStart ;

    private String endTimeEnd ;

    private Integer maxNumber = null;

    private Integer minNumber = null;

    // 关键字
    private String keywords = null;

    // 所属
    private String belonged ;

    // 星级
    private String starStar ;

    // 等级
    private String grade ;

    @Override
    public String toString() {
        return
                ", name='" + name + '\'' +
                ", status=" + status +
                ", types=" + types +
                ", keywords='" + keywords + '\'' +
                ", belonged='" + belonged + '\'' +
                ", starStar='" + starStar + '\'' +
                ", grade='" + grade + '\'';
    }

    public void setCreateTimeStart(String createTimeStart) {
        this.createTimeStart = toDate(createTimeStart);
    }

    public void setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = toDate(createTimeEnd);
    }

    public void setUpshelfTimeStart(String upshelfTimeStart) {
        this.upshelfTimeStart = toDate(upshelfTimeStart);
    }

    public void setUpshelfTimeEnd(String upshelfTimeEnd) {
        this.upshelfTimeEnd = toDate(upshelfTimeEnd);
    }

    public void setDownshelfTimeStart(String downshelfTimeStart) {
        this.downshelfTimeStart = toDate(downshelfTimeStart);
    }

    public void setDownshelfTimeEnd(String downshelfTimeEnd) {
        this.downshelfTimeEnd = toDate(downshelfTimeEnd);
    }

    public void setStartTimeStart(String startTimeStart) {
        this.startTimeStart = toDate(startTimeStart);
    }

    public void setStartTimeEnd(String startTimeEnd) {
        this.startTimeEnd = toDate(startTimeEnd);
    }

    public void setEndTimeStart(String endTimeStart) {
        this.endTimeStart = toDate(endTimeStart);
    }

    public void setEndTimeEnd(String endTimeEnd) {
        this.endTimeEnd = toDate(endTimeEnd);
    }

    public String toDate(String dateTime){
        Long timeLong = Long.parseLong(dateTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//要转换的时间格式
        Date date;
        try {
            date = sdf.parse(sdf.format(timeLong));
            return sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
