/**
 * JedisPipelineDemo.java created at 2017年6月9日 上午11:03:36
 */
package com.monuo.jedis;

import java.util.Set;

import com.monuo.jedis.util.JedisPoolManager;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.ZParams;

/**
 * @author saxon
 */
public class JedisPipelineDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Jedis jedis = null;
		try {
			jedis = JedisPoolManager.getInstance().getResource();
			jedis.auth("123123");
			Pipeline p = jedis.pipelined();
			p.set("fool", "bar");
			p.zadd("foo", 1, "barowitch");
			p.zadd("foo", 0, "barinasky");
			p.zadd("foo", 0, "barikoviev");
			Response<String> pipeString = p.get("fool");
			Response<Set<String>> setpipe = p.zrange("foo", 0, -1);
			p.sync();
			int setsize = setpipe.get().size();
			System.out.println("fool: " + pipeString.get());
			System.out.println("foo size: " + setsize);
			Set<String> set = setpipe.get();
			
			for(String string : set){
				System.out.println(string);
			}
			
		} finally {
			if(jedis != null) jedis.close();
		}
		JedisPoolManager.getInstance().getResource();
	}

}
