/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cardregister;

import CardStorage.TextFileLoader;
import CardStorage.TextFileSaver;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The register of cards currently in use. Provides the basic functionality.
 * @author IstuvaHarka
 */
public class Register {

    /**
     * The cards in the register.
     */
    private List<Card> cards;
    /**
     * The file name the register was last read from or saved to.
     */
    private String fileName;
    /**
     * TRUE if the register has been changed since the last save, FALSE otherwise.
     */
    private boolean changed;
    
    /**
     * The sole constructor.
     */
    public Register() {
        cards = new ArrayList<>();
        fileName = "";
        changed = false;
    }

/**
 * Getter to the cards in the register.
 * @return the content of the register.
 */
    public List<Card> getCards(){
        return cards;
    }
    
    /**
     * Loads cards to the register from the specified file. All the cards
     * already in the register will be removed.
     * @param fileName Path and name of the file.
     * @throws FileNotFoundException If the specified file does not exist.
     */
    public void load(String fileName) throws FileNotFoundException {

        TextFileLoader source = new TextFileLoader(fileName);
        cards = source.load();
        this.fileName = fileName;
        changed = false;
    }
    
    public void load(File file) throws FileNotFoundException{
        TextFileLoader source = new TextFileLoader(file);
        cards = source.load();
        this.fileName = file.getName();
        changed = false;
    }

    /**
     * Saves the current register.
     * @param fileName Path and name of the file to be created.
     * @throws IOException If the writing of the file does not succeed.
     */
    public void save(String fileName) throws IOException {
        TextFileSaver saver = new TextFileSaver(fileName);
        saver.save(cards);
        changed = false;
        this.fileName = fileName;
    }

    /**
     * Adds card to the register.
     * @param card The card to be added.
     */
    public void addCard(Card card) {
        cards.add(card);
        changed = true;
    }

    /**
     * Returns the name of the file to which this register was last saved.
     * @return  The name of the file to which this register was last saved.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Tells if the register has changed since the last save. Register-class itself
     * guarantees this value to be true, if a card has been added to or removed from
     * it. Sorting doesn't change the register.
     * @return TRUE if the file has been changed, FALSE otherwise.
     */
    public boolean hasChanged() {
        return changed;
    }

    /**
     * Sorts the cards according to thealphabetical order (case ignored) 
     * of the field specified by the argument. It's wise to use one of this
     * classes static variables as a parameter.
     * @param field The index of the field by which the register is sorted.
     */
    public void sortBy(int field) {
        CardComparator comparator = new CardComparator(field);
        Collections.sort(cards, comparator);
    }

    /**
     * Removes the specified card from the register.
     * @param card The card to be removed.
     */
    public void delete(Card card) {
        cards.remove(card);
        changed = true;
    }
    

    /**
     * Testaukseen ja kokeiluun.
     */
    public void printAll() {
        int i = 1;
        for (Card card : cards) {
            System.out.println("" + i + ":");
            card.print();
            System.out.println("");
            i++;
        }
    }

    /**
     * Testaukseen ja kokeiluun.
     */
    public List<Card> search(String[] search) {

        List<Card> returnList = new ArrayList<>();
        for (Card card : cards) {
            if (card.compareFields(search)) {
                returnList.add(card);
            }
        }
        return returnList;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }
}
