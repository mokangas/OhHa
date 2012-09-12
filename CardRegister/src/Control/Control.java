/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Control.ExceptionsThrownByRegister.AlmostSameCardExistsException;
import UserInterfaces.GUI.MainWindow;
import cardregister.Card;
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
        mainWindow = new MainWindow(this, fieldNames);
    }

    public boolean needsToBeSaved() {
        return register.needsToBeSaved();
    }

    public File getCurrentFile() {
        return register.getCurrentFile();
    }

    public void setCurrentFile(File file) {
        register.setCurrentFile(file);
    }

    public void save() throws IOException {
        register.save();
        mainWindow.setTitle("Kortisto: " + register.getCurrentFile().getName());
    }

    public void loadFile(File file) throws FileNotFoundException {
        register.load(file);
        mainWindow.setCardTableData(register.getCardData());
        mainWindow.setTitle("Kortisto: " + register.getCurrentFile().getName());
    }

    public void addNewCard(String[] content) throws Exception {
        register.createCard(content);
        mainWindow.setCardTableData(register.getCardData());
        mainWindow.setMessage("Kortti lisätty", false);
    }

    public void newFile() {
        register.reset();
        mainWindow.setCardTableData(null);
        mainWindow.setTitle("Kortisto");
    }

    public void search(String[] searchWords) {
        String[][] newData = register.search(searchWords);
        if (newData != null && newData.length > 0) {
            mainWindow.setCardTableData(newData);
            mainWindow.setMessage("Haun tulokset", false);
        } else {
            mainWindow.setMessage("Haku ei tuottanut tuloksia.", false);
        }
    }

    public void viewAll() {
        mainWindow.setCardTableData(register.getCardData());
        mainWindow.setMessage("Kaikki kortit", false);
    }

    public void editCard(String[] oldData, String[] newData)
            throws AlmostSameCardExistsException {
        register.editCard(oldData, newData);
        mainWindow.setCardTableData(register.getCardData());
        mainWindow.setMessage("Kortin tiedot muutettu", false);
    }

    public void deleteCards(String[][] toBeDeleted) {
        register.deleteCards(toBeDeleted);
        mainWindow.setCardTableData(register.getCardData());
        if (toBeDeleted.length == 1) {
            mainWindow.setMessage("Kortti tuhottu", false);
        } else {
            mainWindow.setMessage("Kortit tuhottu", false);
        }
    }
    
    public int getNameFieldsNumber(){
        return Card.TITLE;
    }
}
