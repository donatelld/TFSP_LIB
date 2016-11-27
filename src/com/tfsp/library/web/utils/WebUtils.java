package com.tfsp.library.web.utils;

import javax.servlet.http.HttpServletRequest;

import com.tfsp.library.common.logging.Logger;
import com.tfsp.library.common.logging.LoggerFactory;

/**
 * @author ALVIN
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.web.utils.WebUtils.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2013-12-13			ALVIN				初始化版本	
 * 
 */
public class WebUtils {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 获取客户端IP地址
	 * @param request
	 * @return
	 * <br>
	 * 创建日期: 2013-12-13 <br>
	 * 创  建  人: ALVIN
	 */
	public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}

