/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrum.table;

/**
 *
 * @author leandro.lima
 * @param <T>
 */
public abstract class AbstractRowModel<T extends Cloneable> {
    public abstract Object getValueAt(int columnIndex);
    
    public abstract void setValueAt(int columnIndex, Object value);
    
    protected abstract T fromString(String source);
    
    public abstract T copy();
}
