package com.zhouyu;

import com.zhouyu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Test {

	public static void main(String[] args) {

		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		UserService userService = (UserService) applicationContext.getBean("userService");
		userService.test();

//		for (Field field : UserService.class.getFields()) {
//			if (field.isAnnotationPresent(Autowired.class)) {
//				field.set(userService,??);//赋一个值，那么这个值又是怎么来的呢？
//			}
//		}
//		for (Method method : UserService.class.getMethods()) {
//			if(method.isAnnotationPresent(PostConstruct.class)){
//				method.invoke(userService,null);
//			}
//		}
	}
}
