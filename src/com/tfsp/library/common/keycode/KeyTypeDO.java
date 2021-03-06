package com.tfsp.library.common.keycode;

import com.tfsp.library.common.orm.BasicDO;
import com.tfsp.library.common.utils.ReflectUtils.ReflectIgnore;

/**
 * 与数据库表KEY_CODE_MASTER中数据相对应的实体类, 不需要做任何的修改操作, 直接使用.
 * 
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.common.keycode.KeyTypeDO.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2012-12-23			Shmilycharlene				初始化版本	
 * 
 */
public class KeyTypeDO extends BasicDO {
	@ReflectIgnore
	private static final long	serialVersionUID	= 1L;

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column KEY_CODE_MASTER.KEY_TYPE
     *
     * 
     */
    private String keyType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column KEY_CODE_MASTER.CODE
     *
     * 
     */
    private String code;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column KEY_CODE_MASTER.DECODE
     *
     * 
     */
    private String decode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column KEY_CODE_MASTER.DISPLAY
     *
     * 
     */
    private String display;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column KEY_CODE_MASTER.DEFAULT_IND
     *
     * 
     */
    private String defaultInd;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column KEY_CODE_MASTER.EDITABLE
     *
     * 
     */
    private String editableInd;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column KEY_CODE_MASTER.KEY_DESC
     *
     * 
     */
    private String keyDesc;
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column KEY_CODE_MASTER.KEY_TYPE
     *
     * @return the value of KEY_CODE_MASTER.KEY_TYPE
     *
     * 
     */
    public String getKeyType() {
        return keyType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column KEY_CODE_MASTER.KEY_TYPE
     *
     * @param keyType the value for KEY_CODE_MASTER.KEY_TYPE
     *
     * 
     */
    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column KEY_CODE_MASTER.CODE
     *
     * @return the value of KEY_CODE_MASTER.CODE
     *
     * 
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column KEY_CODE_MASTER.CODE
     *
     * @param code the value for KEY_CODE_MASTER.CODE
     *
     * 
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column KEY_CODE_MASTER.DECODE
     *
     * @return the value of KEY_CODE_MASTER.DECODE
     */
    public String getDecode() {
        return decode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column KEY_CODE_MASTER.DECODE
     *
     * @param decode the value for KEY_CODE_MASTER.DECODE
     */
    public void setDecode(String decode) {
        this.decode = decode;
    }
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column KEY_CODE_MASTER.DISPLAY
     *
     * @return the value of KEY_CODE_MASTER.DISPLAY
     */
    public String getDisplay() {
        return display;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column KEY_CODE_MASTER.DISPLAY
     *
     * @param display the value for KEY_CODE_MASTER.DISPLAY
     */
    public void setDisplay(String display) {
        this.display = display;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column KEY_CODE_MASTER.DEFAULT_IND
     *
     * @return the value of KEY_CODE_MASTER.DEFAULT_IND
     */
    public String getDefaultInd() {
        return defaultInd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column KEY_CODE_MASTER.DEFAULT_IND
     *
     * @param defaultInd the value for KEY_CODE_MASTER.DEFAULT_IND
     */
    public void setDefaultInd(String defaultInd) {
        this.defaultInd = defaultInd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column KEY_CODE_MASTER.EDITABLE
     *
     * @return the value of KEY_CODE_MASTER.EDITABLE
     */
    public String getEditableInd() {
        return editableInd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column KEY_CODE_MASTER.EDITABLE
     *
     * @param editableInd the value for KEY_CODE_MASTER.EDITABLE
     */
    public void setEditableInd(String editableInd) {
        this.editableInd = editableInd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column KEY_CODE_MASTER.KEY_DESC
     *
     * @return the value of KEY_CODE_MASTER.KEY_DESC
     */
    public String getKeyDesc() {
        return keyDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column KEY_CODE_MASTER.KEY_DESC
     *
     * @param keyDesc the value for KEY_CODE_MASTER.KEY_DESC
     */
    public void setKeyDesc(String keyDesc) {
        this.keyDesc = keyDesc;
    }
}

