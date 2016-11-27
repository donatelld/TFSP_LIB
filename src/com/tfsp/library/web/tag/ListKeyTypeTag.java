package com.tfsp.library.web.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.tfsp.library.common.keycode.CodeMaster;
import com.tfsp.library.common.keycode.KeyType;
import com.tfsp.library.common.logging.Logger;
import com.tfsp.library.common.logging.LoggerFactory;
import com.tfsp.library.common.utils.StringUtils;

/**
 * 查询keyType所有的code，并以select、radio或checkbox的方式显示
 * @author ALVIN
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.common.tag.ListKeyTypeTag.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2012-12-23			ALVIN				初始化版本	
 * 
 */
public class ListKeyTypeTag extends BodyTagSupport {
	private static final long serialVersionUID = 1L;
	protected static final Logger logger = LoggerFactory.getLogger(ListKeyTypeTag.class);
	public static final String INPUT_TYPE_SELECT = "select";
	public static final String INPUT_TYPE_RADIO = "radio";
	public static final String INPUT_TYPE_CHECKBOX = "checkbox";
	
	/**
	 * 输入keyType
	 */
	private String keyType;
	/**
	 * 需要显示的类型，支持select、radio、checkbox
	 */
	private String type;
	/**
	 * 输出元素的name
	 */
	private String name;
	/**
	 * 默认选中值
	 */
	private String value;
	/**
	 * 需要排除显示的code
	 */
	private String exclude;
	/**
	 * 选中class样式
	 */
	private String styleClass;
	/**
	 * 自定义style
	 */
	private String style;
	/**
	 * 元素事件名，例如：onchange、onclick
	 */
	private String funcName;
	/**
	 * 事件方法，例如：show(args);
	 */
	private String func;
	/**
	 * 主要针对select自定义显示在第一位置的文字，默认显示的是：--请选择--
	 */
	private String firstName;
	/**
	 *  主要针对select自定义显示在第一位置的value
	 */
	private String firstValue;
	/**
	 * 当为true时不显示第一位置的文字，默认false
	 */
	private boolean excludeFirst;
	/**
	 * 是否支持默认选中，默认为false，当没有传入value或者value传入的指没有相对应的code值时，会将keyType中的defaultCode默认选中
	 */
	private boolean defaultChoose;
	
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the exclude
	 */
	public String getExclude() {
		return exclude;
	}
	/**
	 * @param exclude the exclude to set
	 */
	public void setExclude(String exclude) {
		this.exclude = exclude;
	}
	/**
	 * @return the styleClass
	 */
	public String getStyleClass() {
		return styleClass;
	}
	/**
	 * @param styleClass the styleClass to set
	 */
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	
	/**
	 * @return the style
	 */
	public String getStyle() {
		return style;
	}
	/**
	 * @param style the style to set
	 */
	public void setStyle(String style) {
		this.style = style;
	}
	/**
	 * @return the funcName
	 */
	public String getFuncName() {
		return funcName;
	}
	/**
	 * @param funcName the funcName to set
	 */
	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}
	/**
	 * @return the func
	 */
	public String getFunc() {
		return func;
	}
	/**
	 * @param func the func to set
	 */
	public void setFunc(String func) {
		this.func = func;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the firstValue
	 */
	public String getFirstValue() {
		return firstValue;
	}
	/**
	 * @param firstValue the firstValue to set
	 */
	public void setFirstValue(String firstValue) {
		this.firstValue = firstValue;
	}
	
	/**
	 * @return the defaultChoose
	 */
	public boolean isDefaultChoose() {
		return defaultChoose;
	}
	/**
	 * @param defaultChoose the defaultChoose to set
	 */
	public void setDefaultChoose(boolean defaultChoose) {
		this.defaultChoose = defaultChoose;
	}
	
	/**
	 * @return the excludeFirst
	 */
	public boolean isExcludeFirst() {
		return excludeFirst;
	}
	/**
	 * @param excludeFirst the excludeFirst to set
	 */
	public void setExcludeFirst(boolean excludeFirst) {
		this.excludeFirst = excludeFirst;
	}
	/* 
	 * 
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		try {
			KeyType keyTypeDo = CodeMaster.getCodeMaster().getKeyType(keyType);
			JspWriter out = this.pageContext.getOut();
			if(StringUtils.equals(type, INPUT_TYPE_SELECT)){
				listSelectCodes(out, keyTypeDo);
			}else if(StringUtils.equals(type, INPUT_TYPE_RADIO)){
				listRadioCodes(out, keyTypeDo);
			}else if(StringUtils.equals(type, INPUT_TYPE_CHECKBOX)){
				listCheckboxCodes(out, keyTypeDo);
			}
		} catch (Exception e) {
			logger.error("查询keyType:{0}的所有code值时出错，出错原因：{1}", keyType, e);
		}
		return super.doStartTag();
	}
	
	/**
	 * 按select方式输出codes
	 * @param out
	 * @param keyTypeDo
	 * @throws JspException
	 * @throws IOException
	 * <br>
	 * 创建日期: 2012-12-24 <br>
	 * 创  建  人: ALVIN
	 */
	private void listSelectCodes(JspWriter out, KeyType keyTypeDo) throws JspException, IOException{
		List<String> excludeList = new ArrayList<String>();
		if(StringUtils.isNotBlank(exclude)){
			excludeList = Arrays.asList(StringUtils.tokenizeToStringArray(exclude));
		}
		out.print("<select name='"+name+"' id='"+name+"'");
		if(StringUtils.isNotBlank(styleClass)){
			out.print(" class='"+styleClass+"'");
		}
		if(StringUtils.isNotBlank(style)){
			out.print(" style='"+style+"'");
		}
		if(StringUtils.isNotBlank(funcName) && StringUtils.isNotBlank(func)){
			out.print(" "+funcName+"=\""+func+"\"");
		}
		out.print(">");
		if(!excludeFirst){
			if(StringUtils.isNotBlank(firstName)){
				out.print("<option value='"+(firstValue == null ? "" : firstValue)+"'>"+firstName+"</option>");
			}else{
				out.print("<option value=''>--Select an option--</option>");
			}
		}
		if(keyTypeDo != null){
			String[] codes = keyTypeDo.getCodes();
			for(String code : codes){
				if(excludeList.contains(code)){
					continue;
				}
				String displayValue = keyTypeDo.getDisplay(code);
				out.print("<option value='"+code+"'");
				if(StringUtils.equals(value, code)){
					out.print(" selected");
				}
				if(StringUtils.isBlank(value) && defaultChoose 
						&& StringUtils.equals(keyTypeDo.getDefaultCode(), code)){
					out.print(" selected");
				}
				out.print(">"+displayValue+"</option>");
			}
		}
		out.print("</select>");
	}
	
	/**
	 * 按radio方式输出codes
	 * @param out
	 * @param keyTypeDo
	 * @throws JspException
	 * @throws IOException
	 * <br>
	 * 创建日期: 2012-12-24 <br>
	 * 创  建  人: ALVIN
	 */
	private void listRadioCodes(JspWriter out, KeyType keyTypeDo) throws JspException, IOException{
		if(keyTypeDo == null)
			return;
		String[] codes = keyTypeDo.getCodes();
		List<String> excludeList = new ArrayList<String>();
		if(StringUtils.isNotBlank(exclude)){
			excludeList = Arrays.asList(StringUtils.tokenizeToStringArray(exclude));
		}
		for(String code : codes){
			if(excludeList.contains(code)){
				continue;
			}
			String displayValue = keyTypeDo.getDisplay(code);
			out.print("<input type='radio' name='"+name+"'");
			if(StringUtils.isNotBlank(styleClass)){
				out.print(" class='"+styleClass+"'");
			}
			if(StringUtils.isNotBlank(style)){
				out.print(" style='"+style+"'");
			}
			if(StringUtils.isNotBlank(funcName) && StringUtils.isNotBlank(func)){
				out.print(" "+funcName+"=\""+func+"\"");
			}
			if(StringUtils.equals(value, code)){
				out.print(" checked");
			}
			if(StringUtils.isBlank(value) && defaultChoose 
					&& StringUtils.equals(keyTypeDo.getDefaultCode(), code)){
				out.print(" checked");
			}
			out.print(" value='"+code+"'/> "+displayValue+"&nbsp;&nbsp;");
		}
		out.print("</select>");
	}
	
	/**
	 * 按checkbox方式输出codes
	 * @param out
	 * @param keyTypeDo
	 * @throws JspException
	 * @throws IOException
	 * <br>
	 * 创建日期: 2012-12-24 <br>
	 * 创  建  人: ALVIN
	 */
	private void listCheckboxCodes(JspWriter out, KeyType keyTypeDo) throws JspException, IOException{
		if(keyTypeDo == null)
			return;
		String[] codes = keyTypeDo.getCodes();
		List<String> excludeList = new ArrayList<String>();
		if(StringUtils.isNotBlank(exclude)){
			excludeList = Arrays.asList(StringUtils.tokenizeToStringArray(exclude));
		}
		String[] inputValues = StringUtils.tokenizeToStringArray(value);
		List<String> valueList = Arrays.asList(inputValues);
		for(String code : codes){
			if(excludeList.contains(code)){
				continue;
			}
			String displayValue = keyTypeDo.getDisplay(code);
			out.print("<input type='checkbox' name='"+name+"'");
			if(StringUtils.isNotBlank(styleClass)){
				out.print(" class='"+styleClass+"'");
			}
			if(StringUtils.isNotBlank(style)){
				out.print(" style='"+style+"'");
			}
			if(StringUtils.isNotBlank(funcName) && StringUtils.isNotBlank(func)){
				out.print(" "+funcName+"=\""+func+"\"");
			}
			if(valueList.contains(code)){
				out.print(" checked");
			}
			if(StringUtils.isBlank(value) && defaultChoose 
					&& StringUtils.equals(keyTypeDo.getDefaultCode(), code)){
				out.print(" checked");
			}
			out.print(" value='"+code+"'/> "+displayValue+"&nbsp;&nbsp;");
		}
		out.print("</select>");
	}
}

