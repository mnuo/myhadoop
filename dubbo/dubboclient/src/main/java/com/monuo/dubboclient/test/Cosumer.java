/**
 * Cosumer.java created at 2017年6月8日 上午11:18:19
 */
package com.monuo.dubboclient.test;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.monuo.dubboserver.entity.User;
import com.monuo.dubboserver.service.DemoService;

/**
 * @author saxon
 */
public class Cosumer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring/springmvc-config.xml","dubbo/consumer.xml"});
		context.start();
		
		DemoService demoService = (DemoService) context.getBean("demoService");
		String hello = demoService.sayHello("hejingyuan");  
        System.out.println(hello);  
  
        List<User> list = demoService.getUsers();  
        if (list != null && list.size() > 0) {  
            for (int i = 0; i < list.size(); i++) {  
                System.out.println(list.get(i));  
            }  
        }  
        	
	}

}
