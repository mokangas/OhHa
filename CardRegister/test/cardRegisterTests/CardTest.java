package cardRegisterTests;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import cardregister.Card;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author IstuvaHarka
 */
public class CardTest {

    static String[] input;
    static Card testCard;

    public CardTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        input = new String[Card.NUMBER_OF_FIELDS];
        for (int i = 0; i < Card.NUMBER_OF_FIELDS; i++) {
            input[i] = "text" + i + " some more text" + (i * i);
        }
        testCard = new Card(input);
    }

    @Test
    public void createsCardWithNonEmptyFields() {
        try {
            Card card = new Card(input); // this card will be used later on too.
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void nullInputDoesntCrashConstructor() {
        try {
            Card card = new Card(null);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void oneEmptyFieldInInputDoesntCrashConstructor() {
        int emptyField = Card.NUMBER_OF_FIELDS / 2; // Sure to cause no arrayIndex exception.
        String temp = input[emptyField];
        input[emptyField] = "";
        try {
            Card card = new Card(input);
        } catch (Exception e) {
            fail();
        }
        input[emptyField] = temp;
    }

    @Test
    public void allFieldsEmptyDoesntCrashTheConstructor() {
        String[] empty = new String[Card.NUMBER_OF_FIELDS];
        try {
            Card card = new Card(empty);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void tooShortInputArrayDoesntCrashConstructor() {
        String[] shortInput = new String[Card.NUMBER_OF_FIELDS / 2];
        for (int i = 0; i < shortInput.length; i++) {
            shortInput[i] = "short" + i + "too short";
        }
        try {
            Card card = new Card(shortInput);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void tooLongInputArrayDoesntCrashConstructor() {
        String[] longInput = new String[2 * Card.NUMBER_OF_FIELDS];
        try {
            Card card = new Card(longInput);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void changeFieldChangesField() {
        testCard.changeField(0, "This field has changed");
        testCard.changeField(Card.NUMBER_OF_FIELDS - 1, "This field has changed too!");
        assertTrue(testCard.getContent()[0].equals("This field has changed"));
        assertTrue(testCard.getContent()[Card.NUMBER_OF_FIELDS - 1].equals("This field has changed too!"));
    }

    @Test
    public void compareFieldsWorksWithMatchingFields() {
        assertTrue(testCard.fieldsInclude(input));
    }

    @Test
    public void compareFieldsWorksWitTrunctatedFields() {
        for (int i = 0; i < input.length; i++) {
            int start = input[i].length() / 3;
            int end = (2 * input[i].length()) / 3;
            input[i] = input[i].substring(start, end);
        }

        assertTrue(testCard.fieldsInclude(input));
    }

    @Test
    public void compareFieldWorksWithSomeEmptyFields() {
        input[0] = "";
        assertTrue(testCard.fieldsInclude(input));
    }

    @Test
    public void compareFieldsGivesFalsesToo() {
        input[0] = input[0] + "a";
        assertFalse(testCard.fieldsInclude(input));

        String firstField = testCard.getContent()[0];
        String test = "qwertyuioopsdfghjklzxcvbnmsdlhgasdfkljahsdfkljhfsdflh";
        for (int i = 0; i < test.length(); i++) {
            if ( ! firstField.contains(test.substring(0, i))) {
                test = test.substring(0, i);
                break;
            }
        }
        input[0] = test;
        assertFalse(testCard.fieldsInclude(input));
    }

    @Test
    public void getContentWorks(){
        boolean allRight = true;
        for (int i = 0; i < input.length; i++) {
            if (! testCard.getContent()[i].equals(input[i])) {
                allRight = false;
            }
        }
        assertTrue(allRight);
    }
    
    
    
    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
