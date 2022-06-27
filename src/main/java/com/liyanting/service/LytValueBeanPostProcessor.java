package com.liyanting.service;

import com.spring.BeanPostProcessor;
import com.spring.Componet;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Componet
public class LytValueBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        for (Field field : bean.getClass().getDeclaredFields()) {
            if(field.isAnnotationPresent(LytValue.class)){
                try {
                    field.set(bean,field.getAnnotation(LytValue.class).value());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }
}
