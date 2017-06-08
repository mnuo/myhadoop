/**
 * Demo.java created at 2017-06-07 12:01:19
 */
package com.monuo.dubboserver.service;

import java.util.List;

import com.monuo.dubboserver.entity.User;

/**
 * @author saxon
 */
public interface DemoService {
	public String sayHello(String name);
	public List<User> getUsers();  
}
