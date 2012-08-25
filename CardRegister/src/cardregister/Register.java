/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cardregister;

import CardStorage.TextFileLoader;
import CardStorage.TextFileSaver;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author IstuvaHarka
 */
public class Register {

    private List<Card> cards;
    private String fileName;
    private boolean changed;
    
    
    public Register() {
        cards = new ArrayList<>();
        fileName = "";
        changed = false;
    }


    public List<Card> getCards(){
        return cards;
    }
    
    public void load(String fileName) throws FileNotFoundException {

        TextFileLoader source = new TextFileLoader(fileName);
        cards = source.load();
        this.fileName = fileName;
        changed = false;
    }

    public void save(String fileName) throws IOException {
        TextFileSaver saver = new TextFileSaver(fileName);
        saver.save(cards);
        changed = false;
    }

    public void addCard(Card card) {
        cards.add(card);
        changed = true;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean hasChanged() {
        return changed;
    }

    public void sortBy(int field) {
        CardComparator comparator = new CardComparator(field);
        Collections.sort(cards, comparator);
    }

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
}
