#!/bin/sh


suffixs="html,"
globaJsMethod="MD5FileName_METHOD"
globaCssMethod="MD5FileName_METHOD"
versionLable="version"
baseCssDomin=""
baseJsDomin=""
globaCsslPrefixPath=""
globaJslPrefixPath=""
sourceEncoding="utf-8"
clearPageComment="true"
compressionCss="true"
compressionJs="true"
userCompressionSuffix="min"
excludesCss=""
excludesCss=""
# --------------------------------------------------------
outJsCssRoot="/home/user/桌面/test/woyaoabng/out/jscss"
jsPhysicalRootPath="/home/user/桌面/test/woyaoabng/woyaobang/Public/Home/js/"
cssPhysicalRootPath="/home/user/桌面/test/woyaoabng/woyaobang/Public/Home/css/"
jsConstantName="__JS__"
cssConstantName="__CSS__"
webAppRoot="/home/user/桌面/test/woyaoabng/woyaobang/"
outDirRoot="/home/user/桌面/test/woyaoabng/out/"

arge="suffixs:${suffixs} globaJsMethod:${globaJsMethod} globaCssMethod:${globaCssMethod} versionLable:${versionLable} baseCssDomin:${baseCssDomin} baseJsDomin:${baseJsDomin} globaCsslPrefixPath:${globaCsslPrefixPath} globaJslPrefixPath:${globaJslPrefixPath} sourceEncoding:${sourceEncoding} clearPageComment:${clearPageComment} compressionCss:${compressionCss} compressionJs:${compressionJs} userCompressionSuffix:${userCompressionSuffix} excludesCss:${excludesCss} excludesJs:${excludesJs} outJsCssRoot:${outJsCssRoot} jsPhysicalRootPath:${jsPhysicalRootPath} cssPhysicalRootPath:${cssPhysicalRootPath} jsConstantName:${jsConstantName} cssConstantName:${cssConstantName} webAppRoot:${webAppRoot} outDirRoot:${outDirRoot} "
echo ${arge}

#java -Xdebug -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=y -Djava.ext.dirs=lib/ com.iqarr.maven.main.StartMain  $arge
java  -classpath lib/jcv-maven-plugin-0.0.5.jar:lib/js-1.7R2.jar com.iqarr.maven.main.StartMain   $arge