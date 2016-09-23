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
import com.metrum.table.renderer.ColumnResizeDecorator;
import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.swing.Action;
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
            final TableCellRenderer defaultRenderer = (new JTable()).getDefaultRenderer(table.
                    getColumnClass(col));
            TableColumn column = table.getColumnModel().getColumn(col);

            column.setCellRenderer(new AlternateRowDecorator(defaultRenderer, Color.LIGHT_GRAY,
                    1, 1));

            column.setCellRenderer(new ColumnAlignmentDecorator(column.getCellRenderer(),
                    JLabel.CENTER, JLabel.CENTER));

        }
        return table;

    }

    public static void createDefaultRenderer(JTable table) {
        for (int col = 0; col < table.getColumnCount(); col++) {
            final TableCellRenderer defaultRenderer = (new JTable()).getDefaultRenderer(table.
                    getColumnClass(col));
            TableColumn column = table.getColumnModel().getColumn(col);

            column.setCellRenderer(new AlternateRowDecorator(defaultRenderer, Color.LIGHT_GRAY,
                    1, 1));

            column.setCellRenderer(new ColumnAlignmentDecorator(column.getCellRenderer(),
                    JLabel.CENTER, JLabel.CENTER));
            
            column.setCellRenderer(new ColumnResizeDecorator(column.
                    getCellRenderer(), ColumnResizeDecorator.ColumnResizeMode.NONE, true, true, 5));

        }
    }

    public static JTable setDefaultAttributes(JTable table) {

        final JPopupMenu popup = new JPopupMenu();

        setAction(new CopyCutRemoveRowsAction(table), "Copiar",
                CopyCutRemoveRowsAction.ActionCommands.COPY.name(),
                KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK, false), table, popup);
        setAction(new CopyCutRemoveRowsAction(table), "Cortar",
                CopyCutRemoveRowsAction.ActionCommands.CUT.name(),
                KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_MASK, false), table, popup);
        setAction(new InsertRowsAction(table), "Colar",
                InsertRowsAction.ActionCommands.PASTE_ROWS_AT_POSITION.name(),
                KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK, false), table, popup);
        setAction(new InsertRowsAction(table), "Colar no Final",
                InsertRowsAction.ActionCommands.PASTE_ROWS_AT_END.name(),
                KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK,
                        false), table, popup);
        setAction(new CopyCutRemoveRowsAction(table), "Remover",
                CopyCutRemoveRowsAction.ActionCommands.REMOVE.name(),
                KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, KeyEvent.CTRL_MASK, false), table, popup);
        setAction(new InsertRowsAction(table), "Adicionar Linha ao Final",
                InsertRowsAction.ActionCommands.ADD_ROWS_AT_END.name(),
                KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK, false), table, popup);
        setAction(new InsertRowsAction(table), "Adicionar Linha na Posição",
                InsertRowsAction.ActionCommands.ADD_ROWS_AT_POSITION.name(),
                KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,
                        KeyEvent.CTRL_MASK, false), table, popup);
        setAction(new InsertRowsAction(table), "Duplicar Linhas",
                InsertRowsAction.ActionCommands.DUPLICATE_ROWS.name(),
                KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK,
                        false), table, popup);

        table.setComponentPopupMenu(popup);

        return table;
    }

    private static void setAction(final Action action, final String label, final String command,
            final KeyStroke keyStrokeCopy, JTable table, final JPopupMenu popup) {
        table.registerKeyboardAction(action, command, keyStrokeCopy, JComponent.WHEN_FOCUSED);
        final JMenuItem copyItem = new JMenuItem(action);
        copyItem.setActionCommand(command);
        copyItem.setAccelerator(keyStrokeCopy);

        copyItem.setText(label);
        popup.add(copyItem);
    }

    public static void alignColumn(int col, int alignment, JTable table) {
        TableColumn column = table.getColumnModel().getColumn(col);

        column.setCellRenderer(new ColumnAlignmentDecorator(column.getCellRenderer(), alignment,
                JLabel.CENTER));
        column.setCellRenderer(new ColumnPaddingDecorator(column.getCellRenderer(), 10, 10));

    }

}
