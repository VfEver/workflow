package com.zys.executor;

import com.zys.constant.Constant;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Helper {
    /**
     * 获取obj的字段名称
     * @param obj
     * @return
     */
    public static Map<String, Object> Obj2Map (Object obj) {
        Map<String, Object> resultMap = new HashMap<>(Constant.DEFAULT_SIZE);
        if (obj == null) {
            return resultMap;
        }
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                resultMap.put(field.getName(), field.get(obj));
            }catch (Exception e) {
            }
        }
        return resultMap;
    }
}
