package com.shouzan.back.util;

import java.util.UUID;

/**   
 * @ClassName:  ShortUuid   
 * @Description: 生成长度为八的UUID  
 * @author:
 * @date:   2017年12月6日 下午1:44:25   
 *    
 * @Copyright:2017
 *
 */

public class ShortUuid {
	
	/**   
	 * @Fields chars : UUID所有组成数据集合   
	 */ 
	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",  
	                                              "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",  
	                                              "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",  
	                                              "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",  
	                                              "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",  
	                                              "W", "X", "Y", "Z" };  


  	/**
  	 * @Description 方法描述:生成8位UUID
  	 * @Author      作        者:
  	 * @UpdateDate  更新时间:2017年12月6日-下午1:46:40
  	 *
  	 * @return      
  	 * String   
  	 */
  	public static String generateShortUuid() {  
  	StringBuffer shortBuffer = new StringBuffer();  
  	String uuid = UUID.randomUUID().toString().replace("-", "");  
  	for (int i = 0; i < 8; i++) {  
  	    String str = uuid.substring(i * 4, i * 4 + 4);  
  	    int x = Integer.parseInt(str, 16);  
  	    shortBuffer.append(chars[x % 0x3E]);  
  	}  
  	return shortBuffer.toString();  
  	
  	}
}
