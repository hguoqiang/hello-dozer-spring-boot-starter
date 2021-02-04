package org.hello.dozer.spring.boot.autoconfigure;

import org.dozer.DozerBeanMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:此类继承 {@link DozerBeanMapper},扩展一些方法
 * @author: huanggq
 * @create: 2021-02-03 19:24
 **/
public class HelloDozerBeanMapper extends DozerBeanMapper {


    /**
     * 把源对象列表转换成目标对象类型的列表
     * @param source
     * @param destinationClass
     * @param <T>
     * @return
     */
    public <T> List<T> mapList(List<?> source, Class<T> destinationClass) {
        if (source == null) {
            return null;
        }
        List<T> destinationList = new ArrayList<>();
        for (Object each : source) {
            if (each == null) {
                destinationList.add(null);
            } else {
                destinationList.add(map(each, destinationClass));
            }
        }
        return destinationList;
    }

}
