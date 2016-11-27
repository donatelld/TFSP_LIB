package com.tfsp.library.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.tfsp.library.common.keycode.CodeMaster;
import com.tfsp.library.common.logging.Logger;
import com.tfsp.library.common.logging.LoggerFactory;
import com.tfsp.library.common.utils.StringUtils;

/**
 * 根据keyType和code获取decode值并显示
 * @author ALVIN
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.common.tag.DecodeTag.java
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2012-12-23			ALVIN				初始化版本	
 * 
 */
public class DecodeTag extends BodyTagSupport {
	private static final long serialVersionUID = 1L;
	protected static final Logger logger = LoggerFactory.getLogger(DecodeTag.class);
	private String keyType;
	private String code;
	/**
	 * @return the keyType
	 */
	public String getKeyType() {
		return keyType;
	}
	/**
	 * @param keyType the keyType to set
	 */
	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/* 
	 * 
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		try {
			String decode = CodeMaster.getCodeMaster().decode(keyType, code);
			JspWriter out = this.pageContext.getOut();
			out.print(StringUtils.transNull(decode));
		} catch (Exception e) {
			logger.error("查询keyType:{0},code:{1}的decode值时出错，出错原因：{2}", keyType,code,e);
		}
		return super.doStartTag();
	}
	
	
}

