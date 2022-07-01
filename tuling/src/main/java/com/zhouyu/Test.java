package com.zhouyu;


import com.zhouyu.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

	public static void main(String[] args) {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		//ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("spring.xml");
		UserService userService = (UserService) context.getBean("userService");
		userService.test();
	}
}
