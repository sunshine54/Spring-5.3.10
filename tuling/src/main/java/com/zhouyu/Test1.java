package com.zhouyu;

import com.zhouyu.domain.User;
import com.zhouyu.service.OrderService;
import com.zhouyu.service.UserService;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

public class Test1 {

	public static void main(String[] args) throws IOException {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

		//获取国际化-事件发布都通过此测试
		//applicationContext.getMessage("test", null, new Locale("en_CN"));
		OrderService orderService = (OrderService) applicationContext.getBean("orderService");

		orderService.test();

		//获取资源加载
	/*	//文件
		Resource resource = applicationContext.getResource("file://E:\\liyanting\\TuLing\\fiveCode\\spring\\tuling\\src\\main\\java\\com\\zhouyu\\service\\OrderService.java");
		System.out.println(resource.contentLength());
		//网络资源
		Resource resource1 = applicationContext.getResource("https://www.baidu.com");
		System.out.println(resource1.contentLength());
		System.out.println(resource1.getURL());
        //配置文件
		Resource resource2 = applicationContext.getResource("classpath:spring.xml");
		System.out.println(resource2.contentLength());
		System.out.println(resource2.getURL());
		//一次性获取多个
		Resource[] resources = applicationContext.getResources("classpath:com/zhouyu/*.class");
		for (Resource resource3 : resources) {
			System.out.println(resource3.contentLength());
			System.out.println(resource3.getFilename());
		}*/
		//获取资源加载在哪里用到得呢？
		//1.加载配置文件
		//2.扫描 doScan()-->findCandidateComponents(basePackage);-->
		/*String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
				resolveBasePackage(basePackage) + '/' + this.resourcePattern;
		Resource[] resources = getResourcePatternResolver().getResources(packageSearchPath);
		其中interface ResourcePatternResolver extends ResourceLoader */


		//获取运行时环境
		//获取系统得环境变量
/*		Map<String, Object> systemEnvironment = applicationContext.getEnvironment().getSystemEnvironment();
		System.out.println(systemEnvironment);

		System.out.println("=======");

		//获取以-D形式增加得环境变量
		Map<String, Object> systemProperties = applicationContext.getEnvironment().getSystemProperties();
		System.out.println(systemProperties);

		System.out.println("=======");

		//解析@PropertySource("classpath:spring.properties")，但是它还包括上面两个得环境变量，它是最强大得
		MutablePropertySources propertySources = applicationContext.getEnvironment().getPropertySources();
		System.out.println(propertySources);

		System.out.println("=======");

		//去获取某个值得环境变量
		System.out.println(applicationContext.getEnvironment().getProperty("NO_PROXY"));
		System.out.println(applicationContext.getEnvironment().getProperty("sun.jnu.encoding"));
		System.out.println(applicationContext.getEnvironment().getProperty("zhouyu"));*/

	}
}
