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
import com.iqarr.maven.plugin.utils.BaseUtils;
import com.iqarr.maven.plugin.utils.FileUtils;
import com.iqarr.maven.plugin.utils.LoggetFactory;
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
	
	
	
	
	public AbstractProcessFactory( JCVConfig jCVConfig,final Map<String, JCVFileInfo> jcvFiles){
		this.jCVConfig=jCVConfig;
		this.jcvFiles=jcvFiles;
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
        getAllCssFile(jcvFiles,webAppRoot);
        getAllJsFile(jcvFiles,webAppRoot);
        
        for(Entry<String, JCVFileInfo> f:jcvFiles.entrySet()){
            LoggetFactory.debug("find type:"+f.getValue().getFileType()+" file:"+f.getKey() + " md5:"+f.getValue().getFileVersion()); 
        }
        if(pages==null){
        	pages=new ArrayList<PageInfo> ();
        }
        getAllProcessFile(pages,webAppRoot,jCVConfig.getPageSuffixs ());
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
			LoggetFactory.error ("JcvConfig is null");
		}
		// 处理成功的js css文件
		 List<JCVFileInfo>  processSuccessFiles=new ArrayList<JCVFileInfo> ();
		for (PageInfo pageInfo : pages) {
			LoggetFactory.debug ("find page:" + pageInfo.getFile ().getPath ());
			
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
				
				LoggetFactory.debug (" page:" + pageInfo.getFile ().getName () + " Processing is complete");
				FileUtils.writeFile (pageInfo.getOutFile (),jCVConfig.getSourceEncoding (),savehtml);
				
				//复制md5文件
				processMd5FileCpoy(processSuccessFiles);
				
				//开始压缩
				 if (jCVConfig.isCompressionCss () == true || jCVConfig.isCompressionJs () == true) {
			          
					 processCompressionJsCss(processSuccessFiles,jCVConfig.getOutJSCSSDirPath (),jCVConfig);
			     }
				 //处理未使用文件
				 doCopyUntreatedFile(processSuccessFiles);
				 
			}
			catch (Exception e) {
				LoggetFactory.error ("Skip the file :" + pageInfo.getFile ().getPath (),e);
			}
		}
	}
	/**
	 * 
	 * 复制MD5FileName_METHOD　文件
	 * @param processSuccessFiles
	 */
	private void processMd5FileCpoy( List<JCVFileInfo>  processSuccessFiles){
		
		//复制MD5FileName_METHOD　文件
        if (jCVConfig.getCssMethod () == JCVMethodEnum.MD5FileName_METHOD || jCVConfig.getJsMethod () == JCVMethodEnum.MD5FileName_METHOD) {
           
            for (JCVFileInfo info : processSuccessFiles) {
                if(jCVConfig.getCssMethod () == JCVMethodEnum.MD5FileName_METHOD && JCVFileInfo.CSS.equals(info.getFileType())){
                    copyMd5FileNameJssCss(info,jCVConfig.getOutJSCSSDirPath ());
                }
                if( jCVConfig.getJsMethod ()  == JCVMethodEnum.MD5FileName_METHOD && JCVFileInfo.JS.equals(info.getFileType())){
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
            
           
            List<JCVFileInfo> copyFiles = new ArrayList<JCVFileInfo>();
            Map<JCVFileInfo, String> processFilesMap = new HashMap<JCVFileInfo, String>();
            for (JCVFileInfo info : processSuccessFiles) {
                processFilesMap.put(info, "1");
            }
            
            for (Entry<String, JCVFileInfo> map : jcvFiles.entrySet()) {
                String string = processFilesMap.get(map.getValue());
                if (string == null &&  map.getValue().isCopy()==false) {
                    if(map.getValue().getFileType().equals(JCVFileInfo.CSS) && 
                                    (jCVConfig.isCompressionCss ()==true ||  jCVConfig.getCssMethod ()== JCVMethodEnum.MD5FileName_METHOD )  ){
                        copyFiles.add(map.getValue());
                    }else  if(map.getValue().getFileType().equals(JCVFileInfo.JS) && (jCVConfig.isCompressionJs ()==true || jCVConfig.getJsMethod ()==JCVMethodEnum.MD5FileName_METHOD)){
                        copyFiles.add(map.getValue());
                    }
                    
                    
                }
                
            }
            for (JCVFileInfo info : copyFiles) {
                copyFileJssCss(info,jCVConfig.getOutJSCSSDirPath ());
            }
        }
	}
	
	
	/**
     * 
     * 复制未处理文件
     * @param jcf
     */
    private void copyFileJssCss(JCVFileInfo jcf,final String outJSCSSDirPath){
        
        String tempPath= BaseUtils.getJSSCSSOutPath(jcf,false, JCVMethodEnum.DEFAULTS_UNUSED, outJSCSSDirPath);
        File f = new File( BaseUtils.getFilePathDir(tempPath));
        if (!f.exists()) {
            f.mkdirs();
        }
        try {
        	LoggetFactory.info("copy not processed file:"+tempPath);
            FileUtils.fileChannelCopy(jcf.getFile(), new File(tempPath));
        } catch (IOException e) {
        	LoggetFactory.error("copy file error:",e);
        }
        
        
    }
    
    
    
    /**
     * 
     * 获取全部js文件
     * @param collected
     */
	private void getAllJsFile(Map<String,JCVFileInfo> collected,final String rootPath){
        if(collected==null){
            collected=new HashMap<String,JCVFileInfo>();
        }
        String webRoot=rootPath;
        List<String > su=new ArrayList<String>();
        su.add("js");
        String globaJslPrefixPath=jCVConfig.getGlobaJslPrefixPath ();
        if ( globaJslPrefixPath!= null && !"".equals(globaJslPrefixPath)) {
            if (webRoot.endsWith(FileUtils.getSystemFileSeparator())) {
                webRoot+=globaJslPrefixPath;
            }else {
                webRoot+=FileUtils.getSystemFileSeparator()+globaJslPrefixPath;
            }
        }
        if (!webRoot.endsWith(FileUtils.getSystemFileSeparator())) {
            webRoot+=FileUtils.getSystemFileSeparator();
        }
        List<File> listFile=new ArrayList<File>();
        FileUtils.collectFiles(listFile, new File(webRoot), su);
        JCVFileInfo jcv=null;
        for(File f:listFile){
            String path = f.getPath();
            path=path.substring(webRoot.length(), path.length());
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
				LoggetFactory.error (e);
			}
            jcv.setFile(f);
            collected.put(path, jcv);
            jcv=null;
        }
    }
	/**
     * 
     * 获取全部css文件
     * @param collected
     */
    private void getAllCssFile(Map<String,JCVFileInfo> collected,final String rootPath){
        if(collected==null){
            collected=new HashMap<String,JCVFileInfo>();
        }
        String webRoot=rootPath;
        List<String > su=new ArrayList<String>();
        su.add("css");
       String  globaCsslPrefixPath=jCVConfig.getGlobaCsslPrefixPath ();
        if (globaCsslPrefixPath != null && !"".equals(globaCsslPrefixPath)) {
            if (webRoot.endsWith(FileUtils.getSystemFileSeparator())) {
                webRoot+=globaCsslPrefixPath;
            }else {
                webRoot+=FileUtils.getSystemFileSeparator()+globaCsslPrefixPath;
            }
        }
        if (!webRoot.endsWith(FileUtils.getSystemFileSeparator())) {
            webRoot+=FileUtils.getSystemFileSeparator();
        }
        List<File> listFile=new ArrayList<File>();
        FileUtils.collectFiles(listFile, new File(webRoot), su);
        JCVFileInfo jcv=null;
        for(File f:listFile){
            String path = f.getPath();
           // path= path.replaceFirst(webRoot, "");
              path=path.substring(webRoot.length(), path.length());
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
				LoggetFactory.error (e);
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
           LoggetFactory.info(e.getMessage());
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
	        
	      
	        String tempPath= BaseUtils.getJSSCSSOutPath(jcf,true, JCVMethodEnum.MD5FileName_METHOD, outJSCSSDirPath);
	        File f = new File( BaseUtils.getFilePathDir(tempPath));
	        if (!f.exists()) {
	            f.mkdirs();
	        }
	      
	        try {
	            if(null==jcf.getFinalFileName() ||  "".equals(jcf.getFinalFileName())){
	                return;
	            }
	            LoggetFactory.info("copy file:"+tempPath);
	            FileUtils.fileChannelCopy(jcf.getFile(), new File(tempPath));
	        } catch (IOException e) {
	        	LoggetFactory.error("copy file error:",e);
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
