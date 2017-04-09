package com.iqarr.maven.plugin.support.logger;

import org.apache.maven.plugin.logging.Log;

/**
 * @Package
 *          com.iqarr.maven.plugin.support.logger
 * @ClassName:
 *             MavenLoger
 * @since
 *        V1.0
 * @author
 *         zhangyong
 * @version
 *          V1.0
 */
public class MavenLoger implements Logger {
	
	private Log log;
	
	/*
	 * Title: isDebugEnabled
	 * Description:
	 * 
	 * @return
	 * 
	 * @see com.iqarr.maven.plugin.support.logger.Logger#isDebugEnabled()
	 */
	
	/**
	 * Title:
	 * Description:
	 * 
	 * @param log
	 */
	public MavenLoger(Log log) {
		super ();
		this.log = log;
	}
	
	@Override
	public boolean isDebugEnabled() {
		
		return log.isDebugEnabled ();
	}
	
	/*
	 * Title: debug
	 * Description:
	 * 
	 * @param content
	 * 
	 * @see com.iqarr.maven.plugin.support.logger.Logger#debug(java.lang.CharSequence)
	 */
	
	@Override
	public void debug(CharSequence content) {
		log.debug (content);
		
	}
	
	/*
	 * Title: debug
	 * Description:
	 * 
	 * @param content
	 * 
	 * @param error
	 * 
	 * @see com.iqarr.maven.plugin.support.logger.Logger#debug(java.lang.CharSequence, java.lang.Throwable)
	 */
	
	@Override
	public void debug(CharSequence content, Throwable error) {
		log.debug (content,error);
		
	}
	
	/*
	 * Title: debug
	 * Description:
	 * 
	 * @param error
	 * 
	 * @see com.iqarr.maven.plugin.support.logger.Logger#debug(java.lang.Throwable)
	 */
	
	@Override
	public void debug(Throwable error) {
		log.debug (error);
		
	}
	
	/*
	 * Title: isInfoEnabled
	 * Description:
	 * 
	 * @return
	 * 
	 * @see com.iqarr.maven.plugin.support.logger.Logger#isInfoEnabled()
	 */
	
	@Override
	public boolean isInfoEnabled() {
		
		return log.isInfoEnabled ();
	}
	
	/*
	 * Title: info
	 * Description:
	 * 
	 * @param content
	 * 
	 * @see com.iqarr.maven.plugin.support.logger.Logger#info(java.lang.CharSequence)
	 */
	
	@Override
	public void info(CharSequence content) {
		log.info (content);
		
	}
	
	/*
	 * Title: info
	 * Description:
	 * 
	 * @param content
	 * 
	 * @param error
	 * 
	 * @see com.iqarr.maven.plugin.support.logger.Logger#info(java.lang.CharSequence, java.lang.Throwable)
	 */
	
	@Override
	public void info(CharSequence content, Throwable error) {
		log.info (content,error);
		
	}
	
	/*
	 * Title: info
	 * Description:
	 * 
	 * @param error
	 * 
	 * @see com.iqarr.maven.plugin.support.logger.Logger#info(java.lang.Throwable)
	 */
	
	@Override
	public void info(Throwable error) {
		log.info (error);
		
	}
	
	/*
	 * Title: isWarnEnabled
	 * Description:
	 * 
	 * @return
	 * 
	 * @see com.iqarr.maven.plugin.support.logger.Logger#isWarnEnabled()
	 */
	
	@Override
	public boolean isWarnEnabled() {
		
		return log.isWarnEnabled ();
	}
	
	/*
	 * Title: warn
	 * Description:
	 * 
	 * @param content
	 * 
	 * @see com.iqarr.maven.plugin.support.logger.Logger#warn(java.lang.CharSequence)
	 */
	
	@Override
	public void warn(CharSequence content) {
		log.warn (content);
		
	}
	
	/*
	 * Title: warn
	 * Description:
	 * 
	 * @param content
	 * 
	 * @param error
	 * 
	 * @see com.iqarr.maven.plugin.support.logger.Logger#warn(java.lang.CharSequence, java.lang.Throwable)
	 */
	
	@Override
	public void warn(CharSequence content, Throwable error) {
		log.warn (content,error);
	}
	
	/*
	 * Title: warn
	 * Description:
	 * 
	 * @param error
	 * 
	 * @see com.iqarr.maven.plugin.support.logger.Logger#warn(java.lang.Throwable)
	 */
	
	@Override
	public void warn(Throwable error) {
		log.warn (error);
		
	}
	
	/*
	 * Title: isErrorEnabled
	 * Description:
	 * 
	 * @return
	 * 
	 * @see com.iqarr.maven.plugin.support.logger.Logger#isErrorEnabled()
	 */
	
	@Override
	public boolean isErrorEnabled() {
		
		return log.isErrorEnabled ();
	}
	
	/*
	 * Title: error
	 * Description:
	 * 
	 * @param content
	 * 
	 * @see com.iqarr.maven.plugin.support.logger.Logger#error(java.lang.CharSequence)
	 */
	
	@Override
	public void error(CharSequence content) {
		log.error (content);
		
	}
	
	/*
	 * Title: error
	 * Description:
	 * 
	 * @param content
	 * 
	 * @param error
	 * 
	 * @see com.iqarr.maven.plugin.support.logger.Logger#error(java.lang.CharSequence, java.lang.Throwable)
	 */
	
	@Override
	public void error(CharSequence content, Throwable error) {
		log.error (content,error);
		
	}
	
	/*
	 * Title: error
	 * Description:
	 * 
	 * @param error
	 * 
	 * @see com.iqarr.maven.plugin.support.logger.Logger#error(java.lang.Throwable)
	 */
	
	@Override
	public void error(Throwable error) {
		log.error (error);
	}
	
}
