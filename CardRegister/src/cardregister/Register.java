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
import java.util.List;

/**
 *
 * @author IstuvaHarka
 */
public class Register {

    private List<Card> cards;
    String currentFile;
    
    public void setCards(List<Card> cards){
        this.cards = cards;
    }
    
    public boolean load(String fileName){
        try {
            TextFileLoader source = new TextFileLoader(fileName);
            cards = source.load();
            currentFile = fileName;
            return true;
        } catch (FileNotFoundException ex) {
            return false;
        }
    }
    
    public void save(String fileName) throws IOException{
        TextFileSaver saver = new TextFileSaver(fileName);
        saver.save(cards);
    }
    
    public void addCard(Card card){
        cards.add(card);
    }
    
    /**
     * Testaukseen ja kokeiluun.
     */
    public void printAll(){
        int i = 0;
        for (Card card : cards){
            card.print();
            System.out.println("");
            i++;
            System.out.println(i);
            System.out.println("");
        }
    }
    
     /**
     * Testaukseen ja kokeiluun.
     */
    
    public List<Card> search(String[] search){
        
        List<Card> returnList = new ArrayList<>();
        for (Card card : cards){
            if (card.compareFields(search)) {
                returnList.add(card);
            }
        }
        return returnList;
    }
}
