package org.hello.dozer.spring.boot.autoconfigure;

import org.dozer.DozerConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:此类主要是创建指定bean之间映射规则的转换器
 * @author: huanggq
 * @create: 2021-02-03 19:24
 **/
public class HelloDozerBeanMapping<D, E> {

    /**
     * 源对象类型
     */
    private Class<D> sourceType;

    /**
     * 目标对象类型
     */
    private Class<E> destType;

    /**
     * 源对象和目标对象之间的字段映射规则
     */
    private List<BeanFieldMapping> fieldMappingList = new ArrayList<>();

    private HelloDozerBeanMapping(Class<D> sourceType, Class<E> destType) {
        this.sourceType = sourceType;
        this.destType = destType;
    }

    public static <S, D> HelloDozerBeanMapping<S, D> create(Class<S> sourceType, Class<D> destType) {
        return new HelloDozerBeanMapping(sourceType, destType);
    }

    public HelloDozerBeanMapping fields(String srcFieldName, String destFieldName) {
        fields(srcFieldName, destFieldName, null);

        return this;
    }

    public HelloDozerBeanMapping fields(String srcFieldName, DozerConverter typeConverter) {
        fields(srcFieldName, null, typeConverter);

        return this;
    }

    public HelloDozerBeanMapping fields(String srcFieldName, String destFieldName, DozerConverter typeConverter) {
        BeanFieldMapping fieldMapping = BeanFieldMapping.create(srcFieldName, destFieldName, typeConverter);
        fieldMappingList.add(fieldMapping);

        return this;
    }

    public List<BeanFieldMapping> getFieldMappings() {
        return fieldMappingList;
    }


    public Class<D> getSourceType() {
        return sourceType;
    }

    public Class<E> getDestType() {
        return destType;
    }

    public static class BeanFieldMapping {

        /**
         * 源对象字段名称
         */
        private String sourceFieldName;

        /**
         * 目标对象字段名称
         */
        private String destFieldName;


        /**
         * 自定义的转换器
         */
        private DozerConverter typeConverter;

        private BeanFieldMapping(String srcFieldName, String destFieldName) {
            this(srcFieldName, destFieldName, null);
        }

        private BeanFieldMapping(String srcFieldName, DozerConverter typeConverter) {
            this(srcFieldName, null, typeConverter);
        }

        private BeanFieldMapping(String srcFieldName, String destFieldName, DozerConverter typeConverter) {
            this.sourceFieldName = srcFieldName;
            if (destFieldName == null) {
                this.destFieldName = srcFieldName;
            } else {
                this.destFieldName = destFieldName;
            }

            if (typeConverter != null) {
                this.typeConverter = typeConverter;
            }
        }

        public static BeanFieldMapping create(String srcFieldName, String destFieldName) {
            return new BeanFieldMapping(srcFieldName, destFieldName);
        }

        public static BeanFieldMapping create(String srcFieldName, DozerConverter typeConverter) {
            return new BeanFieldMapping(srcFieldName, typeConverter);
        }

        public static BeanFieldMapping create(String srcFieldName, String destFieldName, DozerConverter typeConverter) {
            return new BeanFieldMapping(srcFieldName, destFieldName, typeConverter);
        }

        public String getSourceFieldName() {
            return sourceFieldName;
        }

        public String getDestFieldName() {
            return destFieldName;
        }


        public DozerConverter getDozerConverter() {
            return typeConverter;
        }

    }
}