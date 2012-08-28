/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterfaces.graphicalUI;

import cardregister.Card;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author IstuvaHarka
 */
public class ShowCard implements Runnable {
    
    private Card card;
       public ShowCard(Card card){
           this.card = card;
    }

    @Override
    public void run() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(GraphicalUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JFrame frame = new JFrame("Kortti "+card.getContent()[Card.TITLE]);
        createComponents(frame.getContentPane());
        frame.pack();
        frame.setVisible(true);
    }

    private void createComponents(Container container) {
        container.setLayout(new GridLayout(Card.NUMBER_OF_FIELDS, 2));
        for (int i = 0; i < Card.NUMBER_OF_FIELDS; i++) {
            container.add(new JLabel(Card.getLabels()[i]+": "));
            container.add(new JLabel(card.getContent()[i]));
        }
    }
    
}
