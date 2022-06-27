package com;

import com.liyanting.Appconfig;
import com.liyanting.service.UserInterface;
import com.liyanting.service.UserService;
import com.spring.LytAppcationContext;

public class Test {
    public static void main(String[] args) {
        //1.创建一个Spring容器
        LytAppcationContext appcationContext=new LytAppcationContext(Appconfig.class);
        //2.创建getBean()方法；
        UserInterface userService = (UserInterface) appcationContext.getBean("userService");
        userService.test();

        System.out.println(appcationContext.getBean("orderService"));
    }
}
