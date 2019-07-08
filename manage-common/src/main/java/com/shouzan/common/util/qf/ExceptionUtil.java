package com.shouzan.common.util.qf;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @ClassName: ExceptionToStringUtil
 * @Description:将完整的栈异常信息装换成字符串
 * @author:
 * @date: 2017年10月27日 下午2:04:26
 * @Copyright:2017  .
 *
 */

public class ExceptionUtil {

	/**
	 * @param t 异常类
	 * @return 完整的栈异常信息字符串
	 */
	public static String getTrace(Throwable t) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		t.printStackTrace(writer);
		StringBuffer buffer = stringWriter.getBuffer();
		return buffer.toString();
	}
}
