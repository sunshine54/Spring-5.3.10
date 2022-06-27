package com.zhouyu.domain;

public class User {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void init(){
		System.out.println("init");
	}
	public void test(){
		System.out.println("test");
	}
}
