/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterfaces.graphicalUI;

import cardregister.Register;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author IstuvaHarka
 */
public class SaveListener implements MouseListener {

    private Register register;
    JFrame frame;
    
    public SaveListener(JFrame frame, Register register){
        this.register = register;
        this.frame = frame;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            try {
                register.save(SelectFile.saveFile(frame).getName());
            } catch (IOException ex) {
                Logger.getLogger(SaveListener.class.getName()).log(Level.SEVERE, null, ex);
            }
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
