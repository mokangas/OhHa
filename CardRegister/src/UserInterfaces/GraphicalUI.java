/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterfaces;

import cardregister.Card;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;

/**
 *
 * @author IstuvaHarka
 */
public class GraphicalUI implements Runnable {

    private JFrame frame;
    private List<Card> cardsInUse;

    public GraphicalUI() {
        cardsInUse = null;
    }

    /**
     *
     */
    @Override
    public void run() {
        frame = new JFrame("Kortisto");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

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
        panel.add(new JButton("Näytä kaikki"));
        panel.add(new JButton("Uusi"));
        panel.add(new JButton("Lataa"));
        panel.add(new JButton("Tallenna"));
        panel.add(new JButton("Lisää kortti"));
        panel.add(new JButton("Selaa"));
        panel.add(new JButton("Etsi"));
        panel.add(new JComboBox());
        return panel;
    }

    private JScrollPane addCardList() {
        String[] labels = Card.getLabels();
        Object[][] data = {
            {"Kathy", "Smith",
                "Snowboarding", new Integer(5), new Boolean(false), "more", "more", "more"},
            {"John", "Doe",
                "Rowing", new Integer(3), new Boolean(true), "more", "more", "more"},
            {"Sue", "Black",
                "Knitting", new Integer(2), new Boolean(false), "more", "more", "more"},
            {"Jane", "White",
                "Speed reading", new Integer(20), new Boolean(true), "more", "more", "more"},
            {"Joe", "Brown",
                "Pool", new Integer(10), new Boolean(false), "more", "more", "more"},
            {"John", "Doe",
                "Rowing", new Integer(3), new Boolean(true), "more", "more", "more"},
            {"Sue", "Black",
                "Knitting", new Integer(2), new Boolean(false), "more", "more", "more"},
            {"Jane", "White",
                "Speed reading", new Integer(20), new Boolean(true), "more", "more", "more"},
            {"Joe", "Brown",
                "Pool", new Integer(10), new Boolean(false), "more", "more", "more"},
            {"John", "Doe",
                "Rowing", new Integer(3), new Boolean(true), "more", "more", "more"},
            {"Sue", "Black",
                "Knitting", new Integer(2), new Boolean(false), "more", "more", "more"},
            {"Jane", "White",
                "Speed reading", new Integer(20), new Boolean(true), "more", "more", "more"},
            {"Joe", "Brown",
                "Pool", new Integer(10), new Boolean(false), "more", "more", "more"}
        };
        
        JTable table = new JTable(data, labels);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        return scrollPane;
    }

    public void setCardsInUse(List<Card> cards) {
        cardsInUse = cards;
    }
}
