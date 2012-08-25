/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CardStorage;

import cardregister.Card;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Saves a register as a text file. The format is simple text where different
 * text fields are on separate lines. (That's why no text field of a card can 
 * include "\n"). 
 * @author IstuvaHarka
 */
public class TextFileSaver {

    /**
     * The path and the name of the file to be created.
     */
    String fileName;
    
    /**
     * A device to write file on hard disk.
     */
    FileWriter writer;
    
    /**
     *  Sole constructor.
     * @param fileName the path and name of the file to be created.
     * @throws IOException If the save isn't successful.
     */
    public TextFileSaver(String fileName) throws IOException{
        this.fileName = fileName;
        writer = new FileWriter(fileName);
    }
    
    /**
     * Saves the file.
     * @param list List of cards to be written on the file.
     * @throws IOException If the save doesn't succeed.
     */
    public void save(List<Card> list) throws IOException{
        writer.flush();
        for(Card card : list){
            String[] content = card.getContent();
            for (int i = 0; i < content.length; i++) {
                writer.append(content[i]);
                writer.append("\n");
            }
        }
        writer.close();
    }
}
