package com.monuo.jedis;

import java.util.HashSet;
import java.util.Set;

import com.monuo.jedis.util.ShardedJedisPoolManager;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.util.JedisClusterCRC16;

public class ShardedJedisDemo {

	private static JedisCluster jc = null;

	public static void main(String[] args) {
		clusterTest2();
	}

	public static void cluserTest1(){
		String key = "2";  
		// 这东西 可以直接看到key 的分片数，就能知道放哪个 节点  
		System.out.println(JedisClusterCRC16.getSlot(key));  
		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();  
		jedisClusterNodes.add(new HostAndPort("192.168.159.132", 7000));  
		jedisClusterNodes.add(new HostAndPort("192.168.159.134", 7001));  
		jedisClusterNodes.add(new HostAndPort("192.168.159.135", 7002));  
		jc  = new JedisCluster(jedisClusterNodes);  
		System.out.println(jc .get(key));  
		jc .setnx(key, "bar");  
		String value = jc .get(key);  
		System.out.println(value);  
	}
	public static void clusterTest2(){
		jc = ShardedJedisPoolManager.getMgr().getJedisCluster();
		for (int i = 1; i <= 10000; i++) {  
	         long start = System.currentTimeMillis();  
	         jc.set("k:" + i, "v" + i);  
	         System.out.print("set " + i +"th value in " + (System.currentTimeMillis() - start) + " ms");  
	         start = System.currentTimeMillis();  
	         String value = jc.get("k:" + i);  
	         System.out.println(", get " + i +"th value: " + value + " in " + (System.currentTimeMillis() - start) + " ms");  
	     }  
	}
}
