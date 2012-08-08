/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package storage;

import ohhakortisto.Card;

/**
 *
 * @author IstuvaHarka
 */
public interface CardStorage {

    /**
     * 
     * @param card the card which is added.
     * @return true if the adding succeeds, otherwise false.
     */
    
    /**
     * Adds the specified card to the storage
     * 
     * @param card the card to be added
     * @return true if adding succeeds, false otherwise.
     */
    public boolean add(Card card);
    
    /**
     * Searches cards from the storage by their names
     * 
     * @param title the name by which cards are searched.
     * @return the card whose title corresponds to the argument
     * null if such card is not found.
     */
    public Card search(String title);
}

