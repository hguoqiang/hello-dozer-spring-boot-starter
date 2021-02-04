package org.hello.dozer.spring.boot.utils;


import org.dozer.CustomConverter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * @description: 解析泛型的类型工具栏
 * @author: huanggq
 * @create: 2021-02-02 19:41
 **/
public class ResolveGenericTypeUtils {
    

    public static Pair<Class, Class> resolveParameterizedType(Class<? extends CustomConverter> dozerConverter) {

        Type genericSuperclass = dozerConverter.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {

            Type[] parameterizedTypeArray = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
            return new Pair<Class, Class>(getClass(parameterizedTypeArray[0]), getClass(parameterizedTypeArray[1]));
        }
        return null;

    }

    private static Class getClass(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        }
        return (Class) ((ParameterizedType) type).getRawType();
    }


}
