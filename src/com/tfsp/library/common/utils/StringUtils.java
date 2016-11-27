package com.tfsp.library.common.utils;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * 系统提供的一个供字符串操作的工具类, 该工具类扩展apache的lang3包下的StrinUtils类. 因此该类具有<code>{@link org.apache.commons.lang3.StringUtils}</code>中所有方法.<br>
 * 并提供一个简易的根据","或者";"来拆分字符串的方法. 该功能需要Spring Core的支持. 因此, 使用该类, 需引入Spring Core包
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.common.StringUtils.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2012-12-9			Shmilycharlene				初始化版本	
 * 
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {
//	protected static final Logger logger = LoggerFactory.getLogger(StringUtils.class);
	/** 默认的字符拆分依据. */
	public static final String SPLITE_DELIMITERS = ",; \t\n";
	//包含所有字母数字 的字符数组
	private static final char[] LNA = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
	//大写字母 + 数字 字符数组
	private static final char[] ULNA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
	//所有字母数组
	private static final char[] LA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
	//所有数字数组
	private static final char[] NA = "0123456789".toCharArray();
	
	/**
	 * 将给定的<code>java.lang.String</code>拆分为<code>java.lang.String</code>数组<br>
	 * <p>
	 * 拆分依据为"," 或者 ";". 两种拆分标记可以混用.<br>
	 * 拆分结果自动忽略空白字符串和每个分段的首尾空格.<br>
	 * 例如:<br>
	 * <p><code>String propertiesFiles = "aaaaaaaaaaa;bbbbbbbbbb;;       CCCCCCCCCC;       ,    DDDDDDDDD;";</code></p><br>
	 * 上面的字符串将被拆分为:<br>
	 * aaaaaaaaaaa<br>
	 * bbbbbbbbbb<br>
	 * CCCCCCCCCC<br>
	 * DDDDDDDDD<br>
	 * </p>
	 * @param string
	 * @return arrays
	 * <br>
	 * 创建日期: 2012-12-9 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public static String[] tokenizeToStringArray(String string){
		String[] arrays = tokenizeToStringArray(string, SPLITE_DELIMITERS);
		return arrays;
	}
	
	/**
	 * 将给定的<code>java.lang.String</code>拆分为<code>java.lang.String</code>数组<br>
	 * <p>
	 * 拆分依据由参数: delimiters决定.<br>
	 * 拆分结果自动忽略空白字符串和每个分段的首尾空格.<br>
	 * 例如:<br>
	 * <p><code>String propertiesFiles = "aaaaaaaaaaa;bbbbbbbbbb;;       CCCCCCCCCC;       ,    DDDDDDDDD;";</code></p><br>
	 * 上面的字符串将被拆分为:<br>
	 * aaaaaaaaaaa<br>
	 * bbbbbbbbbb<br>
	 * CCCCCCCCCC<br>
	 * DDDDDDDDD<br>
	 * </p>
	 * @param string
	 * @param delimiters
	 * @return arrays
	 * <br>
	 * 创建日期: 2012-12-9 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public static String[] tokenizeToStringArray(String string, String delimiters){
		String[] arrays = null;
		if(StringUtils.isBlank(string)){
			arrays = new String[0];
		} else {
			StringTokenizer st = new StringTokenizer(string, delimiters);
			List<String> tokens = new ArrayList<String>();
			while (st.hasMoreTokens()) {
				String token = st.nextToken();
				if(isNotBlank(token)){
					tokens.add(token);
				}
			}
			arrays = tokens.toArray(new String[tokens.size()]);
		}
		return arrays;
	}
	
	/**
	 * 根据给定的拆分字符对字符串进行拆分, 返回包含空串的拆分结果.
	 * @param string
	 * @param delimiters
	 * @return String[]
	 * <br>
	 * 创建日期: 2013-10-18 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public static String[] splitWithNull(String string, String delimiters){
		String[] arrays = null;
		if(StringUtils.isBlank(string)){
			arrays = new String[0];
		} else {
			int start = 0;
			List<String> tokens = new ArrayList<String>();
			while(start < string.length()){
				int index = indexOf(string, delimiters, start);
				if(index >= 0){
					String sub = substring(string, start, index);
					if(StringUtils.isBlank(sub)){
						sub = null;
					}
					tokens.add(sub);
					start = index + 1;
					if(start == string.length()){
						tokens.add(null);
					}
				}else{
					tokens.add(substring(string, start));
					start = string.length();
				}
			}
			arrays = tokens.toArray(new String[tokens.size()]);
		}
		return arrays;
	}
	
	/**
	 * 转换null为空字符串
	 * @param str
	 * @return str
	 * <br>
	 * 创建日期: 2012-12-23 <br>
	 * 创  建  人: ALVIN
	 */
	public static String transNull(String str){
		if(str == null) return "";
		else return str;
	}
	
	/**
	 * 获取随机文件名
	 * @return
	 * <br>
	 * 创建日期: 2013-1-19 <br>
	 * 创  建  人: ALVIN
	 */
	public static String getRandomFileName(){
		long currentTime = System.currentTimeMillis();
		Random random = new Random();
		return currentTime+""+random.nextInt(10);
	}
	
	/**
	 * 获取当前工程的根路劲
	 * @return String
	 * @throws Exception
	 * <br>
	 * 创建日期: 2013-3-6 <br>
	 * 创  建  人: ThinkPad
	 */
	public static String getCurrentPath() {
		try{
			String path = new StringUtils().getClass().getClassLoader().getResource("").getPath();
			File file = new File(path);
			path = file.getParentFile().getParentFile().getAbsolutePath()+"/";
			path = StringUtils.replace(path, "%20", " ");
			return path;
		}catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * 用于检测给定的字符串集合中是否包含指定的字符串, 大小写敏感.
	 * @deprecated 方法已过时, 使用equalsWithAny(String, String...)代替.
	 * @param item
	 * @param collection
	 * @return
	 * <br>
	 * 创建日期: 2014-2-10 <br>
	 * 创  建  人: Administrator
	 */
	public boolean include(String item, String... collection){
		boolean include = false;
		for(String string : collection){
			if(equals(item, string)){
				include = true;
				break;
			}
		}
		return include;
	}
	
	/**
	 * 用于检测给定的字符串集合中是否包含指定的字符串, 大小写不敏感.
	 * @param item
	 * @param collection
	 * @return
	 * <br>
	 * 创建日期: 2014-2-10 <br>
	 * 创  建  人: Administrator
	 */
	public boolean includeIgnoreCase(String item, String... collection){
		boolean include = false;
		for(String string : collection){
			if(equalsIgnoreCase(item, string)){
				include = true;
				break;
			}
		}
		return include;
	}
	
	public static boolean notEquals(String str1, String str2) {
		return !equals(str1, str2);
	}

	/**
	 * 检测给定的字符串是否等于给定的字符串集合中的任意一个, 大小写敏感.
	 * @param item
	 * @param collection
	 * @return
	 * <br>
	 * 创建日期: 2014-2-10 <br>
	 * 创  建  人: Administrator
	 */
	public static boolean equalsAny(String item, String... arrsys){
		boolean include = false;
		for(String string : arrsys){
			if(equals(item, string)){
				include = true;
				break;
			}
		}
		return include;
	}
	
	public static boolean notEqualsAny(String item, String... arrsys) {
		return !equalsAny(item, arrsys);
	}
	
	/**
	 * 生成指定长度的[0-9]的数字字符串
	 * @param length
	 * @return
	 * <br>
	 * 创建日期: 2014-1-2 <br>
	 * 创  建  人: ALVIN
	 */
	public static String getRandomNum(int length) {
		StringBuilder sb = new StringBuilder();
		Random r = new Random();
		int index, len = NA.length;
		for (int i = 0; i < length; i++) {
			index = r.nextInt(len);
			sb.append(NA[index]);
		}
		return sb.toString();
	}
	
	/**
	 * 生成指定长度的[A-Z][a-z]的字符串
	 * @param length
	 * @return
	 * <br>
	 * 创建日期: 2014-1-2 <br>
	 * 创  建  人: ALVIN
	 */
	public static String getRandomChar(int length) {
		StringBuilder sb = new StringBuilder();
		Random r = new Random();
		int index, len = LA.length;
		for (int i = 0; i < length; i++) {
			index = r.nextInt(len);
			sb.append(LA[index]);
		}
		return sb.toString();
	}
	
	/**
	 * 生成指定长度的[0-9][A-Z]的字符串
	 * @param length
	 * @return
	 * <br>
	 * 创建日期: 2014-1-2 <br>
	 * 创  建  人: ALVIN
	 */
	public static String getRandomNC(int length) {
		StringBuilder sb = new StringBuilder();
		Random r = new Random();
		int index, len = ULNA.length;
		for (int i = 0; i < length; i++) {
			index = r.nextInt(len);
			sb.append(ULNA[index]);
		}
		return sb.toString();
	}
	
	/**
	 * 根据传入的长度和结尾生成随机key
	 * @param length
	 * @param end
	 * @return
	 * <br>
	 * 创建日期: 2015年3月24日 <br>
	 * 创  建  人: ALVIN
	 */
	public static String getRandomKey(int length, String end) {
		end = (end == null ? "" : end);
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		int index, len = LNA.length;
		for (int i = 0; i < (length- end.length()); i++) {
			index = r.nextInt(len);
			sb.append(LNA[index]);
		}
		sb.append(end);
		return sb.toString();
	}
	
	/**
	 * 大于传入长度 则截断加上...
	 * @param text
	 * @param maxLength
	 * @return
	 * <br>
	 * 创建日期: 2015年5月19日 <br>
	 * 创  建  人: Administrator
	 */
	public static String omitText(String text, int maxLength){
		return getMaxLenghStr(text, maxLength, "...");
	}

	/**
	 * 截断字符串最大的长度
	 * @param str
	 * @param maxLength
	 * @return
	 * <br>
	 * 创建日期: 2015年11月6日 <br>
	 * 创  建  人: ZXQ
	 */
	public static String getMaxLenghStr(String str, int maxLength){
		return getMaxLenghStr(str, maxLength, null);
	}
	/**
	 * 截断字符串最大的长度,超出长度追加字符. 追加为最大长度
	 * @param str
	 * @param maxLength
	 * @return
	 * <br>
	 * 创建日期: 2015年11月6日 <br>
	 * 创  建  人: ZXQ
	 */
	public static String getMaxLenghStr(String str, int maxLength, String appendStr){
		if(isBlank(str)) return str;
		int appendStrLength = 0;
		if(isNotBlank(appendStr)){
			appendStrLength = appendStr.length();
			maxLength = maxLength - appendStrLength;
		}
		if(str.length() > maxLength){
			str = str.substring(0, maxLength);
			if(appendStrLength != 0) str += appendStr;
			return str;
		} else {
			return str;
		}
	}
	
	/**
	 * 将通配符*的字符串, 转为Java能识别的正则表达式.
	 * @param source
	 * @return regex
	 * <br>
	 * 创建日期: 2012-12-16 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public static String buildRegexString(String source) {
		if (StringUtils.contains(source, "*")) {
			source = source.replace('.', '#');
			source = source.replaceAll("#", "\\\\.");
			source = source.replace('*', '#');
			source = source.replaceAll("#", ".*");
			source = source.replace('?', '#');
			source = source.replaceAll("#", ".?");
		}
		source = "^" + source + "$";
		return source;
	}
	
	/**
	 * 格式化输出xml
	 * @param xml
	 * @return
	 * <br>
	 * 创建日期: 2016年9月19日 <br>
	 * 创  建  人: ALVIN
	 */
	public static String formatXml(String xml) {
		try {
			Document document = null;
			document = DocumentHelper.parseText(xml);
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("gb2312");
			StringWriter writer = new StringWriter();
			XMLWriter xmlWriter = new XMLWriter(writer, format);
			xmlWriter.write(document);
			xmlWriter.close();
			return writer.toString().replace("\n\n", "\n");
		} catch (Exception e) {
			return "";
		}
	}

	public static void main(String[] args) {
		System.err.println(getRandomKey(14, "1"));
	}
	
}

