package org.monuo.jeenuo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	@RequestMapping(value = "/loginpage")  
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {  
		if (error != null) {
            return "loginerror";
        }
        return "login";  
    }  
	@RequestMapping(value={"/welcome"})
    public String welcome(){
		//��ȡ������
		SecurityContext securityContext = SecurityContextHolder.getContext();
		//��ȡ��֤����
		Authentication au = securityContext.getAuthentication();
		//����֤�����л�ȡ�������
		Object obj = au.getPrincipal();
		String username = "";
		if(obj instanceof UserDetails)
			username = ((UserDetails) obj).getUsername();
		else
			username = obj.toString();
		System.out.println("\n======================username: " + username + " obj instanceof UserDetails: " +(obj instanceof UserDetails));
        return "index";
    }
	
	@RequestMapping(value={"/accessDenied"})
	public String accessDenied(){
		return "/accessDenied";
	}
	@RequestMapping(value={"/admin"})
	public String admin(){
		return "/admin";
	}
}
