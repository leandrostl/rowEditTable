/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrum.table;

/**
 *
 * @author leandro.lima
 * @param <V> Objeto adaptado (adaptable) pelo RowModel.
 */
public interface RowModel<V> {
    public Object getValueAt(int columnIndex);
    
    public void setValueAt(int columnIndex, Object value);
    
    public RowModel<V> fromString(String adaptableStringRepresentation);
    
    public RowModel<V> copy();
}
