package com.liyanting.service;

import com.spring.*;

@Componet("userService")
@Scope("singleton")
public class UserService implements InitializingBean,UserInterface, BeanNameAware {
    @Autowired
    private OrderService orderService;

    @LytValue("xxx")
    public String value;


    private String beanName;

    @Override
    public void setBeanName(String beanName) {//在执行的时候，实现BeanNameAware，会调用setBeanName()这个方法，把当前bean的beanName赋值给beanNamesh属性
        this.beanName = beanName;
    }

    public void test(){
        System.out.println("userService中的OrderService："+orderService);
        System.out.println("userService中的value:"+value);
        System.out.println("UserService中的beanName:"+beanName);
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("UserService的初始化");
    }
}
