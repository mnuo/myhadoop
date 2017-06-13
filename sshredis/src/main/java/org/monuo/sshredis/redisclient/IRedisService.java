/**
 * IRedisService.java created at 2017年6月13日 上午10:57:59
 */
package org.monuo.sshredis.redisclient;

/**
 * @author saxon
 */
public interface IRedisService<K, V> {
	public void set(K key, V value, long expiredTime);  
    public V get(K key);
    public Object getHash(K key, String name);
    public void del(K key); 
}
