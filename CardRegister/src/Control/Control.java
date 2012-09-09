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
        mainWindow = new MainWindow(this, fieldNames);
    }
    
    public boolean needsToBeSaved(){
        return register.needsToBeSaved();
    }
    
    public File getCurrentFile(){
        return register.getCurrentFile();
    }
    
    public void setCurrentFile(File file){
        register.setCurrentFile(file);
    }
    
    public void save() throws IOException{
        register.save();
    }
    
    public void loadFile(File file) throws FileNotFoundException{
        register.load(file);
        mainWindow.setCardTableData(register.getCardData());
    }

    public void addNewCard(String[] content) {
        register.createCard(content);
        mainWindow.setCardTableData(register.getCardData());
    }
    
    public void newFile(){
        register.reset();
        mainWindow.setCardTableData(null);
    }

    public void search(String[] searchWords) {
        String[][] newData = register.search(searchWords);
        if (newData != null) {
            mainWindow.setCardTableData(newData);
        }
    }

    public void viewAll() {
        mainWindow.setCardTableData(register.getCardData());
    }

    public void editCard(String[] oldData, String[] newData) {
        register.editCard(oldData, newData);
        mainWindow.setCardTableData(register.getCardData());
    }

    public void deleteCards(String[][] toBeDeleted) {
        register.deleteCards(toBeDeleted);
        mainWindow.setCardTableData(register.getCardData());
    }
    
    
}
