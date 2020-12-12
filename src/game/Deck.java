package game;

import java.util.LinkedList;
import java.util.Random;

/**
 * A deck of cards. Contains 25 cards, 5 each of the values 1-5.
 * 
 * @author eliotkaplan
 * 
 */
public class Deck {

	private LinkedList<Card> cards = new LinkedList<Card>();

	/**
	 * Creates a new deck in random order.
	 */
	public Deck() {
		resetAndShuffle();
	}

	/**
	 * Fills up a hand with cards up to the hand limit of 5.
	 * 
	 * @param h
	 *            The hand to be refilled.
	 */
	public void deal(Hand h) {
		while (h.size() < 5) {
			h.add(dealOne());
		}
		h.sort();
	}

	/**
	 * Deals out one card, removing it from the deck and returning it.
	 * 
	 * @return A card.
	 */
	public Card dealOne() {
		return cards.pop();
	}

	/**
	 * Resets the deck to a complete deck of 25 cards.
	 */
	public void resetAndShuffle() {
		cards.clear();
		for (int i = 1; i <= 5; i++) {
			for (int j = 0; j < 5; j++) {
				cards.add(new Card(i));
			}
		}
		Random r = new Random();
		for (int i = 0; i < cards.size(); i++) {
			int randomIndex = r.nextInt(cards.size());
			Card oldCard = cards.get(i);
			cards.set(i, cards.get(randomIndex));
			cards.set(randomIndex, oldCard);
		}
	}
	
	public int size() {
		return cards.size();
	}

}
