package com.metrum.table.editor;

import java.awt.Component;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellEditor;
import javax.swing.text.NumberFormatter;

public class NumberDecorator extends CellEditorDecorator {

    public NumberDecorator(TableCellEditor delagated) {
        super(delagated);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        JFormattedTextField editor = (JFormattedTextField) super.getTableCellEditorComponent(table, value, isSelected, row, column);

        if (value instanceof Number) {
            Locale myLocale = Locale.getDefault();

            NumberFormat doubleFormat = NumberFormat.getInstance(myLocale);

            editor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                    new NumberFormatter(doubleFormat)));

            
            editor.setValue(value);
        }
        return editor;
    }

    @Override
    public boolean stopCellEditing() {
        try {
            // try to get the value
            this.getCellEditorValue();
            return super.stopCellEditing();
        } catch (Exception ex) {
            return false;
        }

    }

    @Override
    public Object getCellEditorValue() {
        // get content of textField
        String str = (String) super.getCellEditorValue();
        if (str == null)
            return null;

        if (str.length() == 0)
            return null;

        // try to parse a number
        try {
            ParsePosition pos = new ParsePosition(0);
            Number n = NumberFormat.getInstance().parse(str, pos);
            if (pos.getIndex() != str.length())
                throw new ParseException("parsing incomplete", pos.getIndex());

            // return an instance of column class
            return n.doubleValue();

        } catch (ParseException pex) {
            throw new RuntimeException(pex);
        }
    }
}
