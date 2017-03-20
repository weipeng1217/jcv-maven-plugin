## <center>jcv-maven-plugin



***

### 简介
`jcv-maven-plugin`是一个自动为网页添加js css的版本号maven插件
 * 支持js css的自动压缩，支持多种方法版本号添加，在使用时对代码0入侵，不需要在页面上做任何标记，对开发友好，不需要调整现在代码．直接引入mvn中配置，会自动对打包的页面进行处理．
 * 该插件自动采集文件的md5值进行文件版本号修订，在使用文件的md5值用于该文件的版本号，因此该插件不会引发js css缓存全部失效（因此不建议使用时间戳的方式）,同时修改的内容又能及时到客户浏览器中去，不会在存在缓存的问题．
 * 支持清理网页上的`<!-- -->`注释，让网页更干净．
 * 所有的操作都不会修改代码，只会对打包文件进行修改．目前已有线上使用．文件名md5的方式可以解决有些浏览器忽略version标签．
 * 目前该插件以发布到mvn中央仓库,可以坐标引用使用.

# Quick Start

## 引入maven依赖

```xml
<plugin>
	<groupId>com.iqarr.maven.plugin</groupId>
	<artifactId>jcv-maven-plugin</artifactId>
	<version>0.0.6</version>
		<executions>
			<execution>
			<id>process</id>
			<phase>package</phase>
				<goals>
					<goal>process</goal>
				</goals>
			</execution>
		</executions>
		<configuration>
			<baseJsDomin>
				<param>http://script.iqarr.com</param>
			</baseJsDomin>
			<baseCssDomin>
				<param>http://style.iqarr.com</param>
				<param>http://script.iqarr.com</param>
			</baseCssDomin>
			<!--需要处理文件后缀-->
			<suffixs>
				<param>html</param>
				<param>jsp</param>
			</suffixs>
			<!--清理页面注释-->
			<clearPageComment>true</clearPageComment>
			<globaJsMethod>MD5FileName_METHOD</globaJsMethod>
			<globaCssMethod>MD5FileName_METHOD</globaCssMethod>
			<!-- 压缩js-->
			<compressionJs>true</compressionJs>
			<!-- 压缩css-->
			<compressionCss>true</compressionCss>
		</configuration>
</plugin>
```

### 配置war插件
```xml
     <plugin>
         <groupId>org.apache.maven.plugins</groupId>
         <artifactId>maven-war-plugin</artifactId>
         <version>2.6</version>
         <configuration>
            <warSourceDirectory>${basedir}/src/main/webapp</warSourceDirectory>
            <encoding>${build.source.encoding}</encoding>
            <!--如果使用md5文件名　就必须排除相应的js css -->
            <!--在使用普通的模式的时候需要配置jsp html -->
            <warSourceExcludes>**/*.html,**/*.jsp,**/*.js,**/*.css</warSourceExcludes>
                 <webResources>
			<resource>
				<directory>${basedir}/src/main/webapp/js/common</directory>
				<includes>
					<include>config.js</include>
				</includes>
				<filtering>true</filtering>
				<targetPath>js/common</targetPath>
			</resource>
		 </webResources>
         </configuration>
       </plugin>
```
### 打包

```
mvn clean package
# 注意该插件不会在eclipse中生效，在package后才会生效
```

### 新版本特性

`version 0.0.6`
  1. 优化整个压缩流程.
  2. 修复部分bug
  3. 添加通用模块支持，支持对php等其他语言处理．
  4. 添加常量名称支持.


### 注意事项

1. 不支持 ../../xxx.js
2. 不支持 ../../xx.css
3. 如果启用js压缩，那么在js中变量定义禁止使用js关键字
4. html 清除注释只支持网页中的`<!-- -->`
5. 插件不会在eclipse中生效，在package后才会生效
6. 注意在使用md5文件名的时候请注意排除一些js动态加载css,如果修改了文件名会导致无法加载到css,因此需要排除掉，目前已知有`kindeditor`,`layer`,`My97DatePicker`



###  参数说明：

1. `outputDirectory`
 * 输出文件目录
 * 默认`${project.build.directory}`

2. `webappDirectory`
 * webapp目录
 * 默认值:`${basedir}/src/main/webapp`

3. `suffixs`
 * 检查文件的后缀
 * 默认`jsp`
 * 参数：

 ```xml
 <suffixs>
	<param>html</param>
	<param>jsp</param>
</suffixs>
 ```

4. `baseJsDomin`
 * 基本js域名,在使用`<script src="http://script.iqarr.com/js/jquery/jquery/1.8.3/jquery.js"></script>`这种方式需要配置
 * 参数:
```xml
  <baseJsDomin>
	<param>http://script.iqarr.com</param>
  </baseJsDomin>
```
5. `baseCssDomin`
 * 基本css域名,在使用`<link rel="stylesheet" type="text/css" href="http://style.iqarr.com/css/public.css?" />`这种方式需要配置
 * 参数:
```xml
   <baseCssDomin>
	<param>http://style.iqarr.com</param>
  </baseCssDomin>
```
6. `globaJslPrefixPath`
 * 全局js path路径
7. `globaCsslPrefixPath`
 * 全局css path路径
8. `globaJsMethod`
 * 全局js版本号使用方法(单个参数)
 * `TIMESTAMP_METHOD`
   > 时间戳方式:　该方式生成为打包时间的时间戳(不建议使用),会在最后添加?`versionLable=`值

 * `MD5_METHOD`
   > md5做为版本号:会在最后添加?`versionLable=`md5值

 * `MD5FileName_METHOD`
  >  md5文件名方式：该方式会将js文件替换为该文件的md5

9. `globaCssMethod`
 * 参考globaJsMethod方式

10. `webRootName`
 * 最终项目名称
 * 默认值:`${project.build.finalName}`

11. `versionLable`
	* 版本号标签:
	* 默认:`version`
12. `sourceEncoding`
	* 文件编码
	* 默认:`UTF-8`

13. `clearPageComment`
	* 清除页面注释，该注释为html `<!-- -->`标准注释的清除
	* 默认:`false`

14. `outJSCSSDirPath`
	* 在使用md5文件名时候使用数据全修改文件后的文件目录(不配置就默认替换当前的文件位置)
15. `compressionCss`

	* 清除css注释，并清理换行
	* 默认:`false`

16. `compressionJs`

	* 清除css注释，并清理换行
	* 默认:`false`

17. `userCompressionSuffix`

	* 压缩文件后缀
	* 默认: min

18. `excludesJs`

	* 排除js文件(只支持全路径匹配)
```xml
   <excludesJs>
	<param>js/dome.js</param>
  </excludesJs>
```

19. `excludesCss`

	* 排除css文件(只支持全路径匹配)
```xml
   <excludesCss>
	<param>css/dome.css</param>
   </excludesCss>
```

20. `yuiConfig`

	* 配置压缩选项
```xml
	<yuiConfig>
		  <!-- 禁止优化(默认false) -->
		  <disableOptimizations></disableOptimizations>
		 <!-- 只压缩, 不对局部变量进行混淆(默认true) -->
		 <nomunge></nomunge>
		 <!-- 保留所有的分号(默认true) -->
		<preserveSemi></preserveSemi>
		<!-- 显示详细信息(默认false) -->
		<verbose></verbose>
	</yuiConfig>
```

21. `skipFileNameSuffix`

	* 跳过文件名后缀(后缀之前的名称)，例如：　ok.min.js 如果想跳过就需要配置`.min`
	* 默认:`.min`
22. `jsPhysicalRootPath`

  * js物理路径目录，该目录是指js路径的root
23. `cssPhysicalRootPath`

  * cssPhysicalRootPath
24. `jsConstantName`

  * js常量名称 `jsConstantName/jspath`
25. `cssConstantName`

  * css 常量名称  `cssConstantName/csspath`
26. webAppRoot

  * app root目录
27. `jsConstantAliasPath`

  * js(注意该目录不是全路径，该路径是指在outJssCssPath+this) 常量对应的输出目录
28. `cssConstantAliasPath`

  * css (注意该目录不是全路径，该路径是指在outJssCssPath+this) 常量输出目录
