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
public interface RowModel<T> {
    public Object getValueAt(int columnIndex);
    
    public void setValueAt(int columnIndex, Object value);
    
    public T fromString(String source);
    
    public T copy();
}
