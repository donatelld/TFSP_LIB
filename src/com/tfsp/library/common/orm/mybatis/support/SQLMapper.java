package com.tfsp.library.common.orm.mybatis.support;

/**
 * 所有Mybatis Mapper接口的父接口. 继承接口可以省下更过的Spring配置, 更方便使用Autowired注解进行mapper的注入
 * <br>
 *  &lt;!-- 通过扫描的模式，扫描目录在com/scxxs/mapper目录下，所有的mapper都继承SQLMapper接口的接口， 这样一个bean就可以了 --&gt;<br>
 *	&lt;bean class="org.mybatis.spring.mapper.MapperScannerConfigurer"&gt;<br>
 *		&nbsp;&nbsp;&nbsp;&nbsp;&lt;property name="basePackage" value="com.scxxs.mapper"/&gt;<br>
 *		&nbsp;&nbsp;&nbsp;&nbsp;&lt;property name="markerInterface" value="com.tfsp.library.common.db.orm.mybatis.support.SQLMapper"/&gt;<br>
 *	&lt;/bean&gt;<br>
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.common.db.orm.mybatis.support.SQLMapper.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2012-12-11			Shmilycharlene				初始化版本	
 * 
 */
public interface SQLMapper {
}

