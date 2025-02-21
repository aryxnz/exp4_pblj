
import java.util.*;

class Card {
    String suit;
    String rank;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Card card = (Card) obj;
        return Objects.equals(suit, card.suit) && Objects.equals(rank, card.rank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, rank);
    }
}

class CardCollection {
    private Set<Card> cards = new HashSet<>();
    private Map<String, List<Card>> suitMap = new HashMap<>();

    public void addCard(String rank, String suit) {
        Card newCard = new Card(rank, suit);
        if (!cards.add(newCard)) {
            System.out.println("Error: Card \"" + newCard + "\" already exists.");
            return;
        }
        suitMap.putIfAbsent(suit, new ArrayList<>());
        suitMap.get(suit).add(newCard);
        System.out.println("Card added: " + newCard);
    }

    public void findCardsBySuit(String suit) {
        List<Card> suitCards = suitMap.getOrDefault(suit, new ArrayList<>());
        if (suitCards.isEmpty()) {
            System.out.println("No cards found for " + suit + ".");
        } else {
            for (Card card : suitCards) {
                System.out.println(card);
            }
        }
    }

    public void displayAllCards() {
        if (cards.isEmpty()) {
            System.out.println("No cards found.");
        } else {
            for (Card card : cards) {
                System.out.println(card);
            }
        }
    }

    public void removeCard(String rank, String suit) {
        Card cardToRemove = new Card(rank, suit);
        if (cards.remove(cardToRemove)) {
            suitMap.get(suit).remove(cardToRemove);
            System.out.println("Card removed: " + cardToRemove);
        } else {
            System.out.println("Error: Card \"" + cardToRemove + "\" not found.");
        }
    }
}

public class Exp4_2 {
    public static void main(String[] args) {
        CardCollection cc = new CardCollection();
        
        cc.displayAllCards();
        cc.addCard("Ace", "Spades");
        cc.addCard("King", "Hearts");
        cc.addCard("10", "Diamonds");
        cc.addCard("5", "Clubs");
        cc.findCardsBySuit("Hearts");
        cc.findCardsBySuit("Diamonds");
        cc.displayAllCards();
        cc.addCard("King", "Hearts");
        cc.removeCard("10", "Diamonds");
    }
}
