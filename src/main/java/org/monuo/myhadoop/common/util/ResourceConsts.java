/**
 * ResourceBundle.java created at 2017年5月18日 上午10:19:03
 */
package org.monuo.myhadoop.common.util;

import java.util.ResourceBundle;

/**
 * @author saxon
 */
public class ResourceConsts {
	public static String getValue(String applicationContext, String key) throws Exception{
		try {
			ResourceBundle resourceBundle = ResourceBundle.getBundle(applicationContext);
			return resourceBundle.getString(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
