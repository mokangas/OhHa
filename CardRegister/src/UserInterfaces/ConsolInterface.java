package UserInterfaces;

import cardregister.CardRegister;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * A text interface. This operates directly through the CardRegister interface,
 * since Control is designed for a graphical interface. Also, due text based
 * user interface being relatively unneeded, this strips the user friendliness
 * away. If the user quits or loads without saving, his data is lost, and nothing
 * can be done about it.
 * 
 * @author mokangas
 */
public class ConsolInterface {

    private CardRegister register;
    private Scanner scanner;
    private String[] labels;
    private String[][] data;

    /**
     * Constructor.
     * @param register The CardRegister attached to this interface.
     */
    public ConsolInterface(CardRegister register) {
        this.register = register;
        this.scanner = new Scanner(System.in);
        this.labels = register.getFieldNames();
        run();
    }

    /**
     * Prints the help menu.
     */
    public void printHelp() {
        System.out.println("==== OHJE ====");
        System.out.println("Kirjoita:");
        System.out.println("---------");
        System.out.println("'ohje'                      -Ohjeen lukemiseksi");
        System.out.println("'lopeta'                    -lopettaksesi");
        System.out.println("-------");
        System.out.println("'lataa <tiedoston nimi>'    -Ladataksesi tiedoston");
        System.out.println("'tallenna <tiedoston nimi>' -Tallentaaksesi tiedoston");
        System.out.println("'uusi'                      -Avataksesi uuden tiedoston");
        System.out.println("----------");
        System.out.println("'lisaa'                     -Lisäteäksesi uuden kortin");
        System.out.println("'poista <korttien numerot>' -Poista valitut kortit");
        System.out.println("'muokkaa <kortin numero>'   -Muokkaa valittua korttia");
        System.out.println("'etsi <hakusana>'           -Etsii kaikista kenttsä hakusanalla");
        System.out.println("'tarkkahaku <hakusanat>'    -Tarkka haku");
        System.out.println("'jarjesta <kentan numero>'  -Jarjestaa kortit annetun kentan perusteella.");
        System.out.println("'kaikki'                    -Mäytä kaikki kortit.");
        System.out.println("=======================");
        System.out.println("Jos käskyssä pitää antaa lista numeroita tai sanoja, ne tulee erotella");
        System.out.println("kaksoispisteillä ja vain kaksoispisteillä. Korteissa ei saa esiintyä merkkijonoa '>#>#>#:::'.");
        System.out.println("Ylimääräiset argumentit jätetään vain huomioimatta. Pelkkä 'tallenna'");
        System.out.println("tallentaa tiedoston sen viimeisimmällä nimellä. Jos se ei onnistu, kokeile");
        System.out.println("'tallenna <tiedoston nimi>'.");
        System.out.println("==========================");
    }

    /**
     * Asks for the command from the user.
     * @return The command split on emptyspaces.
     */
    public String[] prompt() {
        System.out.print("> ");
        String[] input = scanner.nextLine().split(" ");
        return input;
    }

    /**
     * The loop that keeps taking command from the user until he decides to quit.
     */
    private void run() {
        printHelp();
        boolean quit = false;

        while (!quit) {
            String[] input = prompt();
            input[0] = input[0].trim().toLowerCase();
            if (input == null || input.length == 0) {
                continue;
            }

            switch (input[0]) {
                case "ohje":
                    printHelp();
                    break;
                case "lopeta":
                    quit = true;
                    break;
                case "lataa":
                    load(input);
                    break;
                case "tallenna":
                    save(input);
                    break;
                case "uusi":
                    newFile();
                    break;
                case "lisaa":
                    createCard();
                    break;
                case "poista":
                    delete(input);
                    break;
                case "muokkaa":
                    edit(input);
                    break;
                case "etsi":
                    quickSearch(input);
                    break;
                case "tarkkahaku":
                    search();
                    break;
                case "jarjesta":
                    orderData(input);
                    break;
                case "kaikki":
                    showAll();
                    break;
                default:
                    System.out.println("Syntaksivirhe");
                    break;
            }
        }

    }

    /**
     * Loads a fie to the register.
     * @param input The file's name which is being loaded.
     */
    private void load(String[] input) {
        if (input.length == 1) {
            System.out.println("Anna tiedoston nimi.");
            return;
        }
        try {
            register.load(new File(input[1]));
            data = register.getCardData();
            System.out.println("Lataus onnistui.");
        } catch (FileNotFoundException ex) {
            System.out.println("Virhe latauksessa tai syntaksissa.");
        }
    }

    /**
     * Saves the current file, or alternatively with name specified. If the input
     * consists of just one string, this tries to save to the current file. If
     * input's length is at least 2, that is, it contains a file name, this
     * tries to save into file whose name will be <code>input[1]</code>.
     * @param input The original input string[] that launched this process.
     */
    private void save(String[] input) {
        if (input.length == 1) {
            try {
                register.save();
                System.out.println("Tallennus onnistui.");
                return;
            } catch (IOException ex) {
                System.out.println("Syntaksivirhe, tallennusvirhe, tai tiedostolla ei ole nimeä");
                System.out.println("Kokeile 'tallenna <tiedostonNimi'.");
            }
        }

        try {
            register.setCurrentFile(new File(input[1]));
            register.save();
            System.out.println("Tallennus onnistui.");
        } catch (Exception e) {
            System.out.println("Syntaksi virhe, tallennusvirhe tai epäkelpo tiedoston nimi.");
        }

    }

    /**
     * Ampties the register.
     */
    private void newFile() {
        register.reset();
        data = register.getCardData();
    }

    /**
     * Creates a new card.
     */
    private void createCard() {
        String[] content = askForCardContent(purposeOfAsking.NEWCARD, null);
        try {
            register.createCard(content);
            System.out.println("Kortti lisätty onnistuneesti.");
        } catch (Exception ex) {
            System.out.println("Kortin sisältö epäkelpo. Korttia ei lisätty.");
        }
    }

    /**
     * Asks for content for creating, editing or finding card. The purpose is
     * specified in the parameters, and it influences on how this communicates
     * with the user. When editing a card, itäs old data must be supplied. Other
     * purposes will do with null oldData.
     * @param purpose The purpose of this data intake.
     * @param oldData The data of a card, if one is being edited.
     * @return The content or new content of a card, or search words.
     * @see purposeOfAsking
     */
    private String[] askForCardContent(purposeOfAsking purpose, String[] oldData) {

        String introductionText = "";
        boolean oldDataPrinted = false;

        switch (purpose) {
            case NEWCARD:
                introductionText = "Täytä kortin kentät.";
                break;
            case EDITCARD:
                introductionText = "Kirjoita kortin uudet tiedot. Jos kenttä ei"
                        + "muutu, ENTER riittää.";
                oldDataPrinted = true;
                break;
            case SEARCH:
                introductionText = "Kirjoita hakusanat. Tyhjät kentät eivät "
                        + "vaikuta hakuun.";
                break;
        }

        String[] input = new String[labels.length];
        System.out.println(introductionText);
        for (int i = 0; i < input.length; i++) {
            System.out.print(labels[i] + ": ");
            if (oldDataPrinted) {
                System.out.println(oldData[i]);
                System.out.print("Uusi sisältö: ");
            }
            input[i] = scanner.nextLine();
        }

        return input;
    }

    /**
     * Deletes cards. The deleted cards will be selected by their numbers on the
     * current data array. They should be specified in <code>input[1]</code>, 
     * separated from each other by colons, and nothing more than colons.
     * @param input The users original input, containing the cards to be deleted
     * in <code>input[1]</code>.
     */
    private void delete(String[] input) {
        if (input.length == 1) {
            System.out.println("Syntaksivirhe. Anna tuhottavien korttien numerot.");
        }
        try {
            // First we must obtain the number of cards to be deleted:
            String[] cardNumbersAsStrings = input[1].split(":");
            int[] cardNumbers = new int[cardNumbersAsStrings.length];
            for (int i = 0; i < cardNumbers.length; i++) {
                cardNumbers[i] = Integer.parseInt(cardNumbersAsStrings[i]);
            }

            // Then we need to create a table of data of the cards to be deleted:
            String[][] deleteData = new String[cardNumbers.length][labels.length];
            for (int i = 0; i < deleteData.length; i++) {
                deleteData[i] = data[cardNumbers[i]];
            }

            // And finally to delete the cards:
            register.deleteCards(deleteData);

        } catch (Exception e) {
            System.out.println("Syntaksivirhe.");
        }
    }

    /**
     * Searches any field of the cards in the register. The search word is the 
     * original input sans the command word for the search: It is formed from
     * <code>input[1],..,input[input.length]</code> by concatenating and inserting
     * emptyspaces in between. If the search result is nonempty, it will be
     * placed as the data of this UI.
     * @param input the original user command.
     */
    private void quickSearch(String[] input) {
        if (input.length == 1) {
            System.out.println("Kirjoita hakusana.");
            return;
        }

        // The search word might have some empty spaces:
        String searchWord = "";
        for (int i = 1; i < input.length; i++) {
            searchWord = searchWord + input[i] + " ";
        }

        String[][] searchData = register.searchAnyField(searchWord);
        if (searchData == null || searchData.length == 0) {
            System.out.println("Haku ei tuottanut tuloksia.");
        } else {
            data = searchData;
            printData();
        }
    }

    /**
     * Prints data of all the cards in the register.
     */
    private void showAll() {
        data = register.getCardData();
        printData();
    }

    /**
     * Prints data currently handled: search data or the data of all the cards
     * in the register.
     */
    private void printData() {
        if (data == null || data.length == 0) {
            System.out.println("Ei näytettäviä kortteja.");
            return;
        }

        for (int i = 0; i < data.length; i++) {
            System.out.println("--------------");
            System.out.println("Kortti # " + i);
            System.out.println("--------------");
            for (int j = 0; j < labels.length; j++) {
                System.out.println(labels[j] + ": " + data[i][j]);
            }
        }
        System.out.println("===========================================");
    }

    /**
     * Edits card. The card number should be given in <code>input[1]</code>.
     * @param input the users original command.
     */
    private void edit(String[] input) {
        try {
            int editedCard = Integer.parseInt(input[1]);
            String[] oldData = data[editedCard];
            String[] newData = askForCardContent(purposeOfAsking.EDITCARD, oldData);
            register.editCard(oldData, newData);
        } catch (Exception e) {
            System.out.println("Syntaksivirhe tai väärä kortin valinta.");
        }
    }

    /**
     * The detailed search. Asks user the search words and if the result is nonempty,
     * places it as the data of this UI.
     */
    private void search() {
        String[] searchWords = askForCardContent(purposeOfAsking.SEARCH, null);
        String[][] searchData = register.search(searchWords);
        if (searchData == null || searchData.length == 0) {
            System.out.println("Haku ei tuottanut tuloksia.");
            return;
        } else {
            data = searchData;
            printData();
        }
    }

    /**
     * Orders the data by the give field number. The number is parsed from
     * <code>input[1]</code>. This method only does the initial work and leaves all the rest
     * to <code>orderBy</code>.
     * @param input The original user command.
     * @see orderBy
     */
    private void orderData(String[] input) {
        if (input.length == 1) {
            System.out.println("Anna kenttä, jonka mukaan järjestetään.");
            return;
        }
        try {
            int orderingField = Integer.parseInt(input[1]);
            if (orderingField < 0 || orderingField >= labels.length) {
                System.out.println("Annettau kentännumeroa ei ole olemassa.");
                return;
            } else {
                orderBy(orderingField);
                printData();
            }
        } catch (Exception e) {
            System.out.println("Syntaksivirhe");
        }
    }

    /**
     * Orders the card data by the given field number. The data is ordered
     * alphabetically and case insensitively on the field content <code>data[i][field]</code>.
     * @param field The field by which the cards will be ordered.
     */
    private void orderBy(int field) {
        if (data == null || data.length == 0) {
            return;
        }
       
        ArrayList<WordMap> words = new ArrayList<WordMap>();
        for (int i = 0; i < data.length; i++) {
            words.add(new WordMap(data[i][field], i));
        }
        
        String[][] sorted = new String[data.length][labels.length];
        Collections.sort(words);
        for (int i = 0; i < words.size(); i++) {
            sorted[i] = data[words.get(i).mappedTo];
        }
        data = sorted;
    }

    /**
     * Enum variables for the method <code>askForContent</code>.
     */
    private enum purposeOfAsking {

        NEWCARD, EDITCARD, SEARCH
    }
    
    /**
     * Custom class for ordering the data. Each WordMap-object
     * contains a word and a corresponding number, which should
     * point to array <code>data</code> so that 
     * <code>data[number][constantNumber] = word</code>.
     * @see orderBy
     */
    private class WordMap implements Comparable<Object> {
        public String word;
        public int mappedTo;

        /**
         * Constructor takes the word from the field and the ordinal number
         * of the card data in the data table.
         * @param word The word mapped.
         * @param mappedTo The integer mapped to.
         */
        public WordMap(String word, int mappedTo) {
            this.word = word;
            this.mappedTo = mappedTo;
        }

        /**
         * Compares these objects by <code>word</code> case insensitive.
         * @param o The object compared to.
         * @return A negative number if this is smaller than o, a positive if
         * this is bigger than o, and 0 if they are equal.
         */
        @Override
        public int compareTo(Object o) {
            WordMap comparedTo = (WordMap) o;
            return this.word.compareToIgnoreCase(comparedTo.word);
        }
    }
}