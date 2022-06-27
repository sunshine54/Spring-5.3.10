package com.zhouyu;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

public class Test4 {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(AppConfig.class);
//		System.out.println(context.getBean("lytFactoryBean"));
//		System.out.println(context.getBean("&lytFactoryBean"));

		System.out.println(context.getBean("userService1"));

		//ClassPathBeanDefinitionScanner scanner=new ClassPathBeanDefinitionScanner(context);

	}
}
