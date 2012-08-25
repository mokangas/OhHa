/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cardregister;

/**
 *
 * @author IstuvaHarka
 */
public class Card {

    static final int TITLE = 0;
    static final int AUTHOR = 1;
    static final int OTHER_WRITERS = 2;
    static final int LOCATION = 3;
    static final int YEAR = 4;
    static final int ISBN = 5;
    static final int KEY_WORDS = 6;
    static final int REMARKS = 7;
    static public final int NUMBER_OF_FIELDS = 8;
    static final String[] labels = {"Nimeke", "Tekijä", "Muut kirjoittajat",
        "Sijainti", "Vuosi", "ISBN", "Avainsanat", "Muistiinpanoja"};
    String[] content;

    public Card(String[] textFields) {
        this.content = textFields;
    }

    public String[] getContent() {
        return content;
    }
    
    public void setContent(String[] newContent){
        content = newContent;
    }
    
    public void changeField(int fieldNumber, String newText){
        content[fieldNumber]= newText;
    }

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