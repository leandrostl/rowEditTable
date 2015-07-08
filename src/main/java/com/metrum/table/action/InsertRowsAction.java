/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrum.table.action;

import com.metrum.table.LeoTableModel;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

/**
 *
 * @author leandro.lima
 */
public class InsertRowsAction extends AbstractAction {

    public enum ActionCommands {

        ADD_ROWS_AT_END,
        ADD_ROWS_AT_POSITION,
        DUPLICATE_ROWS,
        PASTE_ROWS_AT_POSITION,
        PASTE_ROWS_AT_END,
        NONE
    }

    private final JTable table;
    private Point lastMouseLocation;

    public InsertRowsAction(JTable table) {
        this.table = table;
        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if(e.getButton() == MouseEvent.BUTTON3) {
                    System.err.println("botão: " + e.getButton());
                    lastMouseLocation = e.getPoint();
                }
            }

        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final String commandString = e.getActionCommand();

        ActionCommands command;

        try {
            command = ActionCommands.valueOf(commandString);
        } catch (IllegalArgumentException ex) {
            return;
        }

        switch (command) {
            case ADD_ROWS_AT_END: {
                LeoTableModel model = (LeoTableModel) table.getModel();
                int insertIndex = table.getRowCount();
                model.insertRowsAt(insertIndex, 1);

                selectRows(insertIndex, insertIndex);
                break;
            }
            case ADD_ROWS_AT_POSITION: {
                LeoTableModel model = (LeoTableModel) table.getModel();
                final int insertIndex = table.getSelectedRow() + 1;
                model.insertRowsAt(insertIndex, 1);

                selectRows(insertIndex, insertIndex);
                break;
            }
            case DUPLICATE_ROWS: {

                LeoTableModel model = (LeoTableModel) table.getModel();
                final int selectedRows[] = table.getSelectedRows();

                final int insertIndex = selectedRows[selectedRows.length - 1] + 1;
                List rows = model.getRows(selectedRows[0], insertIndex);

                model.insertRowsAt(insertIndex, rows);

                selectRows(insertIndex, rows.size() - 1);
                break;
            }

            case PASTE_ROWS_AT_END:
            case PASTE_ROWS_AT_POSITION: {
                LeoTableModel model = (LeoTableModel) table.getModel();
                System.err.println("Fonte: " + e.getSource());
                try {
                    String strCopy = (String) Toolkit.getDefaultToolkit()
                            .getSystemClipboard().getContents(this)
                            .getTransferData(DataFlavor.stringFlavor);
                    final int rowCount = table.getRowCount();
                    
                    if (command == ActionCommands.PASTE_ROWS_AT_END) {                        
                        insertAtIndex(model, rowCount, strCopy);
                    } else {
                        Object source = e.getSource();
                        if (source instanceof JMenuItem) {                           
                            int nearRow;
                            if ((nearRow = table.rowAtPoint(lastMouseLocation)) > -1) {
                                insertAtIndex(model, nearRow, strCopy);
                            } else {                                
                                insertAtIndex(model, rowCount, strCopy);
                            }
                        }
                    }

                } catch (UnsupportedFlavorException | IOException ex) {

                }
                break;
            }
        }
    }

    private void insertAtIndex(LeoTableModel model, final int rowIndex, String strCopy) {
        model.insertRowsAt(rowIndex, strCopy);
        selectRows(rowIndex, rowIndex + strCopy.split("\n").length - 1);
    }

    private void selectRows(int firstRowIndex, int lastRowIndex) {
        table.setRowSelectionInterval(firstRowIndex, lastRowIndex);
        table.setColumnSelectionInterval(0, table.getColumnCount() - 1);
    }

}
