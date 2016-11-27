package com.tfsp.library.common.uuid;

/**
 * 基于时间的UUID生成器.
 * 
 * @author Shmilycharlene
 * @version 1.0
 */
public class TimeBasedGenerator{
	protected final EthernetAddress _ethernetAddress;
	/**
	 * Object used for synchronizing access to timestamps, to guarantee
	 * that timestamps produced by this generator are unique and monotonically increasings.
	 * Some implementations offer even stronger guarantees, for example that
	 * same guarantee holds between instances running on different JVMs (or
	 * with native code).
	 */
	protected final UUIDTimer _timer;
	/**
	 * Base values for the second long (last 8 bytes) of UUID to construct
	 */
	protected final long _uuidL2;

	/**
	 * @param ethAddr Hardware address (802.1) to use for generating
	 * spatially unique part of UUID. If system has more than one NIC,
	 */
	public TimeBasedGenerator(EthernetAddress ethAddr, UUIDTimer timer) {
		byte[] uuidBytes = new byte[16];
		if(ethAddr == null){
			ethAddr = EthernetAddress.constructMulticastAddress();
		}
		// initialize baseline with MAC address info
		_ethernetAddress = ethAddr;
		_ethernetAddress.toByteArray(uuidBytes, 10);
		// and add clock sequence
		int clockSeq = timer.getClockSequence();
		uuidBytes[UUIDUtil.BYTE_OFFSET_CLOCK_SEQUENCE] = (byte)(clockSeq >> 8);
		uuidBytes[UUIDUtil.BYTE_OFFSET_CLOCK_SEQUENCE + 1] = (byte)clockSeq;
		long l2 = UUIDUtil.gatherLong(uuidBytes, 8);
		_uuidL2 = UUIDUtil.initUUIDSecondLong(l2);
		_timer = timer;
	}

	public UUIDType getType(){
		return UUIDType.TIME_BASED;
	}

	public EthernetAddress getEthernetAddress(){
		return _ethernetAddress;
	}

	public UUID generate() {
		final long rawTimestamp = _timer.getTimestamp();
		// Time field components are kind of shuffled, need to slice:
		int clockHi = (int)(rawTimestamp >>> 32);
		int clockLo = (int)rawTimestamp;
		// and dice
		int midhi = (clockHi << 16) | (clockHi >>> 16);
		// need to squeeze in type (4 MSBs in byte 6, clock hi)
		midhi &= ~0xF000; // remove high nibble of 6th byte
		midhi |= 0x1000; // type 1
		long midhiL = (long)midhi;
		midhiL = ((midhiL << 32) >>> 32); // to get rid of sign extension
		// and reconstruct
		long l1 = (((long)clockLo) << 32) | midhiL;
		// last detail: must force 2 MSB to be '10'
		return new UUID(l1, _uuidL2);
	}
}
