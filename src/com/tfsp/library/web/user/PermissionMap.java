package com.tfsp.library.web.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tfsp.library.common.logging.Logger;
import com.tfsp.library.common.logging.LoggerFactory;
import com.tfsp.library.web.user.object.Permission;

/**
 * @author ALVIN
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.web.usercenter.ElementMap.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2013-1-5			ALVIN				初始化版本	
 * 
 */
public class PermissionMap {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private static PermissionMap elementMap = new PermissionMap();
	private static List<Permission> elementList = new ArrayList<Permission>();
	private static Map<Integer, Permission> idMap = new HashMap<Integer, Permission>();
	private static Map<String, Permission> actionMap = new HashMap<String, Permission>();
	
	private PermissionMap(){
		
	}
	
	public static final PermissionMap getInstance(){
		return elementMap;
	}
	
	/**
	 * 添加element
	 * @param elementDO
	 * <br>
	 * 创建日期: 2013-1-5 <br>
	 * 创  建  人: ALVIN
	 */
	public void addPermission(Permission elementDO){
		elementList.add(elementDO);
		idMap.put(elementDO.getPermissionID(), elementDO);
		actionMap.put(elementDO.getLinkURI(), elementDO);
	}
	
	/**
	 * 根据linkUrl获取对应的element
	 * @param linkUrl
	 * @return
	 * <br>
	 * 创建日期: 2013-1-5 <br>
	 * 创  建  人: ALVIN
	 */
	public Permission getPermission(String linkUrl){
		return actionMap.get(linkUrl);
	}
	
	/**
	 * 根据elementID获取element
	 * @param elementID
	 * @return
	 * <br>
	 * 创建日期: 2013-1-5 <br>
	 * 创  建  人: ALVIN
	 */
	public Permission getPermission(Integer elementID){
		return idMap.get(elementID);
	}
	
	/**
	 * 清空
	 * 
	 * <br>
	 * 创建日期: 2013-6-25 <br>
	 * 创  建  人: ThinkPad
	 */
	public void clear(){
		elementList.clear();
		idMap.clear();
		actionMap.clear();
	}
}

