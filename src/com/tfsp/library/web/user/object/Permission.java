package com.tfsp.library.web.user.object;

import java.util.ArrayList;
import java.util.List;

import com.tfsp.library.common.orm.BasicDO;

/**
 * 系统通用权限点实体类. 对应数据中的权限表. 如果系统的权限需要扩展信息, 继承该实体类即可.
 * 
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.web.usercenter.object.PermissionDO.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2012-12-29			Shmilycharlene				初始化版本	
 * 
 */
public class Permission extends BasicDO{
	private static final long	serialVersionUID	= 1L;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column ELEMENT.ELEMENT_ID
	 */
	protected Integer permissionID;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column ELEMENT.NAME
	 */
	protected String name;
	
	protected String icon;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column ELEMENT.LINK_URI
	 */
	protected String linkURI;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column ELEMENT.TYPE
	 */
	protected String type;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column ELEMENT.PRIORITY
	 */
	protected Integer priority;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column ELEMENT.PARENT_ID
	 */
	protected Integer parentID;

	/** 权限项下的所有子权限项列表 */
	private List<Permission> childElements;
	
	/**
	 * 获取该权限项下的子权限项. <br>
	 * 如果该权限下没有子权限了, 则返回一个长度为 0 的空列表.
	 * @return childPermission
	 * <br>
	 * 创建日期: 2012-12-30 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public List<Permission> getChildElements(){
		if(childElements == null){
			childElements = new ArrayList<Permission>();
		}
		return childElements;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column ELEMENT.ELEMENT_ID
	 * @return  the value of ELEMENT.ELEMENT_ID
	 */
	public Integer getPermissionID(){
		return permissionID;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column ELEMENT.ELEMENT_ID
	 * @param elementID  the value for ELEMENT.ELEMENT_ID
	 */
	public void setPermissionID(Integer elementID){
		this.permissionID = elementID;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column ELEMENT.NAME
	 * @return  the value of ELEMENT.NAME
	 */
	public String getName(){
		return name;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column ELEMENT.NAME
	 * @param name  the value for ELEMENT.NAME
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column ELEMENT.LINK_URI
	 * @return  the value of ELEMENT.LINK_URI
	 */
	public String getLinkURI(){
		return linkURI;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column ELEMENT.LINK_URI
	 * @param linkURI  the value for ELEMENT.LINK_URI
	 */
	public void setLinkURI(String linkURI){
		this.linkURI = linkURI;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column ELEMENT.TYPE
	 * @return  the value of ELEMENT.TYPE
	 */
	public String getType(){
		return type;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column ELEMENT.TYPE
	 * @param type  the value for ELEMENT.TYPE
	 */
	public void setType(String type){
		this.type = type;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column ELEMENT.PRIORITY
	 * @return  the value of ELEMENT.PRIORITY
	 */
	public Integer getPriority(){
		return priority;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column ELEMENT.PRIORITY
	 * @param priority  the value for ELEMENT.PRIORITY
	 */
	public void setPriority(Integer priority){
		this.priority = priority;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column ELEMENT.PARENT_ID
	 * @return  the value of ELEMENT.PARENT_ID
	 */
	public Integer getParentID(){
		return parentID;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column ELEMENT.PARENT_ID
	 * @param parentID  the value for ELEMENT.PARENT_ID
	 */
	public void setParentID(Integer parentID){
		this.parentID = parentID;
	}
	
	public Permission clone() {
		try{
			return (Permission)super.clone();
		}catch (CloneNotSupportedException e){
		}
		return null;
	}
}
