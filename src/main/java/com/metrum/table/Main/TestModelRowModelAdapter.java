/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrum.table.Main;

import com.metrum.table.RowModel;
import com.metrum.table.ColumnContext;
import static com.metrum.table.Utils.stringToDouble;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author leandro.lima
 */
public class TestModelRowModelAdapter implements RowModel<TestModelRowModelAdapter> {

    private final TestModel delegated;

    private static final Pattern PATTERN
            = Pattern.compile(
                    "(\\d*\\.\\d+)\\t"
                    + "(\\d*\\.\\d+)\\t"
                    + "(\\d*\\.\\d+)\\t"
                    + "(\\d*\\.\\d+)\\t"
                    + "(true|false)\\t"
                    + "(true|false)\\t"
                    + "(true|false)"
            );

    private static final List<ColumnContext> COLUMNS = new ArrayList<>();

    static {
        COLUMNS.add(new ColumnContext("Current", Double.class, true));
        COLUMNS.add(new ColumnContext("Voltage", Double.class, true));
        COLUMNS.add(new ColumnContext("PF", Double.class, true));
        COLUMNS.add(new ColumnContext("Phase", Double.class, true));
        COLUMNS.add(new ColumnContext("Is Inductive", Boolean.class, true));
        COLUMNS.add(new ColumnContext("Is Reactive", Boolean.class, true));
        COLUMNS.add(new ColumnContext("Is Reverse", Boolean.class, true));
    }

    public static List<ColumnContext> getColumns() {
        return COLUMNS;
    }

    public TestModelRowModelAdapter() {
        this.delegated = new TestModel();
    }
    
    public TestModelRowModelAdapter(TestModel model) {
        this.delegated = model;
    }

    @Override
    public Object getValueAt(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return delegated.getElementCurrent();
            case 1:
                return delegated.getElementVoltage();
            case 2:
                return delegated.getPowerFactor();
            case 3:
                return delegated.getPhaseFromA();
            case 4:
                return delegated.isIsInductive();
            case 5:
                return delegated.isIsReactive();
            case 6:
                return delegated.isIsReverse();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(int columnIndex, Object value) {
        switch (columnIndex) {
            case 0:
                delegated.setElementCurrent((double) value);
                break;
            case 1:
                delegated.setElementVoltage((double) value);
                break;
            case 2:
                delegated.setPowerFactor((double) value);
                break;
            case 3:
                delegated.setPhaseFromA((double) value);
                break;
            case 4:
                delegated.setIsInductive((Boolean) value);
                break;
            case 5:
                delegated.setIsReactive((Boolean) value);
                break;
            case 6:
                delegated.setIsReverse((Boolean) value);
                break;
        }
    }

    @Override
    public TestModelRowModelAdapter fromString(String source) {
        TestModel model = new TestModel();
        Matcher matcher = PATTERN.matcher(source);
        if (matcher.find()) {
            model.setElementCurrent(stringToDouble(matcher.group(1)));
            model.setElementVoltage(stringToDouble(matcher.group(2)));
            model.setPowerFactor(stringToDouble(matcher.group(3)));
            model.setPhaseFromA(stringToDouble(matcher.group(4)));

            model.setIsInductive(matcher.group(5).equalsIgnoreCase("true"));
            model.setIsReactive(matcher.group(6).equalsIgnoreCase("true"));
            model.setIsReverse(matcher.group(7).equalsIgnoreCase("true"));
        }
        
        return new TestModelRowModelAdapter(model);
    }

    @Override
    public TestModelRowModelAdapter copy() {
        TestModel clone = new TestModel();

        for (Class type = TestModel.class; !type.equals(Object.class); type = type.getSuperclass()) {
            Field[] fields = type.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    field.set(clone, field.get(this));
                } catch (IllegalArgumentException | IllegalAccessException e) {
                }
            }
        }
        
        return new TestModelRowModelAdapter(clone);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for(Field field: TestModel.class.getDeclaredFields()) {
            builder.append(field.getName()).append("\t");
        }
        builder.insert(builder.length() - 1, "\n");
        return builder.toString();
    }

}
