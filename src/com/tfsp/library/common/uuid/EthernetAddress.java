package com.tfsp.library.common.uuid;

import java.io.Serializable;
import java.net.NetworkInterface;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.Random;

/**
 * 
 * 遵循IEEE 802.1标准的6-byte MAC地址.
 */
public class EthernetAddress implements Serializable, Cloneable, Comparable<EthernetAddress> {
	private static final long	serialVersionUID	= 1L;
	private final static char[]	HEX_CHARS			= "0123456789abcdefABCDEF".toCharArray();
	/** 当无法获取真实的网卡地址时, 使用随机算法创建虚拟的网络地址. */
	protected static Random		_rnd;
	/**
	 * 48-bit MAC 地址, stored in 6 lowest-significant bytes (in
	 * big endian notation)
	 */
	protected final long		_address;
	/**
	 * Lazily-constructed String serialization
	 */
	private volatile String		_asString;

	/**
	 * 使用标准的MAC地址字符串(like '00:C0:F0:3D:5B:7C')创建EthernetAddress对象.
	 * 注意字目不区分大小写. 首位如果为 0, 可以忽略, 比如:
	 * '00:C0:F0:3D:5B:7C' 与 '0:c0:f0:3d:5b:7c'是等价的.
	 * 
	 * @param addrStr
	 * @throws NumberFormatException
	 */
	public EthernetAddress(String addrStr) throws NumberFormatException {
		int len = addrStr.length();
		long addr = 0L;
		for(int i = 0, j = 0; j < 6; ++j){
			if(i >= len){
				// Is valid if this would have been the last byte:
				if(j == 5){
					addr <<= 8;
					break;
				}
				throw new NumberFormatException("不完整的以太网地址");
			}
			char c = addrStr.charAt(i);
			++i;
			int value;
			if(c == ':'){
				value = 0;
			}else{
				if(c >= '0' && c <= '9'){
					value = (c - '0');
				}else if(c >= 'a' && c <= 'f'){
					value = (c - 'a' + 10);
				}else if(c >= 'A' && c <= 'F'){
					value = (c - 'A' + 10);
				}else{
					throw new NumberFormatException("Non-hex character '" + c + "'");
				}
				if(i < len){
					c = addrStr.charAt(i);
					++i;
					if(c != ':'){
						value = (value << 4);
						if(c >= '0' && c <= '9'){
							value |= (c - '0');
						}else if(c >= 'a' && c <= 'f'){
							value |= (c - 'a' + 10);
						}else if(c >= 'A' && c <= 'F'){
							value |= (c - 'A' + 10);
						}else{
							throw new NumberFormatException("Non-hex character '" + c + "'");
						}
					}
				}
			}
			addr = (addr << 8) | value;
			if(c != ':'){
				if(i < len){
					if(addrStr.charAt(i) != ':'){
						throw new NumberFormatException("Expected ':', got ('" + addrStr.charAt(i) + "')");
					}
					++i;
				}else if(j < 5){
					throw new NumberFormatException("不完整的以太网地址");
				}
			}
		}
		_address = addr;
	}

	/**
	 * 使用二进制数组创建EthernetAddress
	 * 
	 * @param addr
	 * @throws NumberFormatException
	 */
	public EthernetAddress(byte[] addr) throws NumberFormatException {
		if(addr.length != 6){
			throw new NumberFormatException("Ethernet address has to consist of 6 bytes");
		}
		long l = addr[0] & 0xFF;
		for(int i = 1; i < 6; ++i){
			l = (l << 8) | (addr[i] & 0xFF);
		}
		_address = l;
	}

	/**
	 * Another binary constructor; constructs an instance from the given
	 * long argument; the lowest 6 bytes contain the address.
	 * 
	 * @param addr long that contains the MAC address in 6 least significant
	 * bytes.
	 */
	public EthernetAddress(long addr) {
		_address = addr;
	}

	/**
	 * Default cloning behaviour (bitwise copy) is just fine...
	 */
	public Object clone() {
		return new EthernetAddress(_address);
	}

	/**
	 * Constructs a new EthernetAddress given the byte array that contains
	 * binary representation of the address.
	 * 
	 * Note that calling this method returns the same result as would
	 * using the matching constructor.
	 * 
	 * @param addr Binary representation of the ethernet address
	 * 
	 * @throws NumberFormatException if addr is invalid (less or more than
	 * 6 bytes in array)
	 */
	public static EthernetAddress valueOf(byte[] addr) throws NumberFormatException {
		return new EthernetAddress(addr);
	}

	/**
	 * Constructs a new EthernetAddress given the byte array that contains
	 * binary representation of the address.
	 * 
	 * Note that calling this method returns the same result as would
	 * using the matching constructor.
	 * 
	 * @param addr Binary representation of the ethernet address
	 * 
	 * @throws NumberFormatException if addr is invalid (less or more than
	 * 6 ints in array)
	 */
	public static EthernetAddress valueOf(int[] addr) throws NumberFormatException {
		byte[] bAddr = new byte[addr.length];
		for(int i = 0; i < addr.length; ++i){
			bAddr[i] = (byte)addr[i];
		}
		return new EthernetAddress(bAddr);
	}

	/**
	 * 通过标准的MAC地址字符串创建EthernetAddress.
	 * 
	 * @param addrStr
	 * @return EthernetAddress
	 * @throws NumberFormatException
	 * <br>
	 * 创建日期: 2013-1-19 <br>
	 * 创 建 人: Shmilycharlene
	 */
	public static EthernetAddress valueOf(String addrStr) throws NumberFormatException{
		return new EthernetAddress(addrStr);
	}

	/**
	 * 通过给定的长整形(64-bit)数字创建EthernetAddress
	 * 
	 * @param addr
	 * @return ethernetAddress <br>
	 * 创建日期: 2013-1-19 <br>
	 * 创 建 人: Shmilycharlene
	 */
	public static EthernetAddress valueOf(long addr) {
		return new EthernetAddress(addr);
	}

	/**
	 * 获取本机物理以太网地址的工厂方法. 如果获取失败, 返回null值.
	 * 
	 * @return ethernetAddress <br>
	 * 创建日期: 2013-1-19 <br>
	 * 创 建 人: Shmilycharlene
	 */
	public static EthernetAddress fromInterface(){
		try{
			Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
			while(en.hasMoreElements()){
				NetworkInterface nint = en.nextElement();
				if(!nint.isLoopback()){
					byte[] data = nint.getHardwareAddress();
					if(data != null && data.length == 6){
						return new EthernetAddress(data);
					}
				}
			}
		}catch (java.net.SocketException e){
		}
		return null;
	}

	/**
	 * Factory method that can be used to construct a random multicast
	 * address; to be used in cases where there is no "real" ethernet
	 * address to use. Address to generate should be a multicase address
	 * to avoid accidental collision with real manufacturer-assigned
	 * MAC addresses.
	 * <p>
	 * Internally a {@link SecureRandom} instance is used for generating random number to base address on.
	 */
	public static EthernetAddress constructMulticastAddress(){
		return constructMulticastAddress(_randomNumberGenerator());
	}

	/**
	 * Factory method that can be used to construct a random multicast
	 * address; to be used in cases where there is no "real" ethernet
	 * address to use. Address to generate should be a multicase address
	 * to avoid accidental collision with real manufacturer-assigned
	 * MAC addresses.
	 * <p>
	 * Address is created using specified random number generator.
	 */
	public static EthernetAddress constructMulticastAddress(Random rnd){
		byte[] dummy = new byte[6];
		synchronized(rnd){
			rnd.nextBytes(dummy);
		}
		dummy[0] |= (byte)0x01;
		return new EthernetAddress(dummy);
	}

	/**
	 * Returns 6 byte byte array that contains the binary representation
	 * of this ethernet address; byte 0 is the most significant byte
	 * (and so forth)
	 * 
	 * @return 6 byte byte array that contains the binary representation
	 */
	public byte[] asByteArray(){
		byte[] result = new byte[6];
		toByteArray(result);
		return result;
	}

	/**
	 * Synonym to 'asByteArray()'
	 * 
	 * @return 6 byte byte array that contains the binary representation
	 */
	public byte[] toByteArray(){
		return asByteArray();
	}

	public void toByteArray(byte[] array){
		if(array.length < 6){
			throw new IllegalArgumentException("Too small array, need to have space for 6 bytes");
		}
		toByteArray(array, 0);
	}

	public void toByteArray(byte[] array, int pos){
		if(pos < 0 || (pos + 6) > array.length){
			throw new IllegalArgumentException("Illegal offset (" + pos + "), need room for 6 bytes");
		}
		int i = (int)(_address >> 32);
		array[pos++] = (byte)(i >> 8);
		array[pos++] = (byte)i;
		i = (int)_address;
		array[pos++] = (byte)(i >> 24);
		array[pos++] = (byte)(i >> 16);
		array[pos++] = (byte)(i >> 8);
		array[pos] = (byte)i;
	}

	public long toLong(){
		return _address;
	}

	/**
	 * Method that can be used to check if this address refers
	 * to a multicast address.
	 * Such addresses are never assigned to individual network
	 * cards.
	 */
	public boolean isMulticastAddress(){
		return (((int)(_address >> 40)) & 0x01) != 0;
	}

	/**
	 * Method that can be used to check if this address refers
	 * to a "locally administered address"
	 * (see [http://en.wikipedia.org/wiki/MAC_address] for details).
	 * Such addresses are not assigned to individual network
	 * cards.
	 */
	public boolean isLocallyAdministeredAddress(){
		return (((int)(_address >> 40)) & 0x02) != 0;
	}

	@Override
	public boolean equals(Object o){
		if(o == this)
			return true;
		if(o == null)
			return false;
		if(o.getClass() != getClass())
			return false;
		return ((EthernetAddress)o)._address == _address;
	}

	/**
	 * Method that compares this EthernetAddress to one passed in as
	 * argument. Comparison is done simply by comparing individual
	 * address bytes in the order.
	 * 
	 * @return negative number if this EthernetAddress should be sorted before the
	 * parameter address if they are equal, os positive non-zero number if this address
	 * should be sorted after parameter
	 */
	public int compareTo(EthernetAddress other){
		long l = _address - other._address;
		if(l < 0L)
			return -1;
		return (l == 0L) ? 0 : 1;
	}

	/**
	 * Returns the canonical string representation of this ethernet address.
	 * Canonical means that all characters are lower-case and string length
	 * is always 17 characters (ie. leading zeroes are not omitted).
	 * 
	 * @return Canonical string representation of this ethernet address.
	 */
	@Override
	public String toString(){
		String str = _asString;
		if(str != null){
			return str;
		}
		StringBuilder b = new StringBuilder(17);
		int i1 = (int)(_address >> 32);
		int i2 = (int)_address;
		_appendHex(b, i1 >> 8);
		b.append(':');
		_appendHex(b, i1);
		b.append(':');
		_appendHex(b, i2 >> 24);
		b.append(':');
		_appendHex(b, i2 >> 16);
		b.append(':');
		_appendHex(b, i2 >> 8);
		b.append(':');
		_appendHex(b, i2);
		_asString = str = b.toString();
		return str;
	}

	/**
	 * 创建随机数生成器的方法.
	 * 
	 * @return random <br>
	 * 创建日期: 2013-1-19 <br>
	 * 创 建 人: Shmilycharlene
	 */
	protected synchronized static Random _randomNumberGenerator(){
		if(_rnd == null){
			_rnd = new SecureRandom();
		}
		return _rnd;
	}

	private final void _appendHex(StringBuilder sb, int hex){
		sb.append(HEX_CHARS[(hex >> 4) & 0xF]);
		sb.append(HEX_CHARS[(hex & 0x0f)]);
	}
}
