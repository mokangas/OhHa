/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cardregister;

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
        
        register.load("10000_Cards.txt");
        
       
        String[] haku = new String[Card.NUMBER_OF_FIELDS];
        for (int i = 0; i < haku.length; i++) {
            haku[i] = "";
        }
        haku[Card.NUMBER_OF_FIELDS-1] = "           F       ";
        
        
        long aika = System.currentTimeMillis();
        List<Card> search = register.search(haku);
        aika = System.currentTimeMillis() - aika;
        System.out.println(aika+" ms.");
        System.out.println(search.size()+" tulosta.");
    }
}
