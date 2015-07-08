/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrum.table.editor;

import java.awt.Component;
import java.util.EventObject;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author leandro.lima
 */
public abstract class CellEditorDecorator implements TableCellEditor {

    protected TableCellEditor delegated;

    public CellEditorDecorator(TableCellEditor delegated) {
        this.delegated = delegated;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, 
            boolean isSelected, int row, int column) {
        return delegated.getTableCellEditorComponent(table, value, isSelected, row, column);
    }

    @Override
    public Object getCellEditorValue() {
        return delegated.getCellEditorValue();
    }

    @Override
    public boolean isCellEditable(EventObject anEvent) {
        return delegated.isCellEditable(anEvent);
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        return delegated.shouldSelectCell(anEvent);
    }

    @Override
    public boolean stopCellEditing() {
        return delegated.stopCellEditing();
    }

    @Override
    public void cancelCellEditing() {
        delegated.cancelCellEditing();
    }

    @Override
    public void addCellEditorListener(CellEditorListener l) {
        delegated.addCellEditorListener(l);
    }

    @Override
    public void removeCellEditorListener(CellEditorListener l) {
        delegated.removeCellEditorListener(l);
    }

}
