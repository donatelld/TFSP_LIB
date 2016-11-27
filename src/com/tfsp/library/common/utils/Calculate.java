package com.tfsp.library.common.utils;

import java.math.BigDecimal;

/**
 * 数字计算工具
 * @author Shmilycharlene
 * @version 1.0
 */
public class Calculate{
	
	private BigDecimal bd;
	
	private Calculate(BigDecimal bd) {
		this.bd = bd;
	}

	public static Calculate create(Double d){
		if(d == null) d = 0d;
		return new Calculate(new BigDecimal(Double.toString(d)));
	}
	public static Calculate create(Integer d){
		if(d == null) d = 0;
		return new Calculate(new BigDecimal(Double.toString(d)));
	}
	
	public static Calculate create(Long d){
		if(d == null) d = 0L;
		return new Calculate(new BigDecimal(Double.toString(d)));
	}
	
	public static Calculate create(String d){
		if(StringUtils.isBlank(d)) d = "0";
		return new Calculate(new BigDecimal(d));
	}
	public static Calculate create(BigDecimal d){
		if(d == null) d = new BigDecimal(0);
		return new Calculate(d);
	}
	
	/** 加 */
	public Calculate add(Long d) {
		if(d == null) return this;
		return this.add(Long.toString(d));
	}
	/** 加 */
	public Calculate add(Double d) {
		if(d == null) return this;
		return this.add(Double.toString(d));
	}
	/** 加 */
	public Calculate add(Integer d) {
		if(d == null) return this;
		return this.add(Integer.toString(d));
	}
	/** 加 */
	public Calculate add(String d) {
		if(StringUtils.isBlank(d)) return this;
		BigDecimal bd = new BigDecimal(d); 
		this.bd = this.bd.add(bd);
		return this;
	}
	/** 加 */
	public Calculate add(BigDecimal d) {
		if(d != null)
			this.bd = this.bd.add(d);
		return this;
	}
	/** 加 */
	public Calculate add(Calculate d) {
		if(d != null)
			this.bd = this.bd.add(d.bd);
		return this;
	}
	
	
	/** 减 */
	public Calculate sub(Long d) {
		return this.sub(Long.toString(d));
	}
	/** 减 */
	public Calculate sub(Double d) {
        return this.sub(Double.toString(d));
	}
	/** 减 */
	public Calculate sub(Integer d) {
		if(d == null) return this;
        return this.sub(Integer.toString(d));
	}
	/** 减 */
	public Calculate sub(String d) {
		if(StringUtils.isBlank(d)) return this;
        BigDecimal bd = new BigDecimal(d); 
        this.bd = this.bd.subtract(bd);
        return this;
	}
	/** 减 */
	public Calculate sub(BigDecimal d) {
		if(d != null)
			this.bd = this.bd.subtract(d);
		return this;
	}
	/** 减 */
	public Calculate sub(Calculate d) {
		if(d != null)
			this.bd = this.bd.subtract(d.bd);
		return this;
	}
	
	
	/** 乘 */
	public Calculate mul(Long d) {
		if(d == null) d = 0l;
		return this.mul(Long.toString(d));
	}
	/** 乘 */
	public Calculate mul(Double d) {
		if(d == null) d = 0d;
		return this.mul(Double.toString(d));
	}
	/** 乘 */
	public Calculate mul(Integer d) {
		if(d == null) d = 0;
		return this.mul(Integer.toString(d));
	}
	/** 乘 */
	public Calculate mul(String d) {
		if(StringUtils.isBlank(d)) d = "0";
		BigDecimal bd = new BigDecimal(d); 
		this.bd = this.bd.multiply(bd);
		return this;
	}
	/** 乘 */
	public Calculate mul(BigDecimal d) {
		if(d != null)
			this.bd = this.bd.multiply(d);
		return this;
	}
	/** 乘 */
	public Calculate mul(Calculate d) {
		if(d != null)
			this.bd = this.bd.multiply(d.bd);
		return this;
	}
	
	
	/** 除 */
	public Calculate div(Long d) {
		return div(d, 2); 
	}
	/** 除 */
	public Calculate div(Double d) {
        return div(d, 2); 
	}
	/** 除 */
	public Calculate div(Integer d) {
		return div(d, 2); 
	}
	/** 除 */
	public Calculate div(String d) {
		return div(d, 2); 
	}
	/** 除 */
	public Calculate div(Long d,int scale) {
		return this.div(d, scale, BigDecimal.ROUND_HALF_UP);
	}
	/** 除 */
	public Calculate div(Double d,int scale) {
		return this.div(d, scale, BigDecimal.ROUND_HALF_UP);
	}
	/** 除 */
	public Calculate div(Integer d,int scale) {
		return this.div(d, scale, BigDecimal.ROUND_HALF_UP);
	}
	/** 除 */
	public Calculate div(String d,int scale) {
		return this.div(d, scale, BigDecimal.ROUND_HALF_UP);
	}
	/** 除 */
	public Calculate div(Long d,int scale,int roundingMode) {
		if(d == null) d = 0l;
		return this.div(Long.toString(d), scale, roundingMode);
	}
	/** 除 */
	public Calculate div(Double d,int scale,int roundingMode) {
		if(d == null) d = 0d;
		return this.div(Double.toString(d), scale, roundingMode);
	}
	/** 除 */
	public Calculate div(Integer d,int scale,int roundingMode) {
		if(d == null) d = 0;
		return this.div(Integer.toString(d), scale, roundingMode);
	}
	/** 除 */
	public Calculate div(String d,int scale,int roundingMode) {
		if(StringUtils.isBlank(d) || StringUtils.equals(d, "0")) {
			this.bd = new BigDecimal(0);
			return this;
		}
		BigDecimal bd = new BigDecimal(d); 
		this.bd = this.bd.divide(bd,scale,roundingMode); 
		return this;
	}
	/** 除 */
	public Calculate div(BigDecimal d,int scale,int roundingMode) {
		if(bd != null)
		 this.bd = this.bd.divide(bd, scale, roundingMode); 
		return this;
	}
	/** 除 */
	public Calculate div(BigDecimal d) {
		return this.div(bd, 2);
	}
	/** 除 */
	public Calculate div(BigDecimal d,int scale) {
		return this.div(bd, 2, BigDecimal.ROUND_HALF_UP);
	}
	/** 除 */
	public Calculate div(Calculate d) {
		return this.div(d.bd(), 2);
	}
	
	public double lv(){
		return bd.longValue();
	}
	public double dv(){
		return bd.doubleValue();
	}
	public double lv(int scale){
		return bd.setScale(scale, BigDecimal.ROUND_HALF_UP).longValue();
	}
	public double dv(int scale){
		return bd.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	public int iv(){
		return bd.intValue();
	}
	public BigDecimal bd(){
		return bd;
	}

	@Override
	public String toString(){
		return bd.toString();
	}
}

