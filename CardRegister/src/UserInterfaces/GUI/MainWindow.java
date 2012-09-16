package UserInterfaces.GUI;

import Control.Control;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

/**
 * This is the main window of the GUI.
 *
 * @author mokangas
 */
public class MainWindow extends JFrame {

    private Control control;
    private String[] fieldNames;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel message;

    /**
     * The sole constructor.
     *
     * @param control the <code>Control</code>-object this window is attached
     * to, and through which the UI uses the register.
     * @param fieldNames Names of the content fields of a card.
     */
    public MainWindow(Control control, String[] fieldNames) {

        this.control = control;
        this.fieldNames = fieldNames;

        // Tries to set look and feel:
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel("Windows Classic");
            } catch (Exception e2) {
                try {
                    UIManager.setLookAndFeel("Windows");
                } catch (Exception e3) {
                    //Uses the default.
                }
            }
        }

        setTitle("Kortisto");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new CloseListener());
        createComponents();
        createMenu();
        pack();
        setVisible(true);
    }

    /**
     * Creates the components for the main window.
     */
    private void createComponents() {

        message = new JLabel("Tervetuloa. Paina CTRL + H lukeaksesi ohjeen.");

        JButton newCardButton = new JButton("Lisää");
        JButton viewCardButton = new JButton("Näytä");
        JButton editCardButton = new JButton("Muokkaa");
        JButton deleteButton = new JButton("Tuhoa kortit");
        JButton viewAllButton = new JButton("Näytä kaikki");
        JButton searchButton = new JButton("Tarkka haku");

        newCardButton.setMnemonic('L');
        viewCardButton.setMnemonic('N');
        editCardButton.setMnemonic('M');
        deleteButton.setMnemonic('u');
        viewAllButton.setMnemonic('k');
        searchButton.setMnemonic('a');

        newCardButton.addActionListener(new NewCardListener());
        viewCardButton.addActionListener(new CardViewListener());
        editCardButton.addActionListener(new CardEditListener());
        deleteButton.addActionListener(new CardDeleteListener());
        viewAllButton.addActionListener(new ViewallListener());
        searchButton.addActionListener(new SearchListener());

        JPanel searchPanel = createSearchPanel();

        this.tableModel = new DataTableModel(null, fieldNames);
        this.table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true);
        table.addMouseListener(new TableEventListener());
        table.addMouseMotionListener(new TableHoverListener());
        JScrollPane tableScrollPane = new JScrollPane(table);

        Container container = getContentPane();
        GroupLayout layout = new GroupLayout(container);
        container.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(message)
                .addGroup(layout.createSequentialGroup()
                .addComponent(newCardButton)
                .addComponent(viewCardButton)
                .addComponent(editCardButton)
                .addComponent(deleteButton)
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addComponent(viewAllButton)
                .addComponent(searchButton))
                .addComponent(searchPanel)
                .addComponent(tableScrollPane)));

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                .addComponent(message)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(newCardButton)
                .addComponent(viewCardButton)
                .addComponent(editCardButton)
                .addComponent(deleteButton)
                .addComponent(viewAllButton)
                .addComponent(searchButton))
                .addComponent(searchPanel)
                .addComponent(tableScrollPane));
    }

    /**
     * Creates the menu bar for the main window.
     */
    private void createMenu() {

        JMenuItem newFileItem = new JMenuItem("Uusi tiedosto");
        JMenuItem loadFileItem = new JMenuItem("Avaa tiedosto");
        JMenuItem saveFileItem = new JMenuItem("Tallenna");
        JMenuItem saveAsItem = new JMenuItem("Tallenna nimellä");
        JMenuItem quitItem = new JMenuItem("Lopeta");

        newFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
        loadFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        saveFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));

        loadFileItem.addActionListener(new LoadFileListener());
        newFileItem.addActionListener(new NewFileListener());
        saveFileItem.addActionListener(new SaveFileListener());
        saveAsItem.addActionListener(new SaveAsListener());
        quitItem.addActionListener(new QuitListener());

        JMenu file = new JMenu("Tiedosto");
        file.setMnemonic('T');
        file.add(newFileItem);
        file.add(loadFileItem);
        file.add(saveFileItem);
        file.add(saveAsItem);
        file.addSeparator();
        file.add(quitItem);

        JMenu helpBar = new JMenu("Ohje");
        helpBar.setMnemonic('O');
        JMenuItem helpItem = new JMenuItem("Ohje");
        helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK));
        helpItem.addActionListener(new HelpMenuitemListener());
        helpBar.add(helpItem);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(file);
        menuBar.add(helpBar);
        setJMenuBar(menuBar);
    }

    /**
     * Creates and returns the search panel for the main window.
     *
     * @return The search panel.
     */
    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel();
        GroupLayout layout = new GroupLayout(searchPanel);
        searchPanel.setLayout(layout);

        JTextField quickSearchField = new JTextField(10);
        JButton quickSearchButton = new JButton("Hae");
        quickSearchButton.setMnemonic('H');
        quickSearchButton.addActionListener(new QuickSearchListener(quickSearchField));

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                .addComponent(quickSearchField)
                .addComponent(quickSearchButton));

        layout.setVerticalGroup(
                layout.createParallelGroup()
                .addComponent(quickSearchField, GroupLayout.PREFERRED_SIZE,
                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(quickSearchButton));

        return searchPanel;
    }

    /**
     * Asks
     * <code>control</code> to delete cards.
     */
    private void deleteCard() {
        // Asks for confirmation first
        String message = "Haluatko todella tuhota valitut kortit?";
        Object[] options = {"Kyllä", "Ei"};
        int confirmation = JOptionPane.showOptionDialog(this, message, "Tuhoa kortit?",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);

        // If canceled, nothing will happen. If confirmed, this will happen:
        if (confirmation == JOptionPane.YES_OPTION) {
            String[][] deleted = new String[table.getSelectedRowCount()][fieldNames.length];
            for (int i = 0; i < table.getSelectedRowCount(); i++) {
                for (int j = 0; j < fieldNames.length; j++) {
                    deleted[i][j] = (String) tableModel.getValueAt(table.getSelectedRows()[i], j);
                }
            }
            control.deleteCards(deleted);
        }
    }

    /**
     * Opens up a dialog for editing card.
     */
    private void editCard() {
        String[] fieldData = new String[fieldNames.length];
        for (int i = 0; i < fieldNames.length; i++) {
            fieldData[i] = (String) tableModel.getValueAt(table.getSelectedRow(), i);
        }
        new CardEditDialog(this, fieldNames, fieldData, control);
    }

    /**
     * Asks user to choose file to be loaded and then
     * <code>control</code> to load it.
     */
    private void loadFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                control.loadFile(chooser.getSelectedFile());
            } catch (Exception ex) {
                message.setText("Lataus epäonnistui");
                message.setForeground(Color.red);
                repaint();
            }
        }
    }

    /**
     * Opens up a card adding window.
     */
    private void newCard() {
        new NewCardDialog(this, fieldNames, control);
    }

    /**
     * Asks user the name by which the file should be saved and then the
     * <code>control</code> to save it.
     */
    private void saveAs() {
        File current = control.getCurrentFile();
        JFileChooser chooser = new JFileChooser(current);
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            control.setCurrentFile(chooser.getSelectedFile());
            saveFile();
        }
    }

    /**
     * Asks
     * <code>control</code> to save the file.
     */
    private void saveFile() {
        if (control.getCurrentFile() == null) {
            saveAs();
        } else {
            try {
                control.save();
                message.setText("Tallennus onnistui");
                message.setForeground(Color.black);
            } catch (IOException ex) {
                message.setText("Tallennus epäonnistui");
                message.setForeground(Color.red);
            }
        }
    }

    /**
     * Asks user if he wants to save the file before losing all the changes made
     * to it. This is called when the file hasn't been saved after a change made
     * to it.
     *
     * @return Yes, Cancel or No as corresponding JOptionPane integer
     */
    private int saveFirstDialog() {
        Object[] options = {"Kyllä", "Ei", "peruuta"};
        int saveFirst = JOptionPane.showOptionDialog(this, "Kortisto on muuttunut, tallenna ensin?", ""
                + "Tallenna tiedosto?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,
                null, options, options[0]);
        return saveFirst;
    }

    /**
     * Opens up a card search dialog.
     */
    private void search() {
        new CardSearchDialog(this, fieldNames, control);
    }

    /**
     * Changed the data of cards displayed to the user. This can be used when
     * the register changes or when the user searches the register.
     *
     * @param newData The data of the cards to be displayed to the user.
     */
    public void setCardTableData(Object[][] newData) {
        tableModel.setDataVector(newData, fieldNames);
    }

    /**
     * Sets the message shown to the user. Used by system to tell the user that
     * operation has succeeded or failed etc. Warning texts are displayed on red
     * and ordinary on black text.
     *
     * @param newMessage The content of the message to be displayed.
     * @param isWarning True if the text is meant to be warning, false
     * otherwise.
     */
    public void setMessage(String newMessage, boolean isWarning) {
        message.setText(newMessage);
        if (isWarning) {
            message.setForeground(Color.red);
        } else {
            message.setForeground(Color.black);
        }
    }

    /**
     * Opens up the Help-window.
     */
    private void showManual() {
        new Manual(this);
    }

    /**
     * Opens up a card display window.
     */
    private void viewCard() {
        String[] fieldData = new String[fieldNames.length];
        for (int i = 0; i < fieldNames.length; i++) {
            fieldData[i] = (String) tableModel.getValueAt(table.getSelectedRow(), i);
        }
        new CardView(this, fieldNames, fieldData, control);
    }

    /**
     * A listener for loading a new file.
     */
    private class LoadFileListener implements ActionListener {

        /**
         * After action being performed, the user will be asked if he wants to
         * save the changes (when needed), and then what file he wishes to load.
         *
         * @param e The event launching this process.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!control.needsToBeSaved()) {
                loadFile();
                return;
            }

            int option = saveFirstDialog();
            if (option == JOptionPane.YES_OPTION) {
                try {
                    saveFile();
                    loadFile();
                    message.setText("Tallennettu ja ladattu!");
                    message.setForeground(Color.black);
                } catch (Exception ex) {
                    message.setText("Tallennus tai lataus epäonnistui. Uutta tiedostoa ei ole ladattu.");
                    message.setForeground(Color.red);
                }
            } else if (option == JOptionPane.NO_OPTION) {
                loadFile();
            }

        }
    }

    /**
     * A listener for opening a new file.
     */
    private class NewFileListener implements ActionListener {

        /**
         * After action being performed, the user will be asked if he wants to
         * save the changes (when needed). After that the
         * <code>reset</code> method of the control is called.
         *
         * @param e The event launching this process.
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            if (!control.needsToBeSaved()) {
                control.newFile();
                return;
            }

            int option = saveFirstDialog();
            if (option == JOptionPane.YES_OPTION) {
                saveFile();
                control.newFile();
            } else if (option == JOptionPane.NO_OPTION) {
                control.newFile();
            }
        }
    }

    /**
     * A listener for saving a new file.
     */
    private class SaveFileListener implements ActionListener {

        /**
         * Calls the method
         * <code>save()</code>.
         *
         * @param e The event launching this process.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            saveFile();
        }
    }

    /**
     * A listener for saving file by a name.
     */
    private class SaveAsListener implements ActionListener {

        /**
         * Calls the method
         * <code>SaveAs()</code>.
         *
         * @param e The event launching this process.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            saveAs();
        }
    }

    /**
     * A listener for quitting the program via the menu.
     */
    private class QuitListener implements ActionListener {

        /**
         * If the file needs to be saved this asks first whether the user wants
         * to do so. Otherwise closes immediately.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!control.needsToBeSaved()) {
                System.exit(0);
            }

            int option = saveFirstDialog();
            if (option == JOptionPane.YES_OPTION) {
                saveFile();
                System.exit(0);
            } else if (option == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
        }
    }

    /**
     * A listener for closing the window from the cross in the upper bar. This
     * needs to exist to prevent user accidentally losing data.
     */
    private class CloseListener extends WindowAdapter {

        /**
         * If the file needs to be saved this asks first whether the user wants
         * to do so. Otherwise closes immediately.
         *
         * @param winEvt The event launching this process.
         */
        @Override
        public void windowClosing(WindowEvent winEvt) {
            if (!control.needsToBeSaved()) {
                System.exit(0);
            }

            int option = saveFirstDialog();

            if (option == JOptionPane.YES_OPTION) {
                saveFile();
                System.exit(0);
            } else if (option == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
        }
    }

    /**
     * A listener for new card to be added.
     */
    private class NewCardListener implements ActionListener {

        /**
         * Calls the method
         * <code>newCard()</code>.
         *
         * @param e The event launching this process.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            newCard();
        }
    }

    /**
     * A listener for the detailed search.
     */
    private class SearchListener implements ActionListener {

        /**
         * calls the method
         * <code>search()</code>.
         *
         * @param e The event launching this process.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            search();
        }
    }

    /**
     * A listener for the view all button.
     */
    private class ViewallListener implements ActionListener {

        /**
         * calls the method
         * <code>viewAll()</code>.
         *
         * @param e The event launching this process.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            control.viewAll();
        }
    }

    /**
     * A listener for a card view window to pop up.
     */
    private class CardViewListener implements ActionListener {

        /**
         * If one and only one card in the table is selected, this calls the
         * <code>viewCard()</code> method.
         *
         * @param e The event launching this process.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (table.getSelectedRowCount() == 1) {
                viewCard();
            }
        }
    }

    /**
     * A listener for the card edit window to pop up.
     */
    private class CardEditListener implements ActionListener {

        /**
         * If one and only one card is selected, this calls
         * <code>editCard()</code> method.
         *
         * @param e The event launching this process.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (table.getSelectedRowCount() == 1) {
                editCard();
            }
        }
    }

    /**
     * A listener for deleting cards.
     */
    private class CardDeleteListener implements ActionListener {

        /**
         * Calls
         * <code>deleteCard()</code> method.
         *
         * @param e The event launching this process.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (table.getSelectedRowCount() > 0) {
                deleteCard();
            }
        }
    }

    /**
     * A listener for popping up the manual window.
     */
    private class HelpMenuitemListener implements ActionListener {

        /**
         * Calls
         * <code>showManual()</code> method.
         *
         * @param e The event launching this process.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            showManual();
        }
    }

    /**
     * A listener for the card table.
     */
    private class TableEventListener implements MouseListener {

        /**
         * When a card is double clicked, a card view window will pop up.
         *
         * @param e The event launching this process.
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                viewCard();
            }
        }

        /**
         * When a card is right clicked, a context menu will pop up.
         *
         * @param e The event launching this process.
         */
        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON3) {
                ContextMenu cMenu = new ContextMenu(e);
                cMenu.show(table, e.getX(), e.getY());
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    /**
     * A listener for the mouse being hovered over the card table.
     */
    private class TableHoverListener extends MouseMotionAdapter {

        /**
         * If the mouse is hovered over the table, this will show a tooltipbox
         * with the information of the card pointed at.
         *
         * @param e The event launching this process.
         */
        @Override
        public void mouseMoved(MouseEvent e) {

            int rowPointed = table.rowAtPoint(e.getPoint());
            String toolTip = "<html>";
            for (int i = 0; i < fieldNames.length; i++) {
                toolTip = toolTip + tableModel.getValueAt(rowPointed, i) + "<br/>";
            }

            ToolTipManager.sharedInstance().setInitialDelay(300);
            table.setToolTipText(toolTip);

        }
    }

    /**
     * The context menu shown to the user when he right clicks the card table.
     */
    private class ContextMenu extends JPopupMenu {

        public ContextMenu(MouseEvent e) {

            // If the clicked row isn't seleceted, it will be set as the single
            // selected row.

            int clickedRow = table.rowAtPoint(e.getPoint());
            ListSelectionModel selModel = table.getSelectionModel();
            if (!selModel.isSelectedIndex(clickedRow)) {
                selModel.clearSelection();
                selModel.addSelectionInterval(clickedRow, clickedRow);
            }

            // After that the context menu appears:

            JMenuItem show = add(new JMenuItem("Näytä"));
            JMenuItem edit = add(new JMenuItem("Muokkaa"));
            JMenuItem delete = add(new JMenuItem("Tuhoa"));
            show.addActionListener(new CardViewListener());
            edit.addActionListener(new CardEditListener());
            delete.addActionListener(new CardDeleteListener());
        }
    }

    /**
     * A listener for the quick search button.
     */
    private class QuickSearchListener implements ActionListener {

        private JTextField searchField;

        /**
         * The constructor needs to know where it is reading the search word
         * from when the button is clicked.
         *
         * @param searchField The field from which the search word is read from.
         */
        public QuickSearchListener(JTextField searchField) {
            this.searchField = searchField;
        }

        /**
         * Asks the control to do quick search.
         *
         * @param e The event launching this process.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            control.searchAnyField(searchField.getText());
        }
    }
}
