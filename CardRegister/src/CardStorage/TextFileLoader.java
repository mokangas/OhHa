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
 *
 * @author IstuvaHarka
 */
public class TextFileLoader {

    Scanner scanner;
    String fileName;
    File file;

    public TextFileLoader(String fileName) throws FileNotFoundException {
        this.fileName = fileName;
        this.file = new File(fileName);
        this.scanner = new Scanner(file);
    }

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
