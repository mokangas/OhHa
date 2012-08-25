/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cardregister;

import java.util.Comparator;

/**
 * A comparator for ordering cards according to a chosen field.
 * @author IstuvaHarka
 */
public class CardComparator implements Comparator<Card> {
    
    /**
     * The index of cards field by which this comparator orders cards.
     */
    int comparisionField;
    
    /**
     * Creates a comparator that orders cards by the given field alphabetically.
     * 
     * @param comparisionField The index of card's field by which the comparator orders cards.
     */
    public CardComparator(int comparisionField){
        this.comparisionField = comparisionField;
    }
    
    /**
     * Compares the card1 to card2. Card1 is prior to card2, if it's text field
     * whose index is comparisionField is alphabetically before card2's 
     * corresponding field. 
     * @param card1 The first card to be compared.
     * @param card2 The second card to be compared.
     * @return A negative integer if card1 is prior to card2, a positive number 
     * if it's the other way round and 0 if they are equal (case ignored).
     *          
     */
    @Override
    public int compare(Card card1, Card card2){
        String c1 = card1.getContent()[comparisionField].toLowerCase();
        String c2 = card2.getContent()[comparisionField].toLowerCase();
        
        return c1.compareTo(c2);
    }
}
