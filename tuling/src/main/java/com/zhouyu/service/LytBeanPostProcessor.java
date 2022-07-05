package com.zhouyu.service;

import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;

@Component
public class LytBeanPostProcessor implements MergedBeanDefinitionPostProcessor {
	@Override
	public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
		if(beanName.equals("userService")){
			OrderService orderService=new OrderService();
			System.out.println("orderService:"+orderService);
			beanDefinition.getPropertyValues().add("orderService",orderService);
		}
	}
}
