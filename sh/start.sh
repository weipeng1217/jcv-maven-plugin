#!/bin/sh


DEPLOY_DIR=`pwd`
LIB_DIR=$DEPLOY_DIR/lib
LIB_JARS=`ls $LIB_DIR|grep .jar|awk '{print "'$LIB_DIR'/"$0}'|tr "\n" ":"`
if [ "$1" = "debug" ]; then
    JAVA_DEBUG_OPTS=" -Xdebug -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=y"
fi
#------config 基本信息
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
# 多个英文逗号分隔,
excludesJs=""
# 多个英文逗号分隔,
excludesCss=""
# --------------------------------------------------------
outJsCssRoot="/home/user/桌面/test/test/out/"
jsPhysicalRootPath="/home/user/桌面/test/test/test/Public/Home/js/"
cssPhysicalRootPath="/home/user/桌面/test/test/test/Public/Home/css/"
jsConstantName="__JS__"
cssConstantName="__CSS__"
jsConstantAliasPath="Public/Home/js/"
cssConstantAliasPath="Public/Home/css/"
webAppRoot="/home/user/桌面/test/test/test/"
outDirRoot="/home/user/桌面/test/test/out/"
#----------------------cinfig end

arge="suffixs:${suffixs} globaJsMethod:${globaJsMethod} globaCssMethod:${globaCssMethod} versionLable:${versionLable} baseCssDomin:${baseCssDomin} baseJsDomin:${baseJsDomin} globaCsslPrefixPath:${globaCsslPrefixPath} globaJslPrefixPath:${globaJslPrefixPath} sourceEncoding:${sourceEncoding} clearPageComment:${clearPageComment} compressionCss:${compressionCss} compressionJs:${compressionJs} userCompressionSuffix:${userCompressionSuffix} excludesCss:${excludesCss} excludesJs:${excludesJs} outJsCssRoot:${outJsCssRoot} jsPhysicalRootPath:${jsPhysicalRootPath} cssPhysicalRootPath:${cssPhysicalRootPath} jsConstantName:${jsConstantName} cssConstantName:${cssConstantName} webAppRoot:${webAppRoot} outDirRoot:${outDirRoot} jsConstantAliasPath:${jsConstantAliasPath} cssConstantAliasPath:${cssConstantAliasPath} "
echo ${arge}

java $JAVA_DEBUG_OPTS  -classpath $CONF_DIR:$LIB_JARS com.iqarr.maven.main.StartMain   $arge