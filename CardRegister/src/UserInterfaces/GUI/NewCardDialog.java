package UserInterfaces.GUI;

import Control.Control;
import cardregister.CardRegisterExceptions.AlmostSameCardExistsException;
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
 *
 * A dialog for adding new cards to the register.
 *
 * @author mokangad
 */
public class NewCardDialog extends JDialog {

    private String[] fieldNames;
    private JTextField[] textFields;
    private JTextArea textArea;
    private int components;
    private Control control;
    private JLabel message;

    /**
     * The constructor.
     *
     * @param owner The frame which causes this dialog to pop up.
     * @param fieldNames The names of the card's content fields.
     * @param control The <code>Control</code>-object linking GUI
     * to <code>CardRegister</code>.
     */
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

    /**
     * Creates the components to this dialog.
     */
    private void createComponents() {
        JLabel[] texts = new JLabel[components];
        textFields = new JTextField[components - 1];

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(layout);

        message = new JLabel(" ");
        c.gridx = 1;
        c.gridy = 0;
        add(message, c);

        for (int i = 0; i < components - 1; i++) {
            texts[i] = new JLabel(fieldNames[i]);
            c.gridx = 0;
            c.gridy = i + 1;
            c.ipadx = 0;
            c.weightx = 1;
            c.anchor = GridBagConstraints.FIRST_LINE_END;
            c.fill = GridBagConstraints.EAST;
            add(texts[i], c);
            textFields[i] = new JTextField(30);
            c.gridx = 1;
            c.gridy = i + 1;
            c.ipadx = 120;
            c.weightx = 6;
            c.anchor = GridBagConstraints.FIRST_LINE_START;
            c.fill = GridBagConstraints.HORIZONTAL;
            add(textFields[i], c);
        }


        texts[components - 1] = new JLabel(fieldNames[components - 1]);
        c.gridx = 0;
        c.gridy = components;
        c.ipadx = 0;
        c.weightx = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        c.fill = GridBagConstraints.NONE;
        add(texts[components - 1], c);

        textArea = new JTextArea(10, 30);
        c.gridx = 1;
        c.gridy = components;
        c.ipadx = 2;
        c.weightx = 6;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(textArea, c);

        JButton addCard = new JButton("Lisää");
        addCard.setMnemonic('L');
        addCard.addActionListener(new NewCardListener());

        c.gridx = 0;
        c.gridy = components + 1;
        c.ipadx = 0;
        c.weightx = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        add(addCard, c);

        JButton cancel = new JButton("Peruuta");
        cancel.setMnemonic('P');
        cancel.addActionListener(new CancelListener());
        c.gridx = 1;
        c.gridy = components + 1;
        c.ipadx = 1;
        c.weightx = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        add(cancel, c);
    }

    /**
     * Listener for the cancel button.
     */
    private class CancelListener implements ActionListener {

        /**
         * Disposes the window when "cancel" is clicked.
         *
         * @param e The event launching this process.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    /**
     * Listener for the "New Card"-button.
     */
    private class NewCardListener implements ActionListener {

        /**
         * Sends the content typed by the user to control to be added to the
         * register.
         *
         * @param e The event launching this process.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] content = new String[components];
            for (int i = 0; i < components - 1; i++) {
                content[i] = textFields[i].getText();
            }
            content[components - 1] = textArea.getText();
            try {
                control.addNewCard(content);
                dispose();
            } catch (NullInputException ex) {
                message.setText("Jonkin kentän on sisällettävä tekstiä");
                message.setForeground(Color.red);
            } catch (AlmostSameCardExistsException ex) {
                message.setText("Kortti, jolla on sama nimeke ja tekijä on jo olemassa!");
                message.setForeground(Color.red);
            } catch (Exception ex) {
            }
        }
    }
}
