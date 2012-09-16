package cardRegisterTests;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import cardregister.Card;
import cardregister.CardComparator;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author IstuvaHarka
 */
public class CardCompartorTest {
    
    Card cardA;
    Card cardB;
            
    
    public CardCompartorTest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        String[] contentA = new String[Card.getLabels().length];
        String[] contentB = new String[Card.getLabels().length];
        contentA[0] = "A";
        contentB[0] = "b";
        contentA[Card.getLabels().length - 1] = "a";
        contentB[Card.getLabels().length - 1] = "B";
        cardA = new Card(contentA);
        cardB = new Card(contentB);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void comparisionTest(){
        CardComparator firstField = new CardComparator(0);
        assertTrue( firstField.compare(cardA, cardB) < 0);
        assertTrue( firstField.compare(cardB, cardA) > 0);
        assertEquals(0, firstField.compare(cardA, cardA));
        
        CardComparator lastField = new CardComparator(Card.getLabels().length - 1);
        assertTrue( lastField.compare(cardA, cardB) < 0);
        assertTrue( lastField.compare(cardB, cardA) > 0);
        assertEquals(0, lastField.compare(cardA, cardA));
    }
    
    
}
