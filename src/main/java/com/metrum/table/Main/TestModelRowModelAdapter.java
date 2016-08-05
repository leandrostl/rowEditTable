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
import java.util.LinkedList;
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
                    + "(\\w[1,3])\\t"
                    + "(\\d*\\.\\d+)\\t"
                    + "(\\d*\\.\\d+)\\t"
                    + "(true|false)\\t"
                    + "(true|false)\\t"
                    + "(true|false)"
            );

    static enum Column {
        CURRENT(new ColumnContext("Current", Double.class, true)),
        VOLTAGE(new ColumnContext("Voltage", Double.class, true)),
        ELEMENTS(new ColumnContext("Elements", Elements.class, true)),
        POWER_FACTOR(new ColumnContext("PF", Double.class, true)),
        PHASE(new ColumnContext("Phase", Double.class, true)),
        INDUCTIVE(new ColumnContext("Is Inductive", Boolean.class, true)),
        REACTIVE(new ColumnContext("Is Reactive", Boolean.class, true)),
        REVERSE(new ColumnContext("Is Reverse", Boolean.class, true));

        private final ColumnContext context;

        private Column(ColumnContext context) {
            this.context = context;
            CONTEXTS.add(context);
        }

        public ColumnContext getContext() {
            return context;
        }

        public static LinkedList<ColumnContext> getColumns() {
            return CONTEXTS;
        }
    }
    final static LinkedList<ColumnContext> CONTEXTS = new LinkedList<>();

    public static List<ColumnContext> getColumns() {
        return Column.getColumns();
    }

    public TestModelRowModelAdapter() {
        this.delegated = new TestModel();
    }

    public TestModelRowModelAdapter(TestModel model) {
        this.delegated = model;
    }

    @Override
    public Object getValueAt(int columnIndex) {
        if (columnIndex == Column.CURRENT.ordinal())
            return delegated.getElementCurrent();
        if (columnIndex == Column.VOLTAGE.ordinal())
            return delegated.getElementVoltage();
        if (columnIndex == Column.ELEMENTS.ordinal())
            return delegated.getElements();
        if (columnIndex == Column.POWER_FACTOR.ordinal())
            return delegated.getPowerFactor();
        if (columnIndex == Column.PHASE.ordinal())
            return delegated.getPhaseFromA();
        if (columnIndex == Column.INDUCTIVE.ordinal())
            return delegated.isIsInductive();
        if (columnIndex == Column.REACTIVE.ordinal())
            return delegated.isIsReactive();
        if (columnIndex == Column.REVERSE.ordinal())
            return delegated.isIsReverse();

        return null;
    }

    @Override
    public void setValueAt(int columnIndex, Object value) {
        if (columnIndex == Column.CURRENT.ordinal())
            delegated.setElementCurrent((double) value);
        else if (columnIndex == Column.VOLTAGE.ordinal())
            delegated.setElementVoltage((double) value);
        else if (columnIndex == Column.ELEMENTS.ordinal())
            delegated.setElements((Elements) value);
        if (columnIndex == Column.POWER_FACTOR.ordinal())
            delegated.setPowerFactor((double) value);
        if (columnIndex == Column.PHASE.ordinal())
            delegated.setPhaseFromA((double) value);
        if (columnIndex == Column.INDUCTIVE.ordinal())
            delegated.setIsInductive((Boolean) value);
        if (columnIndex == Column.REACTIVE.ordinal())
            delegated.setIsReactive((Boolean) value);
        if (columnIndex == Column.REVERSE.ordinal())
            delegated.setIsReverse((Boolean) value);
    }

    @Override
    public TestModelRowModelAdapter fromString(String source) {
        TestModel model = new TestModel();
        Matcher matcher = PATTERN.matcher(source);
        if (matcher.find()) {
            model.setElementCurrent(stringToDouble(matcher.group(1)));
            model.setElementVoltage(stringToDouble(matcher.group(2)));
            model.setElements(Elements.valueOf(matcher.group(3)));
            model.setPowerFactor(stringToDouble(matcher.group(4)));
            model.setPhaseFromA(stringToDouble(matcher.group(5)));

            model.setIsInductive(matcher.group(6).equalsIgnoreCase("true"));
            model.setIsReactive(matcher.group(7).equalsIgnoreCase("true"));
            model.setIsReverse(matcher.group(8).equalsIgnoreCase("true"));
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
                    field.set(clone, field.get(delegated));
                } catch (IllegalArgumentException | IllegalAccessException e) {
                }
            }
        }

        return new TestModelRowModelAdapter(clone);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (Field field : TestModel.class.getDeclaredFields())
            builder.append(field.getName()).append("\t");
        builder.insert(builder.length() - 1, "\n");
        return builder.toString();
    }

}
