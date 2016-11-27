package com.tfsp.library.common.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.junit.Test;

import com.tfsp.library.common.logging.Logger;
import com.tfsp.library.common.logging.LoggerFactory;

/**
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.common.utils.PackageScan.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2013-5-21			Shmilycharlene				初始化版本	
 * 
 */
public class PackageScan{
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 从给定的报名中加载Class对象.
	 * @param packageName
	 * @return List<Class<?>>
	 * @throws IOException
	 * <br>
	 * 创建日期: 2013-5-22 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public List<Class<?>> packageScan(String packageName, boolean includeInnerClass, boolean includeSubPackage) throws IOException{
		List<Class<?>> classList = new ArrayList<Class<?>>();
		packageName = packageName.replace(".", "/");
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Enumeration<URL> dir = classLoader.getResources(packageName);
		while(dir.hasMoreElements()){
			URL url = (URL)dir.nextElement();
			if("file".equals(url.getProtocol())){//从文件系统中扫描.
				File packageFile = null;
				if(url.getFile().indexOf("%") > 0){
					packageFile = new File(URLDecoder.decode(url.getFile(), "UTF-8"));
				} else {
					packageFile = new File(url.getFile());
				}
				File[] files = packageFile.listFiles(new FileFilter(){
					@Override
					public boolean accept(File pathname){
						return pathname.isDirectory() || pathname.getAbsolutePath().toLowerCase().endsWith(".class");
					}
				});
				for(File file : files){
					classList.addAll(loadClassesFromFile(file, packageName.replace("/", "."), includeInnerClass, includeSubPackage));
				}
				
			} else if ("jar".equals(url.getProtocol())) {//从Jar包中扫描
				String jarFile = getNativeJarFile(url);
				logger.debug("load class from jar file: {0}", jarFile);
				JarFile jar = new JarFile(jarFile);
				Enumeration<JarEntry> enumeration = jar.entries();
				while(enumeration.hasMoreElements()){
					JarEntry entry = enumeration.nextElement();
					try{
						Class<?> clazz = loadClassFromJar(entry, packageName, includeInnerClass, includeSubPackage);
						if(clazz != null){
							classList.add(clazz);
						}
					}catch (ClassNotFoundException e){
						logger.error("Exception: {0}", e);
						continue;
					}
				}
			}
		}
		return classList;
	}
	
	
	
	/**
	 * 从包路径下的文件系统中加载Class对象.
	 * @param classFile
	 * @param targetPackage
	 * @return
	 * @throws ClassNotFoundException
	 * <br>
	 * 创建日期: 2013-5-21 <br>
	 * 创  建  人: Shmilycharlene
	 */
	private Class<?> loadClassFromFile(File classFile, String targetPackage, boolean includeInnerClass) throws ClassNotFoundException{
		String fileName = classFile.getAbsolutePath().replace(File.separator, ".");
		logger.debug("loadClassFromFile====fileName:{0}", fileName);
		if(fileName.indexOf("$") <= -1 || includeInnerClass){
			int start = fileName.indexOf(targetPackage);
			int end   = fileName.toLowerCase().lastIndexOf(".class");
			String className = fileName.substring(start,end);
			return Class.forName(className);
		} else {
			return null;
		}
	}
	
	/**
	 * 从文件系统中加载Class对象
	 * @param directoryFile
	 * @param targetPackage
	 * @return
	 * <br>
	 * 创建日期: 2013-5-21 <br>
	 * 创  建  人: Shmilycharlene
	 */
	private List<Class<?>> loadClassesFromFile(File directoryFile, String targetPackage, boolean includeInnerClass, boolean includeSubPackage){
		List<Class<?>> classList = new ArrayList<Class<?>>();
		if(directoryFile.isDirectory() && includeSubPackage){
			File[] classFiles = directoryFile.listFiles(new FileFilter(){
				@Override
				public boolean accept(File pathname){
					return pathname.isDirectory() || pathname.getAbsolutePath().toLowerCase().endsWith(".class");
				}
			});
			for(File classFile : classFiles){
				if(classFile.isFile()){
					try{
						Class<?> clazz = loadClassFromFile(classFile, targetPackage, includeInnerClass);
						if(clazz != null && (!clazz.isInterface())){
							classList.add(clazz);
						}
					}catch (ClassNotFoundException e){
						logger.error("Exception: {0}", e);
						continue;
					}
				} else if (classFile.isDirectory()) {
					classList.addAll(loadClassesFromFile(classFile, targetPackage, includeInnerClass, includeSubPackage));
				}
			}
		} else if(directoryFile.isFile()){
			try{
				Class<?> clazz = loadClassFromFile(directoryFile, targetPackage, includeInnerClass);
				if(clazz != null){
					classList.add(clazz);
				}
			}catch (ClassNotFoundException e){
				logger.error("Exception: {0}", e);
			}
		}
		return classList;
	}
	
	/**
	 * 从jar:file:/协议中的URL地址获取URL代表的文件的具体实例.
	 * @param url
	 * @return
	 * @throws MalformedURLException
	 * <br>
	 * 创建日期: 2013-10-29 <br>
	 * 创  建  人: Shmilycharlene
	 */
	private String getNativeJarFile(URL url) throws MalformedURLException{
		String urlFile = url.getFile();
		URL fileURL = null;
		if(urlFile.indexOf("!/") > 0){
			fileURL = new URL(urlFile.substring(0, urlFile.indexOf("!/")));
		} else {
			fileURL = new URL(urlFile.substring(0, urlFile.length()));
		}
		urlFile = fileURL.getFile().replace("%20", " ");
		return urlFile;
	}
	
	private Class<?> loadClassFromJar(JarEntry jarEntry, String targetPackage, boolean includeInnerClass, boolean includeSubPackage) throws ClassNotFoundException{
		String entryName = jarEntry.getName();
		logger.debug("loadClass from jar file ==== class:{0}", entryName.replace("/", "."));
		if(entryName.startsWith(targetPackage) && entryName.toLowerCase().endsWith(".class")){
			if(entryName.indexOf("$") <= -1 || includeInnerClass){
				boolean isSubPackage = false;
				if(entryName.indexOf("/", targetPackage.length() + 1) > 0){
					isSubPackage = true;
				}
				
				if(includeSubPackage && isSubPackage){
					entryName = entryName.replace("/", ".");
					int end   = entryName.toLowerCase().lastIndexOf(".class");
					String className = entryName.substring(0,end);
					return Class.forName(className);
				} else if (!isSubPackage) {
					entryName = entryName.replace("/", ".");
					int end   = entryName.toLowerCase().lastIndexOf(".class");
					String className = entryName.substring(0,end);
					return Class.forName(className);
				}
			}
		}
		return null;
	}
	
	@Test
	public void test(){
//		String test = "org/apache/commons/logging/impl/AvalonLogger.class";
//		System.err.println(test.indexOf("/", "org/apache/commons/logging/impl".length()+1));
		
		try{
			List<Class<?>> classList = packageScan("org.apache.commons.logging", false, false);
//			List<Class<?>> classList = packageScan("com.tfsp.library.common.orm", true, true);
			for(Class<?> c : classList){
					System.err.println(c.getName());
			}
		}catch (IOException e){
			logger.error("Exception: {0}", e);
		}
	}
}

