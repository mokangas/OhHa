/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterfaces.GUI;

import Control.Control;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import Control.ExceptionsThrownByRegister;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.ToolTipManager;

/**
 *
 * @author IstuvaHarka
 */
public class MainWindow extends JFrame {

    private Control control;
    private String[] fieldNames;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel message;

    public MainWindow(Control control, String[] fieldNames) {

        this.control = control;
        this.fieldNames = fieldNames;

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

    private void createComponents() {

        message = new JLabel(" ");

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
     *
     * @return corresponding JOptionPane integer
     */
    private int saveFirstDialog() {
        Object[] options = {"Kyllä", "Ei", "peruuta"};
        int saveFirst = JOptionPane.showOptionDialog(this, "Kortisto on muuttunut, tallenna ensin?", ""
                + "Tallenna tiedosto?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,
                null, options, options[0]);
        return saveFirst;
    }

    // Tämän metodin on heitettävä poikkeus, ettei kriittisissä tilanteissa
    // (Uusi tiedosto, lataa tiedosto jne) menetetä tietoja. Muut metodit
    // käsittelevät tämän heittämät poikkeukset.
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

    private void saveAs() {
        File current = control.getCurrentFile();
        JFileChooser chooser = new JFileChooser(current);
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            control.setCurrentFile(chooser.getSelectedFile());
            saveFile();
        }
    }

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

    public void setMessage(String newMessage, boolean isWarning) {
        message.setText(newMessage);
        if (isWarning) {
            message.setForeground(Color.red);
        } else {
            message.setForeground(Color.black);
        }
    }

    private void newCard() {
        new NewCardDialog(this, fieldNames, control);
    }

    private void search() {
        new CardSearchDialog(this, fieldNames, control);
    }

    private void showManual() {
        new Manual(this);
    }

    private void editCard() {
        new CardEditDialog(this, fieldNames, control, tableModel, table.getSelectedRow());
    }

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

    public void setCardTableData(Object[][] newData) {
        tableModel.setDataVector(newData, fieldNames);
    }

    private class LoadFileListener implements ActionListener {

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

    private class NewFileListener implements ActionListener {

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

    private class SaveFileListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            saveFile();
        }
    }

    private class SaveAsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            saveAs();
        }
    }

    private class QuitListener implements ActionListener {

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

    private class CloseListener extends WindowAdapter {

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

    private class NewCardListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            newCard();
        }
    }

    private class SearchListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            search();
        }
    }

    private class ViewallListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            control.viewAll();
        }
    }

    private class CardViewListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (table.getSelectedRowCount() == 1) {
                new CardView(fieldNames, control, tableModel, table.getSelectedRow());
            }
        }
    }

    private class CardEditListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (table.getSelectedRowCount() == 1) {
                editCard();
            }
        }
    }

    private class CardDeleteListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (table.getSelectedRowCount() > 0) {
                deleteCard();
            }
        }
    }

    private class HelpMenuitemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            showManual();
        }
    }

    private class TableEventListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                new CardView(fieldNames, control, tableModel, table.getSelectedRow());
            }
        }

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

    private class TableHoverListener extends MouseMotionAdapter {

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

    private class QuickSearchListener implements ActionListener {

        private JTextField searchField;

        public QuickSearchListener(JTextField searchField) {
            this.searchField = searchField;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            control.searchAnyField(searchField.getText());
        }
    }
}
