/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterfaces.GUI;

import Control.Control;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

/**
 *
 * @author IstuvaHarka
 */
public class NewCardDialog extends JDialog {

    private String[] fieldNames;
    private JTextField[] textFields;
    private JTextArea textArea;
    private int components;
    private Control control;

    public NewCardDialog(JFrame owner, String[] fieldNames, Control control) {
        
        super(owner);
        this.fieldNames = fieldNames;
        this.components = fieldNames.length;
        this.control = control;



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

        setTitle("Uusi kortti");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        createComponents();
        pack();
        setVisible(true);
    }

    private void createComponents() {
        JLabel[] texts = new JLabel[components];
        textFields = new JTextField[components - 1];

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(layout);

        for (int i = 0; i < components - 1; i++) {
            texts[i] = new JLabel(fieldNames[i]);
            c.gridx = 0;
            c.gridy = i;
            c.ipadx = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
            add(texts[i], c);
            textFields[i] = new JTextField(30);
            c.gridx = 1;
            c.gridy = i;
            c.ipadx = 2;
            add(textFields[i], c);
        }


        texts[components - 1] = new JLabel(fieldNames[components - 1]);
        c.gridx = 0;
        c.gridy = components - 1;
        c.ipadx = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        add(texts[components - 1], c);

        textArea = new JTextArea(10, 30);
        c.gridx = 1;
        c.gridy = components - 1;
        c.ipadx = 2;
        add(textArea, c);
        
        JButton addCard = new JButton("Lisää");
        addCard.addActionListener(new NewCardListener());
        
        c.gridx = 1;
        c.gridy = components;
        c.ipadx = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        add(addCard, c);
        
        JButton cancel  = new JButton("Peruuta");
        cancel.addActionListener(new CancelListener());
        c.gridx = 2;
        c.gridy = components;
        c.ipadx = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        add(cancel, c);
    }
    
    private class NewCardListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String[] content = new String[components];
            for (int i = 0; i < components - 1; i++) {
                content[i] = textFields[i].getText();
            }
            content[components - 1] = textArea.getText();
            control.addNewCard(content);
            dispose();
        }
}
    
    private class CancelListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
        
    }
}
