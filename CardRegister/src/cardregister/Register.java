package cardregister;

import CardStorage.FileLoader;
import CardStorage.FileSaver;
import cardregister.CardRegisterExceptions.AlmostSameCardExistsException;
import cardregister.CardRegisterExceptions.CardAlreadyExistsException;
import cardregister.CardRegisterExceptions.NullInputException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An implementation of
 * <code>CardRegister</code>. This class both contains all the cards in the
 * register, and provides the basic functionality to operate with them.
 *
 * @author mokangas
 */
public class Register implements CardRegister {

    private List<Card> cards; // List of cards in the register
    private File currentFile; // The file this register is attached to.
    private boolean needSave; // Has this register changed since the last save.

    /**
     * The sole constructor. Initializes variables, but does nothing else.
     */
    public Register() {
        cards = new ArrayList<>();
        currentFile = null;
        needSave = false;
    }

    /**
     *
     * Creates a new Card to the register.
     *
     * @param content The text fields of the card to be created
     * @throws Control.CardRegisterExceptions.NullInputException if no text
     * field has content.
     * @throws Control.CardRegisterExceptions.AlmostSameCardExistsException if
     * there already is a card which is almost the same by the standards of
     * @see CardRegister.Card#isRelativelyEqualTo method.
     * @throws CardAlreadyExistsException never.
     */
    @Override
    public void createCard(String[] content) throws NullInputException,
            AlmostSameCardExistsException, CardAlreadyExistsException {

        // Checks that at least one of the text fields has content-
        boolean contentIsEmpty = true;
        for (int i = 0; i < Card.getNumberOfFields(); i++) {
            if (! content[i].trim().equals("")) {
                contentIsEmpty = false;
                break;
            }
        }
        if (contentIsEmpty) {
            throw new NullInputException();
        }

        //Checks if there already is a card that are relatively similar.
        String[][] similarCards = searchRelativelySimilarCardsData(content);
        if (similarCards != null) {
            throw new CardRegisterExceptions.AlmostSameCardExistsException(similarCards);
        }

        // If all the above checks are passed, a new card is created.
        cards.add(new Card(content));
        needSave = true;
    }

    /**
     * For deleting cards from the register.
     *
     * @param toBeDeleted The content of the cards which are being deleted.
     * First argument specifies the card and the second it's fields. Thus, when
     * deleting two cards both having five fields the argument would be of form
     * String[2][5]. Deleting cards will set <code>needsSave</code> true.
     */
    @Override
    public void deleteCards(String[][] toBeDeleted) {
        if (toBeDeleted == null) {
            return;
        }
        for (int i = 0; i < toBeDeleted.length; i++) {
            Card card = findCard(toBeDeleted[i]);
            cards.remove(card);
        }
        needSave = true;
    }

    /**
     * Edits data of an already existing card.
     *
     * @param oldData the data of the card which is being edited. This is used
     * to specify the card.
     * @param newData the data of the card once it is edited.
     * @throws Control.CardRegisterExceptions.NullInputException if all the
     * fields are empty.
     * @throws Control.CardRegisterExceptions.AlmostSameCardExistsException if
     * the card is sufficiently similar to an already existing card other than
     * the one being edited. The similarity is decided by the
     * <conde>searchRelativelySimilarCardsData</code>-method, and thus relies
     * ultimately on the <code>isRelativelyEqualTo</code>-method of *
     * the <code>Card</code>-class.
     * @throws Control.CardRegisterExceptions.CardAlreadyExistsException Never.
     */
    @Override
    public void editCard(String[] cardData, String[] newData)
            throws AlmostSameCardExistsException, NullInputException {

        // Card with empty fields is not allowed:
        boolean hasContent = false;
        for (int i = 0; i < newData.length; i++) {
            if (newData[0].length() > 0) {
                hasContent = true;
                break;
            }
        }

        if (!hasContent) {
            throw new NullInputException();
        }

        // Addition of card relatively similar to an already existing card is 
        // prohibited. It must be also prohibited to edit an existing card so that
        // it will become too similar to some card. The criteria could be for example
        // that two cards do not have the same author and title. It is explicitly
        // defined in Card's isRelativelyEqualTo method.

        // When editing a card we must first check if there already exists a card
        // relatively similar to the edited card in which case the edit is not allowed.
        // However, if the already existing card is the card that is edited, that is:
        // the user edits a field by which the similarity isn't decided, the edit 
        // must be allowed.

        String[][] similar = searchRelativelySimilarCardsData(newData);
        if (similar == null || (findCard(similar[0]) == findCard(cardData))) {
            findCard(cardData).setContent(newData);
            needSave = true;
        } else {
            throw new CardRegisterExceptions.AlmostSameCardExistsException(similar);
        }
    }

    /**
     * A private method used to find a card by it's content. Card having the
     * searched content depends on the
     * <code>Card</code>-class's
     * <code>contentEqualsTo</code>-method.
     *
     * @param content the content of the card searched for
     * @return The card with the given content.
     */
    private Card findCard(String[] content) {
        for (Card card : cards) {
            if (card.contentEqualsTo(content)) {
                return card;
            }
        }
        return null;
    }

    /**
     * Retrieves card data. All the contents of the cards in the register will
     * be returned as a string array, whose first dimension is the same as the
     * number of the cards and the second is the number of text fields in a
     * card. Thus, for 20 cards having 7 text field the returned array would be
     * <code>String[20][7]</code>.
     *
     * @return The content of the cards.
     */
    @Override
    public String[][] getCardData() {

        String[][] cardData = new String[cards.size()][Card.getLabels().length];
        for (int i = 0; i < cards.size(); i++) {
            cardData[i] = cards.get(i).getContent();
        }

        return cardData;
    }

    /**
     * Tells which file the register is attached to. This is the file the
     * register was last saved to or loaded from.
     *
     * @return the file attached to this register.
     */
    @Override
    public File getCurrentFile() {
        return currentFile;
    }

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
    @Override
    public String[] getFieldNames() {
        return Card.getLabels();
    }

    
    /**
     * Tells which text field is the name of the whole card. I.e. for race
     * horses it would be natural to select the title of the card to be the 
     * horse's name. This methods tells what field contains the name of the horse.
     * @return The name field's number.
     */
    public int getNameField(){
        return Card.getNameField();
    }
    
       
    /**
     * Loads the card data from the specified file. About the format of the file, 
     * check specifications from the package <code>CardStrorage</code>.
     * @param file The file from which the data is read.
     * @throws FileNotFoundException If the specified file does not exist.
     */
    @Override
    public void load(File file) throws FileNotFoundException {
        FileLoader loader = new FileLoader(file, Card.getNumberOfFields());
        String[][] newContent = loader.load();
        cards.clear();
        for (int i = 0; i < newContent.length; i++) {
            cards.add(new Card(newContent[i]));
        }
        this.currentFile = file;
        needSave = false;
    }

     /**
     * Tells whether information is lost when the file is closed without saving.
     * This can be used to warn the user before he closes the program or tries
     * to load a new file. The register is considered changed after adding, deleting
     * or editing a card. Successful load, save or reset will set this false again.
     * @return true if the register has changed since the last save, false otherwise.
     */
    @Override
    public boolean needsToBeSaved() {
        return needSave;
    }

    /**
     * Empties the register.
     */
    @Override
    public void reset() {
        cards.clear();
        currentFile = null;
        needSave = false;
    }

     /**
     * Saves the register to the file set as current. When using this method it's
     * advised to check that there really is a current file to save on.
     * @throws IOException If the save fails.
     */
    @Override
    public void save() throws IOException {
        FileSaver saver = new FileSaver(currentFile);
        saver.save(transformCardsToRawData(cards));
        needSave = false;
    }

    /**
     * Used for detailed search of the register.
     * @param searchWords The search words as an array of strings. Each string
     * corresponds to a text field in a card. Whether or not card is included in
     * the search is decided by the <code>fieldsInclude</code>-method of the 
     * <code>Card</code>-class.
     * @return The contents of the cards satisfying the search. The first argument 
     * indicated the card and the second it's field's number.
     */
    @Override
    public String[][] search(String[] search) {

        List<Card> searchList = new ArrayList<>();
        for (Card card : cards) {
            if (card.fieldsInclude(search)) {
                searchList.add(card);
            }
        }

        return transformCardsToRawData(searchList);
    }

    
     /**
     * Searches the search word from any field of the cards. The result includes
     * all the cards for which the method <code>searchAnyField</code> returns <code>true</code>.
     * @param searchWord The word to be searched.
     * @return The contents of the cards satisfying the search. The first argument 
     * indicated the card and the second it's field's number.
     */
    @Override
    public String[][] searchAnyField(String searchWord) {
        ArrayList<Card> searchResults = new ArrayList<Card>();
        for (Card card : cards) {
            if (card.searchAnyField(searchWord)) {
                searchResults.add(card);
            }
        }
        return transformCardsToRawData(searchResults);
    }

    /**
     * For finding cards that are relatively similar to the given one. This is used
     * if it is wanted to prohibit adding of cards too similar to each others, or 
     * if the user should be warned about there already existing relatively similar
     * card. Whether some card is relatively similar to the given one is decided by
     * <code>Card</code>'s <code>isRelativelyEqualTo</code>-method.
     * @param content The content of the card's fields for which the search is done.
     * @return An array of all the cards that are relatively similar to the one
     * given as a parameter.
     */
    private String[][] searchRelativelySimilarCardsData(String[] content) {
        ArrayList<Card> foundCards = new ArrayList<Card>();
        for (Card card : cards) {
            if (card.isRelativelyEqualTo(content)) {
                foundCards.add(card);
            }
        }

        return transformCardsToRawData(foundCards);
    }

     /**
     * Sets the register attached to the file for saving etc.
     * @param file The file register will be attached to.
     */
    @Override
    public void setCurrentFile(File file) {
        currentFile = file;
    }

    /**
     * Sorts the cards according to the alphabetical order (case ignored) of the
     * field specified by the argument. It's wise to use one of this class's
     * static variables as a parameter.
     *
     * @deprecated This method is practical for consol interfaces, but has
     * no use in any relatively reasonable GUI.
     * @param field The index of the field by which the register will be sorted.
     */
    public void sortBy(int field) {
        CardComparator comparator = new CardComparator(field);
        Collections.sort(cards, comparator);
    }

    /**
     * Converts the data of a card list to a one without a predefined structure.
     * This is used to communicate with other packages which don't have to know
     * about the structure of class <code>Card</code>. In the returned array first
     * dimension indicates the number of cards and the second one card's text fields.
     * @param cardList The list of cards to be converted
     * @return The card list as an array of strings.
     */
    private String[][] transformCardsToRawData(List<Card> cardList) {
        if (cardList.size() == 0) {
            return null;
        }

        String[][] data = new String[cardList.size()][Card.getNumberOfFields()];
        for (int i = 0; i < cardList.size(); i++) {
            data[i] = cardList.get(i).getContent();
        }
        return data;
    }
}
