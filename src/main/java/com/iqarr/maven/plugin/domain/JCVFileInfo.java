package com.iqarr.maven.plugin.domain;

import java.io.File;

/**  
* @Package 文件信息
*	 com.iqarr.maven.plugin.domain
* @ClassName: 
*	 JCVFileInfo  
* @since 
*	  V1.0
* @author 
*		zhangyong   
* @version 
*		V1.0      
*/
public class JCVFileInfo {
    
    /**
     * css
     */
    public static final String CSS="css"; //css
    /** js**/
    public static final String JS="js";
    
    /**相对文件路径  去除了文件webroot 和全部前缀路径 **/
    private String relativelyFilePath;
    
    /** 文件版本号**/
    private String fileVersion;
    
    /**文件类型 **/
    private String fileType;
    /** 文件名称**/
    private String fileName;
    
    /** 文件对象**/
    private File file;
    
    /** 输出文件对象**/
   // private File outFile;
    
    /**输出最终文件名　**/
    private String finalFileName;
    
    /**文件是否被复制 **/
    private boolean isCopy=false;
    
    /** 文件hash 值**/
    private String fileHashKey;
    /**
     * 获取 相对文件路径 
     * @return relativelyFilePath
     */
    public String getRelativelyFilePath() {
        return relativelyFilePath;
    }

    /**
     * 设置 相对文件路径
     * @param relativelyFilePath 相对文件路径
     */
    public void setRelativelyFilePath(String relativelyFilePath) {
        this.relativelyFilePath = relativelyFilePath;
    }

    /**
     * 获取 文件版本号 
     * @return fileVersion
     */
    public String getFileVersion() {
        return fileVersion;
    }

    /**
     * 设置 文件版本号
     * @param fileVersion 文件版本号
     */
    public void setFileVersion(String fileVersion) {
        this.fileVersion = fileVersion;
    }

    /**
     * 获取 文件类型 
     * @return fileType
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * 设置 文件类型
     * @param fileType 文件类型
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * 获取 文件名称 
     * @return fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置 文件名称
     * @param fileName 文件名称
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 获取 文件对象 
     * @return file
     */
    public File getFile() {
        return file;
    }

    /**
     * 设置 文件对象
     * @param file 文件对象
     */
    public void setFile(File file) {
        this.file = file;
    }

  
   

    /*
    * <p>Title: equals</p>  
    * <p>Description: </p>  
    * @param obj
    * @return  
    * @see java.lang.Object#equals(java.lang.Object)  
    */
    
    /**
     * 获取 文件是否被复制 
     * @return isCopy
     */
    public boolean isCopy() {
        return isCopy;
    }

    /**
     * 设置 文件是否已被复制
     * @param isCopy 文件是否已被复制
     */
    public void setCopy(boolean isCopy) {
        this.isCopy = isCopy;
    }

    /**
     * 获取 输出文件对象 
     * @return finalFileName
     */
    public String getFinalFileName() {
        return finalFileName;
    }

    /**
     * 设置 输出文件对象
     * @param finalFileName 输出文件对象
     */
    public void setFinalFileName(String finalFileName) {
        this.finalFileName = finalFileName;
    }
    
    

    /**
	 * 获取 文件hash 值 
	 * @return fileHashKey
	 */
	public String getFileHashKey() {
		return fileHashKey;
	}

	/**
	 * 设置 文件hash 值
	 * @param fileHashKey 文件hash 值
	 */
	public void setFileHashKey(String fileHashKey) {
		this.fileHashKey = fileHashKey;
	}

	@Override
    public boolean equals(Object obj) {
        if(obj==null){
            return false;
        }
        
        if( !(obj instanceof JCVFileInfo)){
            return false;
        }
        JCVFileInfo ob=(JCVFileInfo)obj;
        
        if(!fileType.equals(ob.fileType)){
            return false;
        }
        
        if(!relativelyFilePath.equals(ob.relativelyFilePath)){
            return false;
        }
        
        if(!fileVersion.equals(ob.fileVersion)){
            return false;
        }
        
        return true;
    }

	/*
	* <p>Title: toString</p>  
	* <p>Description: </p>  
	* @return  
	* @see java.lang.Object#toString()  
	*/
	
	@Override
	public String toString() {
		return "JCVFileInfo [relativelyFilePath=" + relativelyFilePath + ", fileVersion=" + fileVersion + ", fileType="
		                + fileType + ", fileName=" + fileName + ", file=" + file + ", finalFileName=" + finalFileName
		                + ", isCopy=" + isCopy + ", fileHashKey=" + fileHashKey + "]";
	}

  
    
}
