package com.iqarr.maven.plugin.support;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.iqarr.maven.plugin.constant.JCVConstant;
import com.iqarr.maven.plugin.domain.JCVConfig;
import com.iqarr.maven.plugin.domain.JCVFileInfo;
import com.iqarr.maven.plugin.domain.JCVMethodEnum;
import com.iqarr.maven.plugin.domain.PageInfo;
import com.iqarr.maven.plugin.support.logger.LoggerFactory;
import com.iqarr.maven.plugin.utils.BaseUtils;
import com.iqarr.maven.plugin.utils.FileUtils;
import com.iqarr.maven.plugin.utils.Md5Utils;

/**
 * @Package
 *          com.iqarr.maven.plugin.support
 * @ClassName:
 *             AbstractProcessFactory
 * @since
 *        V1.0
 * @author
 *         zhangyong
 * @date
 *       2017/03/16-15:47:04
 * @version
 *          V1.0
 */
public abstract class AbstractProcessFactory implements ProcessFactory {
	
	protected final static String HTML_URL_SEPARATOR = "/";
	
	protected final static String HTML_JAVASCRIPT_LABLE_START = "<script";
	protected final static String HTML_JAVASCRIPT_SRC = "src=";
	protected final static String HTML_JAVASCRIPT_END = ">";
	
	// css
	protected final static String HTML_CSS_LABLE_START = "<link";
	protected final static String HTML_CSS_LABLE_SRC = "href=";
	protected final static String HTML_CSS_LABLE_END = ">";
	
	// comment
	protected final static String HTML_COMMENT_LABLE_START = "<!--";
	protected final static String HTML_COMMENT_LABLE_END = "-->";
	
	private final static String DISPLAY_STR="--------------------------------- ";
	
	private long  timeStart;
	
	/**
	 * 配置信息
	 */
	protected JCVConfig jCVConfig;
	
	/**
     * 所有的js css文件信息　link:fileInfo
     */
	protected Map<String, JCVFileInfo> jcvFiles;
	
	/**
	 * 所有的页面
	 */
	protected List<PageInfo> pages;
	
	private List<String> displayInfo=new ArrayList<String>();
	
	
	
	
	public AbstractProcessFactory( JCVConfig jCVConfig){
		this.jCVConfig=jCVConfig;
	}
	
	
	 /*
	* Title: initDisplayInfo  
	* Description:     
	* @see com.iqarr.maven.plugin.support.ProcessFactory#initDisplayInfo()  
	*/
	@Override
	public void initDisplayInfo() {
		  LoggerFactory.info("=================================JCV====================================");
		  LoggerFactory.info("      _  _______      __ ");
		  LoggerFactory.info("     | |/ ____\\ \\    / / ");
		  LoggerFactory.info("     | | |     \\ \\  / /  ");
		  LoggerFactory.info(" _   | | |      \\ \\/ /   ");
		  LoggerFactory.info("| |__| | |____   \\  /    ");
		  LoggerFactory.info(" \\____/ \\_____|   \\/    ");
		  LoggerFactory.info("                         ");
		  LoggerFactory.info("                         ");
	        
		  LoggerFactory.info("find suffixs size:"+jCVConfig.getPageSuffixs ().size ());
		 // LoggetFactory.info("build webRootName:"+jCVConfig.getOutDirRoot ());
		  LoggerFactory.info("build sourceEncoding:"+jCVConfig.getSourceEncoding ());
	        timeStart=new Date ().getTime ();
	        
	      //显示日志
	        //LoggetFactory.info("web app Dir:"+webappDirectory.getPath());
	        LoggerFactory.info("out Dir:"+jCVConfig.getOutDirRoot ());
	        LoggerFactory.info("system is linux:"+FileUtils.getSystemFileSeparatorIslinux());
	        LoggerFactory.info("css method is :"+jCVConfig.getCssMethod ());
	        LoggerFactory.info("js method is :"+jCVConfig.getJsMethod ());
	}
	
	
   /*
	* <p>Title: initJcv</p>  
	* <p>Description: 初始化</p>  
	* @param webAppRoot  
	* @see com.iqarr.maven.plugin.support.ProcessFactory#initJcv(java.lang.String)  
	*/
	
	@Override
	public void initJcv(String webAppRoot) {
		if(jcvFiles==null){
			jcvFiles=new HashMap<String, JCVFileInfo> ();
		}
        getAllCssFile(jcvFiles,webAppRoot,jCVConfig);
        getAllJsFile(jcvFiles,webAppRoot,jCVConfig);
        
        for(Entry<String, JCVFileInfo> f:jcvFiles.entrySet()){
            LoggerFactory.debug("find type:"+f.getValue().getFileType()+" file:"+f.getKey() + " md5:"+f.getValue().getFileVersion()); 
        }
        if(pages==null){
        	pages=new ArrayList<PageInfo> ();
        }
        getAllProcessFile(pages,webAppRoot,jCVConfig.getPageSuffixs ());
        //webRootName
        String out= jCVConfig.getOutDirRoot ();
        for(int i=0;i<pages.size();i++){
            
           String path = pages.get(i).getFile().getPath();
           //path= path.replaceAll(webRoot, "");
           path=path.substring(webAppRoot.length(), path.length());
           String tm="";
           if(path.endsWith(FileUtils.getSystemFileSeparator())){
              tm=out+path;
           }else {
               tm=out+FileUtils.getSystemFileSeparator()+path;
           }
          int lastIndexOf = tm.lastIndexOf(FileUtils.getSystemFileSeparator());
          String sub = tm.substring(0, lastIndexOf);
          File f=new File(sub);
          if(!f.exists()){
              f.mkdirs();
          }
            pages.get(i).setOutFile(new File(tm));
        }
	}
	
	 
	/*
	 * <p>Title: doProcessPageFile</p>
	 * <p>Description: </p>
	 * 
	 * @param pages
	 * 
	 * @see com.iqarr.maven.plugin.support.ProcessFactory#doProcessPageFile(java.util.List)
	 */
	
	@Override
	public void doProcessPageFile() {
		
		if(null==jCVConfig){
			LoggerFactory.error ("JcvConfig is null");
		}
		// 处理成功的js css文件
		 List<JCVFileInfo>  processSuccessFiles=new ArrayList<JCVFileInfo> ();
		for (PageInfo pageInfo : pages) {
			LoggerFactory.debug ("find page:" + pageInfo.getFile ().getPath ());
			
			try {
				String strAll = FileUtils.readToStr (pageInfo.getFile (),jCVConfig.getSourceEncoding ());
				List<String> savehtml = new ArrayList<String> ();
				if (strAll == null || strAll.length () == 0) {
					continue;
				}
				StringBuffer sb = null;
				if (sb == null) {
					sb = new StringBuffer (strAll);
				}
				int ret = processCSSVersion (sb,0,processSuccessFiles,jCVConfig);
				int ret2 = processJSVersion (sb,0,processSuccessFiles,jCVConfig);
				int ret3 = 0;
				if (jCVConfig.isClearPageComment ()) {
					ret3 = processPageComment (sb,0,jCVConfig);
					FileUtils.clearBlankLines (sb,jCVConfig.getSourceEncoding ());
				}
				if (ret == 1 || ret2 == 1 || ret3 == 1) {
					savehtml.add (sb.toString ());
					sb = null;
					
				}
				else {
					savehtml.add (sb.toString ());
					sb = null;
					
				}
				
				LoggerFactory.debug (" page:" + pageInfo.getFile ().getName () + " Processing is complete");
				FileUtils.writeFile (pageInfo.getOutFile (),jCVConfig.getSourceEncoding (),savehtml);
				
				
				 
			}
			catch (Exception e) {
				LoggerFactory.error (" the file process error :" + pageInfo.getFile ().getPath (),e);
			}
		}//for end
		
		//复制md5文件
		processMd5FileCpoy(processSuccessFiles);
		
		//开始压缩
		 if (jCVConfig.isCompressionCss () == true || jCVConfig.isCompressionJs () == true) {
	          
			 processCompressionJsCss(processSuccessFiles,jCVConfig.getOutJSCSSDirPath (),jCVConfig);
	     }
		 //处理未使用文件
		 doCopyUntreatedFile(processSuccessFiles);
		 displayInfo.add (DISPLAY_STR+"process success file size: "+processSuccessFiles.size ());
	}
	
	
	@Override
	public void displaySuccessInfo(){
		for(String info:displayInfo){
			LoggerFactory.info (info);
		}
		
		LoggerFactory.info("===============  Total time ["+(new Date().getTime()-timeStart)+" millisecond]===========================");
		LoggerFactory.info("========================================================================");
		
	}
	
	/**
	 * 
	 * 复制MD5FileName_METHOD　文件
	 * @param processSuccessFiles
	 */
	private void processMd5FileCpoy( List<JCVFileInfo>  processSuccessFiles){
		
		if(jCVConfig.isCompressionCss () ==false && jCVConfig.isCompressionJs ()==false){
			return;
		}
		
		//复制MD5FileName_METHOD　文件
        if (jCVConfig.getCssMethod () == JCVMethodEnum.MD5FileName_METHOD || jCVConfig.getJsMethod () == JCVMethodEnum.MD5FileName_METHOD) {
           
            for (JCVFileInfo info : processSuccessFiles) {
                if(jCVConfig.getCssMethod () == JCVMethodEnum.MD5FileName_METHOD && JCVFileInfo.CSS.equals(info.getFileType()) && jCVConfig.isCompressionCss ()==false){
                    copyMd5FileNameJssCss(info,jCVConfig.getOutJSCSSDirPath ());
                }
                if( jCVConfig.getJsMethod ()  == JCVMethodEnum.MD5FileName_METHOD && JCVFileInfo.JS.equals(info.getFileType()) && jCVConfig.isCompressionJs ()==false ){
                    copyMd5FileNameJssCss(info,jCVConfig.getOutJSCSSDirPath ());
                }
            }
        }
        
        
     
		
		
	}
	/**
	 * 
	 * 复制未处理文件
	 * @param processSuccessFiles
	 */
	private void  doCopyUntreatedFile(List<JCVFileInfo>  processSuccessFiles){
		 //复制未处理文件 　
        if (jCVConfig.isCompressionJs () == true || jCVConfig.isCompressionCss () == true ||
        				jCVConfig.getCssMethod () == JCVMethodEnum.MD5FileName_METHOD || jCVConfig.getJsMethod ()==JCVMethodEnum.MD5FileName_METHOD ) {
            
           final int size=processSuccessFiles.size ();
            List<JCVFileInfo> copyFiles = new ArrayList<JCVFileInfo>(size);
            Map<JCVFileInfo, String> processFilesMap = new HashMap<JCVFileInfo, String>( (int)((float)size/0.75F +1.00F) );
            for (JCVFileInfo info : processSuccessFiles) {
                processFilesMap.put(info, "1");
            }
            
            for (Entry<String, JCVFileInfo> map : jcvFiles.entrySet()) {
                String string = processFilesMap.get(map.getValue());
                if (string == null &&  map.getValue().isCopy()==false) {
                    if(map.getValue().getFileType().equals(JCVFileInfo.CSS) 
                    				&&(jCVConfig.isCompressionCss ()==true ||  jCVConfig.getCssMethod ()== JCVMethodEnum.MD5FileName_METHOD )  ){
                        copyFiles.add(map.getValue());
                    }else  if(map.getValue().getFileType().equals(JCVFileInfo.JS) 
                    				&& (jCVConfig.isCompressionJs ()==true || jCVConfig.getJsMethod ()==JCVMethodEnum.MD5FileName_METHOD)){
                        copyFiles.add(map.getValue());
                    }else {
                    	LoggerFactory.warn ("fiel type error :"+map.getValue().getFileType ());
                    }
                    
                    
                }
                
            }
            for (JCVFileInfo info : copyFiles) {
                copyFileJssCss(info,jCVConfig.getOutJSCSSDirPath ());
            }
            displayInfo.add (DISPLAY_STR+"copy untreated file file size: "+copyFiles.size ());
        }else {
        	//不是使用文件名md5方式,复制全部
			for (Entry<String, JCVFileInfo> map : jcvFiles.entrySet ()) {
				JCVFileInfo info = map.getValue ();
				if (info == null) {
					continue;
				}
				copyFileJssCss (info,jCVConfig.getOutJSCSSDirPath ());
				
			}
        }
	}
	
	
	/**
     * 
     * 复制未处理文件
     * @param jcf
     */
    private void copyFileJssCss(JCVFileInfo jcf,final String outJSCSSDirPath){
        
        String tempPath= BaseUtils.getJSSCSSOutPath(jcf,false, JCVMethodEnum.DEFAULTS_UNUSED, outJSCSSDirPath,jCVConfig);
        File f = new File( BaseUtils.getFilePathDir(tempPath));
        if (!f.exists()) {
            f.mkdirs();
        }
        try {
        	LoggerFactory.debug("copy Untreated file:"+tempPath);
            FileUtils.fileChannelCopy(jcf.getFile(), new File(tempPath));
        } catch (IOException e) {
        	LoggerFactory.error("copy file error:",e);
        }
        
        
    }
    
    
    
    /**
     * 
     * 获取全部js文件
     * @param collected
     */
	private void getAllJsFile(Map<String,JCVFileInfo> collected,final String rootPath,final JCVConfig jcvConfig){
        if(collected==null){
            collected=new HashMap<String,JCVFileInfo>();
        }
        String jsRoot="";
        if(null!=jcvConfig.getJsPhysicalRootPath ()&& !"".equals (jcvConfig.getJsPhysicalRootPath ())){
        	jsRoot=jcvConfig.getJsPhysicalRootPath ();
        }else {
        	jsRoot=rootPath;
        }
        
        List<String > su=new ArrayList<String>();
        su.add("js");
        String globaJslPrefixPath=jCVConfig.getGlobaJslPrefixPath ();
        if ( globaJslPrefixPath!= null && !"".equals(globaJslPrefixPath)) {
            if (jsRoot.endsWith(FileUtils.getSystemFileSeparator())) {
                jsRoot+=globaJslPrefixPath;
            }else {
                jsRoot+=FileUtils.getSystemFileSeparator()+globaJslPrefixPath;
            }
        }
        if (!jsRoot.endsWith(FileUtils.getSystemFileSeparator())) {
            jsRoot+=FileUtils.getSystemFileSeparator();
        }
        List<File> listFile=new ArrayList<File>();
        FileUtils.collectFiles(listFile, new File(jsRoot), su);
        JCVFileInfo jcv=null;
        for(File f:listFile){
            String path = f.getPath();
            path=path.substring(jsRoot.length(), path.length());
            if(jcv==null){
                jcv=new JCVFileInfo();
            }
            if(!FileUtils.getSystemFileSeparatorIslinux()){
                path=BaseUtils.replaceLinuxSystemLine(path);
            }
            jcv.setFileType(JCVFileInfo.JS);
            jcv.setFileVersion(getFileVersion(f,jCVConfig.getJsMethod ()));
            jcv.setRelativelyFilePath(path);
            jcv.setFileName(f.getName());
            try {
				jcv.setFileHashKey (BaseUtils.getFileHashKey (f,JCVConstant.FILE_HASH_MD5));
			}
			catch (Exception e) {
				LoggerFactory.error (e);
			}
            jcv.setFile(f);
            //version 6.0
            if(null!=jcvConfig.getJsConstantName () &&!"".equals (jcvConfig.getJsConstantName ())){
            	path=jcvConfig.getJsConstantName ()+JCVConstant.HTML_PATH_SEPARATED+path;
            }
            collected.put(path, jcv);
            jcv=null;
        }
    }
	/**
     * 
     * 获取全部css文件
     * @param collected
     */
    private void getAllCssFile(Map<String,JCVFileInfo> collected,final String rootPath,final JCVConfig jcvConfig){
        if(collected==null){
            collected=new HashMap<String,JCVFileInfo>();
        }
        String cssRoot="";
        if(null!=jcvConfig.getCssPhysicalRootPath () && !"".equals (jcvConfig.getCssPhysicalRootPath ())){
        	cssRoot=jcvConfig.getCssPhysicalRootPath ();
        }else {
        	 cssRoot=rootPath;
        }
        List<String > su=new ArrayList<String>();
        su.add("css");
       String  globaCsslPrefixPath=jCVConfig.getGlobaCsslPrefixPath ();
        if (globaCsslPrefixPath != null && !"".equals(globaCsslPrefixPath)) {
            if (cssRoot.endsWith(FileUtils.getSystemFileSeparator())) {
                cssRoot+=globaCsslPrefixPath;
            }else {
                cssRoot+=FileUtils.getSystemFileSeparator()+globaCsslPrefixPath;
            }
        }
        if (!cssRoot.endsWith(FileUtils.getSystemFileSeparator())) {
            cssRoot+=FileUtils.getSystemFileSeparator();
        }
        List<File> listFile=new ArrayList<File>();
        FileUtils.collectFiles(listFile, new File(cssRoot), su);
        JCVFileInfo jcv=null;
        for(File f:listFile){
            String path = f.getPath();
           // path= path.replaceFirst(webRoot, "");
              path=path.substring(cssRoot.length(), path.length());
            if(jcv==null){
                jcv=new JCVFileInfo();
            }
            if(!FileUtils.getSystemFileSeparatorIslinux()){
                path=BaseUtils.replaceLinuxSystemLine(path);
            }
            jcv.setFileType(JCVFileInfo.CSS);
            jcv.setFileVersion(getFileVersion(f,jCVConfig.getCssMethod ()));
            jcv.setRelativelyFilePath(path);
            jcv.setFileName(f.getName());
            jcv.setFile(f);
            try {
				jcv.setFileHashKey (BaseUtils.getFileHashKey (f,JCVConstant.FILE_HASH_MD5));
			}
			catch (Exception e) {
				LoggerFactory.error (e);
			}
            //version 6.0
            if(null!=jcvConfig.getCssConstantName () &&!"".equals (jcvConfig.getCssConstantName ())){
            	path=jcvConfig.getCssConstantName ()+JCVConstant.HTML_PATH_SEPARATED+path;
            }
            collected.put(path, jcv);
            jcv=null;
        }
    }
    
    /**
     * 
     * 获取全部的文件
     * @param files
     * @param suffix
     */
    private void getAllProcessFile(List<PageInfo> files, String webRoot,List<String > suffix){
        if(files==null){
            files=new ArrayList<PageInfo>();
        }
        List<File> fs=new ArrayList<File>();
        FileUtils.collectFiles(fs, new File(webRoot), suffix);
        PageInfo pi=null;
        for (File file : fs) {
            if(pi==null){
                pi=new PageInfo();
            }
            pi.setFile(file);
            files.add(pi);
            pi=null;
        }
    }
    
	/**
     * 
     * 获取文件版本信息
     * @param f
     * @param en
     * @return
     */
    private  String getFileVersion(File f,JCVMethodEnum en){
        try {
            switch (en) {
                case MD5_METHOD:
                   return  Md5Utils.getFileMD5(f);
                    
               case MD5FileName_METHOD:
                   return  Md5Utils.getFileMD5(f);
                    
                case TIMESTAMP_METHOD:
                 return new Date ().getTime ()+"";
                
               default:
                   return  Md5Utils.getFileMD5(f);
                   
            }
        } catch (Exception e) {
           LoggerFactory.info(e.getMessage());
        }
        return new Date ().getTime ()+"";
       
    }
	
    
	/**
	 * 
	 * 复制md5文件处理
	 * @param jcf
	 * @param outJSCSSDirPath
	 */
	private  void copyMd5FileNameJssCss(JCVFileInfo jcf,final String outJSCSSDirPath){
	        
	      
	        String tempPath= BaseUtils.getJSSCSSOutPath(jcf,true, JCVMethodEnum.MD5FileName_METHOD, outJSCSSDirPath,jCVConfig);
	        File f = new File( BaseUtils.getFilePathDir(tempPath));
	        if (!f.exists()) {
	            f.mkdirs();
	        }
	      
	        try {
	            if(null==jcf.getFinalFileName() ||  "".equals(jcf.getFinalFileName())){
	                return;
	            }
	            LoggerFactory.debug("copy md5 name  file:"+tempPath);
	            FileUtils.fileChannelCopy(jcf.getFile(), new File(tempPath));
	        } catch (IOException e) {
	        	LoggerFactory.error("copy file error:",e);
	        }
	        
	        
	    }
	
	/**
	 * 
	 * 压缩css js
	 * 
	 * @param processFiles
	 * @param outDir
	 */
	public abstract void processCompressionJsCss(List<JCVFileInfo> processFiles, final String outDir, final JCVConfig jCVConfig);
	
	/**
	 * 
	 * 处理css版本号
	 * 
	 * @param html
	 * @param index
	 * @return
	 */
	public abstract int processCSSVersion(StringBuffer html, int index,  List<JCVFileInfo>  processSuccessFiles,final JCVConfig jCVConfig);
	
	/**
	 * 
	 * 处理js版本号
	 * 
	 * @param html
	 * @param index
	 * @return
	 */
	public abstract int processJSVersion(StringBuffer html, int index,  List<JCVFileInfo>  processSuccessFiles, final JCVConfig jCVConfig);
	
	/**
	 * 
	 * 处理页面注释
	 * 
	 * @param sb
	 * @param index
	 * @return
	 */
	public abstract int processPageComment(StringBuffer sb, int index, final JCVConfig jCVConfig);
	
	/**
	 * 
	 * 合并页面css <暂未实现>
	 * 
	 * @param sb
	 * @param index
	 * @return
	 */
	public abstract int mergePageCss(StringBuffer html, int index, final JCVConfig jCVConfig);
	
	/**
	 * 
	 * 合并js　<暂未实现>
	 * 
	 * @param html
	 * @param index
	 * @return
	 */
	public abstract int mergePageJS(StringBuilder html, int index, final JCVConfig jCVConfig);
	
}
