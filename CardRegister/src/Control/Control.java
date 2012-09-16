
package Control;

import UserInterfaces.GUI.MainWindow;
import cardregister.CardRegister;
import cardregister.CardRegisterExceptions.AlmostSameCardExistsException;
import cardregister.CardRegisterExceptions.CardAlreadyExistsException;
import cardregister.CardRegisterExceptions.NullInputException;
import cardregister.Register;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * A mediator class between <code>CardRegister</code> and the UI. This class
 * also takes care of the running of the program, i.e. this contains the main-
 * method and the when the constructor of this class is called, the program will
 * start to run.
 * 
 * @author mokangas
 */
public class Control {

   /**
    * The main-method. Constructs a new <code>Control</code>-object, which launches
    * the program.
    * 
    * @param args Vommand line arguments, nothing is done with them.
    */ 
    public static void main(String[] args) {
        Control control = new Control();
    }
    
    private CardRegister register; // The register this object is attached to
    private MainWindow mainWindow; // The main window of the program's GUI.

    /**
     * 
     */
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

    public void addNewCard(String[] content) throws NullInputException, 
            AlmostSameCardExistsException, CardAlreadyExistsException {
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
            throws AlmostSameCardExistsException, NullInputException,
            CardAlreadyExistsException{
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

    public int getNameFieldsNumber() {
        return register.getNameField();
    }

    public void searchAnyField(String searchWord) {
        String[][] newData = register.searchAnyField(searchWord);
        if (newData != null && newData.length > 0) {
            mainWindow.setCardTableData(newData);
            mainWindow.setMessage("Haun tulokset", false);
        } else {
            mainWindow.setMessage("Haku ei tuottanut tuloksia.", false);
        }
    }
}
