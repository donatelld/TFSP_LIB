package com.tfsp.library.common.orm.mybatis.plugin.paging.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.session.RowBounds;

import com.tfsp.library.common.orm.mybatis.plugin.paging.PageBounds;
import com.tfsp.library.common.orm.mybatis.plugin.paging.dialect.Dialect;

/**
 * MyBatis分页插件. 在执行查询期间, 检查是否进行分页操作, 如果需要进行分页操作, 动态的构建分页语句.
 * 
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.common.db.orm.mybatis.support.plugin.paging.interceptor.PaginationInterceptor.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2012-12-18			Shmilycharlene				初始化版本	
 * 
 */
@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class})})
public class PaginationInterceptor implements Interceptor {
//	protected final static Logger logger = LoggerFactory.getLogger(PaginationInterceptor.class);
	protected Dialect dialect;
	
	/* (non-Javadoc)
	 * @see org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin.Invocation)
	 */
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler)invocation.getTarget();
//		BoundSql boundSql = statementHandler.getBoundSql();
		MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, new DefaultObjectFactory(), new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());
		RowBounds rowBounds = (RowBounds)metaStatementHandler.getValue("delegate.rowBounds");
		if(rowBounds == null || rowBounds == RowBounds.DEFAULT){
			return invocation.proceed();
		}
		PageBounds pageBounds = null;
		if(rowBounds instanceof PageBounds){
			pageBounds = (PageBounds)rowBounds;
		}else {
			//对于使用mybatis RowBounds分页的程序, 这里自动转换为我们的PageBounds类.
			pageBounds = new PageBounds(rowBounds.getOffset(), rowBounds.getLimit());
			//使用PageBounds替换原有的RowBunds分页标示.
			metaStatementHandler.setValue("delegate.rowBounds", pageBounds);
		}
		
		if(!pageBounds.isIgnoreCount())// 非忽略总数计算, 在分页前, 查询处理数据总数, 分页总数. 信息.
			processPrepagingAction(invocation);
		//
		String originalSql = (String)metaStatementHandler.getValue("delegate.boundSql.sql");
		// 查找到当前selectStatement对应的ResultMap
		MappedStatement mappedStatement = (MappedStatement)metaStatementHandler.getValue("delegate.mappedStatement");
		Iterator<ResultMap> iterator = mappedStatement.getResultMaps().iterator();
		ResultMap resultMap = null;
		while(iterator.hasNext()){
			resultMap = iterator.next();
			if(resultMap != null){
				break;
			}
		}
		metaStatementHandler.setValue("delegate.boundSql.sql", Dialect.buildPagingSQL(originalSql, pageBounds, resultMap));
		metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
		metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
//		logger.debug("生成分页SQL : " + boundSql.getSql());
		return invocation.proceed();
	}

	/**
	 * 在对查询进行分页处理前, 先查询记录总数和页面总数.
	 * 
	 * @param invocation
	 * @throws Throwable
	 * <br>
	 * 创建日期: 2012-12-20 <br>
	 * 创  建  人: Shmilycharlene
	 */
	private void processPrepagingAction(Invocation invocation) throws Throwable{
		StatementHandler statementHandler = (StatementHandler)invocation.getTarget();
		MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, new DefaultObjectFactory(), new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());
		PageBounds pageBounds = (PageBounds)metaStatementHandler.getValue("delegate.rowBounds");
		
		// 取出原始的查询语句.
		String originalSql = (String)metaStatementHandler.getValue("delegate.boundSql.sql");
		// 根据原始的查询语句, 构建查询总数的语句.
		String countSQL = Dialect.buildQueryCountSQL(originalSql);
		// 替换原始的查询语句为, 总数查询语句.
		metaStatementHandler.setValue("delegate.boundSql.sql", countSQL);
		// 执行总数查询语句.
		Object object = invocation.proceed();
		PreparedStatement psts = (PreparedStatement)object;
		statementHandler.getParameterHandler().setParameters(psts);
		if(psts.execute()){
			ResultSet resultSet = psts.getResultSet();
			if(resultSet.next()){
				// 将记录总数设置到PageBounds中
				pageBounds.setTotalNumber(resultSet.getInt(1));
			}
			resultSet.close();
			psts.close();
		}
		// 恢复原有的数据查询语句.
		metaStatementHandler.setValue("delegate.boundSql.sql", originalSql);
	}
	
	/* (non-Javadoc)
	 * @see org.apache.ibatis.plugin.Interceptor#plugin(java.lang.Object)
	 */
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	/* (non-Javadoc)
	 * @see org.apache.ibatis.plugin.Interceptor#setProperties(java.util.Properties)
	 */
	@Override
	public void setProperties(Properties properties){
		
	}
}

