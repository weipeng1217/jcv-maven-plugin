package com.iqarr.maven.plugin.exception;

import com.iqarr.maven.plugin.utils.LoggetFactory;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

/**  
* @Package 
*	 com.iqarr.maven.plugin.exception
* @ClassName: 
*	 YUIException  
* @since 
*	  V1.0
* @author 
*		zhangyong   
* @date 
*		2017/02/05-11:36:45
* @version 
*		V1.0      
*/
public class YUIException implements ErrorReporter {
    
    
    private String                   fileName;
    /**  
     * <p>Title: </p>  
     * <p>Description: </p>  
     * @param log  
     */
     public YUIException(String fileName) {
         
         this.fileName=fileName;
     }
    
    
    /*
    * <p>Title: error</p>  
    * <p>Description: </p>  
    * @param arg0
    * @param arg1
    * @param arg2
    * @param arg3
    * @param arg4  
    * @see org.mozilla.javascript.ErrorReporter#error(java.lang.String, java.lang.String, int, java.lang.String, int)  
    */
    @Override
    public void error(String message, String sourceName,
                    int line, String lineSource, int lineOffset) {
        
        	LoggetFactory.error("\n[ERROR] in  "+fileName);
            if (line < 0) {
            	LoggetFactory.error("  " + message);
            } else {
            	LoggetFactory.error("  " + line + ':' + lineOffset + ':' + message);
            }
        
    }
    
    /*
    * <p>Title: runtimeError</p>  
    * <p>Description: </p>  
    * @param arg0
    * @param arg1
    * @param arg2
    * @param arg3
    * @param arg4
    * @return  
    * @see org.mozilla.javascript.ErrorReporter#runtimeError(java.lang.String, java.lang.String, int, java.lang.String, int)  
    */
    
    @Override
    public EvaluatorException runtimeError(String message, String sourceName,
                    int line, String lineSource, int lineOffset) {
        
        error(message, sourceName, line, lineSource, lineOffset);
        return new EvaluatorException(message);
    }
    
    /*
    * <p>Title: warning</p>  
    * <p>Description: </p>  
    * @param arg0
    * @param arg1
    * @param arg2
    * @param arg3
    * @param arg4  
    * @see org.mozilla.javascript.ErrorReporter#warning(java.lang.String, java.lang.String, int, java.lang.String, int)  
    */
    
    @Override
    public void warning(String message, String sourceName,
                    int line, String lineSource, int lineOffset) {
    		LoggetFactory.warn("\n[WARNING] in  "+fileName);
            if (line < 0) {
            	LoggetFactory.warn("  " + message);
            } else {
            	LoggetFactory.warn("  " + line + ':' + lineOffset + ':' + message);
            }
        
        
    }
    
}
