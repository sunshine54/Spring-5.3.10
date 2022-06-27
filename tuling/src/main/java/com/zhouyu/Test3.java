package com.zhouyu;

import com.zhouyu.domain.User;
import com.zhouyu.service.UserService1;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

public class Test3 {
	public static void main(String[] args) throws IOException {
//		AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(AppConfig.class);
//		UserService1 userService1 = (UserService1) context.getBean("userService1",UserService1.class);
//		userService1.test();


		SimpleMetadataReaderFactory simpleMetadataReaderFactory = new SimpleMetadataReaderFactory();
		// 构造一个MetadataReader
		MetadataReader metadataReader = simpleMetadataReaderFactory.getMetadataReader("com.zhouyu.service.UserService1");

		// 得到一个ClassMetadata
		ClassMetadata classMetadata = metadataReader.getClassMetadata();
		System.out.println(classMetadata.getClassName());//获取到类名
		System.out.println(classMetadata.getInterfaceNames());//获取类的接口名称
		System.out.println(classMetadata.getMemberClassNames());//获取内部类的名称

		// 获取一个AnnotationMetadata，并获取类上的注解信息
		AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
		System.out.println(annotationMetadata.hasAnnotation(Component.class.getName()));//如果注解是@Service,返回的是false
		System.out.println(annotationMetadata.hasMetaAnnotation(Component.class.getName()));//如果注解是@Service，返回也是true
		for (String annotationType : annotationMetadata.getAnnotationTypes()) {
			System.out.println(annotationType);
		}

	}
}
