package com.shouzan.common.util.qf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
* <p>Title:SerializeUtil </p>
* <p>Description:序列化工具类 </p>
* <p>Company:yss </p> 
* @author
* @date 2017年10月19日
 */
public class SerializeUtil {
	/**
	 * 序列化
	 * @param object 需要序列化的对象
	 * @return 实体
	 * @throws Exception 
	 */
	public static byte[] serialize(Object object) throws Exception {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			// 序列化
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
	        throw new  Exception(e.getMessage()+":序列化发生错误");
		}
	}
    /**
     * 反序列化
     * @param bytes 需要反序列化数组
     * @return 反序列化后的对象
     * @throws Exception 
     */
	public static Object unserialize(byte[] bytes) throws Exception {
		ByteArrayInputStream bais = null;
		try {
			// 反序列化
			bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
          e.printStackTrace();
          throw new  Exception(e.getMessage()+":反序列化发生错误");
		}
	}

}
