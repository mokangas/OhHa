/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterfaces;

import cardregister.Card;
import cardregister.Register;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

/**
 *
 * @author IstuvaHarka
 */
public class GraphicalUI implements Runnable {

    private Register register;
    private JFrame frame;
    private List<Card> cardsInUse;

    public GraphicalUI(Register register) {
        this.register = register;
        cardsInUse = register.getCards();
    }

    /**
     *
     */
    @Override
    public void run() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(GraphicalUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        frame = new JFrame("Kortisto");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setJMenuBar(addMenuBar());

        createComponents(frame.getContentPane());
        frame.pack();
        frame.setVisible(true);
    }

    private void createComponents(Container container) {
        container.setLayout(new BorderLayout());
        container.add(addUseButtons(), BorderLayout.NORTH);
        container.add(addCardList(), BorderLayout.CENTER);
    }

    private JPanel addUseButtons() {
        JPanel panel = new JPanel(new GridLayout(2, 4));
        
        JButton load = new JButton("Lataa");
        load.addMouseListener(new LoadListener(frame, register));
        
        panel.add(new JButton("Näytä kaikki"));
        panel.add(new JButton("Uusi"));
        panel.add(load);
        panel.add(new JButton("Tallenna"));
        panel.add(new JButton("Lisää kortti"));
        panel.add(new JButton("Selaa"));
        panel.add(new JButton("Etsi"));
        panel.add(new JComboBox());
        return panel;
    }

    private JScrollPane addCardList() {
        String[] labels = Card.getLabels();
        Object[][] data = new Object[cardsInUse.size()][Card.NUMBER_OF_FIELDS];
        for (int i = 0; i < cardsInUse.size(); i++) {
            data[i] = cardsInUse.get(i).getContent();
        }
        
        JTable table = new JTable(data, labels);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        return scrollPane;
    }
    
    private JMenuBar addMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(addMainMenu());
        return menuBar;
    }

    public JMenu addMainMenu(){
        JMenuItem newFile = new JMenuItem("Uusi");
        JMenuItem openFile = new JMenuItem("Avaa");
        JMenuItem saveFile = new JMenuItem("Tallenna");
        JMenuItem saveAs = new JMenuItem("Tallenna nimellä");
        
        JMenu mainMenu =new JMenu("Tiedosto");
        mainMenu.add(newFile);
        mainMenu.add(openFile);
        mainMenu.add(saveFile);
        mainMenu.add(saveAs);
        return mainMenu;
    }
    public void setCardsInUse(List<Card> cards) {
        cardsInUse = cards;
    }
}
