/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cardRegisterTests;

import cardregister.Card;
import cardregister.CardRegisterExceptions.AlmostSameCardExistsException;
import cardregister.CardRegisterExceptions.CardAlreadyExistsException;
import cardregister.CardRegisterExceptions.NullInputException;
import cardregister.Register;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class RegisterTest {

    Register register;
    Card card;
    String[] cardData;
    File file;

    public RegisterTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        register = new Register();
        
        // card data used continuously in the tests:
        
        cardData = new String[register.getFieldNames().length];
        for (int i = 0; i < cardData.length; i++) {
            cardData[i] = "test " + i;
        }
        
        // File for saving:
        file = new File("Test.txt");
        register.setCurrentFile(file);
        
    }

    @Test
    public void addingCard() {
        try {
            register.createCard(cardData);
        } catch (Exception ex) {
            fail();
        }
        assertEquals(1, register.getCardData().length);
    }

     @After
    public void tearDown() {
         file.delete();
    }
     
    @Test
    public void addingSameThrowsException() {
        try {
            register.createCard(cardData);
        } catch (Exception ex) {
            fail();
        }
        try {
            register.createCard(cardData);
            fail(); // If the above doesn't throw exception we've failed.
        } catch (NullInputException ex) {
            fail();
        } catch (AlmostSameCardExistsException ex) {
            // It was chosen to use only this exception, so this will do.
            // For different solutions this must be changed.
        } catch (CardAlreadyExistsException ex) {
            // This is whaat the register should normally throw.
        }
    }

    @Test
    public void addingRelativelySimilar() {
        // This is done with inside knowledge of Card's similarity conditions.
        // Must be manually changed if changes are made there.

        String[] cardData2 = cardData.clone();
        for (int i = 2; i < cardData2.length; i++) {
            cardData2[i] = cardData[i].substring(cardData2[i].length() / 2);
        }

        try {
            register.createCard(cardData);
        } catch (Exception ex) {
            fail();
        }
        try {
            register.createCard(cardData2);
            fail(); // This should throw an exception.
        } catch (NullInputException ex) {
            fail();
        } catch (AlmostSameCardExistsException ex) {
            // success!
        } catch (CardAlreadyExistsException ex) {
            fail();
        }
    }

    @Test
    public void addingEmptyCardData() {
        String[] empty = new String[register.getFieldNames().length];
        for (int i = 0; i < empty.length ; i++) {
            empty[i] = "";
        }
        
        try {
            register.createCard(empty);
            fail(); // This should throw an exception.
        } catch (Exception ex) {
            NullInputException e = new NullInputException();
            assertTrue(ex.getClass() == e.getClass());
        }
    }
    
    @Test
    public void justEmptySpacesDontCreateCard(){
        String[] emptySpaces = new String[register.getFieldNames().length];
        for (int i = 0; i < emptySpaces.length; i++) {
            emptySpaces[i] = "  ";
        }
        try {
            register.createCard(emptySpaces);
            fail();
        } catch (Exception ex) {
            NullInputException e = new NullInputException();
            assertTrue(ex.getClass() == e.getClass());
        }
    }

    @Test
    public void deletingOneOfOneWorks() {
        try {
            register.createCard(cardData);
        } catch (Exception ex) {
            fail();
        }

        String[][] del = new String[1][cardData.length];
        del[0] = cardData;
        register.deleteCards(del);
        assertEquals(0, register.getCardData().length);
    }
    
    @Test
    public void deletingOneOfManyWorks(){
        
        // This is a bit long, but easier to do all the intial work in the
        // same method.
        
        // Initialaize:
        String[][] inputData = new String[10][cardData.length];
        for (int i = 0; i < 10; i++) {
            inputData[i][0] = "input" + i; 
            for (int j = 1; j < cardData.length; j++) {
                 inputData[i][j] = cardData[j];
            }
        }
        
        for (int i = 0; i < 10; i++) {
            try {
                register.createCard(inputData[i]);
            } catch (Exception ex) {
                fail();
            }
        }
        
        // Delete two cards:
        String[][] del = new String[2][cardData.length];
        del[0] = inputData[2];
        del[1] = inputData[5];
        register.deleteCards(del);
        
        // Two cards have been deleted:
        assertEquals(8, register.getCardData().length);
        
        // The cards deleted are the right ones.
        boolean containsFirst = false;
        boolean containsSecond = false;
        
        String[][] retrievedData = register.getCardData();
        for (int i = 0; i < 8; i++) {
            if (retrievedData[i][0].equals(del[0][0])) {
                containsFirst = true;
            }
            if (retrievedData[i][0].equals(del[1][0])) {
                containsSecond = true;
            }
        }
        
        assertFalse(containsFirst);
        assertFalse(containsSecond);
    }

    @Test
    public void isntChangedWhenCreated() {
        try {
            Register register2 = new Register();
        assertFalse(register2.needsToBeSaved());
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void hasChangedWhenCardAdded(){
        try {
            register.createCard(cardData);
        } catch (Exception ex) {
            fail();
        }
        assertTrue(register.needsToBeSaved());
    }
    @Test
    public void deletingCardMarksChanged(){
        try {
            register.createCard(cardData);
        } catch (Exception ex) {
            fail();
        }
        
        String[][] del = new String[1][cardData.length];
        del[0] = cardData;
        try {
            register.save();
        } catch (IOException ex) {
            fail();
        }
        register.deleteCards(del);
        assertTrue(register.needsToBeSaved());
    }
   
   
}
