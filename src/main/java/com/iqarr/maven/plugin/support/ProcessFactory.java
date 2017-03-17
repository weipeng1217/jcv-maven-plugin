package com.iqarr.maven.plugin.support;

/**  
* @Package 处理工厂
*	 com.iqarr.maven.plugin.support
* @ClassName: 
*	 ProcessFactory  
* @since 
*	  V1.0
* @author 
*		zhangyong   
* @date 
*		2017/03/16-15:42:50
* @version 
*		V1.0      
*/
public interface ProcessFactory {
	
	/**
	 * 
	 * 初始化
	 * @param webAppRoot
	 */
	 public void initJcv(final String webAppRoot);
	
	  /**
	   * 
	   * 处理页面
	   * @param pages
	   */
	  public void doProcessPageFile();
	
}
