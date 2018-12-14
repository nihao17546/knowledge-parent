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
