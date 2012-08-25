/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterfaces;

import cardregister.Card;
import cardregister.Register;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author IstuvaHarka
 */
public class TextInterface {

    /**
     * Tells how many cards are displayed on the screen at time.
     */
    private static final int NO_OF_CARDS_ON_PAGE = 5;
    /**
     * The reader of user input.
     */
    private Scanner scanner;
    /**
     * The card register which is being used.
     */
    private Register register;
    /**
     * User's selection of card (which he is going to delete or alter).
     */
    private List<Card> selected;

    /**
     * Sole constructor.
     */
    public TextInterface() {
        this.scanner = new Scanner(System.in);
        this.selected = new ArrayList<>();
    }

    /**
     * Prints the main menu.
     */
    private void printMenu() {
        System.out.println("==========================================");
        System.out.println("Kirjoita:");
        System.out.println(" 'U' - luo uusi kortisto");
        System.out.println(" 'L' - lataa olemassaoleva kortisto");
        System.out.println(" 'T' - tallenna kortisto tekstitiedostoksi");
        System.out.println(" -----------------------------------------");
        System.out.println(" 'K' - katsele luetteloa korteista");
        System.out.println(" 'A' - lisää kortti kortistoon");
        System.out.println(" 'D' - tuhoa valitut kortti");
        System.out.println(" 'M' - muuta valitun kortin tietoja");
        System.out.println(" -----------------------------------------");
        System.out.println(" 'Q' - lopeta.");
        System.out.println("==========================================");
    }

    /**
     * Launches the interface. It will print the main menu and ask him what to
     * do until the user chooses "quit".
     */
    public void run() {
        boolean stop = false;
        while (!stop) {
            printMenu();
            System.out.print("> ");
            switch (input()) {
                case "u":
                    createRegister();
                    break;
                case "l":
                    loadRegister();
                    break;
                case "t":
                    saveRegister();
                    break;
                case "k":
                    browseRegister();
                    break;
                case "a":
                    addCard();
                    break;
                case "d":
                    deleteCards();
                    break;
                case "m":
                    editCard();
                    break;
                case "q":
                    stop = true;
                    break;
                default:
                    System.out.println("\nVirheellinen valinta!");
            }
        }
    }

    /**
     * Takes the user input.
     *
     * @return the input uncapitalized and trimmed.
     */
    private String input() {
        return scanner.nextLine().trim().toLowerCase();
    }

    /**
     * Creates a new card register. If the old one has been changed since the
     * last save, this method asks user first if he wants to save it.
     */
    private void createRegister() {
        if (register != null && register.hasChanged()) {
            saveFirst();
        }
        register = new Register();
        System.out.println("Uusi rekisteri luotu.");
    }

    /**
     * Loads an existing card register. If the old one has been changed since
     * the last save, this method asks user first if he wants to save it.
     */
    private void loadRegister() {
        if (register != null && register.hasChanged()) {
            saveFirst();
        }
        System.out.println("Anna avattavan tiedoston polku ja nimi:\n> ");
        String fileName = input();
        try {
            register = new Register();
            register.load(fileName);
            System.out.println("Koristo ladattu onnistuneesti.");
        } catch (FileNotFoundException ex) {
            System.out.println("Lataus epäonnistui. Tarkista tiedoston nimi.");
        }
    }

    /**
     * Method for asking the user whether he wants to save the current file
     * before it being closed.
     */
    private void saveFirst() {
        boolean stop = false;
        while (!stop) {
            System.out.println("Aukiolevaa tiedostoa on muutettu, tallenna muutokset? (K/E)\n> ");
            switch (input()) {
                case "k":
                    saveRegister();
                    stop = true;
                    break;
                case "e":
                    return;
                default:
                    System.out.println("Virheellinen syöte!\n");
            }
        }
    }

    private void saveRegister() {
        if (register == null) {
            System.out.println("Ei avointa tiedostoa.");
            return;
        }
        String file = register.getFileName();
        System.out.println("Tämänhetkinen tiedoston nimi on \"" + file + "\". Anna"
                + "uusi nimi tai paina ENTER tallentaaksesi vanhalla.\n>");
        try {
            String newName = input();
            switch (newName) {
                case "":
                    register.save(file);
                    return;
                default:
                    register.save(newName);
            }
        } catch (IOException ex) {
            System.out.println("Jokin meni vikaan. Tarkista tiedoston nimi.");
        }
    }

    private void browseRegister() {
        if (register == null) {
            System.out.println("Ei avointa kortistoa.\n");
        }
        viewList(register.getCards());
    }

    /**
     * Shows user a list of cards (the whole register or part of it), and allows
     * him to select some of them.
     *
     * @param cards
     */
    private void viewList(List<Card> cards) {
        if (cards == null) {
            return;
        }
        int pages = cards.size() / NO_OF_CARDS_ON_PAGE;
        int onLastPage = cards.size() % NO_OF_CARDS_ON_PAGE;
        // If onLastPage == 0, the "last page" won't be viewed.
        for (int page = 0; page <=  pages; page++) {
            int print = (page == pages ) ? onLastPage : NO_OF_CARDS_ON_PAGE;
            for (int itemOnPage = 0; itemOnPage < print; itemOnPage++) {
                int cardNumber = itemOnPage + page * NO_OF_CARDS_ON_PAGE;
                System.out.println("------");
                // Card numbering shown to the user starts from the index 1.
                System.out.println("Kortti #" + (cardNumber + 1));
                System.out.println("------");
                cards.get(cardNumber).print();
            }
            System.out.println("-----------------");
            selectFromList(cards, page * NO_OF_CARDS_ON_PAGE , (page + 1) * NO_OF_CARDS_ON_PAGE);
        }
    }

    /**
     * Asks the user the fields of the card to be added and then adds it to the
     * current register.
     */
    private void addCard() {
        if (register == null) {
            System.out.println("Avaa ensin kortisto.");
            return;
        }

        System.out.println("Anna uuden kortin tiedot");
        System.out.println("------------------------");
        String[] fields = new String[Card.NUMBER_OF_FIELDS];
        for (int i = 0; i < fields.length; i++) {
            System.out.print(Card.getLabels()[i] + ": ");
            fields[i] = input();
        }
        register.addCard(new Card(fields));
    }

    /**
     * Prints cards in the current selection and asks the user which ones to
     * remove from the selection.
     */
    private void unselectCards() {

        System.out.println("Valitut kortit: ");


        System.out.println("Paina ENTER palataksesi takaisin, tai kirjoita kortin numero "
                + "valitaksesi sen ja paina sitten ENTER. Valitse kaikki kirjoittamalla 'A'.");

    }

    /**
     * Here card numbers in the cards-list is different from the user input, since
     * in display the indices start from 1.
     * @param cards
     * @param start
     * @param end 
     */
    private void selectFromList(List<Card> cards, int start, int end) {
        System.out.println("'v#' -valitse kortti numero #. 'p' - poista kortin nro # valinta.");
        System.out.println("'k' -valitse kaikki sivulta. 'r' - poista kaikki valinant. ENTER - jatka.");
        while (true) {
            String choice = input();
            if (choice.equals("")) {
                return;
            }
            
            switch (choice.charAt(0)){
                case 'k' : for (int i = start; i < end ; i++) {
                        selectCard(cards, i);
                    }
                    break;
                case 'r' : for (int i = start; i < end; i++) {
                        selected.remove(cards.get(i));
                    }
                case 'v' : try {
                    int cardNumber = Integer.parseInt(choice.substring(1));
                    if (0 < cardNumber && cardNumber <= cards.size() ) {
                        selectCard(cards, cardNumber - 1);
                        continue;
                    } 
                } catch (Exception e){
                    System.out.println("Virheellinen valinta.");
                }
                    break;
                case 'p':  try {
                    int cardNumber = Integer.parseInt(choice.substring(1));
                    if (0 < cardNumber && cardNumber <= cards.size() ) {
                        selected.remove(cards.get(cardNumber - 1));
                        continue;
                    } 
                } catch (Exception e){
                    System.out.println("Virheellinen valinta.");
                }
                    break;
                default : System.out.println("Virheellinen valinta.");
            }
            
        }
    }

    private void selectCard(List<Card> cards, int index) {
        if (!selected.contains(cards.get(index))) {
            selected.add(cards.get(index));
        }
    }

    private void deleteCards() {
        for (Card card : selected){
            register.delete(card);
        }
        selected.clear();
        System.out.println("Valitut kortit tuhottu.");
    }

    private void editCard() {
        if (selected.size() == 0) {
            System.out.println("Valitse ensin kortti.");
            return;
        }
        if (selected.size() > 1) {
            System.out.println("Vain yhtä korttia kerralla voi muokata.");
            return;
        }
        System.out.println("----------------------");
        Card card = selected.get(0);
        String[] content = card.getContent();
        for (int i = 1; i < card.NUMBER_OF_FIELDS; i++) { // The numbers visible to user are index+1
            System.out.println(i+". " +Card.getLabels()[i] + ": "+content[i]);
        }
        System.out.println("-----------------------");
        System.out.println("Syötä uudet kentät muodossa '1Uusi sisältö' tai paina ENTER, kun haluat lopettaa.");
        String choice = input();
        
        while ( ! choice.equals("")){
            try {
                int fieldNumber = Integer.parseInt(""+choice.charAt(0)) - 1; // fieldNumber is the actualargument of content.
                if (0 <= fieldNumber && fieldNumber < content.length) {
                    content[fieldNumber] = choice.substring(1); //TODO testaa syöte "1
                } else {
                    System.out.println("Kentän numero valittu väärin.");
                } 
            } catch (Exception e) {
                System.out.println("Virheellinen syöte.");
            }
            choice = input();
        }
        
    }
}
