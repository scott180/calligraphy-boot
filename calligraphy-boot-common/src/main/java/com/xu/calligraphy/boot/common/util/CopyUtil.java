package com.xu.calligraphy.boot.common.util;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xu
 * @date 2020/1/1 15:12
 */
public class CopyUtil<S, T> {

    public static <S, T> List<T> copyPropertiesList(List<S> sourceList, Class<T> clazz) {
        try {
            List<T> targetList = new ArrayList<T>(sourceList.size());
            for (int i = 0; i < sourceList.size(); i++) {
                T t = clazz.newInstance();
                BeanUtils.copyProperties(sourceList.get(i), t);
                targetList.add(t);
            }
            return targetList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
