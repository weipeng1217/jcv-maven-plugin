package com.iqarr.maven.plugin.utils;

import org.junit.Test;

/**  
* @Package 
*	 com.iqarr.maven.plugin.utils
* @ClassName: 
*	 LoggetFactoryTest  
* @since 
*	  V1.0
* @author 
*		zhangyong   
* @date 
*		2017/03/17-11:52:43
* @version 
*		V1.0      
*/
public class LoggetFactoryTest {
	
	@Test
	public void test() {
		LoggetFactory.setPrintStrear (System.out);
		LoggetFactory.LOG_LEVEL=LoggetFactory.LOG_LEVEL_DEBUG;
		LoggetFactory.error ("this error info");
		LoggetFactory.debug ("this is dubug");
	}
	
}
