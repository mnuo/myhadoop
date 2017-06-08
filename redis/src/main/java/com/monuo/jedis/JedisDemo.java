/**
 * JedisDemo.java created at 2017年6月8日 下午5:08:08
 */
package com.monuo.jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.monuo.jedis.util.JedisPoolManager;

import redis.clients.jedis.Jedis;

/**
 * @author saxon
 */
public class JedisDemo {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Jedis jedis = null;
		try {
			jedis = JedisPoolManager.getInstance().getResource();
			jedis.auth("123123");
			
			//simple key - value
			jedis.set("redis", "myredis");
			System.out.println(jedis.get("redis"));
			
			jedis.append("redis", "yourredis");
			jedis.append("mq", "RabitMq");
			System.out.println("mq: " + jedis.get("redis"));
			
			//incr
			String pv = jedis.set("pv", "0");
			System.out.println("pv: " + pv);
			jedis.incr("pv");
			jedis.incrBy("pv", 10);
			System.out.println("pv: " + pv);
			
			//mset
			jedis.mset("firstname", "ricky", "lastname", "Fung");
			System.out.println(jedis.mget("firstname", "lastname"));
			
			//map
			Map<String, String> city = new HashMap<>();
			city.put("beijing", "1");
			city.put("shanghai", "2");
			
			jedis.hmset("city", city);
			System.out.println(jedis.hget("city", "beijing"));
	        System.out.println(jedis.hlen("city"));
	        System.out.println(jedis.hmget("city", "beijing","shanghai"));
	        
	        //list
	        jedis.lpush("hobbies", "reading");
	        jedis.lpush("hobbies", "basketball");
	        jedis.lpush("hobbies", "shopping");
	        
	        List<String> hobbies = jedis.lrange("hobbies", 0, -1);
	        System.out.println("hobbies: " + hobbies);
			
	        //set 
	        jedis.sadd("name", "ricky");
	        jedis.sadd("name", "kings");
	        jedis.sadd("name", "demon");
	        
	        System.out.println("size:"+jedis.scard("name"));
	        System.out.println("exists:"+jedis.sismember("name", "ricky"));
	        System.out.println(String.format("all members: %s", jedis.smembers("name")));
	        System.out.println(String.format("rand member: %s", jedis.srandmember("name")));

	        //remove
	        jedis.srem("name", "demon");
	        
	        //hset
			jedis.hset("address", "country", "CN");
			jedis.hset("address", "province", "BJ");
			jedis.hset("address", "city", "Beijing");
			jedis.hset("address", "district", "Chaoyang");
			
			System.out.println("city:"+jedis.hget("address", "city"));
			System.out.println("keys:"+jedis.hkeys("address"));
			System.out.println("values:"+jedis.hvals("address"));
			
			  //zadd
			jedis.zadd("gift", 0, "car"); 
			jedis.zadd("gift", 0, "bike"); 
			Set<String> gift = jedis.zrange("gift", 0, -1);
			System.out.println("gift:"+gift);
		} finally {
			if(jedis != null) {
	          jedis.close();
	        }
	    }
	    JedisPoolManager.getInstance().destroy();
	}

}
