/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.lmalecic.dal.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 *
 * @author lmalecic
 */
@Retention(RetentionPolicy.RUNTIME)  // Make annotation available at runtime
@Target(ElementType.FIELD)           // Restrict use to fields
public @interface ColumnName {
    String value();
}

