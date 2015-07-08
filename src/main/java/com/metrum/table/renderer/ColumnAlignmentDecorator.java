/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrum.table.renderer;

import java.awt.Component;
import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

public class ColumnAlignmentDecorator extends TableCellRendererDecorator {

    private final int horizontalAlignment;
    private final int verticalAlignment;

    public ColumnAlignmentDecorator(TableCellRenderer delagated,
            int horizontalAlignment, int verticalAlignment) {
        super(delagated);
        this.horizontalAlignment = horizontalAlignment;
        this.verticalAlignment = verticalAlignment;
    }

    public int getHorizontalAlignment() {
        return horizontalAlignment;
    }

    public int getVerticalAlignment() {
        return verticalAlignment;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value,
                isSelected, hasFocus, row, column);

        if (component instanceof JLabel) {
            final JLabel label = (JLabel) component;
            label.setHorizontalAlignment(horizontalAlignment);
            label.setVerticalAlignment(verticalAlignment);
        }

        if (component instanceof JTextField) {
            ((JTextField) component).setHorizontalAlignment(horizontalAlignment);
        }        

        if (component instanceof AbstractButton) {
            final AbstractButton button = (AbstractButton) component;
            button.setHorizontalAlignment(horizontalAlignment);
            button.setVerticalAlignment(verticalAlignment);
        }       

        return component;
    }

}
