/**
 * JedisSubsribeDemo.java created at 2017年6月9日 上午11:14:15
 */
package com.monuo.jedis;

import com.monuo.jedis.util.JedisPoolManager;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * @author saxon
 */
public class JedisSubsribeDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Jedis jedis = null;
		try {
			jedis = JedisPoolManager.getInstance().getResource();
			jedis.auth("123123");
			MyListener l = new MyListener();
			jedis.subscribe(l, "foo");
		}finally {
			if(jedis != null) jedis.close();
		}
		JedisPoolManager.getInstance().getResource();
	}

}
class MyListener extends JedisPubSub {
    public void onMessage(String channel, String message) {
    	System.out.println("onMessage: channel: " + channel + " message: " + message);
    }

    public void onSubscribe(String channel, int subscribedChannels) {
    	System.out.println("onSubscribe: channel: " + channel + " subscribedChannels: " + subscribedChannels);
    }

    public void onUnsubscribe(String channel, int subscribedChannels) {
    	System.out.println("onUnsubscribe: channel: " + channel + " subscribedChannels: " + subscribedChannels);
    }

    public void onPSubscribe(String pattern, int subscribedChannels) {
    	System.out.println("onPSubscribe: pattern: " + pattern + " subscribedChannels: " + subscribedChannels);
    }

    public void onPUnsubscribe(String pattern, int subscribedChannels) {
    	System.out.println("onPUnsubscribe: pattern: " + pattern + " subscribedChannels: " + subscribedChannels);
    }

    public void onPMessage(String pattern, String channel,
        String message) {
    	System.out.println("onPMessage: pattern: " + pattern + " channel: " + channel + " message: " + message);
    }
}

