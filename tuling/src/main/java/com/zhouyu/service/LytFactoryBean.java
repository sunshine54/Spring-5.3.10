package com.zhouyu.service;

import com.zhouyu.domain.User;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.SmartFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class LytFactoryBean implements SmartFactoryBean {//"lytFactoryBean"  LytFactoryBean
	@Override
	public boolean isEagerInit() {
		return true;
	}

	@Override
	public Object getObject() throws Exception {
		return new  User();
	}

	@Override
	public Class<?> getObjectType() {
		return User.class;
	}
}
