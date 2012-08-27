/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cardregister;

import UserInterfaces.GraphicalUI;
import UserInterfaces.TextInterface;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author IstuvaHarka
 */
public class CardRegister {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Register register = new Register();
        try {
            register.load("10_Cards.txt");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CardRegister.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        GraphicalUI ui = new GraphicalUI(register);
        ui.setCardsInUse(register.getCards());
        ui.run();
    }
}
