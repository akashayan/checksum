package com.akash.github.rediscache.aspects;

import com.akash.github.rediscache.annotations.ChecksumParam;
import com.akash.github.rediscache.annotations.Loggable;
import com.akash.github.rediscache.dtos.Address;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.management.relation.RoleUnresolvedList;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Aspect
@Component
public class LoggingAspect {

    private static final String BASE_PACKAGE = "com.akash.github";

    @Around("@annotation(loggable)")
    public Object logAround(ProceedingJoinPoint joinPoint, Loggable loggable) throws Throwable {
        Object[] requests = joinPoint.getArgs();
        Object firstRequest = requests[0];
        Map<Field, Object> fields = getAllFields(firstRequest);
        for(Map.Entry<Field, Object> mapEntry : fields.entrySet()) {
            Field field = mapEntry.getKey();
            Object obj = mapEntry.getValue();
            field.setAccessible(true);
            System.out.println("Key Field : " + field.getName() + " of type : " + field.getType()
                    + " : key value : " + field.get(obj) + " : package-name : " + field.getType().getPackageName());


        }
        getChecksumParams(fields);

        return joinPoint.proceed();


    }

    private Map<Field, Object> getAllFields(Object object) {
        Map<Field, Object> fields = new HashMap<>();
        Map<Field, Object> parentFieldsMap = getFieldsOfParent(object);
        System.out.println("Fetched fields of parent and itself");
        Map<Field, Object> nestedFieldsMap = new HashMap<>();
        for(Map.Entry<Field, Object> mapEntry : parentFieldsMap.entrySet()) {
            Field field = mapEntry.getKey();
            Object obj = mapEntry.getValue();
            field.setAccessible(true);
            if (field.getType().getPackageName().contains(BASE_PACKAGE)) {
                System.out.println("Entered");
                try {
                    nestedFieldsMap = getAllFields(field.get(object));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        fields.putAll(parentFieldsMap);
        fields.putAll(nestedFieldsMap);
        return fields;
    }

    private Map<Field, Object> getFieldsOfParent(Object object) {
        List<Field> fields = new ArrayList<>();
        Class currentClass = object.getClass();
        Map<Field, Object> map = new HashMap<>();
        while(currentClass != null && currentClass.getSuperclass() != null) {
            System.out.println("Class : " + currentClass.getSimpleName());
            fields.addAll(Arrays.asList(currentClass.getDeclaredFields()));
            fields.forEach(field -> {
                map.put(field, object);
            });
            System.out.println("Done1");
            currentClass = currentClass.getSuperclass();
            System.out.println("Done2");
        }
        System.out.println("Done3");
        return map;
    }

    private Map<Integer, Field> getChecksumParams(Map<Field, Object> fieldMap) {
        TreeMap<Integer, Field> finalChecksumParams=new TreeMap<>();
        for(Map.Entry<Field, Object> mapEntry : fieldMap.entrySet()) {
            Field field = mapEntry.getKey();
            Object obj = mapEntry.getValue();
            if(field.isAnnotationPresent(ChecksumParam.class)){
                finalChecksumParams.put(field.getAnnotation(ChecksumParam.class).order(),field);
            }
        }

        for(Map.Entry<Integer, Field> mapEntry : finalChecksumParams.entrySet()) {
            Integer order = mapEntry.getKey();
            Field field = mapEntry.getValue();
            field.setAccessible(true);
            System.out.println("Field : " + field.getName() + " : order : " + order);

        }

        return finalChecksumParams;


    }
}
