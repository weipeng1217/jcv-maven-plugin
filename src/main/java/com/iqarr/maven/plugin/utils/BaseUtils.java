package com.iqarr.maven.plugin.utils;

import java.util.List;

/**
 * @Package
 *          com.iqarr.maven.plugin.utils
 * @ClassName:
 *             BaseUtils
 * @since
 *        V1.0
 * @author
 *         zhangyong
 * @date
 *       2017/01/17-14:42:11
 * @version
 *          V1.0
 */
public class BaseUtils {
    
    /**
     * 
     * 比较数组是否相等
     * 
     * @param source
     * @param startIndex
     * @param comparisonSource
     * @return
     */
    public static boolean comparisonCharArray(final char[] source, final int startIndex, final char[] comparisonSource) {
        
        int sourceLenth = source.length;
        int comLenth = comparisonSource.length;
        
        for (int i = 0; i < comLenth; i++) {
            if (sourceLenth <= (i + startIndex)) {
                return false;
            } else {
                if (source[startIndex + i] == comparisonSource[i]) {
                    if (i == (comLenth - 1)) {
                        return true;
                    } else {
                        continue;
                    }
                } else {
                    return false;
                }
            }
        }
        
        return false;
    }
    
    /**
     * 
     * 比较数组是否相等(向上比较)
     * 
     * @param source
     * @param startIndex
     * @param comparisonSource
     * @return
     */
    public static boolean comparisonUpCharArray(final char[] source, final int startIndex, final char[] comparisonSource) {
        
        int sourceLenth = source.length;
        int comLenth = comparisonSource.length;
        
        for (int i = 0,j=comLenth-1;(i<sourceLenth-startIndex && j>=0); i++,j--) {
          
                if (source[startIndex - i] == comparisonSource[j]) {
                    if (j==0) {
                        return true;
                    } else {
                        continue;
                    }
                } else {
                    return false;
                }
            }
        
        
        return false;
    }
    
    /**
     * 
     * 检查下一个字符串位置
     * 
     * @param cas
     * @param index
     * @param chars
     * @return
     */
    public static int checkNextStrIndex(final char[] cas, final int index, final String chars) {
        
        char[] sub = chars.toCharArray();
        if (sub.length < 0) {
            return -1;
        }
        for (int i = index; i < cas.length; i++) {
            
            if (cas[i] == sub[0]) {
                for (int subI = 0; subI < sub.length; subI++) {
                    if (cas[i + subI] != sub[subI]) {
                        break;
                    } else {
                        if (subI == sub.length - 1) {
                            return i;
                        }
                    }
                }
            }
        }
        
        return -1;
    }
    
    /**
     * 
     * 检查上一个字符的位置
     * 
     * @param cas
     * @param index
     * @param chars
     * @return
     */
    public static int checkUpStrIndex(final char[] cas, final int index, final String chars) {
        char[] sub = chars.toCharArray();
        if (sub.length < 0) {
            return -1;
        }
        for (int i = index; i >= 0; i--) {
            
            if (cas[i] == sub[0]) {
                for (int subI = 0; subI < sub.length; subI++) {
                    if (cas[i + subI] != sub[subI]) {
                        break;
                    } else {
                        if (subI == sub.length - 1) {
                            return i;
                        }
                    }
                }
            }
        }
        
        return -1;
    }
    
    public static int checkNextCharIndex(final char[] cas, final int index, final char[] sub) {
        
        for (int i = index; i < cas.length; i++) {
            if (cas[i] == sub[0]) {
                for (int subI = 0; subI < sub.length; subI++) {
                    if (cas[i + subI] != sub[subI]) {
                        break;
                    } else {
                        if (subI == sub.length - 1) {
                            return i;
                        }
                    }
                }
            }
        }
        
        return -1;
    }
    /**
     * 
     * 检查下一个字符位置
     * @param cas
     * @param index
     * @param cr
     * @return
     */
    public static int checkNextCharIndex(final char[] cas, final int index, final char cr) {
        char[] sub = new char[1];
        sub[0] = cr;
        return checkNextCharIndex(cas, index, sub);
    }
    
    /**
     * 
     * 检查str是否在list中
     * @param checkStr
     * @param list
     * @param isLike  是否模糊匹配（暂时未实现）
     * @return true 在
     */
    public static boolean checkStrIsInList(final String checkStr,final List<String> list,final boolean isLike){
        if(null==checkStr || "".equals(checkStr) ){
            return false;
        }
        if(list ==null){
            return false;
        }
        if(isLike){
            //模糊
            return list.contains(checkStr);
        }else {
            return list.contains(checkStr);
        }
        
        
    }
    
    
}
