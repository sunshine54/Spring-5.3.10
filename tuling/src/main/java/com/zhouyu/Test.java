package com.zhouyu;


import com.zhouyu.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

	public static void main(String[] args) {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(AppConfig.class);
		context.setAllowCircularReferences(false);
		context.refresh();
		UserService userService = (UserService) context.getBean("userService");

		userService.test();
		System.out.println(context.getBean("orderService"));
	}
}
