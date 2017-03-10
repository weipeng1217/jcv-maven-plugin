package com.iqarr.maven.plugin;

import static com.iqarr.maven.plugin.utils.HtmlUtils.getHtmllabDocposition;

import static com.iqarr.maven.plugin.utils.BaseUtils.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.maven.plugin.logging.Log;
import org.mozilla.javascript.EvaluatorException;

import com.iqarr.maven.plugin.domain.DocPosition;
import com.iqarr.maven.plugin.domain.JCVFileInfo;
import com.iqarr.maven.plugin.domain.JCVMethodEnum;
import com.iqarr.maven.plugin.domain.PageInfo;
import com.iqarr.maven.plugin.domain.YUIConfig;
import com.iqarr.maven.plugin.exception.YUIException;
import com.iqarr.maven.plugin.utils.BaseUtils;
import com.iqarr.maven.plugin.utils.FileUtils;
import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

/**
 * 
* @Package 
*	 com.iqarr.maven.plugin
* @ClassName: 
*	 JCVFactory  
* @since 
*	  V1.0
* @author 
*		zhangyong   
* @date 
*		2017/01/11-11:15:56
* @version 
*		V1.0
 */
public class JCVFactory {
    
    
    private final static String HTML_URL_SEPARATOR="/";
    
    private Map<String, JCVFileInfo> jcvs;
    
    private JCVMethodEnum            jsEn;
    
    private JCVMethodEnum            cssEn;
    
    private String                   versionLable;
    
    private List<String>             baseJsDomin;
    
    private List<String>             baseCssDomin;
    
    private String                   globaJslPrefixPath;
    
    private String                   globaCsslPrefixPath;
    
    private String                   sourceEncoding;
    
    private boolean                  clearPageComment;
    
    private Log                      log;
   
    @SuppressWarnings("unused")
    private String              outJSCSSDirPath;
    
    //version 0.0.2
    
    /**压缩css **/
    private boolean compressionCss;
    
    /** 压缩js**/
    private boolean compressionJs;
    
    /**压缩文件后缀 **/
    private String userCompressionSuffix;
    
    
    /** 排除js文件(只支持全路径匹配，如果是文件夹那么该文件夹下全部将会忽略)**/
    private List<String> excludesJs;
    
    /** 排除css文件(只支持全路径匹配，如果是文件夹那么该文件夹下全部将会忽略)**/
    private List<String> excludesCss;
    
    
    /**
     * 处理成功的全部文件
     */
   private List<JCVFileInfo>  processFiles=null;
   
   
   //version 0.0.3
   private YUIConfig yuiConfig;
   
   private String braekFileNameSuffix;
    
    public JCVFactory() {
        
    }
    
    public JCVFactory(Map<String, JCVFileInfo> jcvs,
                    JCVMethodEnum jsEn, JCVMethodEnum cssEn,
                    String versionLable     //
                    , List<String> baseJsDomin, List<String> baseCssDomin   //
                    , String globaJslPrefixPath, String globaCsslPrefixPath   //
                    , String sourceEncoding  //
                    , boolean clearPageComment  //
                    , Log log
                    ,String  outJSCSSDirPath
                    ,boolean compressionCss,boolean compressionJs,String userCompressionSuffix
                    ,List<String> excludesJs,List<String> excludesCss
                    ,YUIConfig yuiConfig,String braekFileNameSuffix) {
        
        this.jcvs = jcvs;
        this.jsEn = jsEn;
        this.cssEn = cssEn;
        this.versionLable = versionLable;
        this.baseJsDomin = baseJsDomin;
        this.baseCssDomin = baseCssDomin;
        this.globaJslPrefixPath = globaJslPrefixPath;
        this.globaCsslPrefixPath = globaCsslPrefixPath;
        this.sourceEncoding = sourceEncoding;
        this.clearPageComment = clearPageComment;
        this.log = log;
        this.outJSCSSDirPath=outJSCSSDirPath;
        this.compressionCss=compressionCss;
        this.compressionJs=compressionJs;
        this.userCompressionSuffix=userCompressionSuffix;
        
        this.excludesJs=excludesJs;
        this.excludesCss=excludesCss;
        if(yuiConfig==null){
            yuiConfig=new YUIConfig();
        }
        this.yuiConfig=yuiConfig;
        this.braekFileNameSuffix=braekFileNameSuffix;
       
        
    }
    
    public void processPageFile(List<PageInfo> pages) {
        
        for (PageInfo pageInfo : pages) {
            if(log!=null){
                log.debug("find page:"+pageInfo.getFile().getPath());
            }
            try {
                String strAll = FileUtils.readToStr(pageInfo.getFile(), sourceEncoding);
                List<String> savehtml=new ArrayList<String>();
                if (strAll == null || strAll.length() == 0) {
                    continue;
                }
                StringBuffer sb = null;
                if (sb == null) {
                   sb = new StringBuffer(strAll);
                }
                int ret = processCSS(sb,0);
                int ret2=processJS(sb,0);
                int ret3=0;
                if(clearPageComment){
                    ret3= processPageComment(sb,0);
                    FileUtils.clearBlankLines(sb, sourceEncoding);
                }
                if(ret==1 || ret2==1 || ret3==1){
                      savehtml.add(sb.toString());
                       sb=null;
                        
                 }else{
                        savehtml.add(sb.toString());
                       sb=null;
                        
                }
                if(null!=log){
                    log.debug(" page:"+pageInfo.getFile().getName() +" Processing is complete");
                }
                FileUtils.writeFile(pageInfo.getOutFile(), sourceEncoding, savehtml);
                
            } catch (Exception e) {
                log.error("break file :" + pageInfo.getFile().getPath() , e);
            }
            
        }
    }
    
    /**
     * 
     * 压缩js css
     * @param outDir
     */
    @SuppressWarnings("unused")
    public void processCompressionJsCss(final String outDir) {
        try {
            if (null != log) {
                log.debug("CompressionJsCss outDir:" + outDir);
            }
            if (log != null) {
                log.debug("Compression find file size:" + processFiles.size());
            }
           // List<String> writeStr = null;
            String fileName= "";
            if (this.processFiles == null) {
                return;
            }
            if (compressionCss == true || compressionJs == true) {
                //yui
                 Reader  in = null;
                 Writer out = null;
                  for(int i=0;i<processFiles.size();i++){
                      
                   try {
                        
                    
                    JCVFileInfo jcv=processFiles.get(i);
                    //不处理后缀为.min.*的文件
                    if(jcv.getFileName().indexOf(braekFileNameSuffix+"."+jcv.getFileType())!=-1){
                        if (log != null) {
                            log.info("The suffix min is not processed:" + jcv.getFileName());
                        }
                        //移除处理文件
                        if ( (JCVFileInfo.CSS.equals(jcv.getFileType()) && cssEn == JCVMethodEnum.MD5FileName_METHOD )
                                        ||(JCVFileInfo.JS.equals(jcv.getFileType()) && jsEn == JCVMethodEnum.MD5FileName_METHOD) ){
                            processFiles.get(i).setCopy(true);
                        }else {
                            processFiles.remove(i);
                            i--; 
                        }
                       
                        continue;
                    }
                    
                    //检查排除
                    if (JCVFileInfo.CSS.equals(jcv.getFileType())) {
                        if(excludesCss!=null && checkStrIsInList(jcv.getRelativelyFilePath(),excludesCss,true) ){
                            if (log != null) {
                                log.info("The file  is not processed:" + jcv.getFileName());
                            }
                          //移除处理文件
                            processFiles.remove(i);
                            i--;
                            continue;
                        }
                    }else  if (JCVFileInfo.JS.equals(jcv.getFileType())) {
                        if(excludesJs!=null && checkStrIsInList(jcv.getRelativelyFilePath(),excludesJs,true) ){
                            if (log != null) {
                                log.info("The file  is not processed:" + jcv.getFileName());
                            }
                          //移除处理文件
                            processFiles.remove(i);
                            i--;
                            continue;
                        }
                    }
                    
                    
                    if (log != null) {
                        log.debug("process file:" + jcv.getFileName()+"   index:"+i);
                    }
                    
                    if (JCVFileInfo.CSS.equals(jcv.getFileType())) {
                        if (compressionCss==true) {
                           
                           String tempPath= BaseUtils.getJSSCSSOutPath(jcv, true,cssEn, outDir);
                           File f = new File( BaseUtils.getFilePathDir(tempPath));
                           if (!f.exists()) {
                               f.mkdirs();
                           }
                             
                            // YUI
                            in = new InputStreamReader(new FileInputStream(jcv.getFile()));
                            CssCompressor compressor = new CssCompressor(in);
                            in.close(); 
                            in = null;
                            out = new OutputStreamWriter(new FileOutputStream(tempPath), sourceEncoding);
                            compressor.compress(out, -1);
                            out.close();
                            out=null;
                            
                        }
                        
                    } else if (JCVFileInfo.JS.equals(jcv.getFileType())) {
                            
                             if (compressionJs == true) {
                                  
                                 String tempPath= BaseUtils.getJSSCSSOutPath(jcv, true,jsEn, outDir);
                                 File f = new File( BaseUtils.getFilePathDir(tempPath));
                                 if (!f.exists()) {
                                     f.mkdirs();
                                 }
                                    // yui start
                                    in = new InputStreamReader(new FileInputStream(jcv.getFile()));
                                    JavaScriptCompressor compressor = new JavaScriptCompressor(in, new YUIException(log, jcv.getFileName()));
                                    in.close();
                                    in = null;
                                    out = new OutputStreamWriter(new FileOutputStream(tempPath), sourceEncoding);
                                    compressor.compress(out, -1, yuiConfig.isNomunge(), yuiConfig.isVerbose(), yuiConfig.isPreserveSemi(), yuiConfig.isDisableOptimizations());
                                    out.close();
                                    out = null;
                                    
                                }
                                
                            }
                    
                     } catch (IOException  | EvaluatorException e) {
                           if (log != null) {
                               log.error(e);
                           }
                    }
                    
                    
                    
                  
                } //for end
                
            }
        } catch (Exception e) {
            if (log != null) {
                log.error(e);
                //log.error("file error:"+jcv.);
            }
        }
        
    }
    
   
    
    /**
     * 
     * 处理css
     * 
     * @param sb
     * @return -1 表示跳过., 1 表示 获取下一行在传入 , 0 处理结束
     */
    public int processCSS(StringBuffer sb, int index) {
        DocPosition dp=new DocPosition();
        if(index==0 || index==-1){
            dp.setIndexPos(index);
        }else {
            dp.setIndexPos(index);
        }
        dp.setStartLab("<link");
        dp.setEndLad("href=");
        dp.setCheckEndLad(">");
        getHtmllabDocposition(sb,dp);
        if(dp.getEndPos()==-1 ||  !dp.isFindIt()){
            if(index<sb.length() && dp.getStartPos()!=-1){
                return  processCSS(sb, index+dp.getStartPos());
            }else {
                return -1;
            }
        }
        char[] cas = sb.toString().toCharArray();
       /* int size=cas.length;
        if(dp.getEndPos()==-1 || dp.getEndPos()>size){
            return 1;
        }*/
       
        char endChar = cas[dp.getEndPos()];
        
        if (endChar != '\'' && endChar != '"') {
            return -1;
        }
        DocPosition dpsrc=new DocPosition();
        dpsrc.setIndexPos(dp.getEndPos()-1);
        dpsrc.setStartLab(endChar+"");
        dpsrc.setEndLad(endChar+"");
        dpsrc.setCheckEndLad(">");
        getHtmllabDocposition(sb,dpsrc);
        if(!dpsrc.isFindIt()){
            return -1;
        }
        int length=dpsrc.getEndPos()-dpsrc.getStartPos()-2;
        if(length<0){
            return -1;
        }
        char[] links = new char[length];
        System.arraycopy(cas, dpsrc.getStartPos()+1, links, 0, length);
        String link = new String(links);
        
        if(log!=null){
            log.debug("find css link:"+link);
        }
        
        processCSSlink(sb,dpsrc.getStartPos()-1,dpsrc.getEndPos()-1,link);
        
        
        int res = processCSS(sb, dpsrc.getEndPos());
        
        return res;  
    }
    
    
    /**
     * 
     * 
     * @param sb
     * @param index
     * @return -1 表示跳过., 1 表示 获取下一行在传入 , 0 处理结束
     */
    public int processJS(StringBuffer sb, int index) {
        DocPosition dp=new DocPosition();
        if(index==0 || index==-1){
            dp.setIndexPos(index);
        }else {
            dp.setIndexPos(index);
        }
        dp.setStartLab("<script");
        dp.setEndLad("src=");
        dp.setCheckEndLad(">");
        getHtmllabDocposition(sb,dp);
        
        if(dp.getEndPos()==-1 ||  !dp.isFindIt() ){
            if(index<sb.length()&& dp.getStartPos()!=-1){
                return  processJS(sb, index+dp.getStartPos()+"<script".length());
            }else {
                return -1;
            }
        }
        char[] cas = sb.toString().toCharArray();
       // int size=cas.length;
        /*if(dp.getEndPos()==-1 || dp.getEndPos()>size){
            if(null!=log){
                log.info("index:"+index +" setStartLab:"+dp.getStartPos()+" endPost:"+dp.getEndPos());
            }
            return 1;
        }*/
       
        char endChar = cas[dp.getEndPos()];
        
        if (endChar != '\'' && endChar != '"') {
            return -1;
        }
        DocPosition dpsrc=new DocPosition();
        dpsrc.setIndexPos(dp.getEndPos()-1);
        dpsrc.setStartLab(endChar+"");
        dpsrc.setEndLad(endChar+"");
        dpsrc.setCheckEndLad(">");
        getHtmllabDocposition(sb,dpsrc);
        if(!dpsrc.isFindIt()){
            return -1;
        }
        int length=dpsrc.getEndPos()-dpsrc.getStartPos()-2;
        if(length<0){
            return -1;
        }
        char[] links = new char[length];
        System.arraycopy(cas, dpsrc.getStartPos()+1, links, 0, length);
        String link = new String(links);
        
        if(log!=null){
            log.debug("find js link:"+link);
        }
        
       processJSlink(sb,dpsrc.getStartPos()-1,dpsrc.getEndPos()-1,link);
        
        
        int res = processJS(sb, dpsrc.getEndPos());
        
        return res;
        
    }
    
    
    /**
     * 
     * 注释处理
     * @param sb
     * @param index
     * @return -1 表示跳过., (1 表示 获取下一行在传入  废弃) , 0 处理结束
     */
    public int processPageComment(StringBuffer sb, int index) {
        if(index==-1){
            return 1;
        }
        DocPosition dp=new DocPosition();
        if(index==0 ){
            dp.setIndexPos(index);
        }else {
            dp.setIndexPos(index);
        }
        dp.setStartLab("<!--");
        dp.setEndLad("-->");
        getHtmllabDocposition(sb,dp);
        if(!dp.isFindIt()){ 
                return -1;  
        }
        
        if(dp.getStartPos()==-1){
            return -1;
        }
        if(dp.getEndPos()==-1){
            sb.delete(dp.getStartPos(), sb.length()); 
            index=-1;
        }else {
            sb.delete(dp.getStartPos(), dp.getEndPos());
            index=dp.getEndPos();
        }
         
        return processPageComment(sb,0);
        
    }
    
    
   
  
    
    public int processCSSlink(StringBuffer sb,final int start,final int end,final String historylink){
        if(historylink.startsWith("http://") || historylink.startsWith("https://")){
            //绝对
           for(String domain:  baseCssDomin){
               String fullLink=domain;
               if(null!=globaCsslPrefixPath && !"".equals(globaCsslPrefixPath)){
                   if(!fullLink.endsWith(HTML_URL_SEPARATOR)){
                       fullLink+=HTML_URL_SEPARATOR;
                   }
                   if(globaCsslPrefixPath.startsWith(HTML_URL_SEPARATOR)){
                       fullLink+= globaCsslPrefixPath.replaceFirst(HTML_URL_SEPARATOR, "");
                   }else {
                       fullLink+=globaCsslPrefixPath;
                   }
              
               }
               String tempUrl = historylink.replaceFirst(fullLink, "");
               if(tempUrl!=null &&tempUrl.startsWith(HTML_URL_SEPARATOR)){
                   tempUrl= tempUrl.replaceFirst(HTML_URL_SEPARATOR, "");
                   }
             
               tempUrl= removeUrlPar(tempUrl);
               JCVFileInfo jcvFileInfo = jcvs.get(tempUrl);
               if(jcvFileInfo!=null){
                   
                   instatVersion(sb, start, end, historylink, fullLink, jcvFileInfo);
                   break;
               }
           }
            
        }else {
            //相对　
            //String fullLink="";
            StringBuilder fullLink=new StringBuilder();
            fullLink.append(historylink);
            if(globaCsslPrefixPath.startsWith(HTML_URL_SEPARATOR)){
                fullLink.append( globaCsslPrefixPath.replaceFirst(HTML_URL_SEPARATOR, ""));
            }else {
                fullLink.append(globaCsslPrefixPath);
            }
            fullLink=new StringBuilder(removeUrlPar(fullLink.toString()));
            if(fullLink.indexOf(HTML_URL_SEPARATOR, 0)==0){
                fullLink.delete(0, 1);
            }
            JCVFileInfo jcvFileInfo = jcvs.get(fullLink.toString());
            if(jcvFileInfo!=null){
                
              //  sb.insert(end, str)
               instatVersion(sb, start, end, historylink, fullLink.toString(), jcvFileInfo);
            }
        }
        
        
        
        
        return 0;
    }
    
    public int processJSlink(StringBuffer sb,final int start,final int end,final String historylink){
        if(historylink.startsWith("http://") || historylink.startsWith("https://")){
            //绝对
           for(String domain:  baseJsDomin){
               String fullLink=domain;
               if(null!=globaCsslPrefixPath && !"".equals(globaCsslPrefixPath)){
                   if(!fullLink.endsWith(HTML_URL_SEPARATOR)){
                       fullLink+=HTML_URL_SEPARATOR;
                   }
                   if(globaCsslPrefixPath.startsWith(HTML_URL_SEPARATOR)){
                       fullLink+= globaCsslPrefixPath.replaceFirst(HTML_URL_SEPARATOR, "");
                   }else {
                       fullLink+=globaCsslPrefixPath;
                   }
              
               }
               String tempUrl = historylink.replaceFirst(fullLink, "");
               if(tempUrl!=null &&tempUrl.startsWith(HTML_URL_SEPARATOR)){
                   tempUrl= tempUrl.replaceFirst(HTML_URL_SEPARATOR, "");
                   }
               tempUrl= removeUrlPar(tempUrl);
               JCVFileInfo jcvFileInfo = jcvs.get(tempUrl);
               if(jcvFileInfo!=null){
                   
                   instatVersion(sb, start, end, historylink, fullLink, jcvFileInfo);
                   break;
               }
           }
            
        }else {
            //相对　
           // String fullLink="";
            StringBuilder fullLink=new StringBuilder();
            if(globaJslPrefixPath.startsWith(HTML_URL_SEPARATOR)){
                fullLink.append( globaJslPrefixPath.replaceFirst(HTML_URL_SEPARATOR, ""));
            }else {
                fullLink.append(globaJslPrefixPath);
            }
            fullLink.append(historylink);
            String s=removeUrlPar(fullLink.toString());
            fullLink= new StringBuilder(s);
            if(fullLink.indexOf(HTML_URL_SEPARATOR, 0)==0){
                fullLink.delete(0, 1);
            }
            JCVFileInfo jcvFileInfo = jcvs.get(fullLink.toString());
            if(jcvFileInfo!=null){
                
              //  sb.insert(end, str)
               instatVersion(sb, start, end, historylink, fullLink.toString(), jcvFileInfo);
            }
        }
        
        
        
        
        return 0;
    }
    
    /**
     * 
     * 插入版本号
     * @param sb
     * @param start
     * @param end
     * @param historylink
     * @param fullLink
     * @param jcvFileInfo
     */
    public void instatVersion(StringBuffer sb, final int start, final int end, final String historylink, String fullLink, JCVFileInfo jcvFileInfo) {
        if (jcvFileInfo != null) {
            
            if (log != null) {
                log.debug("process link:" + historylink);
            }
            if (processFiles == null) {
                processFiles = new ArrayList<JCVFileInfo>();
            }
            
            
            // version 0.0.2
            boolean isReplace = false;
            String versionStr = "";
            
            if (JCVFileInfo.CSS.equals(jcvFileInfo.getFileType())) {
                if (!checkStrIsInList(jcvFileInfo.getRelativelyFilePath(), excludesCss, true)) {
                    if (compressionCss == true) {
                        if ((jcvFileInfo.getFileName().indexOf(braekFileNameSuffix + "." + jcvFileInfo.getFileType()) != -1)) {
                            if (cssEn == JCVMethodEnum.MD5_METHOD || cssEn == JCVMethodEnum.TIMESTAMP_METHOD) {
                                versionStr = getVersionStr(jcvFileInfo, false, false, userCompressionSuffix, historylink);
                            } else
                                if (cssEn == JCVMethodEnum.MD5FileName_METHOD) {
                                    versionStr = getVersionStr(jcvFileInfo, true, false, userCompressionSuffix, historylink);
                                    isReplace = true;
                                } else {
                                    log.warn(" not support method method:" + cssEn.name());
                                }
                        } else {
                            
                            // 压缩
                            if (cssEn == JCVMethodEnum.MD5_METHOD || cssEn == JCVMethodEnum.TIMESTAMP_METHOD) {
                                versionStr = getVersionStr(jcvFileInfo, false, true, userCompressionSuffix, historylink);
                            } else
                                if (cssEn == JCVMethodEnum.MD5FileName_METHOD) {
                                    versionStr = getVersionStr(jcvFileInfo, true, true, userCompressionSuffix, historylink);
                                } else {
                                    log.warn(" not support method method:" + cssEn.name());
                                }
                            isReplace = true;
                        }
                        
                    } else {
                        if (cssEn == JCVMethodEnum.MD5_METHOD || cssEn == JCVMethodEnum.TIMESTAMP_METHOD) {
                            versionStr = getVersionStr(jcvFileInfo, false, false, userCompressionSuffix, historylink);
                        } else
                            if (cssEn == JCVMethodEnum.MD5FileName_METHOD) {
                                versionStr = getVersionStr(jcvFileInfo, true, false, userCompressionSuffix, historylink);
                                isReplace = true;
                            } else {
                                log.warn(" not support method method:" + cssEn.name());
                            }
                    }
                } else {
                    if (null != log) {
                        log.debug(" break file :" + jcvFileInfo.getFileType());
                    }
                }
            } else
                if (JCVFileInfo.JS.equals(jcvFileInfo.getFileType())) {
                    if (!checkStrIsInList(jcvFileInfo.getRelativelyFilePath(), excludesJs, true)) {
                        if (compressionJs == true) {
                            if ((jcvFileInfo.getFileName().indexOf(braekFileNameSuffix + "." + jcvFileInfo.getFileType()) != -1)) {
                                if (jsEn == JCVMethodEnum.MD5_METHOD || jsEn == JCVMethodEnum.TIMESTAMP_METHOD) {
                                    versionStr = getVersionStr(jcvFileInfo, false, false, userCompressionSuffix, historylink);
                                } else
                                    if (jsEn == JCVMethodEnum.MD5FileName_METHOD) {
                                        versionStr = getVersionStr(jcvFileInfo, true, false, userCompressionSuffix, historylink);
                                        isReplace = true;
                                    } else {
                                        log.warn(" not support method method:" + cssEn.name());
                                    }
                            } else {
                                
                                // 压缩
                                if (jsEn == JCVMethodEnum.MD5_METHOD || jsEn == JCVMethodEnum.TIMESTAMP_METHOD) {
                                    versionStr = getVersionStr(jcvFileInfo, false, true, userCompressionSuffix, historylink);
                                } else
                                    if (jsEn == JCVMethodEnum.MD5FileName_METHOD) {
                                        versionStr = getVersionStr(jcvFileInfo, true, true, userCompressionSuffix, historylink);
                                    } else {
                                        log.warn(" not support method method:" + cssEn.name());
                                    }
                                isReplace = true;
                            }
                            
                        } else {
                            if (jsEn == JCVMethodEnum.MD5_METHOD || jsEn == JCVMethodEnum.TIMESTAMP_METHOD) {
                                versionStr = getVersionStr(jcvFileInfo, false, false, userCompressionSuffix, historylink);
                            } else
                                if (jsEn == JCVMethodEnum.MD5FileName_METHOD) {
                                    versionStr = getVersionStr(jcvFileInfo, true, false, userCompressionSuffix, historylink);
                                    isReplace = true;
                                } else {
                                    if (null != log) {
                                        log.warn(" not support method method:" + cssEn.name());
                                    }
                                }
                        }
                    } else {
                        if (null != log) {
                            log.debug(" break file :" + jcvFileInfo.getFileType());
                        }
                    }
                } else {
                    if (null != log) {
                        log.warn(" not support file type:" + jcvFileInfo.getFileType());
                    }
                }
            
            processFiles.add(jcvFileInfo);
            if (isReplace) {
                // 替换
                int fileNamelenth = jcvFileInfo.getFileName().length();
                int parLenth = 0;
                String par = "";
                if (historylink.indexOf("?") > 0) {
                    
                    par = getUrlpPar(historylink);
                    parLenth = par.length();
                    parLenth++;
                    versionStr += "?" + par;
                }
                sb.replace(end - fileNamelenth - parLenth, end, "");
                sb.insert(end - fileNamelenth - parLenth, versionStr);
            } else {
                sb.insert(end, versionStr);
            }
            
        }
        
    }
    /**
     * 
     * 获取版本号字符串
     * @param jcvFileInfo
     * @param isMd5FileName  
     * @param isCompression　　　压缩
     * @param suffix
     * @param historylink
     * @return
     */
     public String getVersionStr( JCVFileInfo jcvFileInfo,final boolean isMd5FileName,final boolean isCompression,final String suffix,final String historylink){
       String  versionStr="";
        if (isCompression) {
            // 压缩
            if (isMd5FileName == false) {
                
                int indexlastOf = jcvFileInfo.getFileName().lastIndexOf('.');
                String fileName = "";
                if (indexlastOf != -1) {
                    fileName = jcvFileInfo.getFileName().substring(0, indexlastOf);
                } else {
                    fileName = jcvFileInfo.getFileName();
                }
                
                versionStr = fileName + "." + suffix + "." + jcvFileInfo.getFileType() ;//+ jcvFileInfo.getFileVersion();
                jcvFileInfo.setFinalFileName(versionStr);
                String par = "";
                  if (historylink.indexOf("?") > 0) {
                    
                    par = getUrlpPar(historylink);
                    
                    versionStr += "?" +versionLable+"="+jcvFileInfo.getFileVersion()+ "&"+par;
                }else {
                    versionStr += "?" +versionLable+"="+jcvFileInfo.getFileVersion();
                }
                
            } else {
                
                String par = "";
                versionStr = jcvFileInfo.getFileVersion() + "." + suffix + "." + jcvFileInfo.getFileType();
                jcvFileInfo.setFinalFileName(versionStr);
                if (historylink.indexOf("?") > 0) {
                    
                    par = getUrlpPar(historylink);
                    
                    versionStr += "?" + par;
                }
            }
            
        } else {
            if (isMd5FileName) {
                
                String par = "";
                versionStr = jcvFileInfo.getFileVersion() + "." + jcvFileInfo.getFileType();
                jcvFileInfo.setFinalFileName(versionStr);
                if (historylink.indexOf("?") > 0) {
                    
                    par = getUrlpPar(historylink);
                    
                    versionStr += "?" + par;
                }
            } else {
                if (historylink.indexOf("?") > 0) {
                    versionStr = "&" + versionLable + "=" + jcvFileInfo.getFileVersion();
                } else {
                    versionStr = "?" + versionLable + "=" + jcvFileInfo.getFileVersion();
                }
            }
        }
         return versionStr;
     }

    public String removeUrlPar(String tempUrl){
        if(tempUrl.indexOf("?")>0){
            String[] split = tempUrl.split("\\?");
            if(split.length==2){
                tempUrl=split[0];
            }
        }
        
        return tempUrl;
    }
    
    public String getUrlpPar(String tempUrl){
        if(tempUrl.indexOf("?")>0){
            String[] split = tempUrl.split("\\?");
            if(split.length==2){
               // tempUrl=split[0];
                return split[1];
            }else {
                return "";
            }
        } 
        return "";
        
    }
    
    
    
    /**
     * 获取
     * 
     * @return jcvs
     */
    public Map<String, JCVFileInfo> getJcvs() {
        return jcvs;
    }
    
    /**
     * 设置
     * 
     * @param jcvs
     */
    public void setJcvs(Map<String, JCVFileInfo> jcvs) {
        this.jcvs = jcvs;
    }
    
   
    
    /**
     * 获取
     * 
     * @return versionLable
     */
    public String getVersionLable() {
        return versionLable;
    }
    
    /**
     * 设置
     * 
     * @param versionLable
     */
    public void setVersionLable(String versionLable) {
        this.versionLable = versionLable;
    }
    
    /**
     * 获取
     * 
     * @return baseJsDomin
     */
    public List<String> getBaseJsDomin() {
        return baseJsDomin;
    }
    
    /**
     * 设置
     * 
     * @param baseJsDomin
     */
    public void setBaseJsDomin(List<String> baseJsDomin) {
        this.baseJsDomin = baseJsDomin;
    }
    
    /**
     * 获取
     * 
     * @return baseCssDomin
     */
    public List<String> getBaseCssDomin() {
        return baseCssDomin;
    }
    
    /**
     * 设置
     * 
     * @param baseCssDomin
     */
    public void setBaseCssDomin(List<String> baseCssDomin) {
        this.baseCssDomin = baseCssDomin;
    }
    
    /**
     * 获取
     * 
     * @return globaJslPrefixPath
     */
    public String getGlobaJslPrefixPath() {
        return globaJslPrefixPath;
    }
    
    /**
     * 设置
     * 
     * @param globaJslPrefixPath
     */
    public void setGlobaJslPrefixPath(String globaJslPrefixPath) {
        this.globaJslPrefixPath = globaJslPrefixPath;
    }
    
    /**
     * 获取
     * 
     * @return globaCsslPrefixPath
     */
    public String getGlobaCsslPrefixPath() {
        return globaCsslPrefixPath;
    }
    
    /**
     * 设置
     * 
     * @param globaCsslPrefixPath
     */
    public void setGlobaCsslPrefixPath(String globaCsslPrefixPath) {
        this.globaCsslPrefixPath = globaCsslPrefixPath;
    }
    
    /**
     * 获取
     * 
     * @return sourceEncoding
     */
    public String getSourceEncoding() {
        return sourceEncoding;
    }
    
    /**
     * 设置
     * 
     * @param sourceEncoding
     */
    public void setSourceEncoding(String sourceEncoding) {
        this.sourceEncoding = sourceEncoding;
    }
    
    /**
     * 获取
     * 
     * @return clearPageComment
     */
    public boolean isClearPageComment() {
        return clearPageComment;
    }
    
    /**
     * 设置
     * 
     * @param clearPageComment
     */
    public void setClearPageComment(boolean clearPageComment) {
        this.clearPageComment = clearPageComment;
    }
    
    /**
     * 获取
     * 
     * @return log
     */
    public Log getLog() {
        return log;
    }
    
    /**
     * 设置
     * 
     * @param log
     */
    public void setLog(Log log) {
        this.log = log;
    }

    /**
     * 获取  
     * @return jsEn
     */
    public JCVMethodEnum getJsEn() {
        return jsEn;
    }

    /**
     * 设置 
     * @param jsEn 
     */
    public void setJsEn(JCVMethodEnum jsEn) {
        this.jsEn = jsEn;
    }

    /**
     * 获取  
     * @return cssEn
     */
    public JCVMethodEnum getCssEn() {
        return cssEn;
    }

    /**
     * 设置 
     * @param cssEn 
     */
    public void setCssEn(JCVMethodEnum cssEn) {
        this.cssEn = cssEn;
    }

    /**
     * 获取 压缩css 
     * @return compressionCss
     */
    public boolean isCompressionCss() {
        return compressionCss;
    }

    /**
     * 设置 压缩css
     * @param compressionCss 压缩css
     */
    public void setCompressionCss(boolean compressionCss) {
        this.compressionCss = compressionCss;
    }

    /**
     * 获取 压缩js 
     * @return compressionJs
     */
    public boolean isCompressionJs() {
        return compressionJs;
    }

    /**
     * 设置 压缩js
     * @param compressionJs 压缩js
     */
    public void setCompressionJs(boolean compressionJs) {
        this.compressionJs = compressionJs;
    }

    /**
     * 获取 压缩文件后缀 
     * @return userCompressionSuffix
     */
    public String getUserCompressionSuffix() {
        return userCompressionSuffix;
    }

    /**
     * 设置 压缩文件后缀
     * @param userCompressionSuffix 压缩文件后缀
     */
    public void setUserCompressionSuffix(String userCompressionSuffix) {
        this.userCompressionSuffix = userCompressionSuffix;
    }

    /**
     * 获取 处理成功的全部文件 
     * @return processFiles
     */
    public List<JCVFileInfo> getProcessFiles() {
        return processFiles;
    }

    /**
     * 设置 处理成功的全部文件
     * @param processFiles 处理成功的全部文件
     */
    public void setProcessFiles(List<JCVFileInfo> processFiles) {
        this.processFiles = processFiles;
    }

    /**
     * 获取 排除js文件(只支持全路径匹配，如果是文件夹那么该文件夹下全部将会忽略) 
     * @return excludesJs
     */
    public List<String> getExcludesJs() {
        return excludesJs;
    }

    /**
     * 设置 排除js文件(只支持全路径匹配，如果是文件夹那么该文件夹下全部将会忽略)
     * @param excludesJs 排除js文件(只支持全路径匹配，如果是文件夹那么该文件夹下全部将会忽略)
     */
    public void setExcludesJs(List<String> excludesJs) {
        this.excludesJs = excludesJs;
    }

    /**
     * 获取 排除css文件(只支持全路径匹配，如果是文件夹那么该文件夹下全部将会忽略) 
     * @return excludesCss
     */
    public List<String> getExcludesCss() {
        return excludesCss;
    }

    /**
     * 设置 排除css文件(只支持全路径匹配，如果是文件夹那么该文件夹下全部将会忽略)
     * @param excludesCss 排除css文件(只支持全路径匹配，如果是文件夹那么该文件夹下全部将会忽略)
     */
    public void setExcludesCss(List<String> excludesCss) {
        this.excludesCss = excludesCss;
    }
    
    
}
