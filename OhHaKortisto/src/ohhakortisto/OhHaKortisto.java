/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ohhakortisto;

import storage.CardStorage;
import storage.TxtFileStorage;
import userInterface.TextInterface;


/**
 *
 * @author mokangas
 */
public class OhHaKortisto {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        // ATM this method is just to test the others.
        CardStorage storage = new TxtFileStorage();
        TextInterface userInterface = new TextInterface(storage);
        userInterface.launch();
    }
}
