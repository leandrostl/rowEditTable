/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrum.table.renderer;

import java.awt.Component;
import java.awt.FontMetrics;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class ColumnResizeDecorator extends TableCellRendererDecorator {

    public static enum ColumnResizeMode {

        MIN, MAX, NONE
    };
    private final ColumnResizeMode mode;
    private final int columnPadding;

    public ColumnResizeDecorator(TableCellRenderer delagated, ColumnResizeMode mode, int columnPadding) {
        super(delagated);
        this.mode = mode;
        this.columnPadding = columnPadding;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int col) {
        Component component = super.getTableCellRendererComponent(table,
                value, isSelected, hasFocus, row, col);

        final JTableHeader header = table.getTableHeader();
        final FontMetrics headerFontMetrics
                = header.getFontMetrics(header.getFont());
        final TableColumn column = table.getColumnModel().getColumn(col);
        final Double headerBounds = headerFontMetrics.getStringBounds((String) column.getHeaderValue(), header.getGraphics()).getWidth();
        final int headerWidth = headerBounds.intValue() + columnPadding;
        column.setMaxWidth(Integer.MAX_VALUE);
        column.setMinWidth(headerWidth);
        int preferredWidth = headerWidth;

        for (int rowIndex = 0; rowIndex < table.getRowCount(); rowIndex++) {
            preferredWidth = Math.max(component.getPreferredSize().width,
                    preferredWidth);
            
        }

        switch (mode) {
            case MAX:
                column.setMinWidth(preferredWidth);
                break;
            case MIN:
                column.setMaxWidth(preferredWidth);
                break;
            case NONE:
                column.setPreferredWidth(preferredWidth);
                break;
        }

        return component;
    }

}
