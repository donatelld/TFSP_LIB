package com.tfsp.library.common.keycode;

import java.util.List;

import com.tfsp.library.common.config.SystemProperty;
import com.tfsp.library.common.logging.Logger;
import com.tfsp.library.common.logging.LoggerFactory;
import com.tfsp.library.common.utils.StringUtils;
import com.tfsp.library.web.context.SpringContext;

/**
 * 操作数据表KE_CODE_MASTER的统一类.
 * 
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.common.keycode.CodeMaster.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2012-12-23			Shmilycharlene				初始化版本	
 * 
 */
public abstract class CodeMaster{
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private static final String CACHE_TYPE = "KEY_CODE_MASTER_CACHE_TYPE";
	private static CodeMaster codeMaster = null;
	
	protected KeyTypeMapper mapper;

	protected CodeMaster() {
		mapper = SpringContext.getBean(KeyTypeMapper.class);
	}

	public static final synchronized CodeMaster getCodeMaster(){
		if(codeMaster == null){
			String cacheType = SystemProperty.getProperty(CACHE_TYPE);
			if(StringUtils.isBlank(cacheType)){
				cacheType = "jvm";
			}
			if(StringUtils.equalsIgnoreCase("jvm", cacheType)){
				codeMaster = new JvmCodeMaster();
			} else if (StringUtils.equalsIgnoreCase("mem", cacheType)) {
				codeMaster = new MemCodeMaster();
			} else {
				throw new IllegalArgumentException("系统仅支持JVM以及Memcached两种缓存方式.");
			}
		}
		return codeMaster;
	}

	/**
	 * 通过KeyType以及Code值, 获取KEY_CODE_MASTER表中与之对应的Decode值.
	 * 
	 * @param keyType
	 * @param code
	 * @return decode <br>
	 * 创建日期: 2012-12-24 <br>
	 * 创 建 人: Shmilycharlene
	 */
	public String decode(String keyType, String code) {
		boolean loaded = false;
		String decode = null;
		KeyType typeObject = get(keyType);
		if(typeObject == null){
			typeObject = load(keyType, false);
			put(keyType, typeObject);
			loaded = true;
		}
		if(typeObject != null){
			decode = typeObject.decode(code);
			if(StringUtils.isBlank(decode) && loaded == false){
				typeObject = load(keyType, true);
				remove(keyType);
				put(keyType, typeObject);
				decode = typeObject.decode(code);
			}
		}
		if(StringUtils.isBlank(decode)){
			logger.warn("KEY_CODE_MASTER表中不存在查询的记录, KeyType:{0}, Code:{1}", keyType, code);
			return "";
		}
		return decode;
	}

	public String decode(String keyType, String code, String defaultValue){
		String decode = decode(keyType, code);
		if(StringUtils.isBlank(decode))
			return defaultValue;
		return decode;
	}
	
	/**
	 * 通过KeyType值, 获取KEY_CODE_MASTER表中, KeyType下所有的Code值.
	 * 
	 * @param keyType
	 * @return code[] <br>
	 * 创建日期: 2012-12-24 <br>
	 * 创 建 人: Shmilycharlene
	 */
	public String[] getCodes(String typeString) {
		boolean loaded = false;
		String[] codes = null;
		KeyType keyType = get(typeString);
		if(keyType == null){
			keyType = load(typeString, false);
			put(typeString, keyType);
		}
		codes = keyType.getCodes();
		if((codes == null || codes.length < 1) && loaded == false){
			keyType = load(typeString, true);
			remove(typeString);
			put(typeString, keyType);
			codes = keyType.getCodes();
		}
		if(codes == null || codes.length == 0){
			logger.warn("KEY_CODE_MASTER表中不存在查询的记录, KeyType:{0}", keyType);
		}
		return codes;
	}

	/**
	 * 通过KeyType, 获取KEY_CODE_MASTER表中, 关于KeyType的完整记录.
	 * 
	 * @param keyType
	 * @return KeyType <br>
	 * 创建日期: 2012-12-24 <br>
	 * 创 建 人: Shmilycharlene
	 */
	public KeyType getKeyType(String typeString) {
		KeyType keyType = get(typeString);
		if(keyType == null){
			keyType = load(typeString, false);
			put(typeString, keyType);
		}
		return keyType;
	}

	/**
	 * 通过KeyType以及Code值, 获取KEY_CODE_MASTER表中与之对应的display显示值.
	 * 
	 * @param keyType
	 * @param code
	 * @return display <br>
	 * 创建日期: 2012-12-24 <br>
	 * 创 建 人: Shmilycharlene
	 */
	public String getDisplayValue(String typeString, String code) {
		boolean loaded = false;
		String display = null;
		KeyType keyType = get(typeString);
		if(keyType == null){
			keyType = load(typeString, false);
			put(typeString, keyType);
			loaded = true;
		}
		display = keyType.getDisplay(code);
		if(StringUtils.isBlank(display) && loaded == false){
			keyType = load(typeString, true);
			remove(typeString);
			put(typeString, keyType);
			display = keyType.getDisplay(code);
		}
		if(StringUtils.isBlank(display)){
			logger.warn("KEY_CODE_MASTER表中不存在查询的记录, KeyType:{0}, Code:{1}", typeString, code);
		}
		return display;
	}

	/**
	 * 根据KeyType加载KeyType信息
	 * 
	 * @param keyType 要加载的KeyType
	 * @param forceReload 强制重新加载 <br>
	 * 创建日期: 2012-12-23 <br>
	 * 创 建 人: Shmilycharlene
	 */
	protected synchronized KeyType load(String keyType, boolean forceReload) {
		KeyType type = get(keyType);
		if(type == null || forceReload){
			logger.debug("强制加载KeyType: {0}, Force:{1}", keyType, forceReload);
			KeyType keyTypeObject = new KeyType(keyType);
			List<KeyTypeDO> keyTypeList = null;
			try{
				keyTypeList = mapper.getKeyType(keyType);
			}catch (Exception e){
				logger.error("加载KeyType:{0}失败, ForceReload:{1}, Exception:{2}", keyType, forceReload, e.getMessage());
				return null;
			}
			if(keyTypeList != null && keyTypeList.size() > 0){
				for(KeyTypeDO typeDO : keyTypeList){
					Code code = new Code(typeDO.getCode(), typeDO.getDecode(), typeDO.getDisplay(), typeDO.getEditableInd(), typeDO.getKeyDesc());
					keyTypeObject.addCode(code, typeDO.getDefaultInd());
				}
				type = keyTypeObject;
			}
		}
		return type;
	}

	public List<KeyTypeDO> getKeyType(KeyTypeDO keyTypeDO){
		return mapper.getKeyTypeByExample(keyTypeDO);
	}
	
	protected abstract KeyType get(String keyType);
	protected abstract void put(String keyType, KeyType keyTypeObject);
	protected abstract void remove(String keyType);
	public abstract void clear();
}
