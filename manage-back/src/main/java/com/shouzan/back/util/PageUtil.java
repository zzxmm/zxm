package com.shouzan.back.util;

import java.util.List;

import com.github.pagehelper.Page;
/**
* <p>Title:PageUtil </p>
* <p>Description:分页工具 </p>
* <p>Company:yss </p> 
* @author
* @date 2017年8月31日
 */
public class PageUtil {
	/**
	 * 
	 * @param count 总数
	 * @param pageNum 页码
	 * @param pageSize 每页条数
	 * @param results 查询结果
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static  Page getPage(int count,int pageNum,int pageSize,List results) {
		Page page = new Page();
		page.setTotal(count);
		page.setPageNum(pageNum);
		page.setPageSize(pageSize);
		if(results!=null && results.size()>0) {
			page.addAll(results);
		}
		return page;
	}

}
