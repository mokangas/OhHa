/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterfaces.graphicalUI;

import cardregister.Register;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author IstuvaHarka
 */
public class LoadListener implements MouseListener {

    private Register register;
    private JFrame frame;

    public LoadListener(JFrame frame, Register register) {
        this.frame = frame;
        this.register = register;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (register.hasChanged()) {
                int choice = SaveBeforeClosing.saveBeforeClosing(frame);
                if (choice == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (choice == JOptionPane.OK_OPTION){
                    //TALLENNA
                } 
            }
        }
        load();
    }
    
    private void load(){
        File file;
        file = SelectFile.openFile(frame);
        if (file == null){
            return;
        }
        try {
            register.load(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LoadListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
