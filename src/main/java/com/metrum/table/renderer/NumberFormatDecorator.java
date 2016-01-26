/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrum.table.renderer;

import java.awt.Component;
import java.text.NumberFormat;
import java.util.IllegalFormatException;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author vinicius.fonseca
 */
public class NumberFormatDecorator extends TableCellRendererDecorator {

    public NumberFormatDecorator(TableCellRenderer delegated) {
        super(delegated);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        Component component = super.getTableCellRendererComponent(table, doubleToString((Double) value, 2),
                isSelected, hasFocus, row, column);
        return component;

    }

    private String doubleToString(Double value, int decimalsNumber) {
        if (value == null || value.isNaN())
            return "-";
        try {
            NumberFormat doubleFormat = NumberFormat.getInstance();
            doubleFormat.setMaximumFractionDigits(decimalsNumber);
            doubleFormat.setMinimumFractionDigits(decimalsNumber);
            String number = doubleFormat.format(value);
            return number;
        } catch (IllegalFormatException ex) {

            return "-";
        }
    }

}
