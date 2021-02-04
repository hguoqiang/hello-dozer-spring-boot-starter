package org.hello.dozer.spring.boot.autoconfigure;

import org.dozer.DozerConverter;
import org.dozer.loader.DozerBuilder;
import org.dozer.loader.api.BeanMappingBuilder;

import java.lang.reflect.Field;

/**
 * @description: 此类继承 {@link BeanMappingBuilder},此类只有一个方法{@link AbstractBeanMappingBuilder#getDozerBuilder()}，
 * 获取当前 {@link AbstractBeanMappingBuilder}类实列的 {@link DozerBuilder}属性对象，
 * 目的是调用{@link org.dozer.loader.DozerBuilder#configuration()}类实列的 方法，
 * 此方法返回{@link org.dozer.loader.DozerBuilder.ConfigurationBuilder}用来注册自定义的转换器，
 * 自定义转换器都要继承 {@link DozerConverter}类，
 * 具体注册转换器逻辑在 {@link HelloDozerBeanMapperFactoryBean#afterPropertiesSet()}
 * @author: huanggq
 * @create: 2021-02-03 10:11
 **/
public abstract class AbstractBeanMappingBuilder extends BeanMappingBuilder {


    public DozerBuilder getDozerBuilder() {
        try {
            Field field = this.getClass().getSuperclass().getSuperclass().getDeclaredField("dozerBuilder");
            field.setAccessible(true);
            return (DozerBuilder) field.get(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
