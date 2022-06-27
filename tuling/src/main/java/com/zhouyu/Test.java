package com.zhouyu;

import com.zhouyu.domain.User;
import com.zhouyu.service.OrderService;
import com.zhouyu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Test {

	public static void main(String[] args) {

		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		UserService userService = (UserService) applicationContext.getBean("userService");
		userService.test();

		//编程式定义Bean
//		AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
//		beanDefinition.setBeanClass(User.class);
//		beanDefinition.setInitMethodName("init");
//		applicationContext.registerBeanDefinition("user",beanDefinition);


		//AnnotatedBeanDefinitionReader
		//这个工具类是帮我们封装了上述编程式定义的代码，register()方法里面，
		// 它会帮我们创建一个AnnotatedGenericBeanDefinition,并解析@Conditional，@Scope、@Lazy、@Primary、@DependsOn、@Role、@Description
		//并在Spring容器中注册，放到Spring容器中
		/*AnnotatedBeanDefinitionReader annotatedBeanDefinitionReader=new AnnotatedBeanDefinitionReader(applicationContext);
		annotatedBeanDefinitionReader.register(User.class);*/

		//在new AnnotationConfigApplicationContext()里面的this方法，就创建了个	this.reader = new AnnotatedBeanDefinitionReader(this);
		//context也可以直接调用register()，applicationContext.register();//底层就是this.reader.register(componentClasses);去创建BeanDefinition,解析，注册


		//解析XML
	/*	XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
		int i = xmlBeanDefinitionReader.loadBeanDefinitions("spring.xml");*/

		//ClassPathDefinitionScanner
		//如果创建AnnotationConfigApplicationContext()的时候没有传参数，及时refresh，getBean("UserService")的时候也是null
		/*AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.refresh();*/

		//ClassPathBeanDefinitionScanner是扫描器，但是它的作用和BeanDefinitionReader类似，它可以进行扫描，
		// 扫描某个包路径，对扫描到的类进行解析，比如，扫描到的类上如果存在@Component注解，那么就会把这个类解析为一个BeanDefinition
		//把BeanDefinition注册到Spring容器，就能得到一个Bean
//		ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(context);
//		scanner.scan("com.zhouyu");
//		OrderService orderService = (OrderService) context.getBean("orderService");
//		System.out.println(orderService);

		//BeanFactory
		DefaultListableBeanFactory beanFactory=new DefaultListableBeanFactory();
		AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
		beanDefinition.setBeanClass(User.class);
		beanFactory.registerBeanDefinition("user",beanDefinition);
		System.out.println(beanFactory.getBean("user"));

	}
}
