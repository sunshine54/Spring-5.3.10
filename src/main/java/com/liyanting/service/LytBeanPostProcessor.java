package com.liyanting.service;

import com.spring.BeanPostProcessor;
import com.spring.Componet;
import com.spring.LytAppcationContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Componet
public class LytBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
//        if(beanName.equals("userService")){
//            System.out.println(beanName+ " After ininialization");
//        }
        if(beanName.equals("userService")){
          Object proxyInstance=  Proxy.newProxyInstance(LytBeanPostProcessor.class.getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    //切面逻辑
                    return method.invoke(bean,args);
                }
            });
            return proxyInstance;
        }
        return bean;
    }
}
