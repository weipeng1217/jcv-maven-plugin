package com.iqarr.maven.plugin;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.iqarr.maven.plugin.domain.JCVConfig;
import com.iqarr.maven.plugin.domain.JCVMethodEnum;
import com.iqarr.maven.plugin.domain.YUIConfig;
import com.iqarr.maven.plugin.support.DefaultProcessFactory;
import com.iqarr.maven.plugin.support.ProcessFactory;
import com.iqarr.maven.plugin.utils.FileUtils;
import com.iqarr.maven.plugin.utils.LoggetFactory;

/**  
* @Package 
*	 com.iqarr.maven.plugin
* @ClassName: 
*	 JCMojo  
* @since 
*	  V1.0
* @author 
*		zhangyong   
* @date 
*		2017/01/06-10:34:34
* @version 
*		V1.0      
*/
@Mojo( name = "process" , defaultPhase = LifecyclePhase.PROCESS_RESOURCES, threadSafe = true)
public class JCVMojo extends AbstractMojo {
    
    
    
    /**
     * 输出文件目录
     */
    @Parameter( defaultValue = "${project.build.directory}", property = "outputDir", required = true )
    private File outputDirectory;
    
    /**
     * webapp目录
     */
    @Parameter( defaultValue = "${basedir}/src/main/webapp", property = "webappDirectory", required = true )
    private File webappDirectory;
    
    /**
     * 默认检查文件后缀
     */
    @Parameter(defaultValue ="jsp")
    private List<String> suffixs;
    
    /**
     * 基本的域名　script标签的src的前缀 
     * 该配置适合采用动静分离等方法，相对路径不需要配置
     */
    @Parameter
    private List<String> baseJsDomin;
    
    /**
     * 基本的域名　css 标签的link的前缀
     * 该配置适合采用动静分离等方法，相对路径不需要配置
     */
    @Parameter
    private List<String> baseCssDomin;
    
    /**
     * 全局js文件前缀  最中计算路径 baseCssDomin+globaJslPrefixPath+实际地址
     * 不配置该属性，就从根目录全部扫描
     */
    @Parameter(defaultValue ="")
    private String globaJslPrefixPath="";
    
    /**
     * 全局css文件前缀
     * 不配置该属性，就从根目录全部扫描
     */
    @Parameter(defaultValue ="")
    private String globaCsslPrefixPath="";
    
    /**
     * js 使用方法
     */
    @Parameter(defaultValue ="MD5_METHOD")
    private JCVMethodEnum globaJsMethod;
    
    /**
     * css 使用方法
     */
    @Parameter(defaultValue ="MD5_METHOD")
    private JCVMethodEnum globaCssMethod;
    
    
    /**根目录名称 **/
    @Parameter(defaultValue ="${project.build.finalName}")
    private String webRootName;
    
    /**版本号标签 **/
    @Parameter(defaultValue ="version")
    private String versionLable;
    /**文件编码 **/
    @Parameter(defaultValue ="UTF-8")
    private String sourceEncoding;
    /**清除页面注释 **/
    @Parameter(defaultValue ="false")
    private boolean clearPageComment;
    
    /** 使用md5文件名输出js css 指定目录**/
    @Parameter(defaultValue ="")
    private String outJSCSSDirPath;
    
    //version 0.0.2
    
    /**压缩css **/
    @Parameter(defaultValue ="false")
    private boolean compressionCss;
    
    /** 压缩js**/
    @Parameter(defaultValue ="false")
    private boolean compressionJs;
    
    /**压缩文件后缀 **/
    @Parameter(defaultValue ="min")
    private String userCompressionSuffix;
    
    /** 排除js文件(只支持全路径匹配)**/
    @Parameter
    private List<String> excludesJs;
    
    /** 排除css文件(只支持全路径匹配)**/
    @Parameter
    private List<String> excludesCss;
    
    /** yui config**/
    @Parameter
    private YUIConfig yuiConfig;
    
    /**跳过文件名后缀(后缀之前的名称) **/
    @Parameter(defaultValue =".min")
    private String skipFileNameSuffix;
    
    /*
    * <p>Title: execute</p>  
    * <p>Description: </p>  
    * @throws MojoExecutionException
    * @throws MojoFailureException  
    * @see org.apache.maven.plugin.Mojo#execute()  
    */
    
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
    	
    	LoggetFactory.setLogger (getLog ());
        

        String webRoot=webappDirectory.getPath();
        if(!webRoot.endsWith(FileUtils.getSystemLineSpearator())){
            webRoot+=FileUtils.getSystemFileSeparator();
        }
        
        String outJsCssRoot="";
        if(null!=outJSCSSDirPath &&!"".equals(outJSCSSDirPath) ){
        	outJsCssRoot=outJSCSSDirPath;
            
        }else {
        	outJsCssRoot=outputDirectory.getPath()+FileUtils.getSystemFileSeparator()+webRootName;
        }
        JCVConfig jcvConfig=new JCVConfig ();
        jcvConfig.setPageSuffixs (suffixs);
        jcvConfig.setJsMethod (globaJsMethod);
        jcvConfig.setCssMethod (globaCssMethod);
        jcvConfig.setVersionLable (versionLable);
        jcvConfig.setBaseCssDomin (baseCssDomin);
        jcvConfig.setBaseJsDomin (baseJsDomin);
        jcvConfig.setGlobaCsslPrefixPath (globaCsslPrefixPath);
        jcvConfig.setGlobaJslPrefixPath (globaJslPrefixPath);
        jcvConfig.setSourceEncoding (sourceEncoding);
        jcvConfig.setClearPageComment (clearPageComment);
        jcvConfig.setOutJSCSSDirPath (outJsCssRoot);
        jcvConfig.setCompressionCss (compressionCss);
        jcvConfig.setCompressionJs (compressionJs);
        jcvConfig.setUserCompressionSuffix (userCompressionSuffix);
        jcvConfig.setExcludesCss (excludesCss);
        jcvConfig.setExcludesJs (excludesJs);
        if(yuiConfig==null){
            yuiConfig=new YUIConfig();
        }
        jcvConfig.setYuiConfig (yuiConfig);
        jcvConfig.setSkipFileNameSuffix (skipFileNameSuffix);
        String out= outputDirectory.getPath()+FileUtils.getSystemFileSeparator()+webRootName;
        jcvConfig.setOutDirRoot (out);
       ProcessFactory processFactory=new DefaultProcessFactory(jcvConfig);
       processFactory.initDisplayInfo ();
       getLog().info("build webRootName:"+webRootName);
       //显示日志
        getLog().info("web app Dir:"+webappDirectory.getPath());
       processFactory.initJcv (webRoot);
       processFactory.doProcessPageFile ();
       processFactory.displaySuccessInfo ();
       
        
    }
    
  
    
}
