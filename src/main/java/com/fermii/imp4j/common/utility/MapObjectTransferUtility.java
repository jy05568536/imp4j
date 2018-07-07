package com.fermii.imp4j.common.utility;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MapObjectTransferUtility {


    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException {
        Class data = Class.forName("com.fermii.imp4j.common.utility.Person");
        Person person = new Person();
        Field[] fs = data.getDeclaredFields();
        boolean acc = fs[0].isAccessible();
        fs[0].set(person, "123");
        System.out.println(1);
    }

    public static void setValue(Map map, Object thisObj) {
        Set set = map.keySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            Object val = map.get(obj);
            setMethod(obj, val, thisObj);
        }
    }

    public static void setMethod(Object method, Object value, Object thisObj) {
        Class c;
        try {
            c = Class.forName(thisObj.getClass().getName());
            String met = (String) method;
            met = met.trim();
            if (!met.substring(0, 1).equals(met.substring(0, 1).toUpperCase())) {
                met = met.substring(0, 1).toUpperCase() + met.substring(1);
            }
            if (!String.valueOf(method).startsWith("set")) {
                met = "set" + met;
            }
            Class types[] = new Class[1];
            types[0] = Class.forName("java.lang.String");
            Method m = c.getMethod(met, types);
            m.invoke(thisObj, value);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
