package com.tfsp.library.common.orm.mybatis.plugin.paging;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用来表示分页信息的类. 包含了排序字段, 排序类型, 页面大小, 当前页面, 数组总熟和页面数量信息.
 * 
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.common.orm.mybatis.plugin.paging.RowBounds.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2012-12-18			Shmilycharlene				初始化版本	
 * 
 */
public class PageBounds extends org.apache.ibatis.session.RowBounds {
	public static final String ORDER_DESC = "DESC";
	public static final String ORDER_ASC  = "ASC";
	public static final int DEFAULT_PAGE = 1;
	public static final int DEFAULT_ROWS = 10;
	
	private String order = ORDER_DESC;
	private String sort;
	
	private Integer page;
	private Integer rows;
	
	private int totalNumber;
	//忽略计算分页数据总数量
	private boolean ignoreCount = false;
	
	public PageBounds(){
	}
	
	public PageBounds(int offset, int limit){
		super(offset, limit);
	}
	
	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Integer getPage() {
		if(page == null || page == 0){
			page = DEFAULT_PAGE;
		}else if(page == 99999999 && !isIgnoreCount()){
			return totalNumber % getRows() == 0 ? totalNumber / getRows() : totalNumber / getRows() + 1;
		}
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		if(rows == null){
			rows = DEFAULT_ROWS;
		}
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public void setTotalNumber(int totalNumber){
		this.totalNumber = totalNumber;
	}
	
	public int getTotalNumber(){
		return totalNumber;
	}
	
	public int getTotalPage(){
		return totalNumber % getRows() == 0 ? totalNumber / getRows() : totalNumber / getRows() + 1;
	}
	
	public int getSkipNumber(){
		return (getPage() -1) * getRows() +1;
	}
	
	/**
	 * 在MySQL数据库环境下分页， 计算分页的起始量
	 * @return skipNumber
	 * <br>
	 * 创建日期: 2013-1-22 <br>
	 * 创  建  人: Administrator
	 */
	public int getSkipNumber4MySQL(){
		return (getPage() -1) * getRows();
	}
	
	public void setIgnoreCount(boolean ignore) {
		this.ignoreCount = ignore;
	}
	
	public boolean isIgnoreCount() {
		if(ignoreCount){
			if(page == 99999999){
				return false;
			}
			return true;
		}
		return this.ignoreCount;
	}
	
	public void ignoreCount() {
		this.ignoreCount = true;
	}
	
	public int getFetchNumber(){
		return getPage() * getRows();
	}
	
	public Map<String, Object> putResultObj(List<?> list){
		Map<String, Object> result = new HashMap<String, Object>();
		if(isIgnoreCount()){//不统计总数的情况，如果查询出的数据小于每页的行数，表示已经是最后一页了
			if(list.size() < getRows()){
				int records = (getPage() - 1) * getRows() + list.size();
				result.put("records", records);
				result.put("total", getPage());
			}else{
				result.put("total", 99999999);
				result.put("ignoreCount", true);
			}
		}else{
			result.put("records", getTotalNumber());
			result.put("total", getTotalPage());
		}
		result.put("rows", list);
		return result;
	}
	
	/**
	 * 
	 * @param list
	 * @param footer
	 * @return
	 * 创建日期: 2016年8月16日 <br>
	 * 创  建  人: ALVIN
	 */
	public Map<String, Object> putResultObj(List<?> list, Object footer){
		Map<String, Object> resultMap = putResultObj(list);
		resultMap.put("userdata", footer);
		return resultMap;
	}
}

