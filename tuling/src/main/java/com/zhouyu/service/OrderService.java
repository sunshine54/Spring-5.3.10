package com.zhouyu.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Locale;

@Component
public class OrderService implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
	}
	public void test() throws IOException {
		System.out.println(applicationContext.getMessage("test", null, new Locale("en_CN")));
		applicationContext.getResources("classpath:spring.xml");

		//事件发布器
		applicationContext.publishEvent("123455");
	}
}
