package com.iqarr.maven.plugin.utils;

import java.io.PrintStream;

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
public class LoggetFactory {
	
	public static final int LOG_LEVEL_DEBUG = 3;
	
	public static final int LOG_LEVEL_INFO = 2;
	
	public static final int LOG_LEVEL_WARN = 1;
	
	public static final int LOG_LEVEL_ERROR = 0;
	
	/**
	 * 日志等级
	 * 
	 */
	public static int LOG_LEVEL = LOG_LEVEL_INFO;
	
	/**
	 * log
	 */
	private static Log log = null;
	
	private static PrintStream printStream;
	
	public static boolean isDebugEnabled() {
		if (log == null) {
			return LOG_LEVEL >= LOG_LEVEL_DEBUG;
		}
		else {
			return log.isDebugEnabled ();
		}
	}
	
	public static void debug(CharSequence content) {
		if (log == null) {
			if (LOG_LEVEL >= LOG_LEVEL_DEBUG) {
				displayLog (content,null);
			}
		}
		else {
			log.debug (content);
		}
	}
	
	public static void debug(CharSequence content, Throwable error) {
		if (log == null) {
			if (LOG_LEVEL >= LOG_LEVEL_DEBUG) {
				displayLog (content,error);
			}
		}
		else {
			log.debug (content,error);
		}
	}
	
	public static void debug(Throwable error) {
		if (log == null) {
			if (LOG_LEVEL >= LOG_LEVEL_DEBUG) {
				displayLog (null,error);
			}
		}
		else {
			log.debug (error);
		}
	}
	
	public static boolean isInfoEnabled() {
		if (log == null) {
			return LOG_LEVEL >= LOG_LEVEL_INFO;
		}
		else {
			return log.isInfoEnabled ();
		}
	}
	
	public static void info(CharSequence content) {
		if (log == null) {
			if (LOG_LEVEL >= LOG_LEVEL_INFO) {
				displayLog (content,null);
			}
		}
		else {
			log.info (content);
		}
	}
	
	public static void info(CharSequence content, Throwable error) {
		if (log == null) {
			if (LOG_LEVEL >= LOG_LEVEL_INFO) {
				displayLog (content,error);
			}
		}
		else {
			log.info (content,error);
		}
	}
	
	public static void info(Throwable error) {
		if (log == null) {
			if (LOG_LEVEL >= LOG_LEVEL_INFO) {
				displayLog (null,error);
			}
		}
		else {
			log.info (error);
		}
	}
	
	public static boolean isWarnEnabled() {
		if (log == null) {
			return LOG_LEVEL >= LOG_LEVEL_WARN;
		}
		else {
			return log.isWarnEnabled ();
		}
	}
	
	public static void warn(CharSequence content) {
		if (log == null) {
			if (LOG_LEVEL >= LOG_LEVEL_WARN) {
				displayLog (content,null);
			}
		}
		else {
			log.warn (content);
		}
	}
	
	public static void warn(CharSequence content, Throwable error) {
		if (log == null) {
			if (LOG_LEVEL >= LOG_LEVEL_WARN) {
				displayLog (content,error);
			}
		}
		else {
			log.warn (content,error);
		}
	}
	
	public static void warn(Throwable error) {
		if (log == null) {
			if (LOG_LEVEL >= LOG_LEVEL_WARN) {
				displayLog (null,error);
			}
		}
		else {
			log.warn (error);
		}
	}
	
	public static boolean isErrorEnabled() {
		if (log == null) {
			return LOG_LEVEL >= LOG_LEVEL_ERROR;
		}
		else {
			return log.isErrorEnabled ();
		}
	}
	
	public static void error(CharSequence content) {
		if (log == null) {
			if (LOG_LEVEL >= LOG_LEVEL_ERROR) {
				displayLog (content,null);
			}
		}
		else {
			log.error (content);
		}
	}
	
	public static void error(CharSequence content, Throwable error) {
		if (log == null) {
			if (LOG_LEVEL >= LOG_LEVEL_ERROR) {
				displayLog (content,error);
			}
		}
		else {
			log.error (content,error);
		}
	}
	
	public static void error(Throwable error) {
		if (log == null) {
			if (LOG_LEVEL >= LOG_LEVEL_ERROR) {
				displayLog (null,error);
			}
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
		if (content != null) {
			printStream.append (content);
		}
		if (error != null) {
			printStream.append (" error->:");
			printStream.print (error);
		}
		printStream.println ();
		
	}
	
	/**
	 * 
	 * 设置日志
	 * 
	 * @param log
	 */
	public static void setLogger(Log log) {
		LoggetFactory.log = log;
	}
	
	/**
	 * 
	 * 设置输出
	 * 
	 * @param printStream
	 */
	public static void setPrintStrear(PrintStream printStream) {
		LoggetFactory.printStream = printStream;
	}
}
