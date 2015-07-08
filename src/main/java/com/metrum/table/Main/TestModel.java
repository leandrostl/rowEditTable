/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrum.table.Main;

import com.metrum.table.AbstractRowModel;
import com.metrum.table.ColumnContext;
import static com.metrum.table.Utils.stringToDouble;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author leandro.lima
 */
public class TestModel extends AbstractRowModel<TestModel> implements Cloneable {

    private final static Collection<ColumnContext> columns = new ArrayList<>();

    static {
        columns.add(new ColumnContext("Current", Double.class, true));
        columns.add(new ColumnContext("Voltage", Double.class, true));
        columns.add(new ColumnContext("PF", Double.class, true));
        columns.add(new ColumnContext("Phase", Double.class, true));
        columns.add(new ColumnContext("Is Inductive", Boolean.class, true));
        columns.add(new ColumnContext("Is Reactive", Boolean.class, true));
        columns.add(new ColumnContext("Is Reverse", Boolean.class, true));
    }

    private static final Pattern pattern
            = Pattern.compile(
                    "(\\d*\\.\\d+)\\t"
                    + "(\\d*\\.\\d+)\\t"
                    + "(\\d*\\.\\d+)\\t"
                    + "(\\d*\\.\\d+)\\t"
                    + "(true|false)\\t"
                    + "(true|false)\\t"
                    + "(true|false)"
            );

    public TestModel() {

    }

    public static Collection<ColumnContext> getColumns() {
        return columns;
    }

    private double elementCurrent;

    private double elementVoltage;

    private double powerFactor;

    private double phaseFromA;

    private boolean isReactive;

    private boolean isInductive;

    private boolean isReverse;

    @Override
    public Object getValueAt(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return elementCurrent;
            case 1:
                return elementVoltage;
            case 2:
                return powerFactor;
            case 3:
                return phaseFromA;
            case 4:
                return isInductive;
            case 5:
                return isReactive;
            case 6:
                return isReverse;
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(int columnIndex, Object value) {
        switch (columnIndex) {
            case 0:
                this.elementCurrent = (double) value;
                break;
            case 1:
                this.elementVoltage = (double) value;
                break;
            case 2:
                this.powerFactor = (double) value;
                break;
            case 3:
                this.phaseFromA = (double) value;
                break;
            case 4:
                this.isInductive = (Boolean) value;
                break;
            case 5:
                this.isReactive = (Boolean) value;
                break;
            case 6:
                this.isReverse = (Boolean) value;
                break;
        }
    }

    @Override
    protected TestModel clone() throws CloneNotSupportedException {
        super.clone();
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
        return clone;
    }

    @Override
    public TestModel copy() {
        try {            
            return this.clone();
        } catch (CloneNotSupportedException ex) {
            return new TestModel();
        }
    }

    @Override
    public TestModel fromString(String source) {
        TestModel model = new TestModel();
        Matcher matcher = pattern.matcher(source);
        if (matcher.find()) {
            model.elementCurrent = stringToDouble(matcher.group(1));
            model.elementVoltage = stringToDouble(matcher.group(2));
            model.powerFactor = stringToDouble(matcher.group(3));
            model.phaseFromA = stringToDouble(matcher.group(4));

            model.isInductive = matcher.group(5).equalsIgnoreCase("true");
            model.isReactive = matcher.group(6).equalsIgnoreCase("true");
            model.isReverse = matcher.group(7).equalsIgnoreCase("true");
        }

        return model;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(elementCurrent).append("\t")
                .append(elementVoltage).append("\t")
                .append(powerFactor).append("\t")
                .append(phaseFromA).append("\t")
                .append(isInductive).append("\t")
                .append(isReactive).append("\t")
                .append(isReverse).append("\n");
        return builder.toString();
    }

}
