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

/**
 *
 * @author IstuvaHarka
 */
public class MainWindow extends JFrame {

    private Control control;
    private String[] fieldNames;
    private DefaultTableModel tableModel;

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
                    // Use the default.
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

        JButton addButton = new JButton("Lisää kortti");
        JButton deleteButton = new JButton("Tuhoa kortti");
        JButton viewAllButton = new JButton("Näytä kaikki");
        JButton searchButton = new JButton("Etsi");
        
        this.tableModel = new DefaultTableModel(null, fieldNames);
        JTable table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);
        
        Container container = getContentPane();
        GroupLayout layout = new GroupLayout(container);
        container.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                .addComponent(addButton)
                .addComponent(deleteButton)
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addComponent(viewAllButton)
                .addComponent(searchButton))
                .addComponent(tableScrollPane)));

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(addButton)
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
        saveAsItem.addActionListener(new SaveFileListener());
        quitItem.addActionListener(new QuitListener());

        JMenu file = new JMenu("Tiedosto");
        file.add(newFileItem);
        file.add(loadFileItem);
        file.add(saveFileItem);
        file.add(saveAsItem);
        file.addSeparator();
        file.add(quitItem);

        JMenuItem helpItem = new JMenuItem("Ohje");
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
                + "Tallenne tiedosto?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
                null, options, options[0]);
        if (saveFirst == JOptionPane.YES_OPTION) {
            return true;
        }
        return false;
    }

    private void saveFile() throws IOException {
        if (control.getCurrentFile() == null) {
            saveAs();
        } else {
            control.save();
        }
    }

    private void saveAs() throws IOException {
        File current = control.getCurrentFile();
        String directoryPath = "";
        if (current != null) {
            directoryPath = current.getPath();
        }

        JFileChooser chooser = new JFileChooser(directoryPath);
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            control.setCurrentFile(chooser.getSelectedFile());
            control.save();
        }
    }
    
    public void loadFile() throws FileNotFoundException{
        JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                control.loadFile(chooser.getSelectedFile());
            }
    }

    public void setData(Object[][] newData) {
        tableModel.setDataVector(newData, fieldNames);
    }

    private class LoadFileListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (control.needsToBeSaved() && saveFirstDialog()) {
                saveFile();
            }
                loadFile();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex){
                //Talennus ei onnistunut
            }
        }
    }

    private class NewFileListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (control.needsToBeSaved() && saveFirstDialog()) {
                System.out.println("TALLENNA");
            }
            System.out.println("Uusi tiedosto!");
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
            try {
                saveAs();
            } catch (IOException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
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
}
