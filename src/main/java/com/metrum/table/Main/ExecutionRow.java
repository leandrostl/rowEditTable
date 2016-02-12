/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrum.table.Main;

/**
 *
 * @author leandro.lima
 */
public class ExecutionRow {
    private String index;
    private String value;

    public ExecutionRow(String index, String value) {
        this.index = index;
        this.value = value;
    }

    public ExecutionRow() {
        this.index = "";
        this.value = " - ";
    }
    
    

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    
}
