package org.hello.dozer.spring.boot.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @description: 配置类
 * @author: huanggq
 * @create: 2021-02-02 19:41
 **/
@Configuration
@ConditionalOnClass({HelloDozerBeanMapper.class, HelloDozerBeanMapperFactoryBean.class})
public class HelloDozerAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public HelloDozerBeanMapperFactoryBean dozerBeanMapperFactoryBean() throws IOException {
        HelloDozerBeanMapperFactoryBean factory = new HelloDozerBeanMapperFactoryBean();
        return factory;
    }

    @Bean
    @ConditionalOnMissingBean
    public HelloDozerBeanMapper smartDozerBeanMapper() throws Exception {
        return (HelloDozerBeanMapper) dozerBeanMapperFactoryBean().getObject();
    }

}
