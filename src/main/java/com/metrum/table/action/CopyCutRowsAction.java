/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrum.table.action;

import com.metrum.table.LeoTableModel;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JTable;

public class CopyCutRowsAction extends AbstractAction {

    public enum ActionCommands {

        COPY, CUT
    }

    private final JTable table;

    public CopyCutRowsAction(JTable table) {
        this.table = table;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        int firstSelectedRow = table.getSelectedRow();
        if (firstSelectedRow > -1 && isContinuous()) {
            final String selectionString = getSelectionString();

            if (e.getActionCommand().equals(ActionCommands.CUT.name())) {
                LeoTableModel model = (LeoTableModel) table.getModel();
                final int selectedRows[] = table.getSelectedRows();
                model.removeRows(selectedRows[0], selectedRows[selectedRows.length - 1]);
            }

            StringSelection strSelection = new StringSelection(selectionString);
            
            final Clipboard clipboard = Toolkit.getDefaultToolkit()
                    .getSystemClipboard();
            clipboard.setContents(new StringSelection(""), null);
            clipboard.setContents(strSelection, null);
        }

    }

    private String getSelectionString() {
        StringBuilder stringBuilder = new StringBuilder();

        int[] selectedRows = table.getSelectedRows();

        final int colCount = table.getColumnCount();
        final int rowCount = table.getSelectedRowCount();

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount - 1; j++) {
                stringBuilder
                        .append(table.getValueAt(selectedRows[i], j))
                        .append("\t");
            }

            stringBuilder
                    .append(table.getValueAt(selectedRows[i], colCount - 1))
                    .append("\n");
        }
        return stringBuilder.toString();
    }

    private boolean isContinuous() {
        int colsCount = table.getSelectedColumnCount();
        int rowsCount = table.getSelectedRowCount();

        int[] rows = table.getSelectedRows();
        int[] cols = table.getSelectedColumns();

        return (rowsCount - 1 == rows[rows.length - 1] - rows[0]
                && rowsCount == rows.length)
                && (colsCount - 1 == cols[cols.length - 1] - cols[0]
                && colsCount == cols.length);
    }

}
