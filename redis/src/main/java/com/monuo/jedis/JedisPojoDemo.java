/**
 * JedisPojoDemo.java created at 2017年6月9日 上午10:10:22
 */
package com.monuo.jedis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.monuo.jedis.util.JedisPoolManager;

import redis.clients.jedis.Jedis;

/**
 * @author saxon
 */
public class JedisPojoDemo {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Jedis jedis = null;
		try {
			jedis = JedisPoolManager.getInstance().getResource();
			jedis.auth("123123");
			
			Person person = new Person("Ricky", 27);
			//序列化
			byte[] byteArray = serialize(person);
			
			//set 
			jedis.set("Ricky".getBytes(), byteArray);
			
			//get
			byteArray = jedis.get("Ricky".getBytes());
			
			//反序列化
			person = deserialize(byteArray);
			
			System.out.println(person);
		} finally {
			if(jedis != null) jedis.close();
		}
		JedisPoolManager.getInstance().destroy();
	}
	public static Person deserialize(byte[] bytes) throws Exception{
		ObjectInputStream ois = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bis);
			return (Person) ois.readObject();
		} finally {
			ois.close();
		}
	}
	public static byte[] serialize(Person person) throws IOException{
		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos = null;
		try {
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(person);
			oos.flush();
			return bos.toByteArray();
		} finally {
			oos.close();
			bos.close();
		}
	}
}
class Person implements Serializable{
	private static final long serialVersionUID = 5735319877294608703L;
	private String name;
	private int age;
	
	public Person() {
	}
	public Person(String name, int age){
		this.name = name;
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "name: " + this.name + " age: " + this.age;
	}
}
