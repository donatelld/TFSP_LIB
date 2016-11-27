package com.tfsp.library.common.utils;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.tfsp.library.common.cache.vm.CacheManager;
import com.tfsp.library.common.cache.vm.VMCache;
import com.tfsp.library.common.logging.Logger;
import com.tfsp.library.common.logging.LoggerFactory;


/**
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.common.utils.XMLParser.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2016年4月29日			Shmilycharlene				初始化版本	
 * 
 */
public class XMLParser{
	protected static final Logger logger = LoggerFactory.getLogger(XMLParser.class);
	private static final String JAXB_CACHE_POOL = "com.sprite.jaxb.context.cache.pool";
	private static final String CDATA = "<![CDATA[";
	
	/**
	 * 读取XML文本中指定节点的内容, 如果节点内容中包含<![CDATA[]]>标签, 则自动清除该标签
	 * @param node
	 * @param xml
	 * @return
	 * <br>
	 * 创建日期: 2015年10月22日 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public static String getXMLNode(String node, String xml){
        String value = "";
        if(StringUtils.isBlank(node) || StringUtils.isBlank(xml))
        	return value;
        StringBuilder head = new StringBuilder("<");
        head.append(node).append(">");
        StringBuilder tail = new StringBuilder("</");
        tail.append(node).append(">");
        int pos1 = -1;
        int pos2 = -1;
        pos1 = xml.indexOf(head.toString());
        if (pos1 >= 0) {
            pos2 = xml.indexOf(tail.toString());
            if (pos2 >= 0) {
                pos1 += head.length();
                value = xml.substring(pos1, pos2).trim();
            }
        }
        //清除CDATA
        if(value.startsWith(CDATA)) {
        	value = value.substring(9, value.length() -3);
        }
        
        return value;
    }
	
	/**
	 * 设置XML文本中指定节点的值
	 * @param nodename
	 * @param nodeValue
	 * @param xmlcontent
	 * @return
	 * <br>
	 * 创建日期: 2015年10月22日 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public static String setXMLNode(String nodename, String nodeValue, String xmlcontent){
		StringBuilder builder = new StringBuilder();
		String nodename_head = ("<") + nodename + ">";
		String nodename_tail = ("</") + nodename + ">";
		
		int pos1 = -1;
		int pos2 = -1;
		pos1 = xmlcontent.indexOf(nodename_head);
		if(pos1 >= 0) {
			pos2 = xmlcontent.indexOf(nodename_tail);
			if(pos2 >= 0) {
				pos1 += nodename_head.length();
				builder.append(xmlcontent.substring(0, pos1));
				builder.append(nodeValue);
				builder.append(xmlcontent.substring(pos2));
			}
		}
		return builder.toString();
	}
	
	/**
	 * 将Java对象转换为XML, 转换实现方式: JAXB
	 * @param object
	 * @param clazz
	 * @return
	 * @throws JAXBException
	 * <br>
	 * 创建日期: 2016年5月7日 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public static String marshal(Object object, Class<?> clazz) {
		try{
			JAXBContext jaxbContext = getJaxbContext(clazz);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			StringWriter writer = new StringWriter();
			marshaller.marshal(object, writer);
			return writer.toString();
		}catch (JAXBException e){
			logger.error("marshal Exception: {0}", e);
		}
		return null;
	}
	
	/**
	 * 解组XML, 将XML数据转换为Java Bean对象
	 * @param xml
	 * @param clazz
	 * @return
	 * @throws JAXBException
	 * <br>
	 * 创建日期: 2016年5月7日 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public static <T> T unmarshal(String xml, Class<T> clazz) throws JAXBException{
		JAXBContext jaxbContext = getJaxbContext(clazz);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		return (T)unmarshaller.unmarshal(new StringReader(xml));
	}
	
	private static JAXBContext getJaxbContext(Class<?> clazz) throws JAXBException {
		VMCache cache = CacheManager.getInstance().getCache(JAXB_CACHE_POOL);
		JAXBContext jaxbContext = cache.get(clazz.getName());
		if(jaxbContext == null) {
			jaxbContext = JAXBContext.newInstance(clazz);
			cache.put(clazz.getName(), jaxbContext);
		}
		return jaxbContext;
	}
}

