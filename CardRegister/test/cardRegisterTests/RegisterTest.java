/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cardRegisterTests;

import cardregister.Card;
import cardregister.Register;
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
        String[] input = new String[Card.NUMBER_OF_FIELDS];
        for (int i = 0; i < input.length; i++) {
            input[i] = "test " + i;
        }
    }

    @Test
    public void addingCard() {
        register.addCard(card);
        assertEquals(1, register.getCards().size());
    }

    @Test
    public void deletingWorks() {
        register.addCard(card);
        register.delete(card);
        assertEquals(0, register.getCards().size());
    }

    @Test
    public void isntChangedWhenCreated() {
        assertFalse(register.hasChanged());
    }
    
    @Test
    public void hasChangedWhenCardAdded(){
        register.addCard(card);
        assertTrue(register.hasChanged());
    }
    
    @Test
    public void deletingCardMarksChanged(){
        register.addCard(card);
        register.setChanged(false);
        register.delete(card);
        assertTrue(register.hasChanged());
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
