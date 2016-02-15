/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrum.table;

import com.metrum.table.action.CopyCutRemoveRowsAction;
import com.metrum.table.action.InsertRowsAction;
import com.metrum.table.renderer.AlternateRowDecorator;
import com.metrum.table.renderer.ColumnAlignmentDecorator;
import com.metrum.table.renderer.ColumnPaddingDecorator;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author leandro.lima
 */
public final class RowTableFactory {

    private RowTableFactory() {
    }

    public static JTable newDefaultInstance(RowTableModel model) {
        JTable table = new JTable(model) {
            @Override
            public boolean getScrollableTracksViewportHeight() {
                return getPreferredSize().height < getParent().getHeight();
            }

            @Override
            public boolean getScrollableTracksViewportWidth() {
                return getPreferredSize().width < getParent().getWidth();
            }
        };

        for (int col = 0; col < table.getColumnCount(); col++) {
            final TableCellRenderer defaultRenderer = 
                    table.getDefaultRenderer(table.getColumnClass(col));
            TableColumn column = table.getColumnModel().getColumn(col);

            column.setCellRenderer(
                    new AlternateRowDecorator(defaultRenderer, Color.LIGHT_GRAY,
                            1, 1));

            column.setCellRenderer(
                    new ColumnAlignmentDecorator(column.getCellRenderer(),
                            JLabel.CENTER, JLabel.CENTER));

//            column.setCellRenderer(
//                    new ColumnResizeDecorator(column.getCellRenderer(),
//                            ColumnResizeDecorator.ColumnResizeMode.NONE, 35));
        }

        table.registerKeyboardAction(new CopyCutRemoveRowsAction(table),
                CopyCutRemoveRowsAction.ActionCommands.COPY.name(),
                KeyStroke.getKeyStroke(KeyEvent.VK_C,
                        KeyEvent.CTRL_MASK, false), JComponent.WHEN_FOCUSED);

        table.registerKeyboardAction(new CopyCutRemoveRowsAction(table),
                CopyCutRemoveRowsAction.ActionCommands.CUT.name(),
                KeyStroke.getKeyStroke(KeyEvent.VK_X,
                        KeyEvent.CTRL_MASK, false), JComponent.WHEN_FOCUSED);

        table.registerKeyboardAction(new CopyCutRemoveRowsAction(table),
                CopyCutRemoveRowsAction.ActionCommands.REMOVE.name(),
                KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,
                        KeyEvent.CTRL_MASK, false), JComponent.WHEN_FOCUSED);

        table.registerKeyboardAction(new InsertRowsAction(table),
                InsertRowsAction.ActionCommands.ADD_ROWS_AT_END.name(),
                KeyStroke.getKeyStroke(KeyEvent.VK_N,
                        KeyEvent.CTRL_MASK, false), JComponent.WHEN_FOCUSED);

        table.registerKeyboardAction(new InsertRowsAction(table),
                InsertRowsAction.ActionCommands.ADD_ROWS_AT_POSITION.name(),
                KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,
                        KeyEvent.CTRL_MASK, false), JComponent.WHEN_FOCUSED);

        table.registerKeyboardAction(new InsertRowsAction(table),
                InsertRowsAction.ActionCommands.DUPLICATE_ROWS.name(),
                KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,
                        KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK, false),
                JComponent.WHEN_FOCUSED);

        table.registerKeyboardAction(new InsertRowsAction(table),
                InsertRowsAction.ActionCommands.PASTE_ROWS_AT_END.name(),
                KeyStroke.getKeyStroke(KeyEvent.VK_V,
                        KeyEvent.CTRL_MASK, false), JComponent.WHEN_FOCUSED);

        JPopupMenu popup = new JPopupMenu();

        JMenuItem copyItem = new JMenuItem(new CopyCutRemoveRowsAction(table));
        copyItem.setActionCommand(CopyCutRemoveRowsAction.ActionCommands.COPY.
                name());
        copyItem.setText("Copiar");
        popup.add(copyItem);

        JMenuItem cutItem = new JMenuItem(new CopyCutRemoveRowsAction(table));
        cutItem.setActionCommand(CopyCutRemoveRowsAction.ActionCommands.CUT.
                name());
        cutItem.setText("Cortar");
        popup.add(cutItem);

        JMenuItem removeItem = new JMenuItem(new CopyCutRemoveRowsAction(table));
        removeItem.setActionCommand(
                CopyCutRemoveRowsAction.ActionCommands.REMOVE.name());
        removeItem.setText("Remover linhas");
        popup.add(removeItem);

        JMenuItem pasteItem = new JMenuItem(new InsertRowsAction(table));
        pasteItem.setActionCommand(
                InsertRowsAction.ActionCommands.PASTE_ROWS_AT_POSITION.name());
        pasteItem.setText("Colar");
        popup.add(pasteItem);

        JMenuItem addRowItem = new JMenuItem(new InsertRowsAction(table));
        addRowItem.setActionCommand(
                InsertRowsAction.ActionCommands.ADD_ROWS_AT_END.name());
        addRowItem.setText("Adicionar nova linha");
        popup.add(addRowItem);

        JMenuItem insertRowItem = new JMenuItem(new InsertRowsAction(table));
        insertRowItem.setActionCommand(
                InsertRowsAction.ActionCommands.ADD_ROWS_AT_POSITION.name());
        insertRowItem.setText("Inserir nova linha aqui");
        popup.add(insertRowItem);

        JMenuItem duplicateRows = new JMenuItem(new InsertRowsAction(table));
        duplicateRows.setActionCommand(
                InsertRowsAction.ActionCommands.DUPLICATE_ROWS.name());
        duplicateRows.setText("Duplicar linhas");
        popup.add(duplicateRows);

        table.setComponentPopupMenu(popup);
        
        return table;
    }
    
    public static void alignColumn(int col, int alignment, JTable table){
        TableColumn column = table.getColumnModel().getColumn(col);
       
        column.setCellRenderer(
                    new ColumnAlignmentDecorator(column.getCellRenderer(),
                            alignment, JLabel.CENTER));
        column.setCellRenderer(new ColumnPaddingDecorator(column.getCellRenderer(), 10, 10));
        

    }

}
