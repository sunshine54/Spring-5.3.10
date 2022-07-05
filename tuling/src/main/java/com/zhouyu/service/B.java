package com.zhouyu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class B {

	private A a;

	@Lazy
	public B(A a) {
		this.a = a;
	}

	public void test(){
		System.out.println(a);
	}
}
