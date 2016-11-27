package com.tfsp.library.web.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tfsp.library.common.utils.StringUtils;
import com.tfsp.library.web.context.SessionContext.Attribute;
import com.tfsp.library.web.context.SessionContext.UserCenter;
import com.tfsp.library.web.user.object.UserInfo;

/**
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.web.filter.LoginFilter.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2016年4月29日			Shmilycharlene				初始化版本	
 * 
 */
public class LoginFilter extends AbstractFilter{
	private static final String NOT_LOGIN = "com.sprte.library.web.LoginFilter.NOT_LOGIN";
	private static final String NO_PERMISSION = "com.sprte.library.web.LoginFilter.NO_PERMISSION";
	
	/** 用户未登录时, 响应客户端的代码 */
	protected static final int USER_NOT_LOGIN_RESPONSE = 610;
	/** 用户无权限访问资源时, 响应客户端的代码 */
	protected static final int NO_PERMISSION_FOR_REQUEST_RESOURCE = 666;
	
	/* (non-Javadoc)
	 * @see com.tfsp.library.web.filter.AbstractFilter#checkAccessable(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected boolean checkAccessable(HttpServletRequest request){
		if(checkLoginStatus(request)) {
//			if(checkPermission(request)) {
//				return true;
//			}
//			Attribute.set(request, NO_PERMISSION, NO_PERMISSION);
			return true;
		}
		Attribute.set(request, NOT_LOGIN, NOT_LOGIN);
		return false;
	}

	/* (non-Javadoc)
	 * @see com.tfsp.library.web.filter.AbstractFilter#doBusinessProcess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doBusinessProcess(HttpServletRequest request, HttpServletResponse response){
	}

	/* (non-Javadoc)
	 * @see com.tfsp.library.web.filter.AbstractFilter#processBadRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void processBadRequest(HttpServletRequest request, HttpServletResponse response) throws IOException{
		if(StringUtils.equals(NOT_LOGIN, (String)Attribute.get(request, NOT_LOGIN))) {
			response.sendError(USER_NOT_LOGIN_RESPONSE);
		}
		if(StringUtils.equals(NO_PERMISSION, (String)Attribute.get(request, NO_PERMISSION))){
			response.sendError(NO_PERMISSION_FOR_REQUEST_RESOURCE);
		}
	}
	
	/**
	 * 检查用户是否登录. <br>
	 * 
	 * @param request
	 * @return
	 * <br>
	 * 创建日期: 2012-12-20 <br>
	 * 创  建  人: Shmilycharlene
	 */
	protected boolean checkLoginStatus(HttpServletRequest request){
		UserInfo userInfo = UserCenter.getLoggedUser(request);
		return userInfo != null;
	}
	
	protected boolean checkPermission(HttpServletRequest request){
		UserInfo userInfo = UserCenter.getLoggedUser(request);
		String requestURI = request.getRequestURI();
		if(StringUtils.length(contextPath) > 0) {
			requestURI = StringUtils.substring(requestURI, StringUtils.length(contextPath));
		}
		return userInfo.hasPermission(requestURI);
	}
}

