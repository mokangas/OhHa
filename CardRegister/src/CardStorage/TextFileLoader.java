    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CardStorage;

import cardregister.Card;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Loads a register from a text file. Each of the fields of cards are read from
 * different lines in the file with no other way to separate them. This object 
 * is used once and then disposed.
 * @author IstuvaHarka
 */
public class TextFileLoader {

    /**
     * The scanner used to read from the text file.
     */
    Scanner scanner;
    /**
     *  The path and the name of the file from which the data is read.
     */
    String fileName;
    /**
     * A File object for reading the data.
     */
    File file;

    /**
     * @param fileName the path and the name of the file to be read.
     * @throws FileNotFoundException if the file specified doesn't exist.
     */
    public TextFileLoader(String fileName) throws FileNotFoundException {
        this.fileName = fileName;
        this.file = new File(fileName);
        this.scanner = new Scanner(file);
    }
    
    /**
     * 
     * @param file The file to be read.
     * @throws FileNotFoundException if the file doesn't exist.
     */
    public TextFileLoader(File file) throws FileNotFoundException{
        this.file = file;
        fileName = file.getName();
        scanner = new Scanner(file);
    }

    /**
     * Reads the file and returns it as a register to the user.
     * @return The list of cards read from the file.
     */
    public List<Card> load() {
        ArrayList<Card> list = new ArrayList<Card>();
        while (scanner.hasNextLine()) {
            String[] textFields = new String[Card.NUMBER_OF_FIELDS];
            for (int i = 0; i < Card.NUMBER_OF_FIELDS; i++) {
                textFields[i] = scanner.nextLine();
            }
            list.add(new Card(textFields));
        }
        return list;
    }
}
