/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cardregister;

import cardregister.CardRegisterExceptions.AlmostSameCardExistsException;
import cardregister.CardRegisterExceptions.CardAlreadyExistsException;
import cardregister.CardRegisterExceptions.NullInputException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This is an interface for outside objects to communicate with
 * <code>CardRegister</code> package. 
 * @author mokangas
 */
public interface CardRegister {
    
    /**
     * 
     * Creates a new Card to the register.
     * @param content The text fields of the card to be created
     * @throws Control.CardRegisterExceptions.NullInputException if no text field
     * has content.
     * @throws Control.CardRegisterExceptions.AlmostSameCardExistsException if
     * the register decides the created card is similar to an existing one.
     * There is no stone set rules for the similarity, to allow some cases be
     * handled with only warning to the user.
     * @throws CardAlreadyExistsException if there are two cards exactly similar
     * to each others. The word "exactly" doesn't have to mean exactly, for example
     * this can be case insensitive. This should be thrown and handled when it is
     * impossible to create the card with the given data. 
     */
    public void createCard(String[] content) throws NullInputException,
            AlmostSameCardExistsException, CardAlreadyExistsException;
    
    /**
     * For deleting cards from the register.
     * @param toBeDeleted The content of the cards which are being deleted. First
     * argument specifies the card and the second it's fields. Thus, when deleting
     * two cards both having five fields the argument would be of form String[2][5].
     */
    public void deleteCards(String[][] toBeDeleted);
    
    /**
     * Edits data of an already existing card.
     * @param oldData the data of the card which is being edited. This is used to
     * specify the card.
     * @param newData the data of the card once it is edited.
     * @throws Control.CardRegisterExceptions.NullInputException if all the fields
     * are empty.
     * @throws Control.CardRegisterExceptions.AlmostSameCardExistsException if the 
     * card is sufficiently similar to an already existing card. It is up to implementor
     * to decide whether this causes dismissal of the edit, or just a warning.
     * @throws Control.CardRegisterExceptions.CardAlreadyExistsException if the edited
     * card would be similar to an already existing card after the edit. This must be
     * prohibited, since the data is used to identify cards.
     */
    public void editCard(String[] oldData, String[] newData) 
            throws NullInputException, AlmostSameCardExistsException, CardAlreadyExistsException; 
    
    /**
     * Retrieves card data. All the contents of the cards in the register will
     * be returned as a string array, whose first dimension is the same as the number
     * of the cards and the second is the number of text fields in a card.
     * @return The content of the cards.
     */
    public String[][] getCardData();
    
    /**
     * Tells which file the register is attached to. This is the file the register
     * was last saved to or loaded from.
     * @return the file attached to this register.
     */
    public File getCurrentFile();
    
    /**
     * Returns what are the labels of the text fields in a card. For example in a register
     * of race horses, the labels could be {"Name", "Owner", "Fastest run"...}. These labels
     * correspond in order all the data sent in and out the cardRegister. Thus it is safe
     * to couple them with the retrieved data like this:
     * 
     * fieldNames[0] ~ data[0][0],   fieldNames[1] ~ data[0][1],   fieldNames[2] ~ data[0][2],...
     * 
     * @return the labels as an array of Strings.
     */
    public String[] getFieldNames();
    
    /**
     * Tells which text field is the name of the whole card. I.e. for race
     * horses it would be natural to select the title of the card to be the 
     * horse's name. This methods tells what field contains the name of the horse.
     * @return The name field's number.
     */
    public int getNameField();
    
    /**
     * Loads the card data from the specified file. About the format of the file, 
     * check @see CardStrorage.
     * @param file The file from which the data is read.
     * @throws FileNotFoundException If the specified file does not exist.
     */
    public void load(File file) throws FileNotFoundException;
    
    /**
     * Tells whether information is lost when the file is closed without saving.
     * This can be used to warn the user before he closes the program or tries
     * to load a new file.
     * @return true if the register has changed since the last save, false otherwise.
     */
    public boolean needsToBeSaved();
    
    /**
     * Empties the register.
     */
    public void reset();
    
    /**
     * Saves the register to the file set as current. When using this method it's
     * advised to check that there really is a current file to save on.
     * @throws IOException If the save fails.
     */
    public void save() throws IOException;
    
    /**
     * Used for detailed search of the register.
     * @param searchWords The search words as an array of strings. Each string
     * corresponds to a text field in a card.
     * @return The contents of the cards satisfying the search. The first argument 
     * indicated the card and the second it's field's number.
     */
    public String[][] search(String[] searchWords);
    
    /**
     * Searches the searchWord from any field of the cards.
     * @param searchWord The word to be searched.
     * @return The contents of the cards satisfying the search. The first argument 
     * indicated the card and the second it's field's number.
     */
    public String[][] searchAnyField(String searchWord);

    /**
     * Sets the register attached to the file for saving etc.
     * @param file The file register will be attached to.
     */
    public void setCurrentFile(File file);

}
