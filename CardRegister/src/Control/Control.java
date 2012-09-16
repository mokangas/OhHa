
package Control;

import UserInterfaces.ConsolInterface;
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
    * @param args Command line arguments, nothing is done with them.
    */ 
    public static void main(String[] args) {
//        Control control = new Control();
        Control control = new Control("text");
    }
    
    private CardRegister register; // The register this object is attached to
    private MainWindow mainWindow; // The main window of the program's GUI.

    /**
     * Constructor. Sets up th business logic and the GUI and starts the program.
     */
    public Control() {
        register = new Register();
        String[] fieldNames = register.getFieldNames();
        mainWindow = new MainWindow(this, fieldNames);
    }

    private Control(String text) {
        CardRegister register = new Register();
        new ConsolInterface(register);
    }

    /**
     * Tells if the register looses data if not saved before closing.
     * @return true if data is lost, false otherwise.
     */
    public boolean needsToBeSaved() {
        return register.needsToBeSaved();
    }

    /**
     * Tells what is the file register was last saved to/ loaded from.
     * @return The current file.
     */
    public File getCurrentFile() {
        return register.getCurrentFile();
    }

    /**
     * Sets the Current file to be the given parameter.
     * @param file The file that is set to be current.
     */
    public void setCurrentFile(File file) {
        register.setCurrentFile(file);
    }

    /**
     * Saves the register in the current file.
     * @throws IOException if the save fails.
     */
    public void save() throws IOException {
        register.save();
        mainWindow.setTitle("Kortisto: " + register.getCurrentFile().getName());
    }

    /**
     * Loads the given file into a card register.
     * @param file The file from which the data is loaded.
     * @throws FileNotFoundException If the file is not found.
     */
    public void loadFile(File file) throws FileNotFoundException {
        register.load(file);
        mainWindow.setCardTableData(register.getCardData());
        mainWindow.setTitle("Kortisto: " + register.getCurrentFile().getName());
    }

    /**
     * Adds new card to the register.
     * @param content The content of the card to be added.
     * @throws cardregister.CardRegisterExceptions.NullInputException If the data
     * of the card added contains at most emptyspaces.
     * @throws cardregister.CardRegisterExceptions.AlmostSameCardExistsException
     * If there already is a card with almost the same data. This can be handled
     * with a warning to the user or by prohibiting such card to be added.
     * @throws cardregister.CardRegisterExceptions.CardAlreadyExistsException 
     * If there already is exactly the same card in the register. The addition of 
     * the new card should be strictly prohibited.
     */
    public void addNewCard(String[] content) throws NullInputException, 
            AlmostSameCardExistsException, CardAlreadyExistsException {
        register.createCard(content);
        mainWindow.setCardTableData(register.getCardData());
        mainWindow.setMessage("Kortti lisätty", false);
    }

    /**
     * Empties the register from all data.
     */
    public void newFile() {
        register.reset();
        mainWindow.setCardTableData(null);
        mainWindow.setTitle("Kortisto");
    }

    /**
     * Searches words from the register.
     * @param searchWords The search words.
     */
    public void search(String[] searchWords) {
        String[][] newData = register.search(searchWords);
        if (newData != null && newData.length > 0) {
            mainWindow.setCardTableData(newData);
            mainWindow.setMessage("Haun tulokset", false);
        } else {
            mainWindow.setMessage("Haku ei tuottanut tuloksia.", false);
        }
    }

    /**
     * Retrieves data of all the cards in the register.
     */
    public void viewAll() {
        mainWindow.setCardTableData(register.getCardData());
        mainWindow.setMessage("Kaikki kortit", false);
    }

    /**
     * 
     * Edits a card in the register. The same restrictions as in the case of
     * adding a new card hold.
     * @param oldData The data of the card to be edited. This is used to indetify
     * the card.
     * @param newData The new data of the card to be edited.
     * @throws cardregister.CardRegisterExceptions.AlmostSameCardExistsException
     * If the edit would result the card being too similar to some other already
     * existing card.
     * @throws cardregister.CardRegisterExceptions.NullInputException
     * If the new data contains at most emptyspaces.
     * @throws cardregister.CardRegisterExceptions.CardAlreadyExistsException 
     * If there already is exactly similar card. The point of this exception is
     * visible in this method: if this didn't exist, the method couldn't find
     * the right card to edit.
     */
    public void editCard(String[] oldData, String[] newData)
            throws AlmostSameCardExistsException, NullInputException,
            CardAlreadyExistsException{
        register.editCard(oldData, newData);
        mainWindow.setCardTableData(register.getCardData());
        mainWindow.setMessage("Kortin tiedot muutettu", false);
    }

    /**
     * Deletes cards from the register.
     * @param toBeDeleted The data of the cards to be deleted. First dimension
     * identifies the card and the second it's data fields.
     */
    public void deleteCards(String[][] toBeDeleted) {
        register.deleteCards(toBeDeleted);
        mainWindow.setCardTableData(register.getCardData());
        if (toBeDeleted.length == 1) {
            mainWindow.setMessage("Kortti tuhottu", false);
        } else {
            mainWindow.setMessage("Kortit tuhottu", false);
        }
    }

    /**
     * Retrieves the number of the text field whose content is meant to be
     * the name of the card.
     * @return The number of the name field.
     */
    public int getNameFieldsNumber() {
        return register.getNameField();
    }

    /**
     * Searches the search word from all the fields of cards in the register.
     * @param searchWord The search word.
     */
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
