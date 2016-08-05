/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrum.table.renderer;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class ColumnResizeDecorator extends TableCellRendererDecorator {

    public static int count = 0;
    public static int count2 = 0;

    public static enum ColumnResizeMode {

        MIN, MAX, NONE
    };

    private final ColumnResizeMode mode;
    private final int columnPadding;
    private final boolean considerData;
    private final boolean considerHeader;

    public ColumnResizeDecorator(TableCellRenderer delagated, ColumnResizeMode mode,
            boolean considerHeader, boolean considerData, int columnPadding) {
        super(delagated);

        this.mode = mode;
        this.considerHeader = considerHeader;
        this.considerData = considerData;
        this.columnPadding = columnPadding;
    }

    public ColumnResizeDecorator(TableCellRenderer delagated, ColumnResizeMode mode,
            int columnPadding) {
        super(delagated);
        this.mode = mode;
        this.columnPadding = columnPadding;
        this.considerHeader = true;
        this.considerData = true;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int col) {

        final TableColumn column = table.getColumnModel().getColumn(col);
        int width = 0;
        if (considerHeader)
            width = getColumnHeaderWidth(table, col);

        if (considerData)
            width = Math.max(getColumnDataWidth(table, col), width);

        width += 2 * columnPadding;

        switch (mode) {
            case MAX:
                column.setMinWidth(width);
                break;
            case MIN:
                column.setMaxWidth(width);
                break;
            case NONE:
                column.setPreferredWidth(width);
                break;
        }

        return super.getTableCellRendererComponent(table, value, isSelected,
                hasFocus, row, col);
    }

    /**
     * Calculate the width based on the widest cell renderer for the given column.
     */
    private int getColumnDataWidth(JTable table, int column) {
        int preferredWidth = 0;
        int maxWidth = table.getColumnModel().getColumn(column).getMaxWidth();

        for (int row = 0; row < table.getRowCount(); row++) {
            preferredWidth = Math.max(preferredWidth, getCellDataWidth(table, row, column));

            //  We've exceeded the maximum width, no need to check other rows
            if (preferredWidth >= maxWidth)
                break;
        }

        return preferredWidth;
    }

    /**
     * Calculated the width based on the column name
     */
    private int getColumnHeaderWidth(JTable table, int column) {
        TableColumn tableColumn = table.getColumnModel().getColumn(column);
        Object value = tableColumn.getHeaderValue();
        TableCellRenderer renderer = tableColumn.getHeaderRenderer();

        if (renderer == null)
            renderer = table.getTableHeader().getDefaultRenderer();

        Component c = renderer.getTableCellRendererComponent(table, value, false, false, -1, column);
        return c.getPreferredSize().width;
    }

    /**
     * Get the preferred width for the specified cell
     */
    private int getCellDataWidth(JTable table, int row, int column) {
        final Object value = table.getValueAt(row, column);
        if (value == null)
            return table.getIntercellSpacing().width;
        final int width = table.getFontMetrics(table.getFont()).stringWidth(value.toString()) + table.getIntercellSpacing().width;
        return width;
    }

}
