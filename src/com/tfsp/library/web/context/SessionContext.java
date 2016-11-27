package com.tfsp.library.web.context;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import com.tfsp.library.common.utils.StringUtils;
import com.tfsp.library.web.user.object.UserInfo;


/**
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.web.utils.SessionUtils.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2016年4月28日			Shmilycharlene				初始化版本	
 * 
 */
public abstract class SessionContext{
	private static final String LOGGED_USER_IN_SESSION = "com.spring.library.web.Login.LOGGED_IN_SESSION";
	private static final String LOGIN_RANDOM_VALIDATION_CODE = "com.spring.library.web.Login.LOGIN_RANDOM_VALIDATION_CODE";
	private static final char[] ch = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789".toCharArray();
	
	/**
	 * 登录验证相关
	 * @author Shmilycharlene
	 * @version 1.0
	 */
	public static class LoginValidate{
		/**
		 * 创建用户登录时使用的验证码
		 * @param request
		 * @return
		 * <br>
		 * 创建日期: 2016年4月28日 <br>
		 * 创  建  人: Shmilycharlene
		 */
		public static BufferedImage createValidateCode(HttpServletRequest request) {
			BufferedImage img = new BufferedImage(92, 26, BufferedImage.TYPE_INT_RGB);
			// 得到该图片的绘图对象
			Graphics g = img.getGraphics();
			Random r = new Random();
			Color c = new Color(228, 228, 230);
			g.setColor(c);
			// 填充整个图片的颜色
			g.fillRect(0, 0, 92, 26);
			// 向图片中输出数字和字母
			StringBuffer sb = new StringBuffer();
			
			int index, len = ch.length;
			for (int i = 0; i < 4; i++) {
				index = r.nextInt(len);
				g.setColor(new Color(r.nextInt(88), r.nextInt(188), r.nextInt(255)));
				g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 22));// 输出的字体和大小
				g.drawString("" + ch[index], (i * 15) + 12, 22);// 写什么数字，在图片的什么位置画
				sb.append(ch[index]);
			}
			setAttribute(request, LOGIN_RANDOM_VALIDATION_CODE, sb.toString());
			return img;
		}
		
		/**
		 * 验证用户输入的验证码是否与Session中保存的验证码一致
		 * @param request
		 * @param validateCode
		 * @return
		 * <br>
		 * 创建日期: 2016年4月28日 <br>
		 * 创  建  人: Shmilycharlene
		 */
		public static boolean validate(HttpServletRequest request, String validateCode) {
			return StringUtils.equalsIgnoreCase(validateCode, getAttribute(request, LOGIN_RANDOM_VALIDATION_CODE).toString());
		}
	}
	
	/**
	 * 用户信息相关
	 * @author Shmilycharlene
	 * @version 1.0
	 */
	public static class UserCenter{
		/**
		 * 将登录成功的用户信息放置到Session中
		 * @param request
		 * @param userInfo
		 * <br>
		 * 创建日期: 2016年4月28日 <br>
		 * 创  建  人: Shmilycharlene
		 */
		public static void userLogged(HttpServletRequest request, UserInfo userInfo) {
			setAttribute(request, LOGGED_USER_IN_SESSION, userInfo);
		}
		
		/**
		 * 从Session中读取已登录的用户信息
		 * @param request
		 * @return
		 * <br>
		 * 创建日期: 2016年4月28日 <br>
		 * 创  建  人: Shmilycharlene
		 */
		public static UserInfo getLoggedUser(HttpServletRequest request) {
			return (UserInfo)getAttribute(request, LOGGED_USER_IN_SESSION);
		}
		
		/**
		 * 用户登出时从Session中移除用户信息
		 * @param request
		 * <br>
		 * 创建日期: 2016年4月28日 <br>
		 * 创  建  人: Shmilycharlene
		 */
		public static void userLogout(HttpServletRequest request) {
			removeAttribute(request, LOGGED_USER_IN_SESSION);
		}
	}

	/**
	 * 通用Session数据存储相关
	 * @author Shmilycharlene
	 * @version 1.0
	 */
	public static class Attribute{
		public static Object get(HttpServletRequest request, String attrName){
			return getAttribute(request, attrName);
		}
		public static void set(HttpServletRequest request, String attrName, Object value){
			setAttribute(request, attrName, value);
		}
		public static void remove(HttpServletRequest request, String attrName) {
			removeAttribute(request, attrName);
		}
	}
	
	/**
	 * 通过属性名, 从session中获取与之对应的属性.<br>
	 * 当Session中不包含该名称对应的属性时, 返回 null.
	 * @param request
	 * @param attrName
	 * @return attribute
	 * <br>
	 * 创建日期: 2012-12-30 <br>
	 * 创  建  人: Shmilycharlene
	 */
	private static Object getAttribute(HttpServletRequest request, String attrName){
		return request.getSession().getAttribute(attrName);
	}
	
	/**
	 * 将属性放入会话中.
	 * @param request
	 * @param attrName
	 * @param value
	 * <br>
	 * 创建日期: 2014年10月13日 <br>
	 * 创  建  人: Shmilycharlene
	 */
	private static void setAttribute(HttpServletRequest request, String attrName, Object value){
		request.getSession().setAttribute(attrName, value);
	}
	
	private static void removeAttribute(HttpServletRequest request, String attrName) {
		request.getSession().removeAttribute(attrName);
	}
}

