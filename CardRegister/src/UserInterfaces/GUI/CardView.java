/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterfaces.GUI;

import Control.Control;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author IstuvaHarka
 */
public class CardView extends JFrame {

    private String[] fieldNames;
    private Control control;
    private int components;
    private DefaultTableModel tableModel;
    private int row;

    public CardView(String[] fieldNames, Control control, DefaultTableModel tableModel, int row) {
        this.fieldNames = fieldNames;
        this.components = fieldNames.length;
        this.control = control;
        this.tableModel = tableModel;
        this.row = row;

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel("Windows Classic");
            } catch (Exception e2) {
                try {
                    UIManager.setLookAndFeel("Windows");
                } catch (Exception e3) {
                    // Use the default.
                }
            }
        }

        setTitle("");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        createComponents();
        pack();
        setVisible(true);
    }

    private void createComponents() {
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(layout);

        JLabel[] labels = new JLabel[components];
        JLabel[] contents = new JLabel[components];
        for (int i = 0; i < components; i++) {
            labels[i] = new JLabel(fieldNames[i]);
            c.gridx = 0;
            c.gridy = i;
            add(labels[i], c);

            contents[i] = new JLabel((String) tableModel.getValueAt(row, i));
            c.gridx = 1;
            c.gridy = i;
            add(contents[i], c);
        }

        JButton closeButton = new JButton("Sulje");
        closeButton.addActionListener(new ClosebuttonListener());
        c.gridx = 0;
        c.gridy = components;
        add(closeButton, c);

        JButton editButton = new JButton("Muokkaa");
        editButton.addActionListener(new EditbuttonListener());
        c.gridx = 1;
        c.gridy = components;
        add(editButton, c);

    }

    private void editCard() {
        dispose();
        new CardEditDialog(this, fieldNames, control, tableModel, row);
    }

    private class ClosebuttonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    private class EditbuttonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            editCard();
        }
    }
}
