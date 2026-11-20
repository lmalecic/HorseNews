/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.lmalecic.dal.annotations;

import java.lang.reflect.Field;

/**
 *
 * @author lmalecic
 */
public class AnnotationReader {
    public static String getColumnName(Class<?> objClass, String fieldName) throws NoSuchFieldException, Exception {
        Field field = objClass.getDeclaredField(fieldName);
        if (field.isAnnotationPresent(ColumnName.class)) {
            return field.getAnnotation(ColumnName.class).value();
        } else {
            throw new Exception("ColumnName annotation not found!");
        }
    }
}
