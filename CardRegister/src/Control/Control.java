/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;


import UserInterfaces.GUI.MainWindow;
import cardregister.Register;

/**
 *
 * @author IstuvaHarka
 */
public class Control {
    
    private Register register;
    private MainWindow mainWindow;
    
     public static void main(String[] args) {
         Control control = new Control();
    }
     
    public Control(){
        register = new Register();
        mainWindow = new MainWindow();
    }

    
    public boolean hasChanged(){
        return register.hasChanged();
    }
}
