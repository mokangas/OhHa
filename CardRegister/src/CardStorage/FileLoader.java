
package CardStorage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This Class is used to load files into the register. The String 
 * <code>">#>#>#:::"</code> can't be present in any field, since it is used to
 * separate fields from the others. If the amount of fields in the file is not 
 * divisible by <vode>numberOfFields</code>, the last extra fields will just be
 * omitted.
 * 
 * @see FileSaver
 * @author mokangas
 */
public class FileLoader {
    
    Scanner scanner;
    File file;
    int numberOfFields;
    
    /**
     * Constructor
     * @param file The file which will be read.
     * @param numberOfFields The number of text fields in a card.
     * @throws FileNotFoundException If the file is not found.
     */
    public FileLoader(File file, int numberOfFields) throws FileNotFoundException{
        this.file = file;
        this.numberOfFields = numberOfFields;
        scanner = new Scanner(file);
        scanner.useDelimiter(">#>#>#:::");
    }

    /**
     * Reads the data from the file and generates an array of strings of it.
     * The first dimension of the array represents the card number, and the second the 
     * field number in a card. Each card is filled up before new card is begun, the
     * text breaking on each occasion of the substring ">#>#>#:::". If every field
     * of the last card isn't filled, it won't be included but that shouldn't happen 
     * when not using an outside program to generate the file.
     * 
     * @return The read data as an array of strings.
     */
    public String[][] load() {
    
        int fieldsRead = 0;
        String[] textFields = new String[numberOfFields];
        ArrayList<String[]> contentList = new ArrayList<String[]>();

        while (scanner.hasNext()) {
            textFields[fieldsRead] = scanner.next();
            fieldsRead++;
            if (fieldsRead == numberOfFields) {
                contentList.add(textFields);
                textFields = new String[numberOfFields];
                fieldsRead = 0;
            }
        }
        
        String[][] content = new String[contentList.size()][numberOfFields];
        for (int i = 0; i < contentList.size(); i++) {
            content[i] = contentList.get(i);
        }
        
        return content;
    }
}
