package com.shouzan.back.entity.card;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: bin.yang
 * @Date: 2018/12/28 11:55
 * @Description:  卡卷 使用 日期
 */
@Data
public class DateInfo  implements Serializable {

    private static final long serialVersionUID = 6109594631467743557L;

    //使用时间的类型   DATE_TYPE_FIX _TIME_RANGE 表示固定日期区间，DATETYPE FIX_TERM 表示固定时长 （自领取后按天算。
    @NotNull(message = "使用时间类型不能为空")
    private String type;

    //表示起用时间。从1970年1月1日00:00:00至起用时间的秒数，最终需转换为字符串形态传入。（东八区时间,UTC+8，单位为秒）. 可用于DATE_TYPE_FIX_TERM时间类型
    private Long begin_timestamp;

    //表示结束时间 ， 建议设置为截止日期的23:59:59过期 。 （ 东八区时间,UTC+8，单位为秒 ）
    private Long end_timestamp;

    //表示自领取后多少天内有效，不支持填写0。
    private Integer fixed_term;

    //表示自领取后多少天开始生效，领取后当天生效填写0。（单位为天）
    private Integer fixed_begin_term;

}
