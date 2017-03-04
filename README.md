## <center>jcv-maven-plugin



***

### 简介

`jcv-maven-plugin`是js css版本号工具

# Quick Start

## 引入maven依赖

```xml
			<plugin>
				<groupId>com.iqarr.maven.plugin</groupId>
				<artifactId>jcv-maven-plugin</artifactId>
				<version>0.0.X</version>
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
 * ```xml
   <suffixs>
		<param>html</param>
		<param>jsp</param>
	</suffixs>
 ```

4. `baseJsDomin`
 * 基本js域名,在使用`<script src="http://script.iqarr.com/js/jquery/jquery/1.8.3/jquery.js"></script>`这种方式需要配置
 * 参数:
 * ```xml
   <baseJsDomin>
		<param>http://script.iqarr.com</param>
	</baseJsDomin>
 ```
5. `baseCssDomin`
 * 基本css域名,在使用`<link rel="stylesheet" type="text/css" href="http://style.iqarr.com/css/public.css?" />`这种方式需要配置
 * 参数:
 * ```xml
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
	* ```xml
   <excludesJs>
		<param>js/dome.js</param>
	</excludesJs>
 ```

19. `excludesCss`

	* 排除css文件(只支持全路径匹配)
	* ```xml
   <excludesCss>
		<param>css/dome.css</param>
	</excludesCss>
 ```

20. `yuiConfig`

	* 配置压缩选项
	* ```xml
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

21. `braekFileNameSuffix`

	* 跳过文件名后缀(后缀之前的名称)，例如：　ok.min.js 如果想跳过就需要配置`.min`
	* 默认:`.min`