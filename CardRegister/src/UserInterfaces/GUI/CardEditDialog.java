package UserInterfaces.GUI;

import Control.Control;
import cardregister.CardRegisterExceptions.AlmostSameCardExistsException;
import cardregister.CardRegisterExceptions.CardAlreadyExistsException;
import cardregister.CardRegisterExceptions.NullInputException;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
 * Dialog for editing an existing card.
 *
 * @author mokangas
 */
public class CardEditDialog extends JDialog {

    private String[] fieldNames;
    private String[] fieldData;
    private Control control;
    private int fields;
    private JTextField[] textFields;
    private JTextArea textArea;
    private JLabel message;

    /**
     * Contsructor.
     *
     * @param owner The frame that launches this dialog.
     * @param fieldNames The names of the card's data fields.
     * @param fieldData The content of the card to edited.
     * @param control The <code>Control</code> object attached to this GUI.
     */
    public CardEditDialog(JFrame owner, String[] fieldNames, String[] fieldData, Control control) {
        super(owner);
        this.fieldNames = fieldNames;
        this.fieldData = fieldData;
        this.control = control;
        this.fields = fieldNames.length;

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

    /**
     * Creates the components for this window.
     */
    private void createComponents() {
        JLabel[] texts = new JLabel[fields];
        textFields = new JTextField[fields - 1];

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(layout);

        message = new JLabel(" ");
        c.gridx = 1;
        c.gridy = 0;
        add(message, c);

        for (int i = 0; i < fields - 1; i++) {
            texts[i] = new JLabel(fieldNames[i]);
            c.gridx = 0;
            c.gridy = i + 1;
            c.ipadx = 0;
            //c.weightx = 1;
            c.anchor = GridBagConstraints.FIRST_LINE_START;
            c.fill = GridBagConstraints.WEST;
            add(texts[i], c);
            textFields[i] = new JTextField(fieldData[i]);
            c.gridx = 1;
            c.gridy = i + 1;
            c.ipadx = 120;
            //c.weightx = 6;
            c.anchor = GridBagConstraints.FIRST_LINE_START;
            c.fill = GridBagConstraints.HORIZONTAL;
            add(textFields[i], c);
        }


        texts[fields - 1] = new JLabel(fieldNames[fields - 1]);
        c.gridx = 0;
        c.gridy = fields;
        c.ipadx = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        add(texts[fields - 1], c);

        textArea = new JTextArea(fieldData[fields - 1], 10, 30);
        c.gridx = 1;
        c.gridy = fields;
        c.ipadx = 2;
        add(textArea, c);

        JButton saveChanges = new JButton("Tallenna muutokset");
        saveChanges.setMnemonic('T');
        saveChanges.addActionListener(new EditSaveListener());
        c.gridx = 0;
        c.gridy = fields + 1;
        add(saveChanges, c);

        JButton cancel = new JButton("Peruuta");
        cancel.setMnemonic('P');
        cancel.addActionListener(new CancelListener());
        c.gridx = 1;
        c.gridy = fields + 1;
        add(cancel, c);
    }

    /**
     * A listener for the cancel button.
     */
    private class CancelListener implements ActionListener {

        /**
         * Closes the dialog.
         *
         * @param e The event that launches this process.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    /**
     * A listener for the save-button.
     */
    private class EditSaveListener implements ActionListener {

        /**
         * Tries to save the edit, and prints the messages of a failure if
         * doesn't succeed.
         *
         * @param e The event that launches this process.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String[] newData = new String[fields];
                for (int i = 0; i < fields - 1; i++) {
                    newData[i] = textFields[i].getText();
                }
                newData[fields - 1] = textArea.getText();

                control.editCard(fieldData, newData);
                dispose();
            } catch (NullInputException ex) {
                message.setText("Korttia, jonka kaikki kentät ovat tyhiä ei voi olla olemassa.");
                message.setForeground(Color.red);
            } catch (AlmostSameCardExistsException ex) {
                message.setText("Kortti, jolla on sama nimeke ja tekijä on jo olemassa!");
                message.setForeground(Color.red);
            } catch (CardAlreadyExistsException ex) {
                //No need to do anything, since the rules are more strict here:
                //Already adding a card with the same title and wuthor is forbidden.
            } catch (NullPointerException ex){
                message.setText("Muokattavaa korttia ei enää ole olemassa.");
                message.setForeground(Color.red);
            }
        }
    }
}
