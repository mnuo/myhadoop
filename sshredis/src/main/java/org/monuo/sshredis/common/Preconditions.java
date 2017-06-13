/**
 * Preconditions.java created at 2016年10月21日 下午5:24:04
 */
package org.monuo.sshredis.common;

/**
 * @author saxon
 */
public class Preconditions {
	public static void checkIsNotNull(Object obj){
		assert(obj!=null);
	}
}
