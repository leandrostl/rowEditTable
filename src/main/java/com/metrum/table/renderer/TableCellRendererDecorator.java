/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrum.table.renderer;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author leandro.lima
 */
public abstract class TableCellRendererDecorator implements TableCellRenderer {

    protected TableCellRenderer delagated;

    public TableCellRendererDecorator(TableCellRenderer delagated) {
        this.delagated = delagated;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return delagated.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }

}
