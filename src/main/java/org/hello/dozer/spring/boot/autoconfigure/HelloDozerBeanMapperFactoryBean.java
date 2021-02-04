/**
 * Copyright 2005-2013 Dozer Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hello.dozer.spring.boot.autoconfigure;

import org.dozer.CustomConverter;
import org.dozer.Mapper;
import org.dozer.loader.DozerBuilder;
import org.dozer.loader.api.FieldsMappingOptions;
import org.dozer.loader.api.TypeMappingBuilder;
import org.hello.dozer.spring.boot.utils.Pair;
import org.hello.dozer.spring.boot.utils.ResolveGenericTypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @description:此类主要是创建 {@link HelloDozerBeanMapper},并且注册所有的自定义转换器
 * @author: huanggq
 * @create: 2021-02-03 19:24
 **/
public class HelloDozerBeanMapperFactoryBean implements FactoryBean<Mapper>,
        InitializingBean, DisposableBean, ApplicationContextAware {
    private final Logger logger = LoggerFactory.getLogger(HelloDozerBeanMapperFactoryBean.class);

    private HelloDozerBeanMapper beanMapper;

    private ApplicationContext applicationContext;



    @Override
    public final Mapper getObject() throws Exception {
        return this.beanMapper;
    }

    @Override
    public Class<?> getObjectType() {
        return Mapper.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    /**
     * interface 'InitializingBean'
     * @throws Exception
     */
    @Override
    public final void afterPropertiesSet() throws Exception {


        // 获取所有自定义的转换器，此转换器可以理解是全局通用的转换器
        final Map<String, CustomConverter> customConverterMap = applicationContext.getBeansOfType(CustomConverter.class);

        // 获取所有的指定bean之间映射的转换器
        final Map<String, HelloDozerBeanMapping> beanMappingMap = applicationContext.getBeansOfType(HelloDozerBeanMapping.class);

        AbstractBeanMappingBuilder smartBeanMappingBuilder = new AbstractBeanMappingBuilder() {
            @Override
            protected void configure() {
                DozerBuilder dozerBuilder = getDozerBuilder();

                DozerBuilder.ConfigurationBuilder configurationBuilder = dozerBuilder.configuration();

                //注册自定义的全局转换器
                Collection<CustomConverter> customConverterList = customConverterMap.values();
                for (CustomConverter customConverter : customConverterList) {
                    Class<? extends CustomConverter> aClass = customConverter.getClass();
                    Pair<Class, Class> pair = ResolveGenericTypeUtils.resolveParameterizedType(aClass);
                    configurationBuilder.customConverter(aClass).classA(pair.getKey()).classB(pair.getValue());
                }

                //注册指定bean之间映射的转换器
                Collection<HelloDozerBeanMapping> beanMappings = beanMappingMap.values();
                for (HelloDozerBeanMapping beanMapping : beanMappings) {
                    TypeMappingBuilder typeMappingBuilder = mapping(beanMapping.getSourceType(), beanMapping.getDestType());
                    List<HelloDozerBeanMapping.BeanFieldMapping> fieldMappings = beanMapping.getFieldMappings();
                    for (HelloDozerBeanMapping.BeanFieldMapping fieldMapping : fieldMappings) {
                        if (fieldMapping.getDozerConverter() == null) {
                            typeMappingBuilder.fields(fieldMapping.getSourceFieldName(), fieldMapping.getDestFieldName());
                        } else {
                            /**
                             * 此处可以定义转换器参数，如果转换器没有需要参数，则忽略
                             */
                            String parameter = null;
                            try {
                                parameter = fieldMapping.getDozerConverter().getParameter();
                            } catch (Exception e) {
                                logger.warn("未设置自定义转换器参数");
                            }
                            typeMappingBuilder.fields(fieldMapping.getSourceFieldName(), fieldMapping.getDestFieldName(), FieldsMappingOptions.customConverter(fieldMapping.getDozerConverter().getClass(), parameter));
                        }
                    }
                }

            }
        };

        this.beanMapper = new HelloDozerBeanMapper();

        beanMapper.addMapping(smartBeanMappingBuilder);
    }


    @Override
    public void destroy() throws Exception {
        if (this.beanMapper != null) {
            this.beanMapper.destroy();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
