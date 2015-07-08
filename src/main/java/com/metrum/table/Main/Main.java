/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrum.table.Main;

import com.metrum.table.renderer.AlternateRowDecorator;
import com.metrum.table.renderer.ColumnAlignmentDecorator;
import com.metrum.table.action.InsertRowsAction;
import com.metrum.table.LeoTableModel;
import com.metrum.table.action.CopyCutRowsAction;
import com.metrum.table.editor.EditModeDecorator;
import com.metrum.table.renderer.ColumnResizeDecorator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author leandro.lima
 */
public class Main extends JPanel {

    public Main() {
        initComponents();
    }

    private void initComponents() {
        LeoTableModel<TestModel> model
                = new LeoTableModel<>(TestModel.class, TestModel.getColumns());
        model.addRow();

        JTable table = new JTable(model);
//        table.setRowHeight(30);
        table.setDefaultEditor(Double.class, new EditModeDecorator(table.getDefaultEditor(Double.class),
                EditModeDecorator.EditMode.SELECT_ON_FOCUS));

        for (int col = 0; col < table.getColumnCount(); col++) {
            final TableCellRenderer defaultRenderer
                    = table.getDefaultRenderer(table.getColumnClass(col));
            TableColumn column = table.getColumnModel().getColumn(col);

            column.setCellRenderer(
                    new AlternateRowDecorator(defaultRenderer, Color.yellow, 1, 1));
            
            column.setCellRenderer(
                    new ColumnAlignmentDecorator(column.getCellRenderer(),
                            JLabel.CENTER, JLabel.CENTER));    
            
            column.setCellRenderer(new ColumnResizeDecorator(column.getCellRenderer(),
                    ColumnResizeDecorator.ColumnResizeMode.NONE, 0));
        }
        
        table.registerKeyboardAction(new CopyCutRowsAction(table),
                CopyCutRowsAction.ActionCommands.COPY.name(),
                KeyStroke.getKeyStroke(KeyEvent.VK_C,
                        KeyEvent.CTRL_MASK, false), JComponent.WHEN_FOCUSED);
        
        table.registerKeyboardAction(new CopyCutRowsAction(table),
                CopyCutRowsAction.ActionCommands.CUT.name(),
                KeyStroke.getKeyStroke(KeyEvent.VK_X,
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
        
        
        JMenuItem copyItem = new JMenuItem(new CopyCutRowsAction(table));
        copyItem.setActionCommand(CopyCutRowsAction.ActionCommands.COPY.name());
        copyItem.setText("Copiar");
        popup.add(copyItem);
        
        
        JMenuItem cutItem = new JMenuItem(new CopyCutRowsAction(table));        
        cutItem.setActionCommand(CopyCutRowsAction.ActionCommands.CUT.name());
        cutItem.setText("Cortar");
        popup.add(cutItem);
        
        
        JMenuItem pasteItem = new JMenuItem(new InsertRowsAction(table));
        pasteItem.setActionCommand(InsertRowsAction.ActionCommands.PASTE_ROWS_AT_POSITION.name());
        pasteItem.setText("Colar");
        popup.add(pasteItem);
        
        JMenuItem addRowItem = new JMenuItem(new InsertRowsAction(table));
        addRowItem.setActionCommand(InsertRowsAction.ActionCommands.ADD_ROWS_AT_END.name());
        addRowItem.setText("Adicionar nova linha");
        popup.add(addRowItem);
        
        JMenuItem insertRowItem = new JMenuItem(new InsertRowsAction(table));
        insertRowItem.setActionCommand(InsertRowsAction.ActionCommands.ADD_ROWS_AT_POSITION.name());
        insertRowItem.setText("Inserir nova linha aqui");
        popup.add(insertRowItem);
        
        JMenuItem duplicateRows = new JMenuItem(new InsertRowsAction(table));
        duplicateRows.setActionCommand(InsertRowsAction.ActionCommands.DUPLICATE_ROWS.name());
        duplicateRows.setText("Duplicar linhas");
        popup.add(duplicateRows);
        
        table.setComponentPopupMenu(popup);
        
//        popup.add(cutAction);
//        popup.add(copyAction);
//        popup.add(pasteAction);
//        popup.add(deleteAction);
//        popup.addSeparator();
//        popup.add(undoAction);
//        popup.add(redoAction);

        JScrollPane scroller = new JScrollPane(table);
        table.setPreferredScrollableViewportSize(new java.awt.Dimension(500, 300));
        setLayout(new BorderLayout());
        add(scroller, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            JFrame frame = new JFrame("MEMT Table");
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent evt) {
                    System.exit(0);
                }
            });

            frame.getContentPane().add(new Main());
            frame.pack();
            frame.setVisible(true);
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException |
                HeadlessException e) {
        }
    }

}
