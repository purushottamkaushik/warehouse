package com.warehouse.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MyAppUtil {
    public static Map<Integer,String> convertListToMap(List<Object[]> objectList) {
       Map<Integer,String> map = new LinkedHashMap<>();
       for (Object[] obj : objectList) {
           map.put((Integer) obj[0], obj[1].toString());
       }
       return map;
    }

}
