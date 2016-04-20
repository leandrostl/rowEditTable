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
    private String toolTip;
    private Class type;
    private boolean isEditable;
    private boolean isAutoIncremented;

    public ColumnContext(String name, String toolTip, Class type, boolean isEditable, boolean isAutoIncremented) {
        this.name = name;
        this.toolTip = toolTip;
        this.type = type;
        this.isEditable = isEditable;
        this.isAutoIncremented = isAutoIncremented;
    }
    
    public ColumnContext(String name, String toolTip, Class type, boolean isEditable) {
        this.name = name;
        this.toolTip = toolTip;
        this.type = type;
        this.isEditable = isEditable;
        this.isAutoIncremented = false;
    }
    
    public ColumnContext(String name, Class type, boolean isEditable) {
        this.name = name;
        this.toolTip = null;
        this.type = type;
        this.isEditable = isEditable;
        this.isAutoIncremented = false;
    }
    
    public ColumnContext(String name, Class type, boolean isEditable, 
            boolean isAutoIncremented) {
        this.name = name;
        this.toolTip = null;
        this.type = type;
        this.isEditable = isEditable;
        this.isAutoIncremented = isAutoIncremented;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToolTip() {
        return toolTip;
    }

    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
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

    public boolean isAutoIncremented() {
        return isAutoIncremented;
    }

    public void setAutoIncremented(boolean isAutoIncremented) {
        this.isAutoIncremented = isAutoIncremented;
    }
    
    
    
    
}
