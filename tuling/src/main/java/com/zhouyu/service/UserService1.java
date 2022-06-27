package com.zhouyu.service;

import com.zhouyu.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//@Component
public class UserService1 {

	@Value("zhouyu")
	private User user;

	public void test(){
		System.out.println(user);
	}

}
