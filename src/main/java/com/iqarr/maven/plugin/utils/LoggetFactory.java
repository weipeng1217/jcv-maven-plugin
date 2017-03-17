package com.iqarr.maven.plugin.utils;

import org.apache.maven.plugin.logging.Log;

/**
 * @Package log工厂
 *          com.iqarr.maven.plugin.utils
 * @ClassName:
 *             LoggetFactory
 * @since
 *        V1.0
 * @author
 *         zhangyong
 * @date
 *       2017/03/16-16:17:58
 * @version
 *          V1.0
 */
public  class LoggetFactory {
	
	/**
	 * log
	 */
	private static Log log = null;;
	
	public static boolean isDebugEnabled() {
		if (log == null) {
			return true;
		}
		else {
			return log.isDebugEnabled ();
		}
	}
	
	public static void debug(CharSequence content) {
		if (log == null) {
			displayLog (content,null);
		}
		else {
			log.debug (content);
		}
	}
	
	public static void debug(CharSequence content, Throwable error) {
		if (log == null) {
			displayLog (content,error);
		}
		else {
			log.debug (content,error);
		}
	}
	
	public static void debug(Throwable error) {
		if (log == null) {
			displayLog (null,error);
		}
		else {
			log.debug (error);
		}
	}
	
	public static boolean isInfoEnabled() {
		if (log == null) {
			return true;
		}
		else {
			return log.isInfoEnabled ();
		}
	}
	
	public static void info(CharSequence content) {
		if (log == null) {
			displayLog (content,null);
		}
		else {
			log.info (content);
		}
	}
	
	public static void info(CharSequence content, Throwable error) {
		if (log == null) {
			displayLog (content,error);
		}
		else {
			log.info (content,error);
		}
	}
	
	public static void info(Throwable error) {
		if (log == null) {
			displayLog (null,error);
		}
		else {
			log.info (error);
		}
	}
	
	public static boolean isWarnEnabled() {
		if (log == null) {
			return true;
		}
		else {
			return log.isWarnEnabled ();
		}
	}
	
	public static void warn(CharSequence content) {
		if (log == null) {
			displayLog (content,null);
		}
		else {
			log.warn (content);
		}
	}
	
	public static void warn(CharSequence content, Throwable error) {
		if (log == null) {
			displayLog (content,error);
		}
		else {
			log.warn (content,error);
		}
	}
	
	public static void warn(Throwable error) {
		if (log == null) {
			displayLog (null,error);
		}
		else {
			log.warn (error);
		}
	}
	
	public static boolean isErrorEnabled() {
		if (log == null) {
			return true;
		}
		else {
			return log.isErrorEnabled ();
		}
	}
	
	public static void error(CharSequence content) {
		if (log == null) {
			displayLog (content,null);
		}
		else {
			log.error (content);
		}
	}
	
	public static void error(CharSequence content, Throwable error) {
		if (log == null) {
			displayLog (content,error);
		}
		else {
			log.error (content,error);
		}
	}
	
	public static void error(Throwable error) {
		if (log == null) {
			displayLog (null,error);
		}
		else {
			log.error (error);
		}
	}
	
	
	
	/**
	 * 
	 * 打印默认日志
	 * 
	 * @param content
	 * @param error
	 */
	private static void displayLog(CharSequence content, Throwable error) {
		
		System.out.println ("content:"+content+" error:"+error);
	}
	/**
	 * 
	 * 设置日志
	 * @param log
	 */
	public static void setLogger(Log log){
		LoggetFactory.log=log;
	}
}
