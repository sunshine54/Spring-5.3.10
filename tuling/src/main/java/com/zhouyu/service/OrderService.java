package com.zhouyu.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;


@Component
@Conditional(LytCondition.class)
public class OrderService{

}
