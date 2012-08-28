/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterfaces.graphicalUI;

import cardregister.Register;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author IstuvaHarka
 */
public class AddCardListener implements MouseListener {

    private Register register;
    
    public AddCardListener(Register register){
        this.register = register;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            AddNewCardDialog addCard = new AddNewCardDialog();
            addCard.run();
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
