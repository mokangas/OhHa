/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterfaces;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 *
 * @author IstuvaHarka
 */
public class SelectFile {

    /**
     * 
     * @param frame The frame from which this dialog opens.
     * @return The selected file if the user selects one and approves it. 
     * Otherwise null.
     */
    public static File openFile(JFrame frame) {
        JFileChooser chooser = new JFileChooser();
        int option = chooser.showOpenDialog(frame);
        if (option == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        return null;
    }
}
