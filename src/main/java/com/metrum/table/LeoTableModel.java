/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrum.table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.table.AbstractTableModel;

/**
 * Classe que implementa um modelo para tabela genérico.
 *
 * @author leandro.lima
 * @param <T>
 */
public class LeoTableModel<T extends AbstractRowModel> extends AbstractTableModel {
    private final LinkedList<ColumnContext> columns;
    private final LinkedList<T> model = new LinkedList<>();
    private final Class<T> rowType;

    public LeoTableModel(Class<T> rowType, Collection<ColumnContext> columns) {
        this.columns = new LinkedList<>(columns);
        this.rowType = rowType;
    }

    @Override
    public int getRowCount() {
        return model.size();
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return model.get(rowIndex).getValueAt(columnIndex);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columns.get(columnIndex).getName();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columns.get(columnIndex).getType();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columns.get(columnIndex).isEditable();
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        final T row = model.get(rowIndex);
        row.setValueAt(columnIndex, value);
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    public void removeRows(int firstRowIndex, int lastRowIndex) {
        if (firstRowIndex > lastRowIndex) {
            throw new IllegalArgumentException("LastRowIndex " + lastRowIndex
                    + " must be greater or equal to " + firstRowIndex);
        }
        
        if (model.size() <= lastRowIndex) {
            throw new StackOverflowError("The lastRowIndex " + lastRowIndex
                    + " must be less than row count " + model.size());
        }

        for (int i = lastRowIndex; i >= firstRowIndex; i--) {
            model.remove(i);
        }
        fireTableRowsDeleted(firstRowIndex, lastRowIndex);
    }

    public List<T> getRows(int firstRowIndex, int lastRowIndex) {
        if (firstRowIndex > lastRowIndex) {
            throw new IllegalArgumentException("LastRowIndex " + lastRowIndex
                    + " must be greater or equal to " + firstRowIndex);
        }
        if (model.size() < lastRowIndex) {
            throw new StackOverflowError("The lastRowIndex " + lastRowIndex
                    + " must be less or equal to row count " + model.size());
        }

        List<T> copy = new ArrayList<>();

        model.subList(firstRowIndex, lastRowIndex).stream().forEach((element) -> {
            copy.add((T) element.copy());
        });
        return copy;
    }

    public void addRow() {
        addRow(model.size());
    }

    private void addRow(int rowIndex) {
        try {
            addRow(rowIndex, rowType.newInstance());
        } catch (IllegalAccessException | InstantiationException ex) {

        }
    }

    private void addRow(int rowIndex, T row) {
        model.add(rowIndex, row);
        fireTableRowsInserted(rowIndex, rowIndex);
    }

    public void insertRowsAt(int rowIndex, int quantity) {
        if (rowIndex > model.size()) {
            throw new StackOverflowError("The rowIndex " + rowIndex
                    + " must be less or equal to row count " + model.size());
        }

        for (int i = 0; i < quantity; i++) {
            addRow(i + rowIndex);
        }
    }

    public void insertRowsAt(int rowIndex, List<T> rows) {
        if (rowIndex > model.size()) {
            throw new StackOverflowError("The rowIndex " + rowIndex
                    + " must be less or equal to row count " + model.size());
        }

        for (int i = 0; i < rows.size(); i++) {
            addRow(rowIndex + i, rows.get(i));
        }
    }

    public void insertRowsAt(int rowIndex, String rows) {
        StringTokenizer rowTokenizer = new StringTokenizer(rows, "\n");
        ArrayList<T> list = new ArrayList<>();
        while (rowTokenizer.hasMoreTokens()) {
            try {
                T element = (T)rowType.newInstance().fromString(rowTokenizer.nextToken());
                list.add(element);
            } catch (IllegalAccessException | InstantiationException ex) {

            }
        }

        insertRowsAt(rowIndex, list);
    }
}
