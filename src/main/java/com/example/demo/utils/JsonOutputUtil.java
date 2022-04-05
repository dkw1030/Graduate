package com.example.demo.utils;


import com.alibaba.fastjson.JSONArray;

import java.lang.reflect.*;
import java.util.*;

public class JsonOutputUtil {
    public static int tot;

    public static void main(String args[]) throws Exception {
        targetObject target = new targetObject();
        tot = target.getDepth();
        System.out.println(target.getClass());
        Object obj = JSONArray.toJSON(generateObj(target.getClass(), null, tot));
        System.out.println(obj.toString());
    }

    public static Object generateObj(Class<?> clazz, Object templateObj, int timer) throws Exception {
        if (timer <= 0) {
            return null;
        }
        Object temp = null;
        //判断基本类型
        if (clazz.equals(Integer.class) || clazz.equals(int.class)) {
            return 2;
        } else if (clazz.equals(Long.class) || clazz.equals(long.class)) {
            return 3L;
        } else if (clazz.equals(Short.class) || clazz.equals(short.class)) {
            return (short) 1;
        } else if (clazz.equals(Byte.class) || clazz.equals(byte.class)) {
            return 0;
        } else if (clazz.equals(Double.class) || clazz.equals(double.class)) {
            return 1.1;
        } else if (clazz.equals(Float.class) || clazz.equals(float.class)) {
            return 1.2f;
        } else if (clazz.equals(Boolean.class) || clazz.equals(boolean.class)) {
            return true;
        } else if (clazz.equals(String.class)) {
            return "String";
        } else if (clazz.equals(Character.class) || clazz.equals(char.class)) {
            return 'c';
        } else if (clazz.isArray()) {
            Class<?> componentType = clazz.getComponentType();
            Object array =  Array.newInstance(componentType, 2);
            Object element = generateObj(componentType,null, timer);
            Array.set(array,0,element);
            Array.set(array,1,element);
            return array;
        }
        //递归实体类
        Object obj = clazz.newInstance();
        for (int i = 0; i < clazz.getDeclaredFields().length; i++) {
            Field curField = clazz.getDeclaredFields()[i];
            Class<?> subClass = curField.getType();
            System.out.println(F(timer - 1, true) + subClass);
            if (Modifier.isPrivate(curField.getModifiers())) {
                curField.setAccessible(true);
            }
            if (subClass.equals(Integer.class) || subClass.equals(int.class) ||
                    subClass.equals(Long.class) || subClass.equals(long.class) ||
                    subClass.equals(Short.class) || subClass.equals(short.class) ||
                    subClass.equals(Byte.class) || subClass.equals(byte.class) ||
                    subClass.equals(Double.class) || subClass.equals(double.class) ||
                    subClass.equals(Boolean.class) || subClass.equals(boolean.class) ||
                    subClass.equals(Float.class) || subClass.equals(float.class) ||
                    subClass.equals(String.class) ||
                    subClass.equals(Character.class) || subClass.equals(char.class)) {
                if (!Modifier.isFinal(curField.getModifiers()) && !Modifier.isTransient(curField.getModifiers()) && !Modifier.isStatic(curField.getModifiers())) {
                    temp = generateObj(subClass, null, timer - 1);
                    if (temp != null) {
                        curField.set(obj, temp);
                    }
                }
            } else if (List.class.isAssignableFrom(subClass)) {
                Type genericType = curField.getGenericType();
                if (genericType instanceof ParameterizedType) {
                    ParameterizedType pt = (ParameterizedType) genericType;
                    // 得到泛型里的class类型对象
                    List<Object> curEleList = new ArrayList<>();


                    Object element = splitType(pt.getActualTypeArguments()[0].toString(), timer - 1);
                    if (element != null
                            && !Modifier.isFinal(curField.getModifiers())
                            && !Modifier.isTransient(curField.getModifiers())
                            && !Modifier.isStatic(curField.getModifiers())
                            && !clazz.equals(element.getClass())) {
                        curEleList.add(element);
                        curEleList.add(element);
                        curField.set(obj, curEleList);
                    }
                }
            } else if (Map.class.isAssignableFrom(subClass)) {
                Type genericType = curField.getGenericType();
                if (genericType instanceof ParameterizedType) {
                    ParameterizedType pt = (ParameterizedType) genericType;
                    Object key = splitType(pt.getActualTypeArguments()[0].toString(), timer - 1);
                    Object value = splitType(pt.getActualTypeArguments()[1].toString(), timer - 1);
                    Map<Object, Object> map = new HashMap<>();
                    if (key != null && value != null
                            && !Modifier.isFinal(curField.getModifiers())
                            && !Modifier.isTransient(curField.getModifiers())
                            && !Modifier.isStatic(curField.getModifiers())
                            && !clazz.equals(key.getClass())
                            && !clazz.equals((value.getClass()))) {
                        map.put(key, value);
                        curField.set(obj, map);
                    }
                }
            } else if (Set.class.isAssignableFrom(subClass)) {
                Type genericType = curField.getGenericType();
                if (genericType instanceof ParameterizedType) {
                    ParameterizedType pt = (ParameterizedType) genericType;
                    Object key = splitType(pt.getActualTypeArguments()[0].toString(), timer - 1);
                    Set<Object> objectSet = new HashSet<>();
                    if (key != null
                            && !Modifier.isFinal(curField.getModifiers())
                            && !Modifier.isTransient(curField.getModifiers())
                            && !Modifier.isStatic(curField.getModifiers())
                            && !clazz.equals(key.getClass())) {
                        objectSet.add(key);
                        curField.set(obj, objectSet);
                    }

                }
            } else if (subClass.equals(Object.class)) {
                if (!Modifier.isFinal(curField.getModifiers()) && !Modifier.isTransient(curField.getModifiers()) && !Modifier.isStatic(curField.getModifiers())) {
                    curField.set(obj, templateObj);
                }

            } else {
                Type genericType = curField.getGenericType();
                if (genericType instanceof ParameterizedType) {
                    ParameterizedType pt = (ParameterizedType) genericType;
                    // 得到泛型里的class类型对象
                    if (!Modifier.isFinal(curField.getModifiers()) && !Modifier.isTransient(curField.getModifiers()) && !Modifier.isStatic(curField.getModifiers()) && !curField.getType().equals(clazz)) {
                        temp = generateObj(subClass,
                                splitType(pt.getActualTypeArguments()[0].toString(), timer - 1),
                                timer - 1);
                        curField.set(obj, temp);
                        //System.out.println(F(timer-1, true) + F(timer-1, false) + subClass);
                    }

                } else {
                    if (!Modifier.isFinal(curField.getModifiers()) && !Modifier.isTransient(curField.getModifiers()) && !Modifier.isStatic(curField.getModifiers())) {
                        temp = generateObj(subClass, null, timer - 1);
                        curField.set(obj, temp);
                    }
                }
            }
            //System.out.println(F(timer, true) + F(timer-1, false) + subClass);
        }
        System.out.println(F(timer, true) + F(timer, false) + clazz);
        return obj;
    }

    public static Object splitType(String rowType, int timer) throws Exception {
        if(timer<=0){
            return null;
        }
        String type = rowType.replace("class ", "");
        String[] split = type.split("<", 2);
        System.out.println(F(timer-1, true) + rowType);
        if (split.length == 1) {
            return generateObj(Class.forName(type), null, timer-1);
        } else {
            Class<?> thisClass = Class.forName(split[0]);
            //System.out.println(F(timer-1,true)+rowType);
            String[] newTemplateClass = split[1].substring(0, split[1].length() - 1).split(", ");
            Object[] newObject = new Object[newTemplateClass.length];
            for (int j = 0; j < newTemplateClass.length; j++) {
                newObject[j] = splitType(newTemplateClass[j], timer - 1);
            }
            System.out.println(F(timer-1, true) + F(timer-1, false) + rowType);
            if (Map.class.isAssignableFrom(thisClass)) {
                Map<Object, Object> map = new HashMap<>();
                map.put(newObject[0], newObject[1]);
                return map;
            } else if (List.class.isAssignableFrom(thisClass)) {
                List<Object> list = new ArrayList<>();
                list.add(newObject[0]);
                list.add(newObject[0]);
                return list;
            } else if (Set.class.isAssignableFrom(thisClass)) {
                Set<Object> set = new HashSet<>();
                set.add(newObject[0]);
                return set;
            } else {
                return generateObj(thisClass, newObject[0].toString(), timer);
            }
        }
    }

    public static String F(int timer, boolean b) {
        String t = "\t";
        String rt = "____";
        String format = "";
        if (b) {
            for (int i = 0; i < tot - timer; i++) {
                format += t;
            }
        } else {
            for (int i = 0; i < 20 + timer - tot; i++) {
                format += rt;
            }
        }
        return format;
    }
}

