package com.zhouyu.service;


import com.zhouyu.domain.User;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;
import reactor.core.Disposable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author 周瑜
 */


@Component
public class UserService {

	@Autowired
	OrderService orderService;

//	@Autowired
	public void setOrderService(OrderService orderService) {
		this.orderService=orderService;
		System.out.println("111"+orderService);
	}

	public OrderService getOrderService() {
		return orderService;
	}
	public void test(){
		System.out.println("test "+orderService);
	}

}
