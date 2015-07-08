/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrum.table.renderer;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;


public class AlternateRowDecorator extends TableCellRendererDecorator {
    private final Color color;
    private final int decoratedInterval;
    private final int undecoratedInterval;

    public AlternateRowDecorator(TableCellRenderer delagated, Color color, 
            int decoratedInterval, int undecoratedInterval) {
        super(delagated);
        this.color = color;
        this.decoratedInterval = decoratedInterval;
        this.undecoratedInterval = undecoratedInterval;
    }

    public Color getColor() {
        return color;
    }

    public int getDecoratedInterval() {
        return decoratedInterval;
    }

    public int getUndecoratedInterval() {
        return undecoratedInterval;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, 
            boolean isSelected, boolean hasFocus, int row, int column) {
        
        Component component = super.getTableCellRendererComponent(table, value, 
                isSelected, hasFocus, row, column);
        
        if (!isSelected) {
            
            Color defaultBackground = ((row % (decoratedInterval + 
                    undecoratedInterval)) >= undecoratedInterval && 
                    color != null) ? color : table.getBackground();
            component.setBackground(defaultBackground);
        }
        
        return component;
    }
    
}
