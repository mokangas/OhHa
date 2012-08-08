/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface;

import java.util.Scanner;
import ohhakortisto.Card;
import storage.CardStorage;

/**
 *
 * @author IstuvaHarka
 */

/**
 * 
 * TODO: input can be case insensitive.
 *       inserting card takes something else than random text fields.
 *       save function
 *       boolean changed which tells whether the storage has been changed since
 *       the last save.
 */
public class TextInterface {

    private CardStorage storage;
    private Scanner scanner;

    /**
     * 
     * @param storage the storage for the cards which are handled.
     */
    public TextInterface(CardStorage storage) {
        this.storage = storage;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Launches the user interface.
     */
    public void launch() {
        boolean quit = false;
        while (!quit) {
            System.out.println("Options:");
            System.out.println("========");
            System.out.println("'add' for adding a card");
            System.out.println("'search' for searching a card by it's title");
            System.out.println("'quit' for ending the program");
            String choice = scanner.nextLine();
            switch (choice) {
                case "add":
                    addCard();
                    break;
                case "search":
                    serchCard();
                    break;
                case "quit":
                    quit = true;
            }
        }
    }

    /**
     * From this method a card is added to the storage.
     */
    private void addCard() {
        System.out.println("Give 6 text fields (The first one is the title, the other ones don't matter).");
        String[] input = new String[6];
        for (int i = 0; i < 6; i++) {
            input[i] = scanner.nextLine();
        }
        if (storage.add(new Card(input))) {
            System.out.println("Adding succeeded");
        } else {
            System.out.println("Adding failed.");
        }
    }

    /**
     * From this method you can search cards from the storage by their title.
     */
    private void serchCard() {
        System.out.println("Give the card title.");
        String title = scanner.nextLine();
        System.out.println(storage.search(title));
    }
}
