package com.zhouyu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserServiceBase {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	//@Transactional(propagation = Propagation.NEVER)
	public void a(){

	}
}
