package com.tfsp.library.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tfsp.library.common.logging.Logger;
import com.tfsp.library.common.logging.LoggerFactory;
import com.tfsp.library.common.utils.StringUtils;

/**
 * 抽象的web请求过滤器. 该基础过滤器可以检查客户端的请求是否包含在排除列表中. 在排除列表中的请求, 直接通过该过滤器. <br>
 * 该过滤器的子类过滤器, 都具备该项功能, 除非子类重写{@link #doFilter(ServletRequest, ServletResponse, FilterChain)}覆盖父类的定义.<br>
 * 该基础类同时定义了两个抽象方法:<br>
 * 一: {@link #checkAccessable(HttpServletRequest)} 该方法用于检查客户端请求的资源是否可以访问. 子类必须实现该方法.<br>
 * 二: {@link #processBadRequest(HttpServletRequest, HttpServletResponse)} 当客户端请求的资源无法访问时, 此方法将响应客户端. 告知客户端状况.
 * 
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.web.filter.BasicFilter.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2012-12-20			Shmilycharlene				初始化版本	
 * 
 */
public abstract class AbstractFilter implements Filter {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String EXCLUSION_PARAM = "exclusions";
	private static final String ALL_STATIC_RESOURCES = ".bmp.jpeg.png.gif.ico.jpg.css.js";
	protected String contextPath;
	protected boolean exclusionStaticResource = true;
	
	/** 过滤器配置参数 */
	protected FilterConfig filterConfig;
	/** 排除列表, 在排除列表中的请求URI将不经过该过滤器, 直接处理 */
	protected List<Pattern> exclusionPatterns;
	/**其他列表**/
	protected List<Pattern> otherPatterns;

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		if(exclusionStaticResource && isRequestStaticResouce(request)) {//请求静态资源文件
			chain.doFilter(request, response);
		} else if (isRequestURIInExclusionList(request)) {//请求的资源在排除检查列表中
			doBusinessProcess(request, response);
			chain.doFilter(request, response);
		} else {
			if(checkAccessable(request)) { //检查用户是否具备访问权限(包含是否登录)
				doBusinessProcess(request, response);
				chain.doFilter(request, response);
			}else {//用户未登录, 或者不具备访问该资源的权限
				processBadRequest(request, response);
			}
		}
	}
	
	private boolean isRequestStaticResouce(HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		int dotIndex = requestURI.lastIndexOf('.');
		if(dotIndex > 0) {
			String suffix = StringUtils.substring(requestURI, dotIndex);
			return StringUtils.containsIgnoreCase(ALL_STATIC_RESOURCES, suffix);
		}
		return false;
	}

	/**
	 * 可访问性检查.<br>
	 * 检查请求是否可访问. 具体业务逻辑需要子类实现.
	 * @param request
	 * @return true / false
	 * <br>
	 * 创建日期: 2012-12-20 <br>
	 * 创  建  人: Shmilycharlene
	 */
	protected abstract boolean checkAccessable(HttpServletRequest request);
	
	protected abstract void doBusinessProcess(HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * 处理非法请求. <br>
	 * 当无法访问请求的资源时. 通过该方法响应客户端. 通知客户.
	 * @param request
	 * @param response
	 * <br>
	 * 创建日期: 2012-12-20 <br>
	 * 创  建  人: Shmilycharlene
	 */
	protected abstract void processBadRequest(HttpServletRequest request, HttpServletResponse response) throws IOException ;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		contextPath = filterConfig.getServletContext().getContextPath();
		if(StringUtils.equals(contextPath, "/")){
			contextPath = "";
		}
		if(StringUtils.isNotBlank(filterConfig.getInitParameter("exclusion_all_static_resources"))) {
			exclusionStaticResource = Boolean.valueOf(filterConfig.getInitParameter("exclusion_all_static_resources"));
		}
 		
 		String[] exclusions = StringUtils.tokenizeToStringArray(filterConfig.getInitParameter(EXCLUSION_PARAM));
 		for (String exclusion : exclusions) {
			addExclusionPageToList(contextPath+exclusion);
		}
	}

	/**
	 * 检查请求的URI是否包含在排除列表中.
	 * 
	 * @param request
	 * @return true/false <br>
	 * 创建日期: 2012-12-20 <br>
	 * 创 建 人: Shmilycharlene
	 */
	protected boolean isRequestURIInExclusionList(HttpServletRequest request) {
		boolean inExclusions = false;
//		String requestURI = StringUtils.replace(request.getRequestURI(), request.getContextPath(), "");
		String requestURI = request.getRequestURI();
		for (Iterator<Pattern> iterator = getExclusionPatterns().iterator(); iterator.hasNext();) {
			Pattern pattern = iterator.next();
			Matcher matcher = pattern.matcher(requestURI);
			if (matcher.matches()) {
				inExclusions = true;
				break;
			}
		}
		return inExclusions;
	}
	
	/**
	 * 将配置的排除列表项转化为正则表达式, 并添加到排除列表
	 * 
	 * @param exclusionPage
	 * <br>
	 * 创建日期: 2012-12-20 <br>
	 * 创 建 人: Shmilycharlene
	 */
	private void addExclusionPageToList(String exclusionPage) {
		Pattern pattern = Pattern.compile(StringUtils.buildRegexString(exclusionPage));
		getExclusionPatterns().add(pattern);
	}

	/**
	 * 取得过滤器中配置的排除列表. 以正则表达式的模式方式返回.
	 * 
	 * @return pattern list <br>
	 * 创建日期: 2012-12-20 <br>
	 * 创 建 人: Shmilycharlene
	 */
	protected List<Pattern> getExclusionPatterns() {
		if (exclusionPatterns == null) {
			exclusionPatterns = new ArrayList<Pattern>();
		}
		return exclusionPatterns;
	}

	@Override
	public void destroy() {
	}
}
