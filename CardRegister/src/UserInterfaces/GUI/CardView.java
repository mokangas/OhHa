package UserInterfaces.GUI;

import Control.Control;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

/**
 * A dialog to show the details of a card.
 *
 * @author mokangas
 */
public class CardView extends JDialog {

    private String[] fieldNames;
    private String[] fieldData;
    private Control control;
    private int fields;
    private JFrame owner;

    /**
     * Constructor.
     *
     * @param owner The frame that caused this window to pop up.
     * @param fieldNames The names of the card's data fields.
     * @param fieldData The content of card's data fields.
     * @param control The <code>Control</code> object attached to this GUI.
     */
    public CardView(JFrame owner, String[] fieldNames, String[] fieldData, Control control) {
        super(owner);
        this.owner = owner;
        this.fieldNames = fieldNames;
        this.fieldData = fieldData;
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

        setTitle(fieldData[control.getNameFieldsNumber()]);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        createComponents();
        pack();
        setVisible(true);
    }

    /**
     * Creates the components for this window.
     */
    private void createComponents() {
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(layout);

        JLabel[] labels = new JLabel[fields];
        JLabel[] contents = new JLabel[fields];


        for (int i = 0; i < fields; i++) {
            labels[i] = new JLabel(fieldNames[i] + ":");
            c.gridx = 0;
            c.gridy = i;
            c.ipadx = 0;
            c.weightx = 1;
            c.anchor = GridBagConstraints.FIRST_LINE_START;
            c.fill = GridBagConstraints.EAST;
            c.insets = new Insets(10, 10, 0, 20);
            add(labels[i], c);

            contents[i] = new JLabel("<html><body style='width: 200px'>"
                    + fieldData[i]);
            c.gridx = 1;
            c.gridy = i;
            c.ipadx = 0;
            c.weightx = 1;
            c.anchor = GridBagConstraints.FIRST_LINE_START;
            c.fill = GridBagConstraints.EAST;
            add(contents[i], c);
        }

        JButton closeButton = new JButton("Sulje");
        closeButton.setMnemonic('S');
        closeButton.addActionListener(new ClosebuttonListener());
        c.gridx = 0;
        c.gridy = fields;
        c.insets = new Insets(10, 10, 10, 20);
        add(closeButton, c);

        JButton editButton = new JButton("Muokkaa");
        editButton.setMnemonic('M');
        editButton.addActionListener(new EditbuttonListener());
        c.gridx = 1;
        c.gridy = fields;
        c.insets = new Insets(10, 10, 10, 20);
        add(editButton, c);

    }

    /**
     * Launches a card edit window. This window will be closed, and the owner of
     * this window will be passed on to be the owner of the edit-window.
     */
    private void editCard() {
        dispose();
        new CardEditDialog(owner, fieldNames, fieldData, control);
    }

    /**
     * A listener for the close-button.
     */
    private class ClosebuttonListener implements ActionListener {

        /**
         * Closes the window.
         *
         * @param e The event that launches this process.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    /**
     * A listener for the edit-button.
     */
    private class EditbuttonListener implements ActionListener {

        /**
         * Calls
         * <code>editCard()</code> method.
         *
         * @param e The event that launches this process.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            editCard();
        }
    }
}
