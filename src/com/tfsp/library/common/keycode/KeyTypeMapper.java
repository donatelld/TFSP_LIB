package com.tfsp.library.common.keycode;

import java.util.List;

import com.tfsp.library.common.orm.mybatis.support.SQLMapper;

/**
 * 使用MyBatis加载KEY_CODE_MASTER表数据的接口. <br>
 * <p>
 * 该接口包含两个方法:<br>
 * 1: {@link #getKeyType(String)} 通过KeyType获取与此KeyType相关的所有Code记录. <br>
 * 2: {@link #saveKeyType(KeyTypeDO)} 修改KeyType下一个Code的decode以及其它值的方法.<br>
 * </p>
 * 如果对于KEY_CODE_MASTER表有额外操作, 则需要扩展这个接口. 并编写相关的SQL语句.
 * 
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.common.keycode.KeyTypeMapper.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2012-12-23			Shmilycharlene				初始化版本	
 * 
 */
public interface KeyTypeMapper extends SQLMapper {
	/**
	 * 通过KeyType键值, 获取KEY_CODE_MASTER表中所有与KeyType相关的Code记录.
	 * @param keyType
	 * @return
	 * <br>
	 * 创建日期: 2012-12-24 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public List<KeyTypeDO> getKeyType(String keyType);
	
	/**
	 * 修改KeyType下的某一个Code记录.
	 * @param keyTypeDO
	 * <br>
	 * 创建日期: 2012-12-24 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public void saveKeyType(KeyTypeDO keyTypeDO);

	public List<KeyTypeDO> getKeyTypeByExample(KeyTypeDO keyTypeDO);
	
	public void insertKeyType(KeyTypeDO keyTypeDO);
}

