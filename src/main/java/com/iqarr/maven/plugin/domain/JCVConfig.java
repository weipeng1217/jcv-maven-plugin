package com.iqarr.maven.plugin.domain;

import java.util.List;

/**
 * jcv配置信息
 * 
 * @Package
 *          com.iqarr.maven.plugin.domain
 * @ClassName:
 *             JCVConfig
 * @since
 *        V1.0
 * @author
 *         zhangyong
 * @date
 *       2017/03/16-16:00:40
 * @version
 *          V1.0
 */
public class JCVConfig {
	
	/**
	 * 处理的页面后缀
	 */
	private List<String> pageSuffixs;
	
	/**
	 * js 使用方法
	 */
	private JCVMethodEnum jsMethod;
	
	/**
	 * css使用方法
	 */
	private JCVMethodEnum cssMethod;
	
	/**
	 * 版本号标签
	 */
	private String versionLable;
	
	/**
	 * 基本的js域名
	 */
	private List<String> baseJsDomin;
	/**
	 * 基本css域名
	 */
	private List<String> baseCssDomin;
	
	/**
	 * 全局js　头部适合于　/${demoApp}/js/demo.js
	 */
	private String globaJslPrefixPath;
	/**
	 * 全局css　头部适合于　/${demoApp}/css/demo.css
	 */
	private String globaCsslPrefixPath;
	
	/**
	 * 文件编码
	 */
	private String sourceEncoding;
	
	/**
	 * 清理页面注释
	 */
	private boolean clearPageComment;
	

	/**
	 * 输出js Css 目录
	 */
	private String outJSCSSDirPath;
	
	// version 0.0.2
	
	/** 压缩css **/
	private boolean compressionCss;
	
	/** 压缩js **/
	private boolean compressionJs;
	
	/** 压缩文件后缀 **/
	private String userCompressionSuffix;
	
	/** 排除js文件(只支持全路径匹配，如果是文件夹那么该文件夹下全部将会忽略) **/
	private List<String> excludesJs;
	
	/** 排除css文件(只支持全路径匹配，如果是文件夹那么该文件夹下全部将会忽略) **/
	private List<String> excludesCss;
	
	// version 0.0.3
	
	/**
	 * yui cinfig
	 */
	private YUIConfig yuiConfig;
	/**
	 * 跳过文件后缀
	 */
	private String skipFileNameSuffix;
	
	
	//===============================================================================================
	
	/**
	 * 获取 js 使用方法 
	 * @return jsMethod
	 */
	public JCVMethodEnum getJsMethod() {
		return jsMethod;
	}
	/**
	 * 设置 js 使用方法
	 * @param jsMethod js 使用方法
	 */
	public void setJsMethod(JCVMethodEnum jsMethod) {
		this.jsMethod = jsMethod;
	}
	/**
	 * 获取 css使用方法 
	 * @return cssMethod
	 */
	public JCVMethodEnum getCssMethod() {
		return cssMethod;
	}
	/**
	 * 设置 css使用方法
	 * @param cssMethod css使用方法
	 */
	public void setCssMethod(JCVMethodEnum cssMethod) {
		this.cssMethod = cssMethod;
	}
	/**
	 * 获取 版本号标签 
	 * @return versionLable
	 */
	public String getVersionLable() {
		return versionLable;
	}
	/**
	 * 设置 版本号标签
	 * @param versionLable 版本号标签
	 */
	public void setVersionLable(String versionLable) {
		this.versionLable = versionLable;
	}
	/**
	 * 获取 基本的js域名 
	 * @return baseJsDomin
	 */
	public List<String> getBaseJsDomin() {
		return baseJsDomin;
	}
	/**
	 * 设置 基本的js域名
	 * @param baseJsDomin 基本的js域名
	 */
	public void setBaseJsDomin(List<String> baseJsDomin) {
		this.baseJsDomin = baseJsDomin;
	}
	/**
	 * 获取 基本css域名 
	 * @return baseCssDomin
	 */
	public List<String> getBaseCssDomin() {
		return baseCssDomin;
	}
	/**
	 * 设置 基本css域名
	 * @param baseCssDomin 基本css域名
	 */
	public void setBaseCssDomin(List<String> baseCssDomin) {
		this.baseCssDomin = baseCssDomin;
	}
	/**
	 * 获取 全局js　头部适合于　${demoApp}jsdemo.js 
	 * @return globaJslPrefixPath
	 */
	public String getGlobaJslPrefixPath() {
		return globaJslPrefixPath;
	}
	/**
	 * 设置 全局js　头部适合于　${demoApp}jsdemo.js
	 * @param globaJslPrefixPath 全局js　头部适合于　${demoApp}jsdemo.js
	 */
	public void setGlobaJslPrefixPath(String globaJslPrefixPath) {
		this.globaJslPrefixPath = globaJslPrefixPath;
	}
	/**
	 * 获取 全局css　头部适合于　${demoApp}cssdemo.css 
	 * @return globaCsslPrefixPath
	 */
	public String getGlobaCsslPrefixPath() {
		return globaCsslPrefixPath;
	}
	/**
	 * 设置 全局css　头部适合于　${demoApp}cssdemo.css
	 * @param globaCsslPrefixPath 全局css　头部适合于　${demoApp}cssdemo.css
	 */
	public void setGlobaCsslPrefixPath(String globaCsslPrefixPath) {
		this.globaCsslPrefixPath = globaCsslPrefixPath;
	}
	/**
	 * 获取 文件编码 
	 * @return sourceEncoding
	 */
	public String getSourceEncoding() {
		return sourceEncoding;
	}
	/**
	 * 设置 文件编码
	 * @param sourceEncoding 文件编码
	 */
	public void setSourceEncoding(String sourceEncoding) {
		this.sourceEncoding = sourceEncoding;
	}
	/**
	 * 获取 清理页面注释 
	 * @return clearPageComment
	 */
	public boolean isClearPageComment() {
		return clearPageComment;
	}
	/**
	 * 设置 清理页面注释
	 * @param clearPageComment 清理页面注释
	 */
	public void setClearPageComment(boolean clearPageComment) {
		this.clearPageComment = clearPageComment;
	}
	/**
	 * 获取 输出js Css 目录 
	 * @return outJSCSSDirPath
	 */
	public String getOutJSCSSDirPath() {
		return outJSCSSDirPath;
	}
	/**
	 * 设置 输出js Css 目录
	 * @param outJSCSSDirPath 输出js Css 目录
	 */
	public void setOutJSCSSDirPath(String outJSCSSDirPath) {
		this.outJSCSSDirPath = outJSCSSDirPath;
	}
	/**
	 * 获取 version 0. 
	 * @return compressionCss
	 */
	public boolean isCompressionCss() {
		return compressionCss;
	}
	/**
	 * 设置 version 0.
	 * @param compressionCss version 0.
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
	/**
	 * 获取 version 0.0.3 
	 * @return yuiConfig
	 */
	public YUIConfig getYuiConfig() {
		return yuiConfig;
	}
	/**
	 * 设置 version 0.0.3
	 * @param yuiConfig version 0.0.3
	 */
	public void setYuiConfig(YUIConfig yuiConfig) {
		this.yuiConfig = yuiConfig;
	}
	/**
	 * 获取 跳过文件后缀 
	 * @return skipFileNameSuffix
	 */
	public String getSkipFileNameSuffix() {
		return skipFileNameSuffix;
	}
	/**
	 * 设置 跳过文件后缀
	 * @param skipFileNameSuffix 跳过文件后缀
	 */
	public void setSkipFileNameSuffix(String skipFileNameSuffix) {
		this.skipFileNameSuffix = skipFileNameSuffix;
	}
	/**
	 * 获取 处理的页面后缀 
	 * @return pageSuffixs
	 */
	public List<String> getPageSuffixs() {
		return pageSuffixs;
	}
	/**
	 * 设置 处理的页面后缀
	 * @param pageSuffixs 处理的页面后缀
	 */
	public void setPageSuffixs(List<String> pageSuffixs) {
		this.pageSuffixs = pageSuffixs;
	}
	
	
	
}
