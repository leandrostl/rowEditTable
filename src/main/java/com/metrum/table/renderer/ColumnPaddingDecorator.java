/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrum.table.renderer;

import java.awt.Component;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author leandro.lima
 */
public class ColumnPaddingDecorator extends TableCellRendererDecorator {
    private final Border padding;
    
    public ColumnPaddingDecorator(TableCellRenderer delagated,
            int top, int left, int bottom, int right) {
        super(delagated);
        padding = BorderFactory.createEmptyBorder(top, left, bottom, right);
    }
    
    public ColumnPaddingDecorator(TableCellRenderer delagated,
            int topBottom, int leftRight) {
        super(delagated);
        padding = BorderFactory.createEmptyBorder(topBottom, leftRight, topBottom, leftRight);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value,
                isSelected, hasFocus, row, column);
        if (component instanceof JLabel) {
            final JLabel label = (JLabel) component;
            label.setBorder(BorderFactory.createCompoundBorder(label.getBorder(), padding));
            return label;
        }

        if (component instanceof JTextField) {
            final JTextField field = (JTextField) component;
            field.setBorder(BorderFactory.createCompoundBorder(field.getBorder(), padding));
            return field;
        }

        if (component instanceof AbstractButton){
            final AbstractButton button = (AbstractButton) component;
            button.setBorder(BorderFactory.createCompoundBorder(button.getBorder(), padding));
            return button;
        }
        
        return component;
    } 

}
