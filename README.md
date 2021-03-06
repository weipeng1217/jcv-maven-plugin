## <center>jcv-maven-plugin



***

### 简介
`jcv-maven-plugin`是一个自动为网页添加js css的版本号maven插件
 * 支持js css的自动压缩，支持多种方法版本号添加，在使用时对代码0入侵，不需要在页面上做任何标记，对开发友好，不需要调整现在代码．直接引入mvn中配置，会自动对打包的页面进行处理．
 * 该插件自动采集文件的md5值进行文件版本号修订，在使用文件的md5值用于该文件的版本号，因此该插件不会引发js css缓存全部失效（因此不建议使用时间戳的方式）,同时修改的内容又能及时到客户浏览器中去，不会在存在缓存的问题．
 * 支持清理网页上的`<!-- -->`注释，让网页更干净．
 * 所有的操作都不会修改代码，只会对打包文件进行修改．文件名md5的方式可以解决有些浏览器忽略version标签．
 * 目前该插件以发布到mvn中央仓库,可以坐标引用使用.
 * 添加通用支持，不论php等其他语言也同样可以使用，使用详细见`sh/start.sh`

# Quick Start

## 引入maven依赖

```xml
<plugin>
	<groupId>com.iqarr.maven.plugin</groupId>
	<artifactId>jcv-maven-plugin</artifactId>
	<version>1.0.0</version>
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
			<globaJsMethod>MD5_METHOD</globaJsMethod>
			<globaCssMethod>MD5_METHOD</globaCssMethod>
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
            <!--如果使用md5文件名　就必须排除相应的js css  ,**/*.js,**/*.css-->
            <!--在使用普通的模式的时候需要配置jsp html -->
            <warSourceExcludes>**/*.html,**/*.jsp</warSourceExcludes>
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

`version 1.0.0`
  1. 支持截取指定长度的version.
  
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
7. js css文件编码必须utf-8
8. jdk version >=1.7 ,对于需要支持1.6的版本可以自己编译安装到本地仓库就可以

### 参数说明

详细参数说明: [参数说明](/README_PARAMETER.md "参数说明")
