package com.zhouyu.service;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component("userService1")
public class LytFactoryBean implements FactoryBean {
	//没实现FactoryBean的时候 lytFactoryBean-->类型LytFactoryBean
	//实现了FactoryBean的时候 lytFactoryBean对应的是getobject()方法返回的对象了
	//实现逻辑：扫描的时候，发现LytFactoryBean上面加了@Componet注解，就会生成beanDefinition，然后生成对象，把它放到单例池里面去
	//那为什么我现在通过lytFactoryBean拿到的对象是UserService1呢？其实是在getBean()的时候去判断，
	// 判断Instance instance of Factory Bean,是的话，返回getObject里面的对象并加入到FactoryBeanCahe这个Map中去
	@Override
	public Object getObject() throws Exception {
		return new UserService1();
	}

	@Override
	public Class<?> getObjectType() {
		return UserService1.class;
	}
}
