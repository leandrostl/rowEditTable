/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrum.table;

/**
 *
 * @author leandro.lima
 */
public final class ColumnContext {
    
    private String name;
    private Class type;
    private boolean isEditable;

    public ColumnContext(String name, Class type, boolean isEditable) {
        this.name = name;
        this.type = type;
        this.isEditable = isEditable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public void isEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }
    
    
    
    
}
