package com.shouzan.common.util.qf;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
* <p>Title:PatternUtil </p>
* <p>Description:公共校验类 </p>
* <p>Company:yss </p> 
* @author
* @date 2017年9月27日
 */
public class PatternUtil {
    
	/**
	 * 校验金额是否合法
	 * @param amount 金额
	 * @return 合法返回true，否则返回false
	 */
	public static boolean checkAmount(String amount) {
		boolean flag = false;
		if(StringUtils.isNotEmpty(amount)) {
			Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,9})?$");
			Matcher match = pattern.matcher(amount);
			if (match.matches() == false) {
				flag = false;
			} else {
				flag = true;
			}
		}
		return flag;
	}

}
