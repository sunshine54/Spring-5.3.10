package com.zhouyu;

import com.zhouyu.domain.User;
import com.zhouyu.service.UserService1;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.convert.support.DefaultConversionService;

public class Test2 {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(AppConfig.class);
		UserService1 userService1 = (UserService1) context.getBean("userService1",UserService1.class);
		userService1.test();

		//PropertyEditor
	/*	StringToUserPropertyEditor propertyEditor = new StringToUserPropertyEditor();
		propertyEditor.setAsText("1");
		User user = (User) propertyEditor.getValue();
		System.out.println(user);*/


		//ConversionService
		DefaultConversionService conversionService = new DefaultConversionService();
		conversionService.addConverter(new StringToUserConverter());
		User value = conversionService.convert("1", User.class);
		System.out.println(value);

		//TypeConverter是Spring内部使用的，为什么呢？因为Spring支持这两种转化器，它使用SimpleTypeConverter把它们给收集起来
		//转化的时候，直接去SimpleTypeConverter里面找就可以了

		SimpleTypeConverter typeConverter = new SimpleTypeConverter();
		//PropertyEditor
		typeConverter.registerCustomEditor(User.class, new StringToUserPropertyEditor());
		//ConversionService
        typeConverter.setConversionService(conversionService);
		User value1 = typeConverter.convertIfNecessary("1", User.class);
		System.out.println(value1);

	}
}
