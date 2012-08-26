/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cardregister;

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
        
        String[] a = new String[Card.NUMBER_OF_FIELDS];
        a[0] = "asdasd";
        a[1] = "asdasdas";
        Card card = new Card(a);
        card.print();
//        TextInterface ui = new TextInterface();
//        ui.run();
    }
}
