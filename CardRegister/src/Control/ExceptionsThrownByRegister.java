/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

/**
 *
 * @author IstuvaHarka
 */
public class ExceptionsThrownByRegister {

    public static class NullInputException extends Exception{    
    }
    
    public static class AlmostSameCardExistsException extends Exception{
        
        String[][] existingCardsData;
        
        public AlmostSameCardExistsException(String[][] existingCardsData){
            super();
            this.existingCardsData = existingCardsData;
        }
        
        public String[][] getCardsData(){
            return existingCardsData;
        }
    }
}
