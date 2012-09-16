package cardregister;

/**
 * This class represents information about books in cards.
 *
 * Cards contain information in an array of strings called
 * <code>content[]</code>. The constant integers starting "
 * <code>LABEL_</code>" tell what information the corresponding content-string
 * contains. Thus
 * <code>content[LABEL_ISBN]</code> contains the book's ISBN-number, for
 * example.
 *
 * Constant
 * <code>NUMBER_OF_FIELDS</code> tells how many text fields there are in a card.
 * The string array
 * <code>labels</code> tells what the corresponding fields should be called by
 * the UI. In Finnish UI for example, it will be
 * <code>labels[LABEL_AUTHOR] = "Tekijä"</code>.
 *
 * Finally, the boolean array
 * <code>cardsEquals</code> tells which fields need to be the same for two cards
 * to be same (or relatively same, depending on how the other classes want to
 * use this). For example, if
 * <code>cardsEquals[LABEL_TITLE] == cardsEquals[LABEL_TITLE] == true</code>,
 * and every other element is
 * <code>false</code>, then the cards are deemed to be equal if and only if they
 * have the same content (case and extra emptyspace insensitive).
 *
 * @author mokangas
 */
public class Card {

    /**
     * A series of integer constants whose names tell what the corresponding
     * content-String[] contain.
     */
    private static final int LABEL_TITLE = 0;
    private static final int LABEL_AUTHOR = 1;
    private static final int LABEL_OTHER_WRITERS = 2;
    private static final int LABEL_LOCATION = 3;
    private static final int LABEL_YEAR = 4;
    private static final int LABEL_ISBN = 5;
    private static final int LABEL_KEY_WORDS = 6;
    private static final int LABEL_REMARKS = 7;
    static private final int NUMBER_OF_FIELDS = 8; //The number of text fields in a card.
    static private final String[] labels = {"Nimeke", "Tekijä", "Muut kirjoittajat",
        "Sijainti", "Vuosi", "ISBN", "Avainsanat", "Muistiinpanoja"};
    // The labels as they are meant for the user to read.
    static final boolean[] cardEquals = {true, true, false, false, false, false,
        false, false}; // Whether or not the field is taken into account when decided on 
    // similarity of two cards.
    private String[] content; // The content of the card.

    /**
     * The sole constructor.
     *
     * @param textFields The content of the card to be created.
     */
    public Card(String[] textFields) {
        if (textFields == null) {
            textFields = new String[NUMBER_OF_FIELDS];
        }
        content = new String[NUMBER_OF_FIELDS];
        int min = Math.min(NUMBER_OF_FIELDS, textFields.length);
        System.arraycopy(textFields, 0, content, 0, min);
    }

    /**
     * tells if the card's content is the same as the given array of strings.
     * This method is insensitive to capital/small letters and emptyspaces at
     * the beginning or end of the string. Thus field " SOme text here" is
     * considered to equal the field "some text HERE". This method can be used
     * to find a card whose identity is unknown, but content known. When doing
     * so, it's important not to have two different cards with the same content.
     *
     * @param text The content this card is being compared to.
     * @return True if the cards equal as told above, false otherwise.
     */
    public boolean contentEqualsTo(String[] text) {
        for (int i = 0; i < NUMBER_OF_FIELDS; i++) {
            if (!content[i].trim().toLowerCase().equals(text[i].trim().toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Compares the input to the content of the card. In order to pass this, the
     * content of each text field in this card must contain as a substring the
     * given Strings. The comparison is not case sensitive and it trims away
     * emptyspaces at the end and beginning of each string.
     *
     * @param text The Strings that are searched from the content of this card.
     * @return True if all the fields contain the searched Strings, False if one
     * or more searchAnyField words aren't found.
     */
    public boolean fieldsInclude(String[] text) {
        for (int i = 0; i < NUMBER_OF_FIELDS; i++) {
            String fieldLowerCase = content[i].toLowerCase();
            String comparator = text[i].toLowerCase().trim();
            if (!fieldLowerCase.contains(comparator)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the content of this card as an array of strings.
     *
     * @return The content.
     */
    public String[] getContent() {
        return content;
    }

    /**
     * The names of the text fields as they are meant to be displayed to the
     * user.
     *
     * @return The names of the text fields as displayed to the user..
     */
    public static String[] getLabels() {
        return labels;
    }

    /**
     * Tells which field is meant to be the name of the whole card.
     *
     * @return the number of the field containing the name.
     */
    static int getNameField() {
        return LABEL_TITLE;
    }

    /**
     * Tells how many text fields there are in a card.
     *
     * @return The number of the text fields in a card.
     */
    static int getNumberOfFields() {
        return NUMBER_OF_FIELDS;
    }

    /**
     * Tells whether this card is or is not "relatively equal" to a given card's
     * data. The fields that count towards two cards being relatively equal are
     * specified by the constant
     * <code>equalsTo</code> of this class. If
     * <code>equalsTo[i] == true</code>, the i:th field has an effect, and
     * otherwise it does not. For the fields that matter the comparision is case
     * insensitive and ignores emptyspaces at the end and beginning of the
     * strings.
     *
     * @param comparedTo The content of the card this is being compared to.
     * @return True if the cards are relatively equal (as explained above),
     * false otherwise.
     */
    public boolean isRelativelyEqualTo(String[] comparedTo) {
        for (int i = 0; i < NUMBER_OF_FIELDS; i++) {
            if (cardEquals[i]) {
                if (!content[i].trim().toLowerCase().equals(comparedTo[i].trim().toLowerCase())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Searches from every field of this card. If any of this card's fields
     * contain the search word (trimmed and case insensitive), this returns
     * true.
     *
     * @param search The word to be searched.
     * @return True if the search word is contained in some of the fields of
     * this card. False otherwise.
     */
    public boolean searchAnyField(String search) {
        for (int i = 0; i < NUMBER_OF_FIELDS; i++) {
            if (content[i].toLowerCase().contains(search.trim().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets a new content to this card.
     *
     * @param newContent The new content.
     */
    public void setContent(String[] newContent) {
        content = newContent;
    }
}