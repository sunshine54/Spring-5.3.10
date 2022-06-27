package com.zhouyu.service;


import com.zhouyu.domain.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * @author 周瑜
 */
@Component
@Scope("prototype")
public class UserService  {

	@Autowired
	private JdbcTemplate jdbcTemplate;

//	@Autowired
//	private UserServiceBase userServiceBase;
	@Autowired
	UserService userService;
	@Autowired
	OrderService orderService;

	@Transactional
	public void test() {
		jdbcTemplate.execute("insert t1 values(1,1,1,1,1)");
		userService.a();
	}
	@Transactional(propagation = Propagation.NEVER)
	public void a(){

	}
}
