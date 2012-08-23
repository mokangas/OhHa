/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cardregister;

import java.io.IOException;
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
        
//        long aika =  System.currentTimeMillis();
        
        boolean a = register.load("10_Cards.txt");
        register.addCard(new Card(Card.labels));
        try {
            register.save("10_Cards.txt");
    //        aika = System.currentTimeMillis() -aika;
    //        System.out.println(aika);
    //        
    //        aika =  System.currentTimeMillis();
    //        register.search("jfh");
    //        aika = System.currentTimeMillis() - aika;
    //register.printAll();
            //register.printAll();
        } catch (IOException ex) {
            Logger.getLogger(CardRegister.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
