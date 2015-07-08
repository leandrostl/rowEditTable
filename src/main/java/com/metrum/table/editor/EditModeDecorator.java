/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrum.table.editor;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.text.JTextComponent;

/**
 *
 * @author leandro.lima
 */
public class EditModeDecorator extends CellEditorDecorator {
    private final EditMode mode;
    public enum EditMode { SELECT_ON_FOCUS, CARET_AT_BEGIN, CARET_AT_END }
    
    public EditModeDecorator(TableCellEditor delagated, EditMode mode) {
        super(delagated);
        this.mode = mode;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        Component component = super.getTableCellEditorComponent(table, value,
                isSelected, row, column);
        
        if (component instanceof JTextComponent) {
            JTextComponent textComponent = (JTextComponent) component;
            textComponent.requestFocus();
            switch(mode) {
                case SELECT_ON_FOCUS:
                    textComponent.selectAll();
                    break;
                case CARET_AT_BEGIN:
                    textComponent.setCaretPosition(0);
                    break;
                case CARET_AT_END:
                    
                    textComponent.setCaretPosition(textComponent.getText().length());
            }
            
        }
        return component;
    }
}
