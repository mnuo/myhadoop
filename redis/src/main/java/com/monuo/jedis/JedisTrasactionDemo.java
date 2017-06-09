/**
 * JedisTrasactionDemo.java created at 2017年6月9日 上午10:48:10
 */
package com.monuo.jedis;

import com.monuo.jedis.util.JedisPoolManager;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * @author saxon
 */
public class JedisTrasactionDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Jedis jedis = null;
		try {
			jedis = JedisPoolManager.getInstance().getResource();
			jedis.auth("123123");
			jedis.watch("foo");
			Transaction transaction = jedis.multi();
			transaction.set("foo", "bar");//断点到这里，先不执行。然后用redis-cli连接redis，修改foo的值为444，修改完后，该断点往下执行
			transaction.set("you", "you1");
			transaction.exec();//能执行，不会抛异常，但是foo=444，you=you。说明该事务被打断，不执行，设置you=you1失败了
			System.out.println(jedis.get("foo"));
			System.out.println(jedis.get("you"));
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(jedis != null) jedis.close();
		}
		JedisPoolManager.getInstance().destroy();
	}
}
