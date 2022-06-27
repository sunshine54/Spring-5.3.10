package com.zhouyu;


import com.zhouyu.domain.User;
import com.zhouyu.service.UserService;
import com.zhouyu.service.UserService1;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyEditor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


//@ComponentScan(value = "com.zhouyu",
//		excludeFilters = {@ComponentScan.Filter(
//				type = FilterType.ASSIGNABLE_TYPE,
//				classes = UserService1.class)})
//@ComponentScan(value = "com.zhouyu",
//		includeFilters = {@ComponentScan.Filter(
//				type = FilterType.ASSIGNABLE_TYPE,
//				classes = UserService1.class)})
@ComponentScan(value = "com.zhouyu")
@EnableTransactionManagement
//@EnableAspectJAutoProxy
@Configuration
public class AppConfig {

	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource());
	}
//	@Bean
//	public UserService1  userService1(){
//		return new UserService1();
//	}


	@Bean
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(dataSource());
		return transactionManager;
	}


	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl("jdbc:mysql://192.168.165.33:3306/knowledge_db?characterEncoding=utf-8&useSSL=false");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		return dataSource;
	}


	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource());
		return sessionFactoryBean.getObject();
	}


	//国际化
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("message");
		return messageSource;
	}


	//事件监听器
	@Bean
	public ApplicationListener applicationListener() {
		return new ApplicationListener() {
			@Override
			public void onApplicationEvent(ApplicationEvent event) {
				System.out.println("接收到了一个事件"+event);
			}
		};
	}

	//PropertyEditor类型转换
	@Bean
	public CustomEditorConfigurer customEditorConfigurer() {
		CustomEditorConfigurer customEditorConfigurer = new CustomEditorConfigurer();
		Map<Class<?>, Class<? extends PropertyEditor>> propertyEditorMap = new HashMap<>();

		// 表示StringToUserPropertyEditor可以将String转化成User类型，在Spring源码中，如果发现当前对象是String，而需要的类型是User，就会使用该PropertyEditor来做类型转化
		propertyEditorMap.put(User.class, StringToUserPropertyEditor.class);
		customEditorConfigurer.setCustomEditors(propertyEditorMap);
		return customEditorConfigurer;
	}

	//ConversionService类型转换
	@Bean
	public ConversionServiceFactoryBean conversionService() {
		ConversionServiceFactoryBean conversionServiceFactoryBean = new ConversionServiceFactoryBean();
		conversionServiceFactoryBean.setConverters(Collections.singleton(new StringToUserConverter()));

		return conversionServiceFactoryBean;
	}
}
