package game;

/**
 * A class for keeping track of cards. Each card has a numerical value from 1-5
 * inclusive. Cards are sortable.
 * 
 * @author eliotkaplan
 * 
 */
public class Card implements Comparable<Card> {

	/**
	 * The numerical value of this card.
	 */
	public final int value;

	public Card(int value) {
		this.value = value;
	}

	@Override
	public int compareTo(Card arg0) {
		return ((Integer) value).compareTo(arg0.value);
	}

	@Override
	public String toString() {
		return "CARD " + value;
	}

}
