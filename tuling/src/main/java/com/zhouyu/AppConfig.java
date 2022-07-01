package com.zhouyu;


import com.zhouyu.service.UserService;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.*;


@ComponentScan(value = "com.zhouyu")
@Configuration
public class AppConfig {

	@Bean(autowire = Autowire.BY_NAME)
	public UserService userService(){
		return new UserService();
	}
}
