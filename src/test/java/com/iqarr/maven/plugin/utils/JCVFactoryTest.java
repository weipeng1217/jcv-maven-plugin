package com.iqarr.maven.plugin.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.iqarr.maven.plugin.JCVFactory;
import com.iqarr.maven.plugin.domain.JCVFileInfo;
import com.iqarr.maven.plugin.domain.JCVMethodEnum;

/**  
* @Package 
*	 com.iqarr.maven.plugin.utils
* @ClassName: 
*	 JCVFactoryTest  
* @since 
*	  V1.0
* @author 
*		zhangyong   
* @date 
*		2017/01/07-13:56:29
* @version 
*		V1.0      
*/
@SuppressWarnings({"unused"})
public class JCVFactoryTest {
    
    
    
    @Test
    public void testprocessJSCSS(){
        String html="<link rel='stylesheet' type='text/css' href='http://style.test.com/css/public.css' />";
        String html2="<link rel=\"stylesheet2\" type=\"text/css2\" href=\"http://style.test2.com/css/public.css\" />";
       // StringBuffer sb=new StringBuffer(html+html2);
       // JCVFactory jc=new JCVFactory();
       // System.out.println(jc.processCSS(sb,0));
    }
    
    
    @Test
    public void testprocessCSS() throws IOException{
        String html="<link rel='stylesheet' type='text/css' href='http://style.test.com/css/public.css' />";
        //String html2="<link rel=\"stylesheet2\" type=\"text/css2\" href=\"http://style.test2.com/css/public.css\" />";
       // String html=FileUtils.readToString(new File("/home/user/d17-workspaces/eclipse-my/zy-storage/storage-center/src/main/webapp/index.html"), "utf-8")[0];
        StringBuffer sb=new StringBuffer(html);
        JCVFactory jc=new JCVFactory();
        List<String> baseCssDomin=new ArrayList<>();
        baseCssDomin.add("http://style.test.com");
        
        Map<String, JCVFileInfo> jcvs=new HashMap<>();
        
        JCVFileInfo ji=new JCVFileInfo();
        ji.setFileType(JCVFileInfo.CSS);
        ji.setFileVersion("1111111111");
        ji.setRelativelyFilePath("css/public.css");
        ji.setFileName("public.css");
        ji.setFileType(JCVFileInfo.CSS);
        jcvs.put("css/public.css", ji);
        
        jc.setBaseCssDomin(baseCssDomin);
        jc.setJcvs(jcvs);
        jc.setCssEn(JCVMethodEnum.MD5FileName_METHOD);
        jc.setVersionLable("version");
        
     //  System.out.println(jc.processCSS(sb,0));
        //ca(sb);
       // System.out.println(sb.toString());
    }
    
    
    @Test
    public void testprocessJS() throws Exception{
      //  String html="<link rel='stylesheet' type='text/css' href='http://style.test.com/css/public.css' />";
        //String html2="<link rel=\"stylesheet2\" type=\"text/css2\" href=\"http://style.test2.com/css/public.css\" />";
        String html=FileUtils.readToStr(new File(this.getClass().getClassLoader().getResource("test.html").getPath()) , "utf-8");
        
        char[] charArray = html.toCharArray();
        
        
        StringBuffer sb=new StringBuffer(html);
        JCVFactory jc=new JCVFactory();
        List<String> baseCssDomin=new ArrayList<>();
      //  baseCssDomin.add("http://style.test.com");
        
        Map<String, JCVFileInfo> jcvs=new HashMap<>();
        
        JCVFileInfo ji=new JCVFileInfo();
        ji.setFileType(JCVFileInfo.JS);
        ji.setFileVersion("1111111111");
        ji.setRelativelyFilePath("js/jquery/jquery-3.1.1.min.js");
        ji.setFileName("jquery-3.1.1.min.js");
        
        jcvs.put("js/jquery/jquery-3.1.1.min.js", ji);
        
        jc.setBaseCssDomin(baseCssDomin);
        jc.setJcvs(jcvs);
        jc.setJsEn(JCVMethodEnum.MD5FileName_METHOD);
        jc.setVersionLable("version");
        jc.setGlobaJslPrefixPath("");
        jc.setCompressionJs(true);
        jc.setUserCompressionSuffix("min");
       
    }
    
    
   
    
}
