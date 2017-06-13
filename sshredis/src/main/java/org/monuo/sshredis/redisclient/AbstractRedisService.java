/**
 * AbstractRedisService.java created at 2017年6月13日 上午10:58:57
 */
package org.monuo.sshredis.redisclient;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author saxon
 */
public abstract class AbstractRedisService<K, V> implements IRedisService<K, V> {
     private RedisTemplate<K, V> redisTemplate;  
    
     public RedisTemplate<K, V> getRedisTemplate() {  
         return redisTemplate;  
     }  
    
     public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {  
         this.redisTemplate = redisTemplate;  
     }  
      
     @Override  
     public void set(final K key, final V value, final long expiredTime) {  
         BoundValueOperations<K, V> valueOper = redisTemplate.boundValueOps(key);  
         if (expiredTime <= 0) {  
             valueOper.set(value);  
         } else {  
             valueOper.set(value, expiredTime, TimeUnit.MILLISECONDS);  
         }  
     }  
    
     @Override  
     public V get(final K key) {  
         BoundValueOperations<K, V> valueOper = redisTemplate.boundValueOps(key);  
         return valueOper.get();  
     } 
     
     @Override 
     public Object getHash(K key, String name){
         Object res = redisTemplate.boundHashOps(key).get(name);
         return res;
     }
     @Override  
     public void del(K key) {  
         if (redisTemplate.hasKey(key)) {  
             redisTemplate.delete(key);  
         }  
     }
     public List<V> getList(final K key){
         ListOperations<K, V> list = redisTemplate.opsForList();
         return list.range(key, 0, -1);
     }
     
     public V getListPop(final K key){
    	 return redisTemplate.opsForList().leftPop(key);
     }
     public synchronized void addList(K key, V value){
    	 redisTemplate.opsForList().leftPush(key, value);
     }
     public synchronized void addHash(K key, HashMap<K, V> map){
         redisTemplate.opsForHash().putAll(key, map);
     }
     public void deleteListItem(K key, V value){
    	 redisTemplate.opsForList().remove(key, 0, value);
     }
     public synchronized void putHash(K key, HashMap<K, V> map){
    	 redisTemplate.boundHashOps(key).putAll(map);
     }
}
