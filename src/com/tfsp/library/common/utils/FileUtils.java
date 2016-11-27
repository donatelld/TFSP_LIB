package com.tfsp.library.common.utils;

import com.tfsp.library.common.logging.Logger;
import com.tfsp.library.common.logging.LoggerFactory;

public class FileUtils extends org.apache.commons.io.FileUtils{
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	public static String convertFileSize(long size) {
        if (size >= FileUtils.ONE_GB) {
            return String.format("%.1f GB", (float) size / FileUtils.ONE_GB);
        } else if (size >= FileUtils.ONE_MB) {
            float f = (float) size / FileUtils.ONE_MB;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= FileUtils.ONE_KB) {
            float f = (float) size / FileUtils.ONE_KB;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else{
        	return String.format("%d B", size);
        }
    }
}


