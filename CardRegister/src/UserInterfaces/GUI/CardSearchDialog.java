package UserInterfaces.GUI;

import Control.Control;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

/**
 *
 * The dialog for more detailed search from the register.
 *
 * @author mokangas
 */
public class CardSearchDialog extends JDialog {

    private String[] fieldNames;
    private JTextField[] textFields;
    private int fields;
    private Control control;

    /**
     * Constructor.
     *
     * @param owner The frame which launches this dialog.
     * @param fieldNames The names of the card's data fields.
     * @param control The <code>COntrol</code> object attached to this GUI.
     */
    public CardSearchDialog(JFrame owner, String[] fieldNames, Control control) {
        super(owner);
        this.fieldNames = fieldNames;
        this.fields = fieldNames.length;
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

        setTitle("Etsi kortistosta");
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
        textFields = new JTextField[fields];

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(layout);

        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        add(new JLabel("Hakukenttä"));

        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        add(new JLabel("Hakusana"));


        for (int i = 0; i < fields; i++) {
            texts[i] = new JLabel(fieldNames[i]);
            c.gridx = 0;
            c.gridy = i + 1;
            c.ipadx = 0;
            c.anchor = GridBagConstraints.FIRST_LINE_END;
            add(texts[i], c);
            textFields[i] = new JTextField(30);
            c.gridx = 1;
            c.gridy = i + 1;
            c.ipadx = 2;
            add(textFields[i], c);
        }

        JButton search = new JButton("Hae");
        search.setMnemonic('H');
        search.addActionListener(new SearchbuttonListener());
        c.gridx = 0;
        c.gridy = fields + 1;
        c.ipadx = 2;
        add(search, c);

        JButton cancel = new JButton("Peruuta");
        cancel.setMnemonic('P');
        cancel.addActionListener(new CancelButtonListener());
        c.gridx = 1;
        c.gridy = fields + 1;
        c.ipadx = 2;
        add(cancel, c);
    }

    /**
     * A listener for the search-button.
     */
    private class SearchbuttonListener implements ActionListener {

        /**
         * Passes the search words to the control and disposes this window.
         *
         * @param e The event that launches this process.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] searchWords = new String[fields];
            for (int i = 0; i < fields; i++) {
                searchWords[i] = textFields[i].getText();
            }
            control.search(searchWords);
            dispose();
        }
    }

    /**
     * A listener for the cancel button.
     */
    private class CancelButtonListener implements ActionListener {

        /**
         * Disposes this window.
         *
         * @param e The event that launches this process.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
}
