## 组件注册
#### 1.使用@Configuration & @Bean给容器中注册组件
@Configuration注解，作用于类，使用了这个注解的类相当于配置文件，即告诉spring这个一个配置类。  
@Bean注解，作用于方法或注解。
```Java
@Configuration // 告诉spring这是一个配置类
public class ConfigDemo {
    
    /**
    * 给容器注册一个Bean，类型为返回值类型，id默认为方法名
    * 若要额外指定id，给注解赋值value，如：@Bean("user")
    * @return 
    */
    @Bean
    public User user(){
        return new User("name", 22);
    }
}
```
这个就相当于如下xml配置方式
```xml
<bean id="user" class="com.nihao.entity.User">
     <property name="name" value="name" />
     <property name="age" value="22"/>
</bean>
```
#### 2.使用@ComponentScan自动扫描组件指定扫描规则
@ComponentScan注解，作用于类，用于定义扫描的路径从中找出标识了需要装配的类自动装配到spring的bean容器中。  
@ComponentScan默认装配标识了@Component注解的类到spring容器中，@Service、@Controller、@Repository都注解了@Component的，  
所以@ComponentScan默认也会装配含有@Service、@Controller、@Repository这这些注解的类到容器中。
```Java
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Repeatable(ComponentScans.class)
public @interface ComponentScan {
    // 指定要扫描的包 
    @AliasFor("basePackages")
    String[] value() default {};
    
    // 与value一样，指定要扫描的包 
    @AliasFor("value")
    String[] basePackages() default {};

    // 指定具体的扫描的类
    Class<?>[] basePackageClasses() default {};

    // 对应的bean名称的生成器 默认的是BeanNameGenerator
    Class<? extends BeanNameGenerator> nameGenerator() default BeanNameGenerator.class;

    // 处理检测到的bean的scope范围
    Class<? extends ScopeMetadataResolver> scopeResolver() default AnnotationScopeMetadataResolver.class;

    // 是否为检测到的组件生成代理
    ScopedProxyMode scopedProxy() default ScopedProxyMode.DEFAULT;

    // 控制符合组件检测条件的类文件   默认是包扫描下的  **/*.class
    String resourcePattern() default "**/*.class";

    // 是否对带有@Component @Repository @Service @Controller注解的类开启检测,默认是开启的
    boolean useDefaultFilters() default true;

    // 指定扫描的时候只需要包含哪些组件（注意的是包扫描默认的是扫描所有的，也就是上个配置useDefaultFilters，所以需要设置为false） 
    // FilterType有5种类型:
    //   ANNOTATION, 注解类型 默认
    //   ASSIGNABLE_TYPE,指定固定类
    //   ASPECTJ， ASPECTJ类型
    //   REGEX,正则表达式
    //   CUSTOM,自定义类型
    ComponentScan.Filter[] includeFilters() default {};
    
    // 指定扫描的时候按照什么规则排除哪些组件
    ComponentScan.Filter[] excludeFilters() default {};

    // 扫描到的类是都开启懒加载 ，默认是不开启的
    boolean lazyInit() default false;

    @Retention(RetentionPolicy.RUNTIME)
    @Target({})
    public @interface Filter {
        FilterType type() default FilterType.ANNOTATION;

        @AliasFor("classes")
        Class<?>[] value() default {};

        @AliasFor("value")
        Class<?>[] classes() default {};

        String[] pattern() default {};
    }
}
```
#### 3.使用@Scope设置作用域，@Lazy设置懒加载
@Scope作用域有如下四种：  
prototype：多实例的：ioc容器启动并不会去调用方法创建对象放在容器中,每次获取的时候才会调用方法创建对象。  
singleton：单实例的（默认值）：ioc容器启动会调用方法创建对象放到ioc容器中，以后每次获取就是直接从容器（map.get()）中拿。  
request：同一次请求创建一个实例。  
session：同一个session创建一个实例。
```Java
@Configuration
public class ConfigDemo {
    
    /**
    * 多实例Bean,每次获取的时候才调用方法创建对象
    * @return 
    */
    @Scope("prototype")
    @Bean
    public User prototypeUser(){
        return new User("prototype", 18);
    }
    
    /**
    * 懒加载：容器启动不创建对象。第一次使用(获取)Bean创建对象，并初始化
    * @return 
    */
    @Lazy
    @Bean
    public User lazyUser(){ 
        return new User("lazy", 22); 
    }
}
```
#### 4.使用@Conditional,按照条件注册bean
@Conditional是Spring4开始支持的，使用该注解只有当所有指定的条件都满足时，组件才可以注册。  
主要用于在创建bean时增加一系列限制条件。  
首先需要自定义一个Condition
```Java
public class DemoCondition implements Condition{
     @Override
      public boolean matches(ConditionContext arg0, AnnotatedTypeMetadata arg1) { 
         // 自定义判断条件，返回true表示满足条件，false不满足
         return true; 
     }
}
```
```Java
@Configuration
public class ConfigDemo {

    @Conditional(DemoCondition.class)
    @Bean
    public User prototypeUser(){
        return new User("Conditional", 18);
    }
    
}
```
#### 5.使用@Import注册组件
@Import作用于类上，可以通过此方式快速注册组件。  
```Java
/**
 * 向容器中注册User
 */
@Configuration
@Import(User.class)
public class DemoConfig {
}
```
基于ImportSelector的方式
```Java
public class DemoSelector implements ImportSelector {
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"com.nihao.entity.User"};
    }
}
```
```Java
@Configuration
@Import(DemoSelector.class)
public class DemoConfig {
}
```
基于ImportBeanDefinitionRegistrar的方式
```Java
/**
 * DemoImportBeanDefinitionRegistrar
 */
public class DemoImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar{
    public void registerBeanDefinitions(
            AnnotationMetadata importingClassMetadata,
            BeanDefinitionRegistry registry) {
        // new一个RootBeanDefinition
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(User.class);
        // 注册一个名字叫demoUser的bean
        registry.registerBeanDefinition("demoUser", rootBeanDefinition);
    }
}
```
```Java
@Configuration
@Import(DemoImportBeanDefinitionRegistrar.class)
public class DemoConfig {
}
```
#### 6.使用@FactoryBean注册组件
```Java
public class UserFactoryBean implements FactoryBean<Color> {

	//返回一个User对象，这个对象会添加到容器中
	@Override
	public User getObject() throws Exception {
		return new User();
	}

	@Override
	public Class<?> getObjectType() {
		return User.class;
	}

	//true：这个bean是单实例，在容器中保存一份
	//false：多实例，每次获取都会创建一个新的bean
	@Override
	public boolean isSingleton() {
		return true;
	}

}
```

```Java
@Configuration
public class DemoConfig {
    /**
     * 通过容器获取User：ApplicationContext#getBean('userFactoryBean')
     * 获取UserFactoryBean：ApplicationContext#getBean('&userFactoryBean')
     */
     @Bean
     public UserFactoryBean userFactoryBean(){
	return new UserFactoryBean();
     }
}
```

#### 7.@Bean指定初始化和销毁方法
方式一：
```Java
public class DemoBean { 
    public void init() {
	// 初始化方法
    }
    public void destroy() {
	// 销毁方法
    }
}
```
```Java
@Configuration
public class DemoConfig { 
    /**
     * initMethod：指定初始化方法
     * destroyMethod：指定销毁方法（只针对交由容器管理的bean，对于多实例的bean无效）
     * @return 
     */
    @Bean(initMethod="init", destroyMethod="destroy")
    public DemoBean demoBean(){
	return new DemoBean();
    }
}
```

方式二：  
实现InitializingBean定义初始化逻辑，实现DisposableBean定义销毁逻辑
```Java
@Component
public class DemoBean implements InitializingBean, DisposableBean {
    @Override
    public void destroy() throws Exception {
	// 销毁方法
    }
    @Override
    public void afterPropertiesSet() throws Exception {
	// 初始化方法
	// 在bean创建完成并且属性赋值完成后
    }
}
```

方式三：  
可以使用JSR250中定义的两个注解@PostConstruct和@PreDestroy  
```Java
@Component
public class DemoBean {
    @PostConstruct
    public void init() {
	// 初始化方法
	// 在bean创建完成并且属性赋值完成后
    }
    @PreDestroy
    public void destroy() {
	// 销毁方法
	// 在容器销毁bean之前
    }
}
```
方式四：  
@BeanPostProcessor后置处理器，用于在bean初始化前后处理一些操作。  
```Java
@Component
public class DemoPostProcessor implements BeanPostProcessor {
    /**
    * 
    * @param bean 执行初始化的bean
    * @param beanName 执行初始化的bean的name
    * @return 
    * @throws BeansException
    */
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
	// 在初始化之前执行
	return bean;
    }
    /**
    * 
    * @param bean 执行初始化的bean
    * @param beanName 执行初始化的bean的name
    * @return 
    * @throws BeansException
    */
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
	// 在初始化之后执行
	return bean;
    }
}
```
Spring会遍历得到容器中所有的BeanPostProcessor；挨个执行beforeInitialization，一但返回null，跳出for循环，不会执行后面的BeanPostProcessor#postProcessorsBeforeInitialization  
BeanPostProcessor在Spring底层有大量应用  
例如：ApplicationContextAwareProcessor  
```Java
class ApplicationContextAwareProcessor implements BeanPostProcessor {
    ........
    public Object postProcessBeforeInitialization(final Object bean, String beanName) throws BeansException {
        AccessControlContext acc = null;
        if (System.getSecurityManager() != null 
            && (bean instanceof EnvironmentAware 
                || bean instanceof EmbeddedValueResolverAware 
                || bean instanceof ResourceLoaderAware 
                || bean instanceof ApplicationEventPublisherAware 
                || bean instanceof MessageSourceAware 
                // 判断bean是否实现了ApplicationContextAware
                || bean instanceof ApplicationContextAware)) {
            acc = this.applicationContext.getBeanFactory().getAccessControlContext();
        }

        if (acc != null) {
            AccessController.doPrivileged(new PrivilegedAction<Object>() {
                public Object run() {
                    ApplicationContextAwareProcessor.this.invokeAwareInterfaces(bean);
                    return null;
                }
            }, acc);
        } else {
            // 执行invokeAwareInterfaces给bean注入值
            this.invokeAwareInterfaces(bean);
        }

        return bean;
    }

    private void invokeAwareInterfaces(Object bean) {
        if (bean instanceof Aware) {
            if (bean instanceof EnvironmentAware) {
                ((EnvironmentAware)bean).setEnvironment(this.applicationContext.getEnvironment());
            }

            if (bean instanceof EmbeddedValueResolverAware) {
                ((EmbeddedValueResolverAware)bean).setEmbeddedValueResolver(this.embeddedValueResolver);
            }

            if (bean instanceof ResourceLoaderAware) {
                ((ResourceLoaderAware)bean).setResourceLoader(this.applicationContext);
            }

            if (bean instanceof ApplicationEventPublisherAware) {
                ((ApplicationEventPublisherAware)bean).setApplicationEventPublisher(this.applicationContext);
            }

            if (bean instanceof MessageSourceAware) {
                ((MessageSourceAware)bean).setMessageSource(this.applicationContext);
            }
            
            // 给bean注入applicationContext
            if (bean instanceof ApplicationContextAware) {
                ((ApplicationContextAware)bean).setApplicationContext(this.applicationContext);
            }
        }

    }
    ......
}
```

#### 8.自动装配
Spring利用依赖注入（DI），完成对IOC容器中中各个组件的依赖关系赋值。  
##### 8.1 @Autowired
@Autowired默认优先按照类型去容器中找对应的组件，找到就赋值。（applicationContext.getBean(User.class)）
如果找到多个相同类型的组件，再将属性的名称作为组件的id去容器中查找。（applicationContext.getBean("user")）  
@Qualifier("user")，使用@Qualifier指定需要装配的组件的id，而不是使用属性名。  
自动装配默认一定要将属性赋值好，如果没有找到对应的组件就会报错，可以使用@Autowired(required = false)来避免报错。  
@Primary，使用了该注解的bean表示为首选bean，Spring进行自动装配的时候，默认使用首选的bean进行装配。  
##### 8.2 @Resource
@Resource和@Autowired一样实现自动装配功能；默认是按照组件名称进行装配的 ；不支持@Primary功能；也不支持reqiured=false。  
##### 8.3 @Inject
使用@Inject需要导入javax.inject的包，和Autowired的功能一样；没有required=false的功能。

#### 9.@Profile根据环境注册bean
@Profile：指定组件在哪个环境的情况下才能被注册到容器中，不指定，任何环境下都能注册这个组件。  
加了环境标识的bean，只有这个环境被激活的时候才能注册到容器中。默认是default环境。  
写在配置类上，只有是指定的环境的时候，整个配置类里面的所有配置才能开始生效。  
没有标注环境标识的bean在任何环境下都要加载。  
