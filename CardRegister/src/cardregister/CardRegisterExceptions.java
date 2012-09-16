package cardregister;

/**
 * Custom exceptions that are thrown.
 *
 * @author mokangas
 */
public class CardRegisterExceptions {

    /**
     * Thrown when there already exists a card whose content is almost the same
     * as this card's. This exception can be used to warn the user of a second
     * almost the same card being created, or to prevent it altogether.
     */
    public static class AlmostSameCardExistsException extends Exception {

        String[][] existingCardsData;

        /**
         * This exception contains the data of the cards that are almost the
         * same as the one being created. They can be presented to the user to
         * make sure he wants to add the almost same card.
         *
         * @param existingCardsData Contents of the existing cards which cause
         * this exception to be thrown.
         */
        public AlmostSameCardExistsException(String[][] existingCardsData) {
            super();
            this.existingCardsData = existingCardsData;
        }

        /**
         * To retrieve the data of the existing almost same cards.
         *
         * @return
         */
        public String[][] getCardsData() {
            return existingCardsData;
        }
    }

    /**
     * This is thrown when there already is a card sufficiently similar to
     * prevent the addition of another. It is important not to have two cards
     * too similar, since the cards are found and handled by their data, not
     * their true identity.
     */
    public static class CardAlreadyExistsException extends Exception {

        String[] existingCardData;

        /**
         * The constructor is given data of <i>the</i> already existing similar
         * card.
         *
         * @param existingCardData The data of the card similar to the one being
         * added.
         */
        public CardAlreadyExistsException(String[] existingCardData) {
            this.existingCardData = existingCardData;
        }

        /**
         * Retrieves the data of the card that prevents a similar being added.
         *
         * @return The data of the already existing card similar to the one
         * being added.
         */
        public String[] getCardData() {
            return existingCardData;
        }
    }

    /**
     * Thrown when a card is tried to be created with an empty content.
     */
    public static class NullInputException extends Exception {
    }
}
