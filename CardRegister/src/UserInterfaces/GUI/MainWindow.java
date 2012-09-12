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
import javax.swing.JLabel;

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
        JButton searchButton = new JButton("Etsi");

        newCardButton.addActionListener(new NewCardListener());
        viewCardButton.addActionListener(new ViewcardListener());
        editCardButton.addActionListener(new EditcardListener());
        deleteButton.addActionListener(new CardDeleteListener());
        viewAllButton.addActionListener(new ViewallListener());
        searchButton.addActionListener(new SearchListener());


        this.tableModel = new DataTableModel(null, fieldNames);
        this.table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true);
        table.addMouseListener(new TableClickListener());
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
                .addComponent(tableScrollPane));
    }

    private void createMenu() {

        JMenuItem newFileItem = new JMenuItem("Uusi tiedosto");
        JMenuItem loadFileItem = new JMenuItem("Lataa tiedosto");
        JMenuItem saveFileItem = new JMenuItem("Tallenna");
        JMenuItem saveAsItem = new JMenuItem("Tallenna nimellä");
        JMenuItem quitItem = new JMenuItem("Lopeta");

        loadFileItem.addActionListener(new LoadFileListener());
        newFileItem.addActionListener(new NewFileListener());
        saveFileItem.addActionListener(new SaveFileListener());
        saveAsItem.addActionListener(new SaveAsListener());
        quitItem.addActionListener(new QuitListener());

        JMenu file = new JMenu("Tiedosto");
        file.add(newFileItem);
        file.add(loadFileItem);
        file.add(saveFileItem);
        file.add(saveAsItem);
        file.addSeparator();
        file.add(quitItem);

        JMenuItem helpItem = new JMenuItem("Ohje");
        helpItem.addActionListener(new HelpMenuitemListener());
        JMenu helpBar = new JMenu("Ohje");
        helpBar.add(helpItem);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(file);
        menuBar.add(helpBar);
        setJMenuBar(menuBar);
    }

    private boolean saveFirstDialog() {
        Object[] options = {"Kyllä", "Ei"};
        int saveFirst = JOptionPane.showOptionDialog(this, "Kortisto on muuttunut, tallenna ensin?", ""
                + "Tallenna tiedosto?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
                null, options, options[0]);
        if (saveFirst == JOptionPane.YES_OPTION) {
            return true;
        }
        return false;
    }

    // Tämän metodin on heitettävä poikkeus, ettei kriittisissä tilanteissa
    // (Uusi tiedosto, lataa tiedosto jne) menetetä tietoja. Muut metodit
    // käsittelevät tämän heittämät poikkeukset.
    private void saveFile() throws IOException {
        if (control.getCurrentFile() == null) {
            saveAs();
        } else {
            control.save();
            message.setText("Tallennus onnistui");
            message.setForeground(Color.black);
        }
    }

    private void saveAs() {
        File current = control.getCurrentFile();
        JFileChooser chooser = new JFileChooser(current);
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            control.setCurrentFile(chooser.getSelectedFile());
            try {
                saveFile();
                message.setText("Tallennus onnistui");
                message.setForeground(Color.black);
            } catch (IOException ex) {
                message.setText("Tallennus epäonnistui");
                message.setForeground(Color.red);
            }
        }
    }

    private void loadFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                control.loadFile(chooser.getSelectedFile());
            } catch (FileNotFoundException ex) {
                message.setText("Lataus epäonnistui");
                message.setForeground(Color.red);
            }
        }
    }
    
    public void setMessage(String newMessage, boolean isWarning){
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
            if (control.needsToBeSaved() && saveFirstDialog()) {
                try {
                    saveFile();
                    loadFile();
                    message.setText("Tallennettu ja ladattu!");
                    message.setForeground(Color.black);
                } catch (IOException ex) {
                    message.setText("Tallennus epäonnistui. Uutta tiedostoa ei ole ladattu.");
                    message.setForeground(Color.red);
                }
            }
            loadFile();
        }
    }

    private class NewFileListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (control.needsToBeSaved() && saveFirstDialog()) {
                try {
                    saveFile();
                    control.newFile();
                    message.setText("Tallennus onnistui.");
                    message.setForeground(Color.black);
                } catch (IOException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private class SaveFileListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                saveFile();
            } catch (IOException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            if (control.needsToBeSaved() && saveFirstDialog()) {
                try {
                    saveFile();
                } catch (IOException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.exit(0);
        }
    }

    private class CloseListener extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent winEvt) {
            if (control.needsToBeSaved() && saveFirstDialog()) {
                try {
                    saveFile();
                } catch (IOException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.exit(0);
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

    private class ViewcardListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (table.getSelectedRowCount() == 1) {
                new CardView(fieldNames, control, tableModel, table.getSelectedRow());
            }
        }
    }

    private class EditcardListener implements ActionListener {

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

    private class TableClickListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                new CardView(fieldNames, control, tableModel, table.getSelectedRow());
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
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
}