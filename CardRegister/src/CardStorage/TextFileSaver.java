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
 *
 * @author IstuvaHarka
 */
public class TextFileSaver {

    String fileName;
    FileWriter writer;
    
    public TextFileSaver(String fileName) throws IOException{
        this.fileName = fileName;
        writer = new FileWriter(fileName);
    }
    
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
