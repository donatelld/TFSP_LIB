package com.tfsp.library.common.uuid;

import java.io.IOException;
import java.util.Random;

import com.tfsp.library.common.logging.Logger;
import com.tfsp.library.common.logging.LoggerFactory;

/**
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.common.uuid.UUIDGenerator.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2013-1-19			Shmilycharlene				初始化版本	
 * 
 */
public class UUIDGenerator{
	protected static final Logger logger = LoggerFactory.getLogger(UUIDGenerator.class);
	protected static UUIDTimer _sharedTimer;

	public static TimeBasedGenerator timeBasedGenerator(EthernetAddress ethernetAddress, TimestampSynchronizer sync){
		UUIDTimer timer;
		try{
			timer = new UUIDTimer(new Random(System.currentTimeMillis()), sync);
		}catch (IOException e){
			throw new IllegalArgumentException("Failed to create UUIDTimer with specified synchronizer: " + e.getMessage(), e);
		}
		return timeBasedGenerator(ethernetAddress, timer);
	}
    
    public static TimeBasedGenerator timeBasedGenerator(EthernetAddress ethernetAddress, UUIDTimer timer) {
        if (timer == null) {
            timer = sharedTimer();
        }
        return new TimeBasedGenerator(ethernetAddress, timer);
    }
    
    public static TimeBasedGenerator timeBasedGenerator(EthernetAddress ethernetAddress) {
        return timeBasedGenerator(ethernetAddress, (UUIDTimer) null);
    }
    
	public static TimeBasedGenerator timeBasedGenerator(){
		EthernetAddress eth = EthernetAddress.fromInterface();
		return timeBasedGenerator(eth, sharedTimer());
	}

	private static synchronized UUIDTimer sharedTimer() {
		if(_sharedTimer == null){
			try{
				_sharedTimer = new UUIDTimer(new java.util.Random(System.currentTimeMillis()), null);
			}catch (IOException e){
				throw new IllegalArgumentException("Failed to create UUIDTimer with specified synchronizer: " + e.getMessage(), e);
			}
		}
		return _sharedTimer;
	}
}

