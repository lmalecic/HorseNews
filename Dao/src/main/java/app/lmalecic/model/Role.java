/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.lmalecic.model;

import app.lmalecic.dal.annotations.ColumnName;

/**
 *
 * @author lmalecic
 */
public enum Role {
    ADMINISTRATOR("Administrator"),
    USER("User");
    
    private String name;
    
    private Role(String name) {
        this.setName(name);
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return this.getName();
    }
}
