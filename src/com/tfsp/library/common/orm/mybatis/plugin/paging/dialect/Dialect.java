package com.tfsp.library.common.orm.mybatis.plugin.paging.dialect;

import java.util.List;

import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;

import com.tfsp.library.common.orm.mybatis.plugin.paging.PageBounds;
import com.tfsp.library.common.utils.StringUtils;

/**
 * 分页方言类, 该类型定义了分页方案支持的数据库系统, 以及一个构建分页语句的抽象方法. <br>
 * 针对不同的数据库分页, 需要继承该类, 并实现{@link #buildPagingSQL(String, PageBounds, ResultMap)}, 在该类中, 正确的返回对应数据库系统的分页语句.<br>
 * 同时, 如果数据库框架支持在分页前查询前执行查询记录总数的操作, 则可以实现{@link #buildQueryCountSQL(String)}方法, 构建查询总数的SQL语句.
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.common.db.orm.mybatis.support.plugin.paging.dialect.Dialect.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2012-12-18			Shmilycharlene				初始化版本	
 * 
 */
public abstract class Dialect {

	/**
	 * 构建分页语句的抽象方法<br>
	 * <p>
	 * 针对每一种数据库系统, 需要编写一个实现来完整分页语句的构建.
	 * @param sql
	 * @param pageBounds
	 * @return
	 * <br>
	 * 创建日期: 2012-12-18 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public static String buildPagingSQL(String sql, PageBounds pageBounds, ResultMap resultMap) {
		String orderColumn = pageBounds.getSort();
		//根据ResultMap获取字段名对应的列名
		if(StringUtils.isNotBlank(orderColumn)){
			if(resultMap != null){
				List<ResultMapping> mappingList = resultMap.getPropertyResultMappings();
				for(ResultMapping mapping : mappingList){
					//如果排序列为空则默认取第一列排序
					if(mapping.getColumn() == null){
						continue;
					}
					if(StringUtils.equals(orderColumn, mapping.getProperty())){
						orderColumn = mapping.getColumn();
						break;
					}
				}
			}
		}
		sql = getLineSql(sql);
		StringBuilder builder = new StringBuilder();
		builder.append(sql);
		if(StringUtils.isNotBlank(orderColumn)){
			builder.append(" ORDER BY ").append(orderColumn).append(" ").append(pageBounds.getOrder());
		}
		builder.append(" LIMIT ").append(pageBounds.getSkipNumber4MySQL()).append(", ").append(pageBounds.getRows().intValue());
		return builder.toString();
	}
	
	/**
	 * 将SQL语句变成一条语句，并且每个单词的间隔都是1个空格
	 * 
	 * @param sql
	 * @return 格式化后的语句
	 * <br>
	 * 创建日期: 2012-12-18 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public static String getLineSql(String sql) {
		return sql.replaceAll("[\r\n]", " ").replaceAll("\\s{2,}", " ");
	}
	
	/**
	 * 构建查询总数的SQL语句
	 * @param sql
	 * @return
	 * <br>
	 * 创建日期: 2012-12-19 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public static String buildQueryCountSQL(String sql){
		sql = getLineSql(sql);
		String newSQL =  sql.toUpperCase();
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT COUNT(*) ");
		if(newSQL.indexOf("SELECT DISTINCT") != -1 || newSQL.indexOf("GROUP BY")!=-1){
			int orderIndex = newSQL.lastIndexOf("ORDER BY");
			builder.append("FROM (");
			if(orderIndex != -1){
				builder.append(sql.substring(0, orderIndex));
			} else {
				builder.append(sql);
			}
			builder.append(") T");
		} else {
			int fromIndex = newSQL.indexOf(" FROM ");
			int orderIndex = newSQL.indexOf("ORDER BY");
			if(orderIndex != -1){
				builder.append(sql.substring(fromIndex, orderIndex));
			}else {
				builder.append(sql.substring(fromIndex));
			}
		}
		return builder.toString();
	}
}

