/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import UserInterfaces.GUI.MainWindow;
import cardregister.Register;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author IstuvaHarka
 */
public class Control {

    public static void main(String[] args) {
        Control control = new Control();
    }
    
    private Register register;
    private MainWindow mainWindow;

    public Control() {
        register = new Register();
        String[] fieldNames = register.getFieldNames();
        // Poistetaan myöhemmin:
//        Object[][] data =null;
//        try {
//            
//            register.load("10_Cards.txt");
//            data = register.getCardData();
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
//        }
                
        mainWindow = new MainWindow(this, fieldNames);
    }
    
    public boolean needsToBeSaved(){
        return register.needsToBeSaved();
    }
    
    public File getCurrentFile(){
        return null;
    }
    
    public void setCurrentFile(File file){
        register.setCurrentFile(file);
    }
    
    public void save() throws IOException{
        register.save();
    }
    
    public void loadFile(File file) throws FileNotFoundException{
        register.load(file);
        mainWindow.setData(register.getCardData());
    }

    public void addNewCard(String[] content) {
        register.createCard(content);
        mainWindow.setData(register.getCardData());
    }
    
    public void newFile(){
        register.reset();
        mainWindow.setData(null);
    }

    public void search(String[] searchWords) {
        String[][] newData = register.search(searchWords);
        if (newData != null) {
            mainWindow.setData(newData);
        }
    }

    public void viewAll() {
        mainWindow.setData(register.getCardData());
    }
    
}
