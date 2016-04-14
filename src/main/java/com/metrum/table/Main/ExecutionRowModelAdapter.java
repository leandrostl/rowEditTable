/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrum.table.Main;

import com.metrum.table.ColumnContext;
import com.metrum.table.RowModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author leandro.lima
 */
public class ExecutionRowModelAdapter implements RowModel<ExecutionRow> {

    private final ExecutionRow delegated;

    private static final Pattern PATTERN
            = Pattern.compile(
                    "(\\.+)\\t"
                    + "(\\.+)"
            );

    private static final List<ColumnContext> COLUMNS = new ArrayList<>();

    static {
        COLUMNS.add(new ColumnContext("Teste", String.class, false));
        COLUMNS.add(new ColumnContext("Medidor 1", String.class, false));
    }

    public ExecutionRowModelAdapter(ExecutionRow delegated) {
        this.delegated = delegated;
    }

    public ExecutionRowModelAdapter() {
        this.delegated = new ExecutionRow();
    }

    public static List<ColumnContext> getColumns() {
        return COLUMNS;
    }

    @Override
    public Object getValueAt(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return delegated.getIndex();
            case 1:
                return delegated.getValue();
            default:
                throw new AssertionError();
        }
    }

    @Override
    public void setValueAt(int columnIndex, Object value) {
        switch (columnIndex) {
            case 0:
                delegated.setIndex((String) value);
            case 1:
                delegated.setValue((String) value);
            default:
                throw new AssertionError();
        }
    }

    @Override
    public ExecutionRowModelAdapter fromString(String source) {
        ExecutionRow model = new ExecutionRow();
        Matcher matcher = PATTERN.matcher(source);
        if (matcher.find()) {
            model.setIndex(matcher.group(1));
            model.setIndex(matcher.group(2));
        }

        return new ExecutionRowModelAdapter(model);
    }

    @Override
    public ExecutionRowModelAdapter copy() {
        ExecutionRow clone = new ExecutionRow();

        for (Class type = ExecutionRow.class; !type.equals(Object.class); type = type.getSuperclass()) {
            Field[] fields = type.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    field.set(clone, field.get(delegated));
                } catch (IllegalArgumentException | IllegalAccessException e) {
                }
            }
        }
        
        return new ExecutionRowModelAdapter(clone);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (Field field : ExecutionRow.class.getDeclaredFields())
            builder.append(field.getName()).append("\t");
        builder.insert(builder.length() - 1, "\n");
        return builder.toString();
    }
}
