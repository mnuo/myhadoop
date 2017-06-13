/**
 * StringUtils.java created at 2017年6月13日 下午1:25:41
 */
package org.monuo.sshredis.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author saxon
 */
public class StringUtils {
	public static String replaceBlank(String str) {  
        String dest = "";  
        if (str!=null) {  
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");  
            Matcher m = p.matcher(str);  
            dest = m.replaceAll("");  
        }  
        return dest;  
    }  
}
