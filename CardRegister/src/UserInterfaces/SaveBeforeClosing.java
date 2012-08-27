/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterfaces;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author IstuvaHarka
 */
public class SaveBeforeClosing {

    public static int saveBeforeClosing(JFrame frame){
        String message = "Kortisto on muuttunut viime tallennuksen jälkeen. Tallenna muutokset?";
        String title = "Tallenna muutokset?";
        String[] options = new String[] {"Kyllä", "Ei", "Peruuta"};
        int n = JOptionPane.showOptionDialog(frame, message, title, JOptionPane.YES_NO_CANCEL_OPTION, 
                JOptionPane.WARNING_MESSAGE, null, options, options[2]);
        return n;
    }
}
