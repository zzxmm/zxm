package com.shouzan.back.constant;

import java.math.BigDecimal;

import com.shouzan.back.config.ConstantConfiguration;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @create 2017-06-17 14:41
 */
public class CommonConstant {
	
	/**
	 * 域名
	 */
	public static final String DOMAIN = ConstantConfiguration.getGateUrl();
    public final static int ROOT = -1;
    public final static int DEFAULT_GROUP_TYPE = 0;
    /**
     * 权限关联类型
     */
    public final static String AUTHORITY_TYPE_GROUP = "group";
    /**
     * 权限资源类型
     */
    public final static String RESOURCE_TYPE_MENU = "menu";
    public final static String RESOURCE_TYPE_BTN = "button";

    public final static String RESOURCE_REQUEST_METHOD_GET = "GET";
    public final static String RESOURCE_REQUEST_METHOD_PUT = "PUT";
    public final static String RESOURCE_REQUEST_METHOD_DELETE = "DELETE";
    public final static String RESOURCE_REQUEST_METHOD_POST = "POST";

    public final static String RESOURCE_ACTION_VISIT = "访问";

    public final static String BOOLEAN_NUMBER_FALSE = "0";

    public final static String BOOLEAN_NUMBER_TRUE = "1";
    //编码时各级别编码的长度限制位数,1级长度为3，其他为5.
    public final static  int FIRSTLEVEL = 3;
    public final  static int OTHERLEVEL = 5;
    
    /** 常量0 */
	public static final String CONSTANT_0 = "0";
	
	/** 日期格式化 ：年月日 yyyyMMdd 例：20160101 */
	public static final String FORMAT_YMD = "yyyyMMdd";
    
	/** 时间单位-日 */
	public static final String TA_TIME_0 = "0";
	/** 时间单位-月 */
	public static final String TA_TIME_1 = "1";
	/** 时间单位-年 */
	public static final String TA_TIME_2 = "2";
	
	public static final BigDecimal Checked =new BigDecimal("1");
	
	public static final BigDecimal UnChecked =new BigDecimal("0");
	
	/*保存微城市文件夹名称*/
	public static final String UP_WCS_NAME = "wcs";
	
	public static final String VersionName = "Version";
	
	public static final String TypeName = "Type";

}
