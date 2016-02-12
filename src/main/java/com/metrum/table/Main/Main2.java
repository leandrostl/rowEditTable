/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrum.table.Main;

import com.metrum.table.RowTableModel;
import com.metrum.table.editor.EditModeDecorator;
import com.metrum.table.renderer.AlternateRowDecorator;
import com.metrum.table.renderer.ColumnAlignmentDecorator;
import com.metrum.table.renderer.ColumnPaddingDecorator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author leandro.lima
 */
public class Main2 extends JPanel {

    private final LinkedList<ExecutionRow> rawModel = new LinkedList<>();
    private final Collection<ExecutionRowModelAdapter> adapterModel = new LinkedList<>();

    public Main2() {

        for (int i = 0; i < 10; i++)
            rawModel.add(new ExecutionRow(String.valueOf(i + 1), " - "));

        for (ExecutionRow executionRow : rawModel)
            adapterModel.add(new ExecutionRowModelAdapter(executionRow));

        initComponents();
    }

    private void initComponents() {
        RowTableModel<ExecutionRowModelAdapter> model = new RowTableModel<>(ExecutionRowModelAdapter.class, ExecutionRowModelAdapter.getColumns());
        model.setModel(adapterModel);

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

        {
            final TableCellRenderer defaultRenderer
                    = table.getDefaultRenderer(table.getColumnClass(0));
            TableColumn column = table.getColumnModel().getColumn(0);

            column.setCellRenderer(new AlternateRowDecorator(defaultRenderer, Color.LIGHT_GRAY,
                            1, 1));

            column.setCellRenderer(new ColumnAlignmentDecorator(column.getCellRenderer(),
                            JLabel.RIGHT, JLabel.CENTER));

        }
        
        for (int col = 1; col < table.getColumnCount(); col++) {
            final TableCellRenderer defaultRenderer
                    = table.getDefaultRenderer(table.getColumnClass(col));
            TableColumn column = table.getColumnModel().getColumn(col);

            column.setCellRenderer(
                    new AlternateRowDecorator(defaultRenderer, Color.LIGHT_GRAY,
                            1, 1));

            column.setCellRenderer(
                    new ColumnAlignmentDecorator(column.getCellRenderer(),
                            JLabel.LEFT, JLabel.CENTER));
            
            column.setCellRenderer(
                    new ColumnPaddingDecorator(column.getCellRenderer(), 10, 50, 0, 5));
        }
        
        table.setRowHeight(20);
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        table.setAutoCreateRowSorter(true);

        JScrollPane scroller = new JScrollPane(table);
        table.setPreferredScrollableViewportSize(new java.awt.Dimension(500, 300));
        setLayout(new BorderLayout());
        add(scroller, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            JFrame frame = new JFrame("Table Execution Row");
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent evt) {
                    System.exit(0);
                }
            });

            frame.getContentPane().add(new Main2());
            frame.pack();
            frame.setVisible(true);
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException |
                HeadlessException e) {
        }
    }
}
