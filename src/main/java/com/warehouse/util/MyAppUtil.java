package com.warehouse.util;

import java.util.*;

public class MyAppUtil {
    public static Map<Integer,String> convertListToMap(List<Object[]> objectList) {
       Map<Integer,String> map = new LinkedHashMap<>();
       for (Object[] obj : objectList) {
           map.put((Integer) obj[0], obj[1].toString());
       }
       return map;
    }

    public static String genPwd() {
        return UUID.randomUUID()
                .toString()
                .replaceAll("-","")
                .substring(0,10);
    }

    public static Integer generateOtp() {
        Random random = new Random();
        int number = random.nextInt(999999);
        return new Integer(String.format("%06d",number));
    }
}
