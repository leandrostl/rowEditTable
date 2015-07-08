/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrum.table.Main;

import com.metrum.table.LeoTableFactory;
import com.metrum.table.LeoTableModel;
import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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

        JTable table = LeoTableFactory.newDefaultInstance(model);

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
