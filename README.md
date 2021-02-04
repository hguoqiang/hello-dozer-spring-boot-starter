# hello-dozer-spring-boot-starter
基于net.sf.dozer 5.5.1版本整合springboot

### 1.HelloDozerBeanMapperFactoryBean

```java
此类主要是创建 {@link HelloDozerBeanMapper},并且注册所有的自定义转换器
    
代码逻辑都在 
public final void afterPropertiesSet() throws Exception {}
```

### 2. HelloDozerBeanMapper

这个是主要类 ，继承的DozerBeanMapper，可以自行扩展

### 3.HelloDozerAutoConfiguration

自动配置类 ，配合 META-INF/spring.factories

### 4.AbstractBeanMappingBuilder

看代码

### 5.HelloDozerBeanMapping

此类主要是创建指定bean之间映射规则的转换器

