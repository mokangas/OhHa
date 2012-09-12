/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterfaces.GUI;

import Control.Control;
import Control.ExceptionsThrownByRegister.AlmostSameCardExistsException;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author IstuvaHarka
 */
public class CardEditDialog extends JDialog {

    private String[] fieldNames;
    private Control control;
    private int components;
    private DefaultTableModel tableModel;
    private int row;
    private JTextField[] textFields;
    private JTextArea textArea;
    private String[] oldData;
    private JLabel message;

    public CardEditDialog(JFrame owner, String[] fieldNames, Control control, DefaultTableModel tableModel, int row) {
        super(owner);
        this.fieldNames = fieldNames;
        this.control = control;
        this.tableModel = tableModel;
        this.row = row;
        this.components = fieldNames.length;
        
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
        
        setTitle("Muokkaa korttia");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        createComponents();
        pack();
        setVisible(true);
    }

    private void createComponents() {
        
        
        JLabel[] texts = new JLabel[components];
        textFields = new JTextField[components - 1];
        oldData =new String[components];

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(layout);

        message = new JLabel(" ");
        c.gridx = 1;
        c.gridy = 0;
        add(message,c);
        
        for (int i = 0; i < components - 1; i++) {
            texts[i] = new JLabel(fieldNames[i]);
            c.gridx = 0;
            c.gridy = i+1;
            c.ipadx = 0;
            //c.weightx = 1;
            c.anchor = GridBagConstraints.FIRST_LINE_START;
            c.fill = GridBagConstraints.WEST;
            add(texts[i], c);
            oldData[i] = (String) tableModel.getValueAt(row, i);
            textFields[i] = new JTextField(oldData[i]);
            c.gridx = 1;
            c.gridy = i+1;
            c.ipadx = 120;
            //c.weightx = 6;
            c.anchor = GridBagConstraints.FIRST_LINE_START;
            c.fill = GridBagConstraints.HORIZONTAL;
            add(textFields[i], c);
        }


        texts[components - 1] = new JLabel(fieldNames[components - 1]);
        c.gridx = 0;
        c.gridy = components;
        c.ipadx = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        add(texts[components - 1], c);

        oldData[components-1] = (String) tableModel.getValueAt(row, components-1);
        textArea = new JTextArea(oldData[components-1], 10, 30);
        c.gridx = 1;
        c.gridy = components;
        c.ipadx = 2;
        add(textArea, c);
        
        JButton saveChanges = new JButton("Tallenna muutokset");
        saveChanges.setMnemonic('T');
        saveChanges.addActionListener(new EditSaveListener());
        c.gridx = 0;
        c.gridy = components+1;
        add(saveChanges, c);
        
        JButton cancel = new JButton("Peruuta");
        cancel.setMnemonic('P');
        cancel.addActionListener(new CancelListener());
        c.gridx = 1;
        c.gridy = components+1;
        add(cancel, c);
    }

    private class CancelListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
}
    
    private class EditSaveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String[] newData = new String[components];
                for (int i = 0; i < components - 1; i++) {
                    newData[i] = textFields[i].getText();
                }
                newData[components-1] = textArea.getText();
                
                control.editCard(oldData, newData);
                dispose();
            } catch (AlmostSameCardExistsException ex) {
                message.setText("Kortti, jolla on sama nimeke ja tekijä on jo olemassa!");
                message.setForeground(Color.red);
            }
        }
        
    }
    
   
    
}
