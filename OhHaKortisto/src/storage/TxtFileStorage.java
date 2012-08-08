/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package storage;

import java.util.HashMap;
import ohhakortisto.Card;

/**
 *
 * @author IstuvaHarka
 */

/**
 * TODO: save function
 *       read from txt file function
 * @author IstuvaHarka
 */
public class TxtFileStorage implements CardStorage{

    HashMap<String, Card> cards;
    
    
    public TxtFileStorage(){
        cards = new HashMap<>();
    }
    
    @Override
    public boolean add(Card card) {
        
        if (cards.containsKey(card.getTitle())) {
            return false;
        }
        cards.put(card.getTitle(), card);
        return true;
    }

    @Override
    public Card search(String title) {
        return cards.get(title);
    }
    
    
    
}
