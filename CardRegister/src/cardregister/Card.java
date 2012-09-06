/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cardregister;

/**
 * Cards which contain information of books. The information is presented
 * in a array of Strings. Each String should contain the information corresponding
 * to the static finals defined in this class.
 * @author IstuvaHarka
 */
public class Card {

  /**
   * A series of integer constants whose names tell what the corresponding
   * content-String[] contain.
   */
    public static final int TITLE = 0;
    public static final int AUTHOR = 1;
    public static final int OTHER_WRITERS = 2;
    public static final int LOCATION = 3;
    public static final int YEAR = 4;
    public static final int ISBN = 5;
    public static final int KEY_WORDS = 6;
    public static final int REMARKS = 7;
    /**
     * The number of text fields in a card.
     */
    static public final int NUMBER_OF_FIELDS = 8;
    /**
     * The names of fields which are displayed to the user.
     */
    
    static final String[] labels = {"Nimeke", "Tekijä", "Muut kirjoittajat",
        "Sijainti", "Vuosi", "ISBN", "Avainsanat", "Muistiinpanoja"};
    
    /**
     * 
     */
    
    /**
     * This tells on what text fields it is determined whether two cards are the
     * same. True, if the text field is included in the decision.
     */
    
    static final boolean[] cardEquals = {true, true, false, false, false, false, 
        false, false};

    /**
     * The content of the text fileds of this card.
     */
    
    private String[] content;

    
    
    /**
     * Creates a new card. Too short array or null in the parameter will cause
     * part of the card's text fields to null. From too long parameter
     * @param textFields the content of the card to be created. What each String
     * means is specified by the static finals of this class.
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
     * Returns the content of this card.
     * @return content as an array of Strings.
     */
    public String[] getContent() {
        return content;
    }
    
    /**
     * Sets a new content to this card.
     * @param newContent The new content.
     */
    public void setContent(String[] newContent){
        content = newContent;
    }
    
    /**
     * Changes the information in one text field of this card.
     * @param fieldNumber The field whose information will be changed. Using the
     * static finals of this class is advised.
     * @param newText 
     */
    public void changeField(int fieldNumber, String newText){
        content[fieldNumber]= newText;
    }

    /**
     * Tells what type of content text fields of this card has. These are the 
     * names which are displayed to the user.
     * @return The names of the text fields.
     */
    public static String[] getLabels(){
        return labels;
    }
    
    /**
     * TESTAUSTA JA KOKEILUA VARTEN
     */
    public void print() {
        for (int i = 0; i < NUMBER_OF_FIELDS; i++) {
            System.out.println(content[i]);
        }
    }
    
   

    /**
     * Testaukseen ja kokeiluun.
     */
    public boolean search(String search) {
        for (int i = 0; i < NUMBER_OF_FIELDS; i++) {
            String toLowerCase = content[i].toLowerCase();
            if (toLowerCase.contains(search)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Compares the input to the content of the card. In order to pass this, the
     * content of each text field in this card must contain as a substring the
     * given Strings. The comparison is not case sensitive.
     * @param comparedTo The Strings which are searched from the content of this
     * card.
     * @return TRUE if all the fields contain the searched Strings, FALSE if one
     * or more search words aren't found.
     */
    public boolean compareFields(String[] comparedTo) {
        for (int i = 0; i < NUMBER_OF_FIELDS; i++) {
            String fieldLowerCase = content[i].toLowerCase();
            String comparator = comparedTo[i].toLowerCase().trim();
            if (!fieldLowerCase.contains(comparator)) {
                return false;
            }
        }
        return true;
    }
    
}