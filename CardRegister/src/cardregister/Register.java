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
    
    private File currentFile;
    
    /**
     * TRUE if the register has been needsToBeSaved since the last save, FALSE otherwise.
     */
    private boolean needSave;
    
    /**
     * The sole constructor.
     */
    public Register() {
        cards = new ArrayList<>();
        currentFile = null;
        needSave = false;
    }

/**
 * Getter to the cards in the register.
 * @return the content of the register.
 */
    public String[][] getCardData(){
        
        String[][] cardData = new String[cards.size()][Card.getLabels().length];
        for (int i = 0; i < cards.size(); i++) {
            cardData[i] = cards.get(i).getContent();
        }
        
        return cardData;
//       Object[][] data = new Object[cards.size()][Card.getLabels().length];
//        
//        for (int i = 0; i < cards.size(); i++) {
//            data[i] = cards.get(i).getContent();
//        }
//        return data; 
    }
    
    /**
     * Loads cards to the register from the specified file. All the cards
     * already in the register will be removed.
     * @param fileName Path and name of the file.
     * @throws FileNotFoundException If the specified file does not exist.
     */
    public void load(String fileName) throws FileNotFoundException {

        load(new File(fileName));
    }
    
    public void load(File file) throws FileNotFoundException{
        TextFileLoader source = new TextFileLoader(file);
        cards = source.load();
        this.currentFile = file;
        needSave = false;
    }

    /**
     * Saves the current register.
     * @param fileName Path and name of the file to be created.
     * @throws IOException If the writing of the file does not succeed.
     */
    public void save(String fileName) throws IOException {
        this.currentFile = new File(fileName);
        save();
    }
    
    public void save() throws IOException{
        TextFileSaver saver = new TextFileSaver(currentFile);
        saver.save(cards);
        needSave = false;
    }

    /**
     * Adds card to the register.
     * @param card The card to be added.
     */
    public void addCard(Card card) {
        cards.add(card);
        needSave = true;
    }
    
    public void createCard(String[] content){
        if (content == null) {
            return;
        }
        cards.add(new Card(content));
    }

    /**
     * Returns the name of the file to which this register was last saved.
     * @return  The name of the file to which this register was last saved.
     */
    public String getFileName() {
        return currentFile.getName();
    }

    /**
     * Tells if the register has needsToBeSaved since the last save. Register-class itself
     * guarantees this value to be true, if a card has been added to or removed from
     * it. Sorting doesn't change the register.
     * @return TRUE if the file has been needsToBeSaved, FALSE otherwise.
     */
    public boolean needsToBeSaved() {
        return needSave;
    }

    /**
     * Sorts the cards according to the alphabetical order (case ignored) 
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
        needSave = true;
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
     * 
     *  A courtesy of the UI this method gives the result of a search to 
     */
    public String[][] search(String[] search) {

        List<Card> searchList = new ArrayList<>();
        for (Card card : cards) {
            if (card.compareFields(search)) {
                searchList.add(card);
            }
        }
        if (searchList == null) {
            return null;
        }
        
        String[][] results = new String[searchList.size()][Card.getLabels().length];
        for (int i = 0; i < searchList.size() ; i++) {
            results[i] = searchList.get(i).getContent();
        }
        return results;
    }
    
    public String[] getFieldNames(){
        return Card.getLabels();
    }

    public void setChanged(boolean changed) {
        this.needSave = changed;
    }
    
    public void reset(){
        cards.clear();
        currentFile = null;
        needSave = false;
    }

    public void setCurrentFile(File file) {
        currentFile = file;
    }
}
