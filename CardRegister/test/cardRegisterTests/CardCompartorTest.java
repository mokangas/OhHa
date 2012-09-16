//package cardRegisterTests;
//
///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//
//import cardregister.Card;
//import cardregister.CardComparator;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
///**
// *
// * @author IstuvaHarka
// */
//public class CardCompartorTest {
//    
//    Card cardA;
//    Card cardB;
//            
//    
//    public CardCompartorTest() {
//        
//    }
//    
//    @BeforeClass
//    public static void setUpClass() {
//    }
//    
//    @AfterClass
//    public static void tearDownClass() {
//    }
//    
//    @Before
//    public void setUp() {
//        String[] contentA = new String[Card.NUMBER_OF_FIELDS];
//        String[] contentB = new String[Card.NUMBER_OF_FIELDS];
//        contentA[0] = "A";
//        contentB[0] = "b";
//        contentA[Card.NUMBER_OF_FIELDS - 1] = "a";
//        contentB[Card.NUMBER_OF_FIELDS - 1] = "B";
//        cardA = new Card(contentA);
//        cardB = new Card(contentB);
//    }
//    
//    @Test
//    public void comparisionTest(){
//        CardComparator firstField = new CardComparator(0);
//        assertTrue( firstField.compare(cardA, cardB) < 0);
//        assertTrue( firstField.compare(cardB, cardA) > 0);
//        assertEquals(0, firstField.compare(cardA, cardA));
//        
//        CardComparator lastField = new CardComparator(Card.NUMBER_OF_FIELDS - 1);
//        assertTrue( lastField.compare(cardA, cardB) < 0);
//        assertTrue( lastField.compare(cardB, cardA) > 0);
//        assertEquals(0, lastField.compare(cardA, cardA));
//    }
//    
//    @After
//    public void tearDown() {
//    }
//    // TODO add test methods here.
//    // The methods must be annotated with annotation @Test. For example:
//    //
//    // @Test
//    // public void hello() {}
//}
