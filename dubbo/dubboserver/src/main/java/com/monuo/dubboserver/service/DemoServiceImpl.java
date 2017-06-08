/**
 * DemoImpl.java created at 2017年6月7日 下午12:02:14
 */
package com.monuo.dubboserver.service;

import java.util.ArrayList;
import java.util.List;

import com.monuo.dubboserver.entity.User;

/**
 * @author saxon
 */
public class DemoServiceImpl implements DemoService {

	@Override
	public List<User> getUsers() {
		List<User> list = new ArrayList<User>();  
		User u1 = new User();  
		u1.setName("hejingyuan");  
		u1.setAge(20);  
		u1.setSex("f");  
  
        User u2 = new User();  
        u2.setName("xvshu");  
		u2.setAge(21);  
		u2.setSex("m");  
  
          
        list.add(u1);  
        list.add(u2);  
          
        return list;  
	}

	@Override
	public String sayHello(String name) {
		return "Hello " + name;  
	}

}
