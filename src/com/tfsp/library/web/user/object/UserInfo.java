package com.tfsp.library.web.user.object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tfsp.library.common.exception.DataSerializationException;
import com.tfsp.library.common.orm.BasicDO;
import com.tfsp.library.common.utils.CloneUtils;
import com.tfsp.library.common.utils.StringUtils;

/**
 * 用户信息基础实体类. <br>
 * 该实体类只包含基础的角色组信息. 不包含其他信息, 应用该权限框架的系统, 需要继承该实体类并添加系统自身的用户扩展信息.
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.web.usercenter.object.UserInfoDO.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2012-12-29			Shmilycharlene				初始化版本	
 * 
 */
public class UserInfo extends BasicDO{
	private static final long	serialVersionUID	= 1L;
	/**用户基本信息 */
	private Integer userID;
	private String userName;
	private String password;
	private String mobile;
	private String email;
	private String userType;
	private boolean locked = false;
	
	/**用户所在的角色组信息 */
	private Integer roleID;
	private String  roleName;
	private String  roleType;
	private boolean specialUser = false;//role.special_ind = Y ? true:false;
	
	/**用户扩展信息**/
	private Map<String, Object> extendMap;
	
	/** 用户具备的权限列表*/
	private List<Permission> permissionList = new ArrayList<Permission>();

	public UserInfo(Integer userID, Integer roleID, List<Permission> permissions) {
		this.userID = userID;
		this.permissionList = permissions;
		this.roleID = roleID;
		extendMap = new HashMap<String, Object>();
		mergePermissions();
	}

	/**
	 * @return the userName
	 */
	public String getUserName(){
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName){
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword(){
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password){
		this.password = password;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile(){
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	/**
	 * @return the email
	 */
	public String getEmail(){
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email){
		this.email = email;
	}

	/**
	 * @return the userType
	 */
	public String getUserType(){
		return userType;
	}

	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType){
		this.userType = userType;
	}

	/**
	 * @return the locked
	 */
	public boolean isLocked(){
		return locked;
	}

	/**
	 * @param locked the locked to set
	 */
	public void setLocked(boolean locked){
		this.locked = locked;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName(){
		return roleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName){
		this.roleName = roleName;
	}

	/**
	 * @return the roleType
	 */
	public String getRoleType(){
		return roleType;
	}

	/**
	 * @param roleType the roleType to set
	 */
	public void setRoleType(String roleType){
		this.roleType = roleType;
	}

	/**
	 * @return the specialUser
	 */
	public boolean isSpecialUser(){
		return specialUser;
	}

	/**
	 * @param specialUser the specialUser to set
	 */
	public void setSpecialUser(boolean specialUser){
		this.specialUser = specialUser;
	}

	/**
	 * @return the userID
	 */
	public Integer getUserID(){
		return userID;
	}

	/**
	 * @return the roleID
	 */
	public Integer getRoleID(){
		return roleID;
	}

	/**
	 * @return the permissions
	 */
	public List<Permission> getPermissionList(){
		return permissionList;
	}
	
	/**
	 * 在UserInfo中放入额外数据, 如果Key重复, 则使用新值覆盖旧值
	 * @param key
	 * @param value
	 * <br>
	 * 创建日期: 2016年4月28日 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public void setAttribute(String key, Object value) {
		extendMap.put(key, value);
	}
	
	/**
	 * 读取UserInfo中的额外数据.
	 * @param key
	 * @return
	 * <br>
	 * 创建日期: 2016年4月28日 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public <T> T getAttribute(String key) {
		return (T)extendMap.get(key);
	}

	/**
	 * 以树形结构返回用户的权限列表
	 * @return
	 * <br>
	 * 创建日期: 2016年4月28日 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public void mergePermissions(){
		Map<Integer, Permission> map = new LinkedHashMap<Integer, Permission>();
		List<Permission> tempList = null;
		try{
			tempList = CloneUtils.deepClone(permissionList);
		}catch (DataSerializationException e){
		}
		for(Permission p : tempList) {
			if(p.getParentID() == null || p.getParentID() == 0) {
				map.put(p.getPermissionID(), p);
				continue;
			}
			Permission p1 = map.get(p.getParentID());
			p1.getChildElements().add(p);
		}
		tempList.clear();
		permissionList.clear();
		Iterator<Integer> iterator = map.keySet().iterator();
		while(iterator.hasNext()){
			permissionList.add(map.get(iterator.next()));
		}
	}
	
	/**
	 * 检查用户是否具备指定的URI访问权限.
	 * @param uri
	 * @return
	 * <br>
	 * 创建日期: 2016年4月28日 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public boolean hasPermission(String uri) {
		boolean has = false;
		for(Permission p : permissionList) {
			if(StringUtils.equals(uri, p.getLinkURI())) {
				has = true;
				break;
			}
		}
		return has;
	}
	
	/**
	 * 检查用户是否具备指定权限点下面的子权限uri的访问权限.
	 * @param parentURI
	 * @param uri
	 * @return
	 * <br>
	 * 创建日期: 2016年4月28日 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public boolean hasPermission(String parentURI, String uri) {
		boolean parentMatched = false;
		boolean childMatched = false;
		for(Permission p : permissionList) {
			if(StringUtils.equals(parentURI, p.getLinkURI()))
				parentMatched = true;
			if(StringUtils.equals(uri, p.getLinkURI()))
				childMatched = true;
			if(parentMatched && childMatched)
				break;
		}
		return parentMatched && childMatched;
	}
	
	public boolean matches(String uri, String type){
		boolean matches = false;
		for(Permission p : permissionList){
			if(StringUtils.equals(p.getLinkURI(), uri) && (StringUtils.isBlank(type) ? true : StringUtils.equalsIgnoreCase(type, p.getType()))){
				matches = true;
				break;
			}
		}
		return matches;
	}

}

