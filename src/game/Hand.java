package game;
import java.util.Collections;
import java.util.LinkedList;

public class Hand {

	private LinkedList<Card> contents = new LinkedList<Card>();

	public void add(Card c) {
		contents.add(c);
	}

	public void clear() {
		contents.clear();
	}

	public boolean contains(Card c) {
		return contents.contains(c);
	}

	public boolean contains(int val) {
		for (Card c : contents) {
			if (c.value == val)
				return true;
		}
		return false;
	}
	
	public boolean remove(Card c) {
		return contents.remove(c);
	}

	public Card remove(int value) {
		for (Card c : contents) {
			if (c.value == value) {
				contents.remove(c);
				return c;
			}
		}
		return null;
	}

	public int size() {
		return contents.size();
	}

	public void sort() {
		Collections.sort(contents);
	}

	public Card[] toArray() {
		return (contents.toArray(new Card[5]));
	}

	@Override
	public String toString() {
		return contents.toString();
	}
}
