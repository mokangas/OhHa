/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cardregister;

import java.util.Comparator;

/**
 *
 * @author IstuvaHarka
 */
public class CardComparator implements Comparator<Card> {
    
    int comparisionField;
    
    public CardComparator(int comparisionField){
        this.comparisionField = comparisionField;
    }
    
    @Override
    public int compare(Card card1, Card card2){
        String c1 = card1.getContent()[comparisionField].toLowerCase();
        String c2 = card2.getContent()[comparisionField].toLowerCase();
        
        return c1.compareTo(c2);
    }
}
