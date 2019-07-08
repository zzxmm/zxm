package com.shouzan.back.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @description 类描述：订单号生成规则
 * @author 作者：LIUZEKUN
 * @create 创建时间：2017年8月31日
 * @update 修改时间：2017年8月31日
 */
public class OrderUtil {

	/** 年月日时分秒毫秒(无下划线) yyyyMMddHHmmssSSS */
	public static final String dtOrder = "yyyyMMddHHmmssSSS";

	/**
	 * @description 方法描述：支付宝订单号
	 * @crater 创建者：LIUZEKUN
	 * @updater 修改者：LIUZEKUN
	 * @create 创建时间：2017年8月31日
	 * @update 修改时间：2017年8月31日
	 * 支付宝订单号以10开头
	 */
	public static String zfbOrder() {
		ThreadLocalRandom rad=ThreadLocalRandom.current();
		int num=rad.nextInt(10000,99999);
		return "10"+getOrderNum()+num;
	}
	
	/**
	 * @description 方法描述：支付宝退款订单号
	 * @crater 创建者：LIUZEKUN
	 * @updater 修改者：LIUZEKUN
	 * @create 创建时间：2017年8月31日
	 * @update 修改时间：2017年8月31日
	 * 支付宝退款订单号以11开头
	 */
	public static String zfbRefundOrder() {
		ThreadLocalRandom rad=ThreadLocalRandom.current();
		int num=rad.nextInt(10000,99999);
		return "11"+getOrderNum()+num;
	}

	/**
	 * @description 方法描述：微信订单号
	 * @crater 创建者：LIUZEKUN
	 * @updater 修改者：LIUZEKUN
	 * @create 创建时间：2017年8月31日
	 * @update 修改时间：2017年8月31日
	 * 微信订单号以20开头
	 */
	public static String wxOrder() {
		ThreadLocalRandom rad=ThreadLocalRandom.current();
		int num=rad.nextInt(10000,99999);
		return "20"+getOrderNum()+num;
	}
	
	/**
	 * @description 方法描述：微信退款订单号
	 * @crater 创建者：LIUZEKUN
	 * @updater 修改者：LIUZEKUN
	 * @create 创建时间：2017年8月31日
	 * @update 修改时间：2017年8月31日
	 * 微信退款订单号以21开头
	 */
	public static String wxRefundOrder() {
		ThreadLocalRandom rad=ThreadLocalRandom.current();
		int num=rad.nextInt(10000,99999);
		return "21"+getOrderNum()+num;
	}
	
	/**
	 * 
	 * @description 方法描述：系统当前时间(精确到毫秒),作为一个唯一的订单编号
	 * @crater      创建者：LIUZEKUN
	 * @updater     修改者：LIUZEKUN 
	 * @create      创建时间：2017年8月31日
	 * @update      修改时间：2017年8月31日
	 * @return
	 */
	private synchronized static String getOrderNum(){
		Date date=new Date();
		DateFormat df=new SimpleDateFormat(dtOrder);
		return df.format(date);
	}
}
