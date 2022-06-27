package com.spring;

import com.liyanting.service.LytBeanPostProcessor;

import java.beans.Introspector;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LytAppcationContext {
    private Class configclass;
    private Map<String,BeanDefinition>beanDefinitionMap=new HashMap<>();
    private Map<String,Object>singletonObjects=new HashMap<>();
    private List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();

    public LytAppcationContext(Class configclass) {
        this.configclass = configclass;

        //扫描,怎么扫描的呢？
        //1.拿到扫描的路径
        scan(configclass);

        //2.扫描结束，拿到beanDefinition,就开始创建了
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            String beanName = entry.getKey();
            BeanDefinition beanDefinition = entry.getValue();
            if(beanDefinition.getScope().equals("singleton")){
                Object bean = createBean(beanName, beanDefinition);
                //3.把单例bean创建了出来，还需要保存一下，创建一个新的map
                singletonObjects.put(beanName,bean);
            }
        }

    }
    private Object createBean(String beanName,BeanDefinition beanDefinition){

        //1.根据类去创建Bean
        Class clazz = beanDefinition.getType();
        //调用无参构造方法
        try {
            Object instance = clazz.getConstructor().newInstance();
//            return instance;
            //如果我想使用有参构造，那么我要考虑传过来的参数值,开始考虑依赖注入
            //依赖注入，怎么实现呢？
            for (Field field : clazz.getDeclaredFields()) {
                if(field.isAnnotationPresent(Autowired.class)){
                  field.setAccessible(true);
                      //field.set(instance,??)//注入的这个值怎么找呢？先根据与ByType在根据ByName
                      //去哪儿找呢？从单例池中找嘛？如果注入的OrderService还没创建呢？我们在扫描的时候，可以生成这样一个Map<Class,BeanDefinition>
                     //根据类型去找到BeanDefinition,然后生成，注入，如果找到多个，在根据ByName,beanDefinitionMap，singletonObjects都可以根据BeanName找到Bean

                    //现在实现是简单的拿字段名字去找
                    field.set(instance,getBean(field.getName()));
                }
            }

            //BeanNameAware时机
            if(instance instanceof BeanNameAware){
                ((BeanNameAware) instance).setBeanName(beanName);
            }
            //初始化前
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                instance=beanPostProcessor.postProcessBeforeInitialization(instance,beanName);
            }
            //依赖注入结束---初始化
            if(instance instanceof  InitializingBean){
              ((InitializingBean) instance).afterPropertiesSet();
            }
            //BeanPostProcessor
            //我们的初始化前-初始化后都跟BeanPostProcessor有关--LytBeanPostProcessor
            //如果简单的继承BeanPostProcessor，那么我调用方法的时候，就需要
           // new LytBeanPostProcessor().postProcessAfterInitialization(instance,beanName);
            //LytBeanPostProcessor是我自己写的，指定不能这样调用，那怎么办呢？让spring知道我自己的LytBeanPostProcessor呢？
            //加上@Componet,所以我们在scan的时候，要去增加一层判断的逻辑

            //
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                instance=beanPostProcessor.postProcessAfterInitialization(instance,beanName);
            }
            //这样写，看到创建所有Bean的时候，都会执行这个postProcessAfterInitialization(),那如果我想只针对一个Bean呢？那就需要自己去控制了

            //还可以基于这个机制去扩展使用LytValue--初始化前

            //Aware UserService想知道bean的名字
            //ApplicationContextAwareProcessor中就实现了BeanPostProcessor,执行初始化前，初始化后，然后查看bean是否实现了ApplicationContextAware等，然后执行设置

            return instance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object getBean(String beanName){

        if(!beanDefinitionMap.containsKey(beanName)){
               throw new NullPointerException();
        }
        BeanDefinition definition = beanDefinitionMap.get(beanName);
        if(definition.getScope().equals("singleton")){
            Object singletonBean = singletonObjects.get(beanName);
            //依赖注入---存在这样的一种UserService中存在一个OrderService的属性，但此时依赖注入调用getBean()
            // OrderService是单例的，但是还没创建，所以在单例池中是拿不到的
            if(singletonBean==null){
                singletonBean=createBean(beanName,definition);
                singletonObjects.put(beanName,singletonBean);
            }
            return singletonBean;
        }else {
            //原型
            Object prototypeBean = createBean(beanName, definition);
            return prototypeBean;
        }
    }

    private void scan(Class configclass) {
        if (configclass.isAnnotationPresent(ComponetScan.class)) {
            ComponetScan componetScanAnnotation = (ComponetScan) configclass.getAnnotation(ComponetScan.class);
            //获取到扫描路径
            String path = componetScanAnnotation.value();
            //2.拿到了扫描路径之后，我们要去解析这个包下的类上面是否有@Componet注解，有就证明是一个单例Bean
            //那么我们拿到的path=com.liyanting.service,我们是直接扫描这个包下的类嘛？肯定不是，这是我们写的源码.java文件，我们应该去扫描.class文件
            //class文件是在target.classes目录下的，而且target.classes目录是在AppClssLoader管理的路径内的
            path=path.replace(".","/");  //    com/liyanting/service;此时的path是一个相对的路径
            ClassLoader classLoader = LytAppcationContext.class.getClassLoader();//AppClassLoader
            URL resource = classLoader.getResource(path); //path(相对路径),相对的是target/classes,所以根据classLoader.getResource()得到一个完整的路径
            //3.可以根据resource直接拿到File
            File file=new File(resource.getFile());//可能是一个目录，也可能是一个文件
            if(file.isDirectory()){
                for (File f : file.listFiles()) {
                    String absolutePath = f.getAbsolutePath();
                    //E:\liyanting\TuLing\fiveCode\liyanting-spring-vip\target\classes\com\liyanting\service\UserService.class

                    absolutePath=absolutePath.substring(absolutePath.indexOf("com"),absolutePath.indexOf(".class"));
                    absolutePath=absolutePath.replace("\\",".");
                    //4.要去判断当前类上是否有@Componet注解，虽然File也可以判断，最简单的方式就是把它加载进来
                    try {
                        Class<?> clazz = classLoader.loadClass(absolutePath);//loadClass("com.liyanting.service.UserService"),但此时absolutePath不是这样的，需要转一下
                       //思路推断
//                        if (clazz.isAnnotationPresent(Componet.class)) {
//                            //当前能证明是一个Bean，但是到底是单例还是原型的，还需要去判断
//                            if(clazz.isAnnotationPresent(Scope.class)){
//                                //还要继续判断@Scope里面写的值
//                                Scope scopeAnnotation = clazz.getAnnotation(Scope.class);
//                                String value = scopeAnnotation.value();
//
//                            }else {
//                                //没有@Scope，证明是一个单例的
//                                //如果是一个单例的，那么就直接去创建这个Bean嘛？举例：如果一个bean是多例的，那么我每次getBean的时候，是根据beanName得到一个Object
//                                //我能根据beanName-->xxService.class,难道我每次都还继续解析嘛？其实我们在扫描的时候，已经把所有的类的信息都解析出来了
//                                //因为，我们引入了BeanDefinition(存储类的信息:Class,是否是懒加载，是否是原型...)
//                            }
//
//
//                        }

                        if (clazz.isAnnotationPresent(Componet.class)) {
                            //证明是一个Bean
                            //判断这个类是不是实现了这个接口
                            //去创建这个bean,加到beanPostProcessorList，这个BeanPostProcessor是需要在其它Bean创建（初始化前-后要用的）
                            //所以现在就要实例化出来
                            if(BeanPostProcessor.class.isAssignableFrom(clazz)){
                                BeanPostProcessor instance = (BeanPostProcessor) clazz.getConstructor().newInstance();
                                beanPostProcessorList.add(instance);
                            }else {
                                //得到BeanName
                                Componet componetAnnotation = clazz.getAnnotation(Componet.class);
                                String beanName = componetAnnotation.value();
                                if("".equals(beanName)){
                                    beanName= Introspector.decapitalize(clazz.getSimpleName());//首字母小写
                                }

                                //创建一个BeanDefinition
                                BeanDefinition definition=new BeanDefinition();
                                definition.setType(clazz);
                                if(clazz.isAnnotationPresent(Scope.class)){
                                    Scope scopeAnnotation = clazz.getAnnotation(Scope.class);
                                    String value = scopeAnnotation.value();
                                    definition.setScope(value);

                                }else {
                                    definition.setScope("singleton");
                                }
                                //我们得到了一个BeanDefinitioin,所以我们要存储起来,但是key是bean的名字，那么名字该如何获取呢？
                                beanDefinitionMap.put(beanName,definition);
                            }
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }

        }
    }


}
