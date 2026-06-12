import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards = new ArrayList<Card>();
    private ArrayList<Card> otherCards = new ArrayList<Card>();
    public Deck()
    {
        ArrayList<String> cardVals = new ArrayList<String>();
        cardVals.add("A");
        cardVals.add("02");
        cardVals.add("03");
        cardVals.add("04");
        cardVals.add("05");
        cardVals.add("06");
        cardVals.add("07");
        cardVals.add("08");
        cardVals.add("09");
        cardVals.add("10");
        cardVals.add("J");
        cardVals.add("Q");
        cardVals.add("K");
        ArrayList<String> suits = new ArrayList<String>();
        suits.add("Hearts");
        suits.add("Diamonds");
        suits.add("Spades");
        suits.add("Clubs");
        for(int x = 0; x < 4; x++)
        {
            for (int i = 0; i < 13; i++)
            {

                Card c = new Card(suits.get(x), cardVals.get(i));
                cards.add(c);
                otherCards.add(c);
            }
        }
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public Card getRandomCard()
    {
        int randomVal = (int)(Math.random() * cards.size());
        Card randomCard = cards.get(randomVal);
        cards.remove(randomVal);
        return randomCard;
    }

    public Card getRandomCardNoConstraints()
    {
        int randomVal = (int)(Math.random() * otherCards.size());
        Card randomCard = otherCards.get(randomVal);
        return randomCard;
    }
}
