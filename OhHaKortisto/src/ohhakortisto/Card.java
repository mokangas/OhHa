/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ohhakortisto;

/**
 *
 * @author IstuvaHarka
 */
public class Card {

    String title;
    String author;
    String year;
    String isbn;
    String location;
    String keyWords;
    
    public Card(String title){
        this.title = title;
    }
    
    /**
     * 
     * @param fields book's
     * [0]: title
     * [1]: author
     * [2]: publishing year
     * [3]: ISBN
     * [4]: Physical location
     * [5]: keywords
     */
    public Card(String[] fields){
        title = fields[0];
        author = fields[1];
        year = fields[2];
        isbn = fields[3];
        location = fields[4];
        keyWords = fields[5];
    }

    /**
     * 
     * @return the title of the card.
     */
    public String getTitle() {
        return title;
    }
    
    @Override
    public String toString(){
        StringBuilder giveBack = new StringBuilder();
        giveBack.append(title).append("\n");
        giveBack.append(author).append("\n");
        giveBack.append(year).append("\n");
        giveBack.append(isbn).append("\n");
        giveBack.append(location).append("\n");
        giveBack.append(keyWords).append("\n");
        
        return giveBack.toString();
    }
    
}
