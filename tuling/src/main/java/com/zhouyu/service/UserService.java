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
@Component("userService")
public class UserService  {

	@Autowired
	OrderService orderService;

	public void test() {
		System.out.println(orderService);
	}

}
